package net.cheddy.ivmanager.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import net.cheddy.ivmanager.model.Hospital;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public class HospitalMapper implements ResultSetMapper<Hospital> {
		public HospitalMapper() {
		}
		public Hospital map(int index, ResultSet r, StatementContext ctx) throws SQLException {
			Hospital hospital = new Hospital();
			hospital.setId(r.getLong("id"));
			hospital.setName(r.getString("name"));
			return hospital;
		}
	}
