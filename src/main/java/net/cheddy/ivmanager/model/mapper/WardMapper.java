package net.cheddy.ivmanager.model.mapper;

import net.cheddy.ivmanager.model.Ward;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WardMapper implements ResultSetMapper<Ward> {

	public WardMapper() {
	}

	public Ward map(int index, ResultSet r, StatementContext ctx) throws SQLException {
		Ward ward = new Ward();
		ward.setId(r.getLong("id"));
		ward.setHospitalId(r.getLong("hospitalId"));
		ward.setName(r.getString("name"));
		return ward;
	}
}