package net.cheddy.ivmanager.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.auth.Auth;
import net.cheddy.ivmanager.auth.UserSession;
import net.cheddy.ivmanager.database.DAO;
import net.cheddy.ivmanager.model.Ward;
import net.cheddy.ivmanager.model.complete.CompleteWard;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;


@Path("/ward")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class WardService {

	private final DAO dao;

	public WardService(DAO dao) {
		this.dao = dao;
	}

	@POST
	@Path("/save")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response saveWard(@Auth UserSession session, @FormParam("data") String data, @Context HttpServletRequest request) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			CompleteWard completeWard = mapper.readValue(data, CompleteWard.class);
			Ward staff = completeWard.toWard();
			if(staff == null){
				return Response.status(Status.NOT_ACCEPTABLE).tag("Hospital not present").build();
			}
			if (staff.getId() == -1) {
				getDao().insertWard(staff);
			} else {
				getDao().updateWard(staff);
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
	public Response deleteWard(@Auth UserSession session, @FormParam("data") String data, @Context HttpServletRequest request) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			CompleteWard completeWard = mapper.readValue(data, CompleteWard.class);
			Ward ward = completeWard.toWard();
			getDao().deleteWard(ward);
			return Response.accepted().build();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Response.status(Status.NOT_ACCEPTABLE).build();
	}

	@GET
	@Path("/all")
	public CompleteWard[] getAllWard(@Auth UserSession session) {
		Iterator<Ward> it = getDao().allWards();
		final ArrayList<CompleteWard> wards = new ArrayList<>();
		it.forEachRemaining(t -> wards.add(new CompleteWard(dao, t)));
		return wards.toArray(new CompleteWard[wards.size()]);
	}

	/**
	 * @return the dao
	 */
	public DAO getDao() {
		return dao;
	}
}
