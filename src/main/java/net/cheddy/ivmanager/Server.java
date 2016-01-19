package net.cheddy.ivmanager;

import com.google.common.cache.CacheBuilderSpec;
import io.dropwizard.Application;
import io.dropwizard.auth.AuthFactory;
import io.dropwizard.auth.CachingAuthenticator;
import io.dropwizard.auth.basic.BasicAuthFactory;
import io.dropwizard.auth.basic.BasicCredentials;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import net.cheddy.ivmanager.auth.CustomAuthenticator;
import net.cheddy.ivmanager.auth.UserSession;
import net.cheddy.ivmanager.config.Configuration;
import net.cheddy.ivmanager.database.DAO;
import net.cheddy.ivmanager.service.*;
import org.skife.jdbi.v2.DBI;

import java.io.File;

/**
 * @author : Cheddy
 */
public class Server extends Application<Configuration> {

	private static CachingAuthenticator<BasicCredentials, UserSession> authenticator;

	public static void main(String[] args) throws Exception {
		File file = new File("config.yaml");
		if(!file.exists()){
			file.createNewFile();
		}
		new Server().run(new String[] {"server", "config.yaml"});
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
		setAuthenticator(new CachingAuthenticator<BasicCredentials, UserSession>(environment.metrics(), new CustomAuthenticator(dao), CacheBuilderSpec.parse(configuration.getCacheBuilderSpec())));
		environment.jersey().register(AuthFactory.binder(new BasicAuthFactory<>(getAuthenticator(), "Authentication Required!", UserSession.class)));
		environment.jersey().register(new InterventionService(dao));
		environment.jersey().register(new HospitalService(dao));
		environment.jersey().register(new LoginService(dao));
		environment.jersey().register(new StaffRankService(dao));
		environment.jersey().register(new PatientService(dao));
		environment.jersey().register(new WardService(dao));
		environment.jersey().register(new StaffService(dao));
		environment.jersey().register(new ImpactService(dao));
		environment.jersey().register(new InterventionActionService(dao));
		environment.jersey().register(new InterventionDetailService(dao));
		environment.jersey().register(new InterventionOutcomeService(dao));
	}

	public static CachingAuthenticator<BasicCredentials, UserSession> getAuthenticator() {
		return authenticator;
	}

	public static void setAuthenticator(CachingAuthenticator<BasicCredentials, UserSession> authenticator) {
		Server.authenticator = authenticator;
	}
}
