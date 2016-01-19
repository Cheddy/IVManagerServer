package net.cheddy.ivmanager.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.auth.Auth;
import net.cheddy.ivmanager.auth.UserSession;
import net.cheddy.ivmanager.database.DAO;
import net.cheddy.ivmanager.model.StaffRank;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

@Path("/staffrank")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StaffRankService {

	private final DAO dao;

	public StaffRankService(DAO dao) {
		this.dao = dao;
	}

	@POST
	@Path("/save")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response saveStaffRank(@Auth UserSession session, @FormParam("data") String data, @Context HttpServletRequest request) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			StaffRank staffRank = mapper.readValue(data, StaffRank.class);
			if (staffRank.getId() == -1) {
				getDao().insertStaffRank(staffRank);
			} else {
				getDao().updateStaffRank(staffRank);
			}
			return Response.accepted().build();
		} catch (Exception e) {
			Response.status(Status.BAD_REQUEST).build();
		}
		return Response.status(Status.BAD_REQUEST).build();
	}

	@POST
	@Path("/delete")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response deleteStaffRank(@Auth UserSession session, @FormParam("data") String data, @Context HttpServletRequest request) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			StaffRank staffRank = mapper.readValue(data, StaffRank.class);
			getDao().deleteStaffRank(staffRank);
			return Response.accepted().build();
		} catch (Exception e) {
			Response.status(Status.BAD_REQUEST).build();
		}
		return Response.status(Status.BAD_REQUEST).build();
	}

	@GET
	@Path("/all")
	public StaffRank[] getAllStaffRanks(@Auth UserSession session) {
		Iterator<StaffRank> it = getDao().allStaffRanks();
		final ArrayList<StaffRank> staffRanks = new ArrayList<>();
		it.forEachRemaining(t -> staffRanks.add(t));
		return staffRanks.toArray(new StaffRank[staffRanks.size()]);
	}

	/**
	 * @return the dao
	 */
	public DAO getDao() {
		return dao;
	}
}
