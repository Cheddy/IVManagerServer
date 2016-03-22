package net.cheddy.ivmanager.service;

import io.dropwizard.auth.Auth;
import net.cheddy.ivmanager.auth.UserSession;
import net.cheddy.ivmanager.database.DAO;
import net.cheddy.ivmanager.logging.Logger;
import net.cheddy.ivmanager.model.Log;
import net.cheddy.ivmanager.model.complete.CompleteLog;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.ArrayList;
import java.util.Iterator;


@Path("/log")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LogService {

	private final DAO dao;

	public LogService(DAO dao) {
		this.dao = dao;
	}

	@GET
	@Path("/clear")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response clearLogs(@Auth UserSession session) {
		if(!session.getStaff().canClearLogs()){
			return Response.status(Status.UNAUTHORIZED).build();
		}
		getDao().clearLogs();
		Logger.logClear(session);
		return Response.status(Status.ACCEPTED).build();
	}

	@GET
	@Path("/all")
	public CompleteLog[] getAllLogs(@Auth UserSession session) {
		if(!session.getStaff().canViewLogs()){
			throw new WebApplicationException(Status.UNAUTHORIZED);
		}
		Iterator<Log> it = getDao().allLogs();
		final ArrayList<CompleteLog> logs = new ArrayList<>();
		it.forEachRemaining(t -> logs.add(new CompleteLog(dao, t)));
		return logs.toArray(new CompleteLog[logs.size()]);
	}

	/**
	 * @return the dao
	 */
	public DAO getDao() {
		return dao;
	}
}
