package dev.fsantana.service.intergrations.infra;

import java.util.List;
import java.util.Map;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

@Path("/localidades/estados/{state}/municipios")
@RegisterRestClient(configKey = "ibge-api")
public interface FindCityRestClient {

  @GET
  List<Map<String, Object>> findCityByState(@PathParam("state") String state);

}
