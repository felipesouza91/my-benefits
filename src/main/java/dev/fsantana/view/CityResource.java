package dev.fsantana.view;

import java.util.Arrays;

import org.jboss.resteasy.reactive.RestPath;

import dev.fsantana.view.dto.CityDTO;
import dev.fsantana.view.dto.StateDTO;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/cities")
@Produces(MediaType.APPLICATION_JSON)
public class CityResource {

  @GET
  @Path("{state}")
  public Response getCitiesByState(@RestPath("state") String state) {
    System.out.println(state);
    return Response.ok(Arrays.asList(new CityDTO("Rio de janeiro", new StateDTO("RJ", "Rio de janeiro")))).build();
  }

}
