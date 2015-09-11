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
import net.cheddy.ivmanager.model.Staff;
import net.cheddy.ivmanager.model.complete.CompleteStaff;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Path("/staff")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StaffService {

	private final DAO dao;

	public StaffService(DAO dao) {
		this.dao = dao;
	}

	@POST
	@Path("/save")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response saveStaff(@Auth UserSession session, @FormParam("data") String data, @Context HttpServletRequest request) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			CompleteStaff completeStaff = mapper.readValue(data, CompleteStaff.class);
			if(completeStaff.getId() == -1 && (completeStaff.getPasswordHash() == null || completeStaff.getPasswordHash().isEmpty())){
				return Response.status(Status.NOT_ACCEPTABLE).build();
			}
			Staff staff = completeStaff.toStaff(dao);
			if (staff.getId() == -1)
				getDao().insertStaff(staff);
			else
				getDao().updateStaff(staff);
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
	public Response deleteStaff(@Auth UserSession session, @FormParam("data") String data, @Context HttpServletRequest request) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			CompleteStaff completeStaff = mapper.readValue(data, CompleteStaff.class);
			if(completeStaff.getId() == -1 && (completeStaff.getPasswordHash() == null || completeStaff.getPasswordHash().isEmpty())){
				return Response.status(Status.NOT_ACCEPTABLE).build();
			}
			Staff staff = completeStaff.toStaff(dao);
			getDao().deleteStaff(staff);
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
	public CompleteStaff[] getAllStaff(@Auth UserSession session) {
		Iterator<Staff> it = getDao().allStaff();
		final ArrayList<CompleteStaff> staffs = new ArrayList<CompleteStaff>();
		it.forEachRemaining(new Consumer<Staff>() {
			public void accept(Staff t) {
				staffs.add(new CompleteStaff(dao, t));
			}
		});
		return (CompleteStaff[]) staffs.toArray(new CompleteStaff[staffs.size()]);
	}

	/**
	 * @return the dao
	 */
	public DAO getDao() {
		return dao;
	}
}
