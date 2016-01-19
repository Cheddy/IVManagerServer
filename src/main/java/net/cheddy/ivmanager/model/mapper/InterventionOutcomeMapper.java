package net.cheddy.ivmanager.model.mapper;

import net.cheddy.ivmanager.model.InterventionOutcome;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class InterventionOutcomeMapper implements ResultSetMapper<InterventionOutcome> {

	public InterventionOutcomeMapper() {
	}

	public InterventionOutcome map(int index, ResultSet r, StatementContext ctx) throws SQLException {
		InterventionOutcome interventionOutcome = new InterventionOutcome();
		interventionOutcome.setId(r.getLong("id"));
		interventionOutcome.setInterventionId(r.getLong("interventionId"));
		interventionOutcome.setDescription(r.getString("description"));
		interventionOutcome.setDetail(r.getString("detail"));
		interventionOutcome.setDateTime(DateTime.parse(r.getString("dateTime"), DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.S")));
		return interventionOutcome;
	}
}
