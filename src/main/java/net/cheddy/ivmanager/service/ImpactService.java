package net.cheddy.ivmanager.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.auth.Auth;
import net.cheddy.ivmanager.auth.UserSession;
import net.cheddy.ivmanager.database.DAO;
import net.cheddy.ivmanager.logging.Logger;
import net.cheddy.ivmanager.model.Impact;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

@Path("/impact")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ImpactService {

	private final DAO dao;

	public ImpactService(DAO dao) {
		this.dao = dao;
	}

	@POST
	@Path("/save")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response saveImpact(@Auth UserSession session, @FormParam("data") String data, @Context HttpServletRequest request) {
		if(!session.getStaff().canCreateImpacts() && !session.getStaff().canEditImpacts()){
			return Response.status(Status.UNAUTHORIZED).build();
		}
		ObjectMapper mapper = new ObjectMapper();
		try {
			Impact impact = mapper.readValue(data, Impact.class);
			if (impact.getId() == -1) {
				if(!session.getStaff().canCreateImpacts()){
					return Response.status(Status.UNAUTHORIZED).build();
				}
				impact.setId(getDao().insertImpact(impact));
				Logger.logInsertion(session, impact);
			} else {
				if(!session.getStaff().canEditImpacts()){
					return Response.status(Status.UNAUTHORIZED).build();
				}
				Impact original = getDao().impactForId(impact.getId());
				getDao().updateImpact(impact);
				Logger.logUpdate(session, impact, original);
			}
			return Response.accepted().build();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Response.status(Status.NOT_ACCEPTABLE).build();
	}

	@POST
	@Path("/delete")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response deleteImpact(@Auth UserSession session, @FormParam("data") String data, @Context HttpServletRequest request) {
		if(!session.getStaff().canDeleteImpacts()){
			return Response.status(Status.UNAUTHORIZED).build();
		}
		ObjectMapper mapper = new ObjectMapper();
		try {
			Impact impact = mapper.readValue(data, Impact.class);
			getDao().deleteImpact(impact);
			Logger.logDeletion(session, impact);
			return Response.accepted().build();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Response.status(Status.NOT_ACCEPTABLE).build();
	}

	@GET
	@Path("/all")
	public Impact[] getAllImpacts(@Auth UserSession session) {
		Iterator<Impact> it = getDao().allImpacts();
		final ArrayList<Impact> impacts = new ArrayList<>();
		it.forEachRemaining(t -> impacts.add(t));
		return impacts.toArray(new Impact[impacts.size()]);
	}

	/**
	 * @return the dao
	 */
	public DAO getDao() {
		return dao;
	}
}
