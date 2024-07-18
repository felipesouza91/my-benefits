package dev.fsantana.service.intergrations.impl;

import java.text.Normalizer;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.htmlunit.FailingHttpStatusCodeException;
import org.htmlunit.WebClient;
import org.htmlunit.html.DomNode;
import org.htmlunit.html.DomNodeList;
import org.htmlunit.html.HtmlPage;
import org.htmlunit.html.HtmlUnorderedList;

import dev.fsantana.domain.model.Holiday;
import dev.fsantana.service.intergrations.FindHolidaysFromApi;
import io.quarkus.logging.Log;

class FindHolidaysFromCalendariosSite implements FindHolidaysFromApi {

  @Override
  public List<Holiday> findHolidays(String city, String state, Integer year) {
    try (final WebClient webClient = new WebClient()) {
      DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");

      if (year == null) {
        year = LocalDateTime.now().getYear();
      }

      String cityUrlFormated = Normalizer.normalize(city, Normalizer.Form.NFKD)
          .replaceAll("\\p{M}", "").replace(' ', '_');
      webClient.getOptions().setJavaScriptEnabled(false);
      webClient.getOptions().setPrintContentOnFailingStatusCode(false);
      String url = String.format("https://www.feriados.com.br/feriados-%s-%s.php?ano=%s", cityUrlFormated, state,
          String.valueOf(year));

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
            String date = nodeValue.substring(0, nodeValue.indexOf("-")).strip();
            String holidayName = nodeValue.substring(nodeValue.indexOf("-") + 1, nodeValue.length()).strip();
            holiday.setName(holidayName);
            holiday.setDate(LocalDate.parse(date, dateFormat));
          }
        }
        holidays.add(holiday);
      }

      return holidays;

    } catch (FailingHttpStatusCodeException httpError) {
      System.out.println(httpError.getMessage());
      throw httpError;
    } catch (Exception e) {
      Log.error(e);
      throw new RuntimeException(e.getMessage());
    }
  }

}
