package net.cheddy.ivmanager.model.mapper;

import net.cheddy.ivmanager.model.Intervention;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class InterventionMapper implements ResultSetMapper<Intervention> {

	public InterventionMapper() {
	}

	public Intervention map(int index, ResultSet r, StatementContext ctx) throws SQLException {
		Intervention intervention = new Intervention();
		intervention.setCompleted(r.getBoolean("completed"));
		intervention.setVerified(r.getBoolean("verified"));
		if(intervention.isCompleted()) {
			intervention.setCompletedStaffId(r.getLong("completedStaffId"));
			intervention.setCompletedDateTime(DateTime.parse(r.getString("completedDateTime"), DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.S")));
		}
		if(intervention.isVerified()) {
			intervention.setVerifiedDateTime(DateTime.parse(r.getString("verifiedDateTime"), DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.S")));
			intervention.setVerifiedStaffId(r.getLong("verifiedStaffId"));
		}

		intervention.setDateTime(DateTime.parse(r.getString("dateTime"), DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.S")));

		intervention.setId(r.getLong("id"));
		intervention.setImpactId(r.getLong("impactId"));
		intervention.setPatientId(r.getLong("patientId"));
		intervention.setStaffId(r.getLong("staffId"));
		intervention.setWardId(r.getLong("wardId"));

		return intervention;
	}
}