package net.cheddy.ivmanager;

import io.dropwizard.Application;
import io.dropwizard.auth.AuthFactory;
import io.dropwizard.auth.basic.BasicAuthFactory;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import net.cheddy.ivmanager.auth.CustomAuthenticator;
import net.cheddy.ivmanager.auth.UserSession;
import net.cheddy.ivmanager.config.Configuration;
import net.cheddy.ivmanager.database.DAO;
import net.cheddy.ivmanager.service.*;
import org.skife.jdbi.v2.DBI;

/**
 * @author : Cheddy
 */
public class Server extends Application<Configuration> {

	public static void main(String[] args) throws Exception {
		new Server().run(args);
	}

	@Override
	public String getName() {
		return "Intervention Manager Server";
	}

	@Override
	public void initialize(Bootstrap<Configuration> bootstrap) {
	}

	@Override
	public void run(Configuration configuration, Environment environment) {
		final DBIFactory factory = new DBIFactory();
		final DBI jdbi = factory.build(environment, configuration.getDatabase(), "mysql");

		final DAO dao = jdbi.onDemand(DAO.class);
		dao.createDatabase();
		dao.useDatabase();
		dao.createInterventionTable();
		dao.createActionsTable();
		dao.createDetailsTable();
		dao.createHospitalsTable();
		dao.createImpactsTable();
		dao.createOutcomesTable();
		dao.createPatientsTable();
		dao.createStaffRanksTable();
		dao.createStaffTable();
		dao.createWardsTable();
		environment.jersey().register(AuthFactory.binder(new BasicAuthFactory<>(new CustomAuthenticator(dao), "Authentication Required!", UserSession.class)));
		environment.jersey().register(new InterventionService(dao));
		environment.jersey().register(new HospitalService(dao));
		environment.jersey().register(new StaffRankService(dao));
		environment.jersey().register(new PatientService(dao));
		environment.jersey().register(new WardService(dao));
		environment.jersey().register(new StaffService(dao));
		environment.jersey().register(new ImpactService(dao));

	}
}
