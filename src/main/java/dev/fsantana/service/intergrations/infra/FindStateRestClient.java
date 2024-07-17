package dev.fsantana.service.intergrations.infra;

import java.util.List;
import java.util.Map;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/localidades/estados")
@RegisterRestClient(configKey = "ibge-api")
public interface FindStateRestClient {

  @GET
  List<Map<String, Object>> getAllStates();

}