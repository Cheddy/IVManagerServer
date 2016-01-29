package net.cheddy.ivmanager.service;

import io.dropwizard.auth.Auth;
import net.cheddy.ivmanager.auth.UserSession;
import net.cheddy.ivmanager.database.DAO;
import net.cheddy.ivmanager.model.complete.CompleteStaff;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/login")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LoginService {

	private final DAO dao;

	public LoginService(DAO dao) {
		this.dao = dao;
	}

	@GET
	public CompleteStaff login(@Auth UserSession session) {
		return new CompleteStaff(dao, getDao().staffForId(session.getStaff().getId()));
	}

	/**
	 * @return the dao
	 */
	public DAO getDao() {
		return dao;
	}
}
