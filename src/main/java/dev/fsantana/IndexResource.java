package dev.fsantana;

import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.jboss.resteasy.reactive.RestHeader;
import org.jboss.resteasy.reactive.RestQuery;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/")
public class IndexResource {

    @Inject
    Template index;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance get(@RestQuery String name, @RestHeader("Accept-Language") String locales) {
        List<DayOfWeek> daysOfTheWeek = new ArrayList<>();
        String language = locales.substring(0, locales.indexOf(","));
        System.out.println(locales);
        Arrays.asList(LocalDateTime.now().getDayOfWeek().values()).stream().forEach(item -> {
            daysOfTheWeek
                    .add(new DayOfWeek(item.getValue(),
                            item.getDisplayName(TextStyle.FULL, parseLocale(language))));
        });
        return index.data("days", daysOfTheWeek);
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
