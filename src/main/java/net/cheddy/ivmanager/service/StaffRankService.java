package net.cheddy.ivmanager.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Predicate;
import io.dropwizard.auth.Auth;
import io.dropwizard.auth.basic.BasicCredentials;
import net.cheddy.ivmanager.Server;
import net.cheddy.ivmanager.auth.UserSession;
import net.cheddy.ivmanager.config.Constants;
import net.cheddy.ivmanager.database.DAO;
import net.cheddy.ivmanager.logging.Logger;
import net.cheddy.ivmanager.model.Staff;
import net.cheddy.ivmanager.model.StaffRank;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
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
		if(!session.getStaff().canCreateStaffRanks() && !session.getStaff().canEditStaffRanks()){
			return Response.status(Status.UNAUTHORIZED).build();
		}
		ObjectMapper mapper = new ObjectMapper();
		try {
			StaffRank staffRank = mapper.readValue(data, StaffRank.class);
			if((staffRank.getPermissions() & Constants.DELETE_INTERVENTION_PERMISSION) != 0){
				staffRank.setPermissions(staffRank.getPermissions() | Constants.DELETE_INTERVENTION_DETAIL_PERMISSION);
				staffRank.setPermissions(staffRank.getPermissions() | Constants.DELETE_INTERVENTION_ACTION_PERMISSION);
				staffRank.setPermissions(staffRank.getPermissions() | Constants.DELETE_INTERVENTION_OUTCOME_PERMISSION);
			}
			if (staffRank.getId() == -1) {
				if(!session.getStaff().canCreateStaffRanks()){
					return Response.status(Status.UNAUTHORIZED).build();
				}
				staffRank.setId(getDao().insertStaffRank(staffRank));
				Logger.logInsertion(session, staffRank);
			} else {
				if(!session.getStaff().canEditStaffRanks()){
					return Response.status(Status.UNAUTHORIZED).build();
				}
				StaffRank original = getDao().staffRankForId(staffRank.getId());
				getDao().updateStaffRank(staffRank);
				Logger.logUpdate(session, staffRank, original);

				final Iterator<Staff> it = getDao().allStaff();
				final ArrayList<Staff> staffs = new ArrayList<>();
				it.forEachRemaining(t -> staffs.add(t));
				Server.getAuthenticator().invalidateAll(new Predicate<BasicCredentials>() {
					@Override
					public boolean apply(@Nullable BasicCredentials input) {
						for (Staff staff : staffs) {
							if (staff.getRankId() == staffRank.getId()) {
								return true;
							}
						}
						return false;
					}
				});
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
		if(!session.getStaff().canDeleteStaffRanks()){
			return Response.status(Status.UNAUTHORIZED).build();
		}
		ObjectMapper mapper = new ObjectMapper();
		try {
			StaffRank staffRank = mapper.readValue(data, StaffRank.class);
			getDao().deleteStaffRank(staffRank);
			Logger.logDeletion(session, staffRank);
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
