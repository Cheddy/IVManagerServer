package net.cheddy.ivmanager.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import net.cheddy.ivmanager.model.InterventionAction;

import org.joda.time.DateTime;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public class InterventionActionMapper implements ResultSetMapper<InterventionAction> {
		public InterventionActionMapper() {
		}
		public InterventionAction map(int index, ResultSet r, StatementContext ctx) throws SQLException {
			InterventionAction interventionAction = new InterventionAction();
			interventionAction.setId(r.getLong("id"));
			interventionAction.setInterventionId(r.getLong("interventionId"));
			interventionAction.setDescription(r.getString("description"));
			interventionAction.setDetail(r.getString("detail"));
			interventionAction.setDateTime(r.getObject("dateTime", DateTime.class));
			return interventionAction;
		}
	}