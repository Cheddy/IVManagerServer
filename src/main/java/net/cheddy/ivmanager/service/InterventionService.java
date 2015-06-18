package net.cheddy.ivmanager.service;

import io.dropwizard.jersey.params.LongParam;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.cheddy.ivmanager.model.Intervention;

@Path("/intervention/{id}/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class InterventionService {

	@GET
	public Intervention getIntervention(@PathParam(value = "id") LongParam id) {
		return new Intervention();
	}
}
