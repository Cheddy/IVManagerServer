package net.cheddy.ivmanager.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import net.cheddy.ivmanager.model.InterventionDetail;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public class InterventionDetailMapper implements ResultSetMapper<InterventionDetail> {
		public InterventionDetailMapper() {
		}
		public InterventionDetail map(int index, ResultSet r, StatementContext ctx) throws SQLException {
			InterventionDetail interventionDetail = new InterventionDetail();
			interventionDetail.setId(r.getLong("id"));
			interventionDetail.setInterventionId(r.getLong("interventionId"));
			interventionDetail.setDescription(r.getString("description"));
			interventionDetail.setDetail(r.getString("detail"));
			return interventionDetail;
		}
	}
	