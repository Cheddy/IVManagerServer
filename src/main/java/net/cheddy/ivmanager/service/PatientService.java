package net.cheddy.ivmanager.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.auth.Auth;
import net.cheddy.ivmanager.auth.UserSession;
import net.cheddy.ivmanager.database.DAO;
import net.cheddy.ivmanager.model.Patient;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

@Path("/patient")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PatientService {

	private final DAO dao;

	public PatientService(DAO dao) {
		this.dao = dao;
	}

	@POST
	@Path("/save")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response savePatient(@Auth UserSession session, @FormParam("data") String data, @Context HttpServletRequest request) {
		if(!session.getStaff().canCreatePatients() && !session.getStaff().canEditPatients()){
			return Response.status(Status.UNAUTHORIZED).build();
		}
		ObjectMapper mapper = new ObjectMapper();
		try {
			Patient patient = mapper.readValue(data, Patient.class);
			if (patient.getId() == -1) {
				if(!session.getStaff().canCreatePatients()){
					return Response.status(Status.UNAUTHORIZED).build();
				}
				getDao().insertPatient(patient);
			} else {
				if(!session.getStaff().canEditPatients()){
					return Response.status(Status.UNAUTHORIZED).build();
				}
				getDao().updatePatient(patient);
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
	public Response deletePatient(@Auth UserSession session, @FormParam("data") String data, @Context HttpServletRequest request) {
		if(!session.getStaff().canDeletePatients()){
			return Response.status(Status.UNAUTHORIZED).build();
		}
		ObjectMapper mapper = new ObjectMapper();
		try {
			Patient patient = mapper.readValue(data, Patient.class);
			getDao().deletePatient(patient);
			return Response.accepted().build();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Response.status(Status.NOT_ACCEPTABLE).build();
	}

	@GET
	@Path("/all")
	public Patient[] getAllPatients(@Auth UserSession session) {
		Iterator<Patient> it = getDao().allPatients();
		final ArrayList<Patient> patient = new ArrayList<>();
		it.forEachRemaining(t -> patient.add(t));
		return patient.toArray(new Patient[patient.size()]);
	}

	/**
	 * @return the dao
	 */
	public DAO getDao() {
		return dao;
	}
}
