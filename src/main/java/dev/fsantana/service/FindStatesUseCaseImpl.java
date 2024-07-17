package dev.fsantana.service;

import java.util.List;

import dev.fsantana.domain.model.State;
import dev.fsantana.domain.repositories.StateRepository;
import dev.fsantana.domain.usecases.FindStatesUseCase;
import dev.fsantana.service.intergrations.FindStateFromApi;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
class FindStatesUseCaseImpl implements FindStatesUseCase {

  private StateRepository stateRepository;

  private FindStateFromApi findStateIntegration;

  public FindStatesUseCaseImpl(StateRepository stateRepository, FindStateFromApi findStateService) {
    this.stateRepository = stateRepository;
    this.findStateIntegration = findStateService;
  }

  @Override
  @Transactional
  public List<State> findStates() {
    Log.info("Find States from datebase");
    List<State> stateList = stateRepository.findAll().list();
    if (stateList.isEmpty()) {
      Log.info("Datebase states is empty, call external servicer data");
      List<State> allStates = findStateIntegration.getAllStates();
      Log.info("Save states in databse start");

      stateRepository.persist(allStates);
      Log.info("Save states in databse finish");

      stateList = allStates;
    }
    Log.info("Load states sucessfully");

    return stateList;
  }

}
