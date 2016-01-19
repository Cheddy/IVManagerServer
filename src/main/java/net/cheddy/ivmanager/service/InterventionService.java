package net.cheddy.ivmanager.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import io.dropwizard.auth.Auth;
import io.dropwizard.jersey.params.LongParam;
import net.cheddy.ivmanager.auth.UserSession;
import net.cheddy.ivmanager.database.DAO;
import net.cheddy.ivmanager.model.Intervention;
import net.cheddy.ivmanager.model.InterventionAction;
import net.cheddy.ivmanager.model.InterventionDetail;
import net.cheddy.ivmanager.model.InterventionOutcome;
import net.cheddy.ivmanager.model.complete.CompleteIntervention;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
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
		ArrayList<CompleteIntervention> interventionViews = new ArrayList<>();
		it.forEachRemaining(t -> interventionViews.add(new CompleteIntervention(dao, t)));
		return interventionViews.toArray(new CompleteIntervention[interventionViews.size()]);
	}

	@POST
	@Path("/save")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response saveIntervention(@Auth UserSession session, @FormParam("data") String data, @Context HttpServletRequest request) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JodaModule());
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		try {
			CompleteIntervention completeIntervention = mapper.readValue(data, CompleteIntervention.class);
			Intervention intervention = completeIntervention.toIntervention();
			long id = intervention.getId();
			if (id == -1) {
				id = getDao().insertIntervention(intervention);
			} else {
				getDao().updateIntervention(intervention);
			}

			for(InterventionDetail detail : completeIntervention.getDetails()){
				detail.setInterventionId(id);
				if(detail.getId() == -1){
					getDao().insertInterventionDetail(detail);
				}else{
					getDao().updateInterventionDetail(detail);
				}
			}
			for(InterventionAction action : completeIntervention.getActions()){
				action.setInterventionId(id);
				if(action.getId() == -1){
					getDao().insertInterventionAction(action);
				}else{
					getDao().updateInterventionAction(action);
				}
			}
			for(InterventionOutcome outcome : completeIntervention.getOutcomes()){
				outcome.setInterventionId(id);
				if(outcome.getId() == -1){
					getDao().insertInterventionOutcome(outcome);
				}else{
					getDao().updateInterventionOutcome(outcome);
				}
			}
			return Response.accepted().build();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Response.accepted().build();
	}

	@POST
	@Path("/delete")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response deleteIntervention(@Auth UserSession session, @FormParam("data") String data, @Context HttpServletRequest request) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JodaModule());
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		try {
			CompleteIntervention completeIntervention = mapper.readValue(data, CompleteIntervention.class);
			Intervention intervention = completeIntervention.toIntervention();
			getDao().deleteIntervention(intervention);
			getDao().deleteAllInterventionActionsForIntervention(intervention);
			getDao().deleteAllInterventionDetailsForIntervention(intervention);
			getDao().deleteAllInterventionOutcomesForIntervention(intervention);
			return Response.accepted().build();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Response.accepted().build();
	}

	/**
	 * @return the dao
	 */
	public DAO getDao() {
		return dao;
	}

}
