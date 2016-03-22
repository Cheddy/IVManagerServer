package net.cheddy.ivmanager.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import io.dropwizard.auth.Auth;
import io.dropwizard.jersey.params.LongParam;
import net.cheddy.ivmanager.auth.UserSession;
import net.cheddy.ivmanager.database.DAO;
import net.cheddy.ivmanager.logging.Logger;
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
	public CompleteIntervention getIntervention(@Auth UserSession session, @PathParam(value = "id") LongParam id) {
		return new CompleteIntervention(dao, id.get());
	}


	@GET
	@Path("/all")
	public CompleteIntervention[] getAllInterventions(@Auth UserSession session) {
		Iterator<Intervention> it = dao.allInterventions();
		ArrayList<CompleteIntervention> interventionViews = new ArrayList<>();
		it.forEachRemaining(t -> interventionViews.add(new CompleteIntervention(dao, t)));
		return interventionViews.toArray(new CompleteIntervention[interventionViews.size()]);
	}

	@POST
	@Path("/save")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response saveIntervention(@Auth UserSession session, @FormParam("data") String data, @Context HttpServletRequest request) {
		if (!session.getStaff().canCreateInterventions() && !session.getStaff().canEditInterventions()) {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JodaModule());
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		try {
			CompleteIntervention completeIntervention = mapper.readValue(data, CompleteIntervention.class);

			Intervention intervention = completeIntervention.toIntervention();
			long id = intervention.getId();
			if (id == -1) {
				if (!session.getStaff().canCreateInterventions()) {
					return Response.status(Response.Status.UNAUTHORIZED).build();
				}
				intervention.setStaffId(session.getStaff().getId());
				id = getDao().insertIntervention(intervention);
				completeIntervention.setId(id);
				Logger.logInsertion(session, completeIntervention);

			} else {
				if (!session.getStaff().canEditInterventions()) {
					return Response.status(Response.Status.UNAUTHORIZED).build();
				}
				Intervention original = getDao().interventionForId(id);
				intervention.setStaffId(original.getStaffId());
				CompleteIntervention originalComplete = new CompleteIntervention(getDao(), getDao().interventionForId(id));
				getDao().updateIntervention(intervention);
				Logger.logUpdate(session, completeIntervention, originalComplete);
			}
			boolean auth = true;

			if (session.getStaff().canDeleteInterventionDetails()) {
				Iterator<InterventionDetail> itd = dao.allDetailsForId(intervention.getId());
				ArrayList<InterventionDetail> interventionDetails = new ArrayList<>();
				itd.forEachRemaining(t -> interventionDetails.add(t));
				for (InterventionDetail detail : interventionDetails) {
					boolean contains = false;
					for (InterventionDetail detail1 : completeIntervention.getDetails()) {
						if (detail.getId() == detail1.getId()) {
							contains = true;
							break;
						}
					}
					if (!contains) {
						getDao().deleteInterventionDetail(detail);
					}
				}
			} else {
				auth = false;
			}

			for (InterventionDetail detail : completeIntervention.getDetails()) {
				detail.setInterventionId(id);
				if (detail.getId() == -1) {
					if (session.getStaff().canCreateInterventionDetails()) {
						getDao().insertInterventionDetail(detail);
					} else {
						auth = false;
					}
				} else {
					if (session.getStaff().canEditInterventionDetails()) {
						getDao().updateInterventionDetail(detail);
					} else {
						auth = false;
					}
				}
			}

			if (session.getStaff().canDeleteInterventionActions()) {
				Iterator<InterventionAction> ita = dao.allActionsForId(intervention.getId());
				ArrayList<InterventionAction> interventionActions = new ArrayList<>();
				ita.forEachRemaining(t -> interventionActions.add(t));
				for (InterventionAction action : interventionActions) {
					boolean contains = false;
					for (InterventionAction action1 : completeIntervention.getActions()) {
						if (action.getId() == action1.getId()) {
							contains = true;
							break;
						}
					}
					if (!contains) {
						getDao().deleteInterventionAction(action);
					}
				}
			} else {
				auth = false;
			}

			for (InterventionAction action : completeIntervention.getActions()) {
				action.setInterventionId(id);
				if (action.getId() == -1) {
					if (session.getStaff().canCreateInterventionActions()) {
						getDao().insertInterventionAction(action);
					} else {
						auth = false;
					}
				} else {
					if (session.getStaff().canEditInterventionActions()) {
						getDao().updateInterventionAction(action);
					} else {
						auth = false;
					}
				}
			}

			if (session.getStaff().canDeleteInterventionOutcomes()) {
				Iterator<InterventionOutcome> ito = dao.allOutcomesForId(intervention.getId());
				ArrayList<InterventionOutcome> interventionOutcomes = new ArrayList<>();
				ito.forEachRemaining(t -> interventionOutcomes.add(t));
				for (InterventionOutcome outcome : interventionOutcomes) {
					boolean contains = false;
					for (InterventionOutcome outcome1 : completeIntervention.getOutcomes()) {
						if (outcome.getId() == outcome1.getId()) {
							contains = true;
							break;
						}
					}
					if (!contains) {
						getDao().deleteInterventionOutcome(outcome);
					}
				}
			} else {
				auth = false;
			}

			for (InterventionOutcome outcome : completeIntervention.getOutcomes()) {
				outcome.setInterventionId(id);
				if (outcome.getId() == -1) {
					if (session.getStaff().canCreateInterventionOutcomes()) {
						getDao().insertInterventionOutcome(outcome);
					} else {
						auth = false;
					}
				} else {
					if (session.getStaff().canEditInterventionOutcomes()) {
						getDao().updateInterventionOutcome(outcome);
					} else {
						auth = false;
					}
				}
			}

			if (!auth) {
				return Response.status(Response.Status.UNAUTHORIZED).build();
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
		if (!session.getStaff().canDeleteInterventions()) {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JodaModule());
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		try {
			CompleteIntervention completeIntervention = mapper.readValue(data, CompleteIntervention.class);
			Intervention intervention = completeIntervention.toIntervention();
			getDao().deleteIntervention(intervention);
			Logger.logDeletion(session, completeIntervention);
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
