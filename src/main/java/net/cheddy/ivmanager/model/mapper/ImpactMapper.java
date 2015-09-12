package net.cheddy.ivmanager.model.mapper;

import net.cheddy.ivmanager.model.Impact;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ImpactMapper implements ResultSetMapper<Impact> {

	public ImpactMapper() {
	}

	public Impact map(int index, ResultSet r, StatementContext ctx) throws SQLException {
		Impact impact = new Impact();
		impact.setId(r.getLong("id"));
		impact.setName(r.getString("name"));
		return impact;
	}
}
