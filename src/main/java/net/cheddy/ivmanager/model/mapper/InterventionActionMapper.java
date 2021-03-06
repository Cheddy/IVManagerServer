package net.cheddy.ivmanager.model.mapper;

import net.cheddy.ivmanager.model.InterventionAction;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class InterventionActionMapper implements ResultSetMapper<InterventionAction> {

	public InterventionActionMapper() {
	}

	public InterventionAction map(int index, ResultSet r, StatementContext ctx) throws SQLException {
		InterventionAction interventionAction = new InterventionAction();
		interventionAction.setId(r.getLong("id"));
		interventionAction.setInterventionId(r.getLong("interventionId"));
		interventionAction.setDescription(r.getString("description"));
		interventionAction.setDetail(r.getString("detail"));
		interventionAction.setDateTime(DateTime.parse(r.getString("dateTime"), DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.S")));
		return interventionAction;
	}
}