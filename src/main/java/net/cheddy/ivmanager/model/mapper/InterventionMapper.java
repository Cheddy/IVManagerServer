package net.cheddy.ivmanager.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import net.cheddy.ivmanager.model.Intervention;

import org.joda.time.DateTime;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public class InterventionMapper implements ResultSetMapper<Intervention> {
		public InterventionMapper() {
		}

		public Intervention map(int index, ResultSet r, StatementContext ctx) throws SQLException {
			Intervention intervention = new Intervention();
			intervention.setCompleted(r.getBoolean("completed"));
			intervention.setVerified(r.getBoolean("verified"));
			intervention.setCompletedDateTime(r.getObject("completedDateTime", DateTime.class));
			intervention.setVerifiedDateTime(r.getObject("verifiedDateTime", DateTime.class));
			intervention.setCompletedStaffId(r.getLong("completedStaffId"));
			intervention.setVerifiedStaffId(r.getLong("verifiedStaffId"));
			intervention.setDateTime(r.getObject("dateTime", DateTime.class));
			intervention.setId(r.getLong("id"));
			intervention.setImpactId(r.getLong("impactId"));
			intervention.setPatientId(r.getLong("patientId"));
			intervention.setStaffId(r.getLong("staffId"));
			intervention.setWardId(r.getLong("wardId"));

			return intervention;
		}
	}