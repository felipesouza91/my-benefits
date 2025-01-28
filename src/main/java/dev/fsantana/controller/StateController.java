package dev.fsantana.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.jboss.resteasy.reactive.RestHeader;
import org.jboss.resteasy.reactive.RestQuery;

import dev.fsantana.controller.dto.StateDTO;
import dev.fsantana.domain.model.State;
import dev.fsantana.domain.usecases.FindStatesUseCase;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/states")
@Produces(MediaType.APPLICATION_JSON)
public class StateController {

    @Inject
    private FindStatesUseCase findStatesUseCase;

    @GET
    public List<StateDTO> get(@RestQuery String name, @RestHeader("Accept-Language") String locales) {

        List<State> allStates = findStatesUseCase.findStates();

        List<StateDTO> statesDto = allStates.stream()
                .map(state -> new StateDTO(state.getShortName(), state.getFullName()))
                .collect(Collectors.toList());
        return statesDto;
    }

}
