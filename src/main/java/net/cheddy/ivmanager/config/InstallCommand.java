package net.cheddy.ivmanager.config;

import io.dropwizard.cli.Command;
import io.dropwizard.cli.ConfiguredCommand;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import net.cheddy.ivmanager.Server;
import net.sourceforge.argparse4j.inf.Namespace;
import net.sourceforge.argparse4j.inf.Subparser;

public class InstallCommand extends ConfiguredCommand<Configuration> {
  public InstallCommand() {
    super("install", "Installs the database");
  }

  @Override
  public void configure(Subparser subparser) {
    subparser.addArgument("-i", "--install")
            .required(false).dest("install")
      .help("");
  }

    @Override
    protected void run(Bootstrap<Configuration> bootstrap, Namespace namespace, Configuration configuration) throws Exception {
        configuration.setInstall(true);
    }
}