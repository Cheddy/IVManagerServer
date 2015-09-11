package net.cheddy.ivmanager.service;

import io.dropwizard.jersey.params.LongParam;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Consumer;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.cheddy.ivmanager.database.DAO;
import net.cheddy.ivmanager.model.Intervention;
import net.cheddy.ivmanager.model.complete.CompleteIntervention;

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
		Iterator<Intervention> it =  dao.allInterventions();
		final ArrayList<Intervention> interventions = new ArrayList<Intervention>();
		it.forEachRemaining(new Consumer<Intervention>() {
			public void accept(Intervention t) {
				interventions.add(t);
			}
		});
		ArrayList<CompleteIntervention> interventionViews = new ArrayList<CompleteIntervention>();
		if(interventions != null){
			for(Intervention intervention : interventions){
				interventionViews.add(new CompleteIntervention(dao, intervention));
			}
		}
		return (CompleteIntervention[]) interventionViews.toArray(new CompleteIntervention[interventionViews.size()]);
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
