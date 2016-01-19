package net.cheddy.ivmanager.config;

import io.dropwizard.db.DataSourceFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;


public class Configuration extends io.dropwizard.Configuration {

	@Valid
	@NotNull
	private DataSourceFactory database = new DataSourceFactory() {
		@Override
		public String getDriverClass() {
			return "com.mysql.jdbc.Driver";
		}

		public String getUrl() {
			return "jdbc:mysql://localhost:3306/IVManager";
		}

		public String getUser() {
			return "root";
		}

		public String getPassword() {
			return "123eRTy789!)";
		}
	};

	@Valid
	@NotNull
	private String cacheBuilderSpec = "maximumSize=10000, expireAfterAccess=10m";

	/**
	 * @return the database
	 */
	public DataSourceFactory getDatabase() {
		return database;
	}

	/**
	 * @param database the database to set
	 */
	public void setDatabase(DataSourceFactory database) {
		this.database = database;
	}

	/**
	 * @return the cacheBuilderSpec
	 */
	public String getCacheBuilderSpec() {
		return cacheBuilderSpec;
	}

	/**
	 * @param cacheBuilderSpec the cacheBuilderSpec to set
	 */
	public void setCacheBuilderSpec(String cacheBuilderSpec) {
		this.cacheBuilderSpec = cacheBuilderSpec;
	}


}
