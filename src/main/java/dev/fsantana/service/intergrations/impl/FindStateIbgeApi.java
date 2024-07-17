package dev.fsantana.service.intergrations.impl;

import java.util.List;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import dev.fsantana.domain.model.State;
import dev.fsantana.service.intergrations.FindStateFromApi;
import dev.fsantana.service.intergrations.infra.FindStateRestClient;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
class FindStateIbgeApi implements FindStateFromApi {

  @RestClient
  FindStateRestClient stateRestClient;

  public List<State> getAllStates() {
    Log.info("Searching states from IBGE API start");
    List<State> states = stateRestClient.getAllStates().stream()
        .map(item -> new State(Long.parseLong(item.get("id").toString()), (String) item.get("sigla"),
            (String) item.get("nome")))
        .toList();
    Log.info("Searching states from IBGE API finish with success");
    return states;
  }
}
