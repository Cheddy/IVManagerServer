package net.cheddy.ivmanager.model.mapper;

import net.cheddy.ivmanager.model.Patient;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PatientMapper implements ResultSetMapper<Patient> {

	public PatientMapper() {
	}

	public Patient map(int index, ResultSet r, StatementContext ctx) throws SQLException {
		Patient patient = new Patient();
		patient.setId(r.getLong("id"));
		patient.setRtx(r.getLong("rtx"));
		patient.setOthernames(r.getString("othernames"));
		patient.setSurname(r.getString("surname"));
		patient.setDob(r.getDate("dob"));
		return patient;
	}
}
