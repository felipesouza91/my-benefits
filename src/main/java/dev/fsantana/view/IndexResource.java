package dev.fsantana.view;

import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.reactive.RestHeader;
import org.jboss.resteasy.reactive.RestQuery;

import dev.fsantana.domain.model.State;
import dev.fsantana.service.FindStateService;
import dev.fsantana.service.HolidayService;
import dev.fsantana.view.dto.StateDTO;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/")
public class IndexResource {

    @Inject
    Template index;

    @RestClient
    FindStateService findStateService;

    @Inject
    HolidayService holidayService;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance get(@RestQuery String name, @RestHeader("Accept-Language") String locales) {

        List<State> allStates = findStateService.getAllStates();
        System.out.println(allStates.size());

        List<StateDTO> states = allStates.stream().map(state -> new StateDTO(state.sigla, state.nome))
                .collect(Collectors.toList());

        List<DayOfWeek> daysOfTheWeek = new ArrayList<>();

        String language = locales.substring(0, locales.indexOf(","));
        System.out.println(locales);
        Arrays.asList(LocalDateTime.now().getDayOfWeek().values()).stream().forEach(item -> {
            daysOfTheWeek
                    .add(new DayOfWeek(item.getValue(),
                            item.getDisplayName(TextStyle.FULL, parseLocale(language))));
        });
        return index.data("days", daysOfTheWeek).data("states", states);
    }

    @POST
    @Produces(MediaType.TEXT_HTML)
    @Path("/create")
    public TemplateInstance save(@FormParam("trafficPrice") String trafficPrice,
            @FormParam("mealPrice") String mealPrice, @FormParam("workedDays") String[] workedDays,
            @FormParam("state") String state, @FormParam("city") String city) {
        System.out.println(state);
        System.out.println(city);
        System.out.println(mealPrice);
        System.out.println(trafficPrice);
        holidayService.loadHoladys(city, state, null);

        return index.instance();
    }

    private Locale parseLocale(String locales) {
        String[] locals = locales.split("-");
        return new Locale(locals[0], locals[1]);
    }

    class DayOfWeek {

        private int index;
        private String displayName;

        public DayOfWeek(int index, String displayName) {
            this.displayName = displayName;
            this.index = index;
        }

        public String getDisplayName() {
            return this.displayName;
        }

        public int getIndex() {
            return this.index;
        }
    }

}
