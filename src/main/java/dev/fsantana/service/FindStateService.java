package dev.fsantana.service;

import java.util.List;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import dev.fsantana.domain.model.State;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/localidades/estados")
@RegisterRestClient(configKey = "ibge-api")
public interface FindStateService {

  @GET
  List<State> getAllStates();

}