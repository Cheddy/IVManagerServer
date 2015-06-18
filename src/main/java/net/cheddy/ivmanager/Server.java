package net.cheddy.ivmanager;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import net.cheddy.ivmanager.config.Configuration;
import net.cheddy.ivmanager.service.InterventionService;

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
    public void run(Configuration configuration,
                    Environment environment) {
        environment.jersey().register(new InterventionService());
    }

}
