package net.cheddy.ivmanager;

import com.google.common.cache.CacheBuilderSpec;
import io.dropwizard.Application;
import io.dropwizard.auth.AuthFactory;
import io.dropwizard.auth.CachingAuthenticator;
import io.dropwizard.auth.basic.BasicAuthFactory;
import io.dropwizard.auth.basic.BasicCredentials;
import io.dropwizard.bundles.redirect.HttpsRedirect;
import io.dropwizard.bundles.redirect.RedirectBundle;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import net.cheddy.ivmanager.auth.AuthUtils;
import net.cheddy.ivmanager.auth.CustomAuthenticator;
import net.cheddy.ivmanager.auth.UserSession;
import net.cheddy.ivmanager.config.Configuration;
import net.cheddy.ivmanager.config.InstallCommand;
import net.cheddy.ivmanager.database.DAO;
import net.cheddy.ivmanager.logging.Logger;
import net.cheddy.ivmanager.model.StaffRank;
import net.cheddy.ivmanager.model.complete.CompleteStaff;
import net.cheddy.ivmanager.service.*;
import org.skife.jdbi.v2.DBI;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

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
		new Server().run("server", "config.yaml");
	}

	@Override
	public String getName() {
		return "Intervention Manager Server";
	}

	@Override
	public void initialize(Bootstrap<Configuration> bootstrap) {
        bootstrap.addBundle(new RedirectBundle(
				new HttpsRedirect()
		));
	}

	@Override
	public void run(Configuration configuration, Environment environment) {
		final DBIFactory factory = new DBIFactory();

        if(configuration.install()) {
            DBI jdbiInstall = factory.build(environment, configuration.getInstallDatabase(), "mysql");
            DAO daoInstall = jdbiInstall.onDemand(DAO.class);
            install(daoInstall);
            System.out.println("Install Complete - Change configuration to disable install and then restart the application!");
            System.exit(0);
        }

        final DBI jdbi = factory.build(environment, configuration.getDatabase(), "mysql");
		final DAO dao = jdbi.onDemand(DAO.class);

		Logger.dao = dao;
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
		environment.jersey().register(new LogService(dao));
        environment.jersey().register(new AuditService(dao));
    }

	public static void install(DAO dao){
        dao.dropDatabase();
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
        dao.createLogsTable();
        dao.createStaffTable();
        dao.createWardsTable();

        StaffRank rank = new StaffRank();
		rank.setName("Admin");
		rank.setPermissions(9007199254740991L);
		rank.setId(dao.insertStaffRank(rank));

		CompleteStaff completeStaff = new CompleteStaff();
		completeStaff.setSurname("User");
		completeStaff.setOthernames("Admin");
		completeStaff.setRank(rank);
		completeStaff.setUsername("Admin");
		completeStaff.setPasswordHash("5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8");
		dao.insertStaff(completeStaff.toStaff(dao));
	}

	public static CachingAuthenticator<BasicCredentials, UserSession> getAuthenticator() {
		return authenticator;
	}

	public static void setAuthenticator(CachingAuthenticator<BasicCredentials, UserSession> authenticator) {
		Server.authenticator = authenticator;
	}
}
