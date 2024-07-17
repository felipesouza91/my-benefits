package dev.fsantana.service.intergrations.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import dev.fsantana.domain.model.City;
import dev.fsantana.domain.model.State;
import dev.fsantana.service.intergrations.FindCityByStateFromApi;
import dev.fsantana.service.intergrations.infra.FindCityRestClient;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
class FindCityByStateIbgeApi implements FindCityByStateFromApi {

  @RestClient
  FindCityRestClient findCityByStateFromApi;

  @Override
  public List<City> execute(String stateShortName) {
    Log.info(String.format("Searching cities from state: %s from IBGE API start", stateShortName));
    List<City> citeis = findCityByStateFromApi.findCityByState(stateShortName).stream()
        .map(item -> {
          Map<String, Object> microregiao = (Map<String, Object>) item.get("microrregiao");
          Map<String, Object> mesorregiao = (Map<String, Object>) microregiao.get("mesorregiao");
          Map<String, Object> uf = (Map<String, Object>) mesorregiao.get("UF");
          State state = new State();
          state.setId(Long.valueOf(uf.get("id").toString()));
          return new City(Long.valueOf(item.get("id").toString()), (String) item.get("nome"), state);
        }).collect(Collectors.toList());

    Log.info(String.format("Searching cities from state: %s from IBGE API finish with success", stateShortName));
    return citeis;
  }

}
