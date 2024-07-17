package dev.fsantana.service.intergrations;

import java.util.List;

import dev.fsantana.domain.model.State;

public interface FindStateFromApi {
  List<State> getAllStates();
}
