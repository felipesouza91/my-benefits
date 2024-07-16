package dev.fsantana.service;

import java.util.List;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import dev.fsantana.domain.model.City;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

@Path("localidades/estados/{state}/municipios")
@RegisterRestClient(configKey = "ibge-api")
public interface FindCityService {

  @GET
  List<City> findCityByState(@PathParam("state") String state);

}