package dev.fsantana.service.intergrations;

import java.util.List;

import dev.fsantana.domain.model.Holiday;

public interface FindHolidaysFromApi {

  List<Holiday> findHolidays(String city, String state, Integer year);

}
