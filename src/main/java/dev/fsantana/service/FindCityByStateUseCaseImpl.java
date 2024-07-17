package dev.fsantana.service;

import java.util.ArrayList;
import java.util.List;

import dev.fsantana.domain.model.City;
import dev.fsantana.domain.repositories.CityRepository;
import dev.fsantana.domain.repositories.StateRepository;
import dev.fsantana.domain.usecases.FindCityByStateUseCase;
import dev.fsantana.service.intergrations.FindCityByStateFromApi;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
class FindCityByStateUseCaseImpl implements FindCityByStateUseCase {

  private CityRepository cityRepository;

  private StateRepository stateRepository;

  private FindCityByStateFromApi findCityByStateFromApi;

  public FindCityByStateUseCaseImpl(FindCityByStateFromApi findCityByStateFromApi, CityRepository cityRepository,
      StateRepository stateRepository) {
    this.findCityByStateFromApi = findCityByStateFromApi;
    this.cityRepository = cityRepository;
    this.stateRepository = stateRepository;
  }

  @Override
  @Transactional
  public List<City> execute(String stateShortName) {
    Log.info(String.format("Find cities by state: %s start", stateShortName));

    Log.info(String.format("Check if state: %s exits", stateShortName));

    this.stateRepository.findByShortName(stateShortName).orElseThrow(() -> new RuntimeException("State not Found"));

    Log.info(String.format("Find cities by state: %s on database", stateShortName));
    List<City> cities = this.cityRepository.findByState(stateShortName);
    if (cities.isEmpty()) {
      Log.info(String.format("Databsae is empty, call external service", stateShortName));

      List<City> apiCities = new ArrayList<>();

      apiCities = this.findCityByStateFromApi.execute(stateShortName);
      Log.info("Save external services cities on local database start");

      cityRepository.persist(apiCities);

      Log.info("Save external services cities on local database finish");

      cities = apiCities;
    }
    Log.info("Load cities sucessfully");
    return cities;
  }

}
