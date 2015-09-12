package net.cheddy.ivmanager.service;

import io.dropwizard.jersey.params.LongParam;
import net.cheddy.ivmanager.database.DAO;
import net.cheddy.ivmanager.model.Intervention;
import net.cheddy.ivmanager.model.complete.CompleteIntervention;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Iterator;

@Path("/intervention")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class InterventionService {

	private final DAO dao;

	public InterventionService(DAO dao) {
		this.dao = dao;
	}

	@GET
	@Path("/{id}")
	public CompleteIntervention getIntervention(@PathParam(value = "id") LongParam id) {
		return new CompleteIntervention(dao, id.get());
	}


	@GET
	@Path("/all")
	public CompleteIntervention[] getAllInterventions() {
		Iterator<Intervention> it = dao.allInterventions();
		final ArrayList<Intervention> interventions = new ArrayList<>();
		it.forEachRemaining(t -> interventions.add(t));
		ArrayList<CompleteIntervention> interventionViews = new ArrayList<>();
		for (Intervention intervention : interventions) {
			interventionViews.add(new CompleteIntervention(dao, intervention));
		}
		return interventionViews.toArray(new CompleteIntervention[interventionViews.size()]);
	}

//	@POST
//	@Path("/save")
//	public Response saveIntervention(@Valid @FormParam(value = "intervention") CompleteIntervention intervention) {
//		
//		return Response.accepted().build();
//	}

	/**
	 * @return the dao
	 */
	public DAO getDao() {
		return dao;
	}

}
