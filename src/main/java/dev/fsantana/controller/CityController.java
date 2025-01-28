package dev.fsantana.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.jboss.resteasy.reactive.RestPath;

import dev.fsantana.controller.dto.CityDTO;
import dev.fsantana.controller.dto.StateDTO;
import dev.fsantana.domain.model.City;
import dev.fsantana.domain.usecases.FindCityByStateUseCase;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/cities")
@Produces(MediaType.APPLICATION_JSON)
public class CityController {

  @Inject
  private FindCityByStateUseCase findCityByStateUseCase;

  @GET
  @Path("{state}")
  public Response getCitiesByState(@RestPath("state") String state) {
    List<City> cityByState = findCityByStateUseCase.execute(state.toUpperCase);
    List<CityDTO> cities = cityByState.stream()
        .map(city -> new CityDTO(city.getName(),
            new StateDTO(city.getState().getShortName(), city.getState().getFullName())))
        .collect(Collectors.toList());
    return Response.ok(cities).build();
  }

}
