package dev.fsantana.domain.repositories;

import java.util.List;

import dev.fsantana.domain.model.City;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CityRepository implements PanacheRepository<City> {

  public List<City> findByState(String stateShortName) {
    return find("state.shortName", stateShortName).list();
  }

}
