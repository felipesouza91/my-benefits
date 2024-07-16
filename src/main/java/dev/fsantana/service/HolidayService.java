package dev.fsantana.service;

import java.text.DateFormat;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.htmlunit.FailingHttpStatusCodeException;
import org.htmlunit.WebClient;
import org.htmlunit.html.DomNode;
import org.htmlunit.html.DomNodeList;
import org.htmlunit.html.HtmlPage;
import org.htmlunit.html.HtmlUnorderedList;

import dev.fsantana.domain.model.Holiday;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class HolidayService {

  public void loadHoladys(String city, String state, Integer year) {

    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    try (final WebClient webClient = new WebClient()) {
      if (year == null) {
        year = LocalDateTime.now().getYear();
      }
      String cityUrlFormated = Normalizer.normalize(city, Normalizer.Form.NFKD)
          .replaceAll("\\p{M}", "").replace(' ', '_');
      webClient.getOptions().setJavaScriptEnabled(false);
      webClient.getOptions().setPrintContentOnFailingStatusCode(false);
      String url = String.format("https://www.feriados.com.br/feriados-%s-%s.php?ano=%s", cityUrlFormated, state,
          String.valueOf(year));

      System.out.println(url);
      final HtmlPage page = webClient.getPage(url);
      DomNodeList<DomNode> querySelectorAll = page.querySelectorAll("ul.multi-column");
      if (querySelectorAll.isEmpty()) {
        throw new RuntimeException("Not found list with holidays");
      }
      HtmlUnorderedList list = (HtmlUnorderedList) querySelectorAll.get(0);
      List<Holiday> holidays = new ArrayList<>();
      for (DomNode node : list.getChildNodes()) {
        Holiday holiday = new Holiday();
        for (DomNode divNode : node.getChildNodes()) {
          String holidayType = divNode.getAttributes().getNamedItem("title").getNodeValue().strip().substring(3,
              divNode.getAttributes().getNamedItem("title").getNodeValue().strip().length() - 4);
          holiday.setType(holidayType.strip());
          for (DomNode spaDomNode : divNode.getChildNodes()) {
            String nodeValue = spaDomNode.asNormalizedText();
            String date = nodeValue.substring(0, nodeValue.indexOf("-"));
            String holidayName = nodeValue.substring(nodeValue.indexOf("-") + 1, nodeValue.length()).strip();
            holiday.setName(holidayName);
            holiday.setDate(dateFormat.parse(date));
          }
        }
        holidays.add(holiday);
      }

      holidays.stream().forEach(System.out::println);

    } catch (FailingHttpStatusCodeException httpError) {
      System.out.println(httpError.getMessage());

    } catch (Exception e) {
      System.out.println("Generic error");

    }
  }

}
