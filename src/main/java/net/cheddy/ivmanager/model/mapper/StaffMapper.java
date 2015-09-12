package net.cheddy.ivmanager.model.mapper;

import net.cheddy.ivmanager.model.Staff;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StaffMapper implements ResultSetMapper<Staff> {

	public StaffMapper() {
	}

	public Staff map(int index, ResultSet r, StatementContext ctx) throws SQLException {
		Staff staff = new Staff();
		staff.setId(r.getLong("id"));
		staff.setRankId(r.getLong("rankId"));
		staff.setOthernames(r.getString("othernames"));
		staff.setSurname(r.getString("surname"));
		staff.setPasswordHash(r.getString("passwordHash"));
		staff.setUsername(r.getString("username"));
		staff.setPasswordSalt(r.getString("passwordSalt"));
		return staff;
	}
}
