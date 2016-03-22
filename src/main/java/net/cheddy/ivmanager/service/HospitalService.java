package net.cheddy.ivmanager.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.auth.Auth;
import net.cheddy.ivmanager.auth.UserSession;
import net.cheddy.ivmanager.database.DAO;
import net.cheddy.ivmanager.logging.Logger;
import net.cheddy.ivmanager.model.Hospital;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

@Path("/hospital")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class HospitalService {

	private final DAO dao;

	public HospitalService(DAO dao) {
		this.dao = dao;
	}

	@POST
	@Path("/save")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response saveHospital(@Auth UserSession session, @FormParam("data") String data, @Context HttpServletRequest request) {
		if(!session.getStaff().canCreateHospitals() && !session.getStaff().canEditHospitals()){
			return Response.status(Status.UNAUTHORIZED).build();
		}
		ObjectMapper mapper = new ObjectMapper();
		try {
			Hospital hospital = mapper.readValue(data, Hospital.class);
			if (hospital.getId() == -1) {
				if(!session.getStaff().canCreateHospitals()){
					return Response.status(Status.UNAUTHORIZED).build();
				}
				hospital.setId(getDao().insertHospital(hospital));
				Logger.logInsertion(session, hospital);
			} else {
				if(!session.getStaff().canEditHospitals()){
					return Response.status(Status.UNAUTHORIZED).build();
				}
				Hospital original = getDao().hospitalForId(hospital.getId());
				getDao().updateHospital(hospital);
				Logger.logUpdate(session, hospital, original);
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
	public Response deleteHospital(@Auth UserSession session, @FormParam("data") String data, @Context HttpServletRequest request) {
		if(!session.getStaff().canDeleteHospitals()){
			return Response.status(Status.UNAUTHORIZED).build();
		}
		ObjectMapper mapper = new ObjectMapper();
		try {
			Hospital hospital = mapper.readValue(data, Hospital.class);
			getDao().deleteHospital(hospital);
			Logger.logDeletion(session, hospital);
			return Response.accepted().build();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Response.status(Status.NOT_ACCEPTABLE).build();
	}

	@GET
	@Path("/all")
	public Hospital[] getAllHospitals(@Auth UserSession session) {
		Iterator<Hospital> it = getDao().allHospitals();
		final ArrayList<Hospital> hospitals = new ArrayList<>();
		it.forEachRemaining(t -> hospitals.add(t));
		return hospitals.toArray(new Hospital[hospitals.size()]);
	}

	/**
	 * @return the dao
	 */
	public DAO getDao() {
		return dao;
	}
}
