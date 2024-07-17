package dev.fsantana.service.intergrations;

import java.util.List;

import dev.fsantana.domain.model.City;

public interface FindCityByStateFromApi {
  List<City> execute(String stateShortName);
}
