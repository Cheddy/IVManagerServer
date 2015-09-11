package net.cheddy.ivmanager.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import net.cheddy.ivmanager.model.InterventionOutcome;

import org.joda.time.DateTime;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public class InterventionOutcomeMapper implements ResultSetMapper<InterventionOutcome> {
		public InterventionOutcomeMapper() {
		}
		public InterventionOutcome map(int index, ResultSet r, StatementContext ctx) throws SQLException {
			InterventionOutcome interventionOutcome = new InterventionOutcome();
			interventionOutcome.setId(r.getLong("id"));
			interventionOutcome.setInterventionId(r.getLong("interventionId"));
			interventionOutcome.setDescription(r.getString("description"));
			interventionOutcome.setDetail(r.getString("detail"));
			interventionOutcome.setDateTime(r.getObject("dateTime", DateTime.class));
			return interventionOutcome;
		}
	}
