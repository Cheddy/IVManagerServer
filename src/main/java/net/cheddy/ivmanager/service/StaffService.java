package net.cheddy.ivmanager.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Predicate;
import io.dropwizard.auth.Auth;
import io.dropwizard.auth.basic.BasicCredentials;
import net.cheddy.ivmanager.Server;
import net.cheddy.ivmanager.auth.UserSession;
import net.cheddy.ivmanager.database.DAO;
import net.cheddy.ivmanager.logging.Logger;
import net.cheddy.ivmanager.model.Staff;
import net.cheddy.ivmanager.model.complete.CompleteStaff;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

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
		if(!session.getStaff().canCreateStaff() && !session.getStaff().canEditStaff()){
			return Response.status(Status.UNAUTHORIZED).build();
		}
		ObjectMapper mapper = new ObjectMapper();
		try {
			CompleteStaff completeStaff = mapper.readValue(data, CompleteStaff.class);
			if(completeStaff.getId() == -1){
				if(!session.getStaff().canCreateStaff()){
					return Response.status(Status.UNAUTHORIZED).build();
				}
			}else{
				if(!session.getStaff().canEditStaff()){
					return Response.status(Status.UNAUTHORIZED).build();
				}
			}

			if (completeStaff.getId() == -1 && (completeStaff.getPasswordHash() == null || completeStaff.getPasswordHash().isEmpty())) {
				return Response.status(Status.NOT_ACCEPTABLE).build();
			}

			Staff staff = completeStaff.toStaff(dao);
			if (staff.getId() == -1) {
				completeStaff.setId(getDao().insertStaff(staff));
				completeStaff.setPasswordHash(null);
				Logger.logInsertion(session, completeStaff);
			} else {
				if(completeStaff.getId() == session.getStaff().getId()){
					staff.setRankId(session.getStaff().getRank().getId());
					staff.setUsername(session.getStaff().getUsername());
				}
				CompleteStaff original = new CompleteStaff(getDao(), getDao().staffForId(staff.getId()));
				getDao().updateStaff(staff);
				completeStaff.setPasswordHash(null);
				original.setPasswordHash(null);
				Logger.logUpdate(session, completeStaff, original);
				if(completeStaff.getPasswordHash() != null && !completeStaff.getPasswordHash().isEmpty() && !completeStaff.getPasswordHash().equals(session.getStaff().getPasswordHash())){
					Server.getAuthenticator().invalidateAll(new Predicate<BasicCredentials>(){
						@Override
						public boolean apply(@Nullable BasicCredentials input) {
							return input == null || input.getUsername().equalsIgnoreCase(session.getStaff().getUsername());
						}
					});
				}
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
	public Response deleteStaff(@Auth UserSession session, @FormParam("data") String data, @Context HttpServletRequest request) {
		if(!session.getStaff().canDeleteStaff()){
			return Response.status(Status.UNAUTHORIZED).build();
		}
		ObjectMapper mapper = new ObjectMapper();
		try {
			CompleteStaff completeStaff = mapper.readValue(data, CompleteStaff.class);
			if (completeStaff.getId() == -1 && (completeStaff.getPasswordHash() == null || completeStaff.getPasswordHash().isEmpty())) {
				return Response.status(Status.NOT_ACCEPTABLE).build();
			}
			Staff staff = completeStaff.toStaff(dao);
			getDao().deleteStaff(staff);
			Logger.logDeletion(session, completeStaff);
			return Response.accepted().build();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Response.status(Status.NOT_ACCEPTABLE).build();
	}

	@GET
	@Path("/all")
	public CompleteStaff[] getAllStaff(@Auth UserSession session) {
		Iterator<Staff> it = getDao().allStaff();
		final ArrayList<CompleteStaff> staffs = new ArrayList<>();
		it.forEachRemaining(t -> staffs.add(new CompleteStaff(dao, t)));
		return staffs.toArray(new CompleteStaff[staffs.size()]);
	}

	/**
	 * @return the dao
	 */
	public DAO getDao() {
		return dao;
	}

}
