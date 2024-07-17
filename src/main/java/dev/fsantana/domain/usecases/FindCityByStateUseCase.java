package dev.fsantana.domain.usecases;

import java.util.List;

import dev.fsantana.domain.model.City;

public interface FindCityByStateUseCase {

  List<City> execute(String stateShortName);

}
