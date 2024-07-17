package dev.fsantana.domain.usecases;

import java.util.List;

import dev.fsantana.domain.model.State;

public interface FindStatesUseCase {

  List<State> findStates();

}
