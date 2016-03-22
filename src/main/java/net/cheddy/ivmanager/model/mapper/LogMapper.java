package net.cheddy.ivmanager.model.mapper;

import net.cheddy.ivmanager.model.Log;
import org.joda.time.DateTime;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LogMapper implements ResultSetMapper<Log> {

	public LogMapper() {
	}

	public Log map(int index, ResultSet r, StatementContext ctx) throws SQLException {
		Log log = new Log();
		log.setId(r.getLong("id"));
		log.setNewData(r.getString("newData"));
		log.setOldData(r.getString("oldData"));
		log.setDescription(r.getString("description"));
		log.setOpcode(r.getInt("opcode"));
		log.setTimestamp(new DateTime(r.getTimestamp("timestamp").getTime()));
		log.setStaffId(r.getLong("staffId"));
		return log;
	}
}