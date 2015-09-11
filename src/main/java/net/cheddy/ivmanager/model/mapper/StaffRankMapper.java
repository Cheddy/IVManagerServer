package net.cheddy.ivmanager.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import net.cheddy.ivmanager.model.StaffRank;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public class StaffRankMapper implements ResultSetMapper<StaffRank> {
	public StaffRankMapper() {
	}
	public StaffRank map(int index, ResultSet r, StatementContext ctx) throws SQLException {
		StaffRank staffRank = new StaffRank();
		staffRank.setId(r.getLong("id"));
		staffRank.setPermissions(r.getInt("permissions"));
		staffRank.setName(r.getString("name"));
		return staffRank;
	}
}
