package net.cheddy.ivmanager.service;

import io.dropwizard.auth.Auth;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Consumer;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import net.cheddy.ivmanager.auth.UserSession;
import net.cheddy.ivmanager.database.DAO;
import net.cheddy.ivmanager.model.Impact;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
		ObjectMapper mapper = new ObjectMapper();
		try {
			Impact impact = mapper.readValue(data, Impact.class);
			if (impact.getId() == -1)
				getDao().insertImpact(impact);
			else
				getDao().updateImpact(impact);
			return Response.accepted().build();
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Response.status(Status.NOT_ACCEPTABLE).build();
	}
	
	@POST
	@Path("/delete")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response deleteImpact(@Auth UserSession session, @FormParam("data") String data, @Context HttpServletRequest request) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			Impact impact = mapper.readValue(data, Impact.class);
			getDao().deleteImpact(impact);
			return Response.accepted().build();
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Response.status(Status.NOT_ACCEPTABLE).build();
	}
	
	@GET
	@Path("/all")
	public Impact[] getAllImpacts(@Auth UserSession session) {
		Iterator<Impact> it = getDao().allImpacts();
		final ArrayList<Impact> impacts = new ArrayList<Impact>();
		it.forEachRemaining(new Consumer<Impact>() {
			public void accept(Impact t) {
				impacts.add(t);
			}
		});
		return (Impact[]) impacts.toArray(new Impact[impacts.size()]);
	}

	/**
	 * @return the dao
	 */
	public DAO getDao() {
		return dao;
	}
}
