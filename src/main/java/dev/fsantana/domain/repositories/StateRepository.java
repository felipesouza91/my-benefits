package dev.fsantana.domain.repositories;

import java.util.Optional;

import dev.fsantana.domain.model.State;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class StateRepository implements PanacheRepository<State> {

  public Optional<State> findByShortName(String stateShortName) {
    return this.find("shortName", stateShortName).firstResultOptional();
  }

}
