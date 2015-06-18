package net.cheddy.ivmanager.database;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;

public interface HospitalDAO {

	@SqlUpdate(value = "Create Table Hospitals (id BIGINT NOT NULL AUTO_INCREMENT, name VARCHAR(1024), PRIMARY KEY (id))")
	public void createTable();

	@SqlQuery("select name from Hospitals where id = :id")
	public String findNameById(@Bind("id") long id);

}
