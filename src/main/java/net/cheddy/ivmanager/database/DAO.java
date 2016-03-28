package net.cheddy.ivmanager.database;

import net.cheddy.ivmanager.model.*;
import net.cheddy.ivmanager.model.mapper.*;
import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.Iterator;


@RegisterMapper({
		InterventionMapper.class,
		HospitalMapper.class,
		ImpactMapper.class,
		InterventionActionMapper.class,
		InterventionOutcomeMapper.class,
		InterventionDetailMapper.class,
		PatientMapper.class,
		StaffMapper.class,
		StaffRankMapper.class,
		WardMapper.class,
		LogMapper.class})
public interface DAO {

    @SqlUpdate(value = "DROP DATABASE IVManager")
    void dropDatabase();

    @SqlUpdate(value = "CREATE DATABASE IF NOT EXISTS IVManager")
	void createDatabase();

	@SqlUpdate(value = "Use IVManager")
	void useDatabase();

	@SqlUpdate(value = "CREATE TABLE IF NOT EXISTS Interventions (id BIGINT NOT NULL AUTO_INCREMENT, patientId BIGINT NOT NULL, wardId BIGINT NOT NULL, staffId BIGINT NOT NULL, dateTime DATETIME NOT NULL, verified BOOL NOT NULL, verifiedDateTime DATETIME, verifiedStaffId BIGINT, completed BOOL NOT NULL, completedDateTime DATETIME, completedStaffId BIGINT, impactId BIGINT NOT NULL, PRIMARY KEY (id))")
	void createInterventionTable();

	@SqlUpdate(value = "CREATE TABLE IF NOT EXISTS Staff (id BIGINT NOT NULL AUTO_INCREMENT, username VARCHAR(255) NOT NULL UNIQUE, surname VARCHAR(255) NOT NULL, othernames VARCHAR(255) NOT NULL, passwordHash VARCHAR(32) CHARACTER  SET utf8 COLLATE utf8_general_ci NOT NULL, passwordSalt VARCHAR(32) CHARACTER  SET utf8 COLLATE utf8_general_ci NOT NULL, rankId BIGINT NOT NULL, PRIMARY KEY (id))")
	void createStaffTable();

	@SqlUpdate(value = "CREATE TABLE IF NOT EXISTS Logs (id BIGINT NOT NULL AUTO_INCREMENT, staffId BIGINT NOT NULL, opcode INT NOT NULL, description VARCHAR(255) NOT NULL, oldData TEXT, newData TEXT, timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP, PRIMARY KEY (id))")
	void createLogsTable();

	@SqlUpdate(value = "CREATE TABLE IF NOT EXISTS StaffRanks (id BIGINT NOT NULL AUTO_INCREMENT, name VARCHAR(255) NOT NULL UNIQUE, permissions BIGINT NOT NULL, PRIMARY KEY (id))")
	void createStaffRanksTable();

	@SqlUpdate(value = "CREATE TABLE IF NOT EXISTS Patients (id BIGINT NOT NULL AUTO_INCREMENT, rtx BIGINT NOT NULL UNIQUE, surname VARCHAR(1024) NOT NULL, othernames VARCHAR(1024) NOT NULL, dob DATE NOT NULL, PRIMARY KEY (id))")
	void createPatientsTable();

	@SqlUpdate(value = "CREATE TABLE IF NOT EXISTS Wards (id BIGINT NOT NULL AUTO_INCREMENT, hospitalId BIGINT NOT NULL, name VARCHAR(255) NOT NULL UNIQUE, PRIMARY KEY (id))")
	void createWardsTable();

	@SqlUpdate(value = "CREATE TABLE IF NOT EXISTS Hospitals (id BIGINT NOT NULL AUTO_INCREMENT, name VARCHAR(255) NOT NULL UNIQUE, PRIMARY KEY (id))")
	void createHospitalsTable();

	@SqlUpdate(value = "CREATE TABLE IF NOT EXISTS Impacts (id BIGINT NOT NULL AUTO_INCREMENT, name VARCHAR(255) NOT NULL UNIQUE, PRIMARY KEY (id))")
	void createImpactsTable();

	@SqlUpdate(value = "CREATE TABLE IF NOT EXISTS InterventionActions (id BIGINT NOT NULL AUTO_INCREMENT, interventionId BIGINT NOT NULL, description VARCHAR(1024) NOT NULL, detail TEXT, dateTime DATETIME NOT NULL, PRIMARY KEY (id))")
	void createActionsTable();

	@SqlUpdate(value = "CREATE TABLE IF NOT EXISTS InterventionOutcomes (id BIGINT NOT NULL AUTO_INCREMENT, interventionId BIGINT NOT NULL, description VARCHAR(1024) NOT NULL, detail TEXT, dateTime DATETIME NOT NULL, PRIMARY KEY (id))")
	void createOutcomesTable();

	@SqlUpdate(value = "CREATE TABLE IF NOT EXISTS InterventionDetails (id BIGINT NOT NULL AUTO_INCREMENT, interventionId BIGINT NOT NULL, description VARCHAR(1024) NOT NULL, detail TEXT, PRIMARY KEY (id))")
	void createDetailsTable();

	@SqlQuery("SELECT * FROM Interventions ORDER BY dateTime")
	Iterator<Intervention> allInterventions();

	@SqlQuery("SELECT * FROM Logs ORDER BY timestamp DESC")
	Iterator<Log> allLogs();

	@SqlQuery("SELECT * FROM Interventions WHERE id = :id")
	Intervention interventionForId(@Bind("id") long id);

	@SqlQuery("SELECT * FROM Patients ORDER BY rtx")
	Iterator<Patient> allPatients();

	@SqlQuery("SELECT * FROM Patients WHERE id = :id")
	Patient patientForId(@Bind("id") long id);

	@SqlQuery("SELECT * FROM Hospitals ORDER BY name")
	Iterator<Hospital> allHospitals();

	@SqlQuery("SELECT * FROM Hospitals WHERE id = :id")
	Hospital hospitalForId(@Bind("id") long id);

	@SqlQuery("SELECT * FROM Wards ORDER BY name")
	Iterator<Ward> allWards();

	@SqlQuery("SELECT * FROM Wards WHERE id = :id")
	Ward wardForId(@Bind("id") long id);

	@SqlQuery("SELECT * FROM Staff ORDER BY surname")
	Iterator<Staff> allStaff();

	@SqlQuery("SELECT * FROM Staff WHERE id = :id")
	Staff staffForId(@Bind("id") long id);

	@SqlQuery("SELECT * FROM Staff WHERE username = :username")
	Staff staffForUsername(@Bind("username") String username);

	@SqlQuery("SELECT * FROM StaffRanks ORDER BY name")
	Iterator<StaffRank> allStaffRanks();

	@SqlQuery("SELECT * FROM StaffRanks WHERE id = :id")
	StaffRank staffRankForId(@Bind("id") long id);

	@SqlQuery("SELECT * FROM Impacts ORDER BY name")
	Iterator<Impact> allImpacts();

	@SqlQuery("SELECT * FROM Impacts WHERE id = :id")
	Impact impactForId(@Bind("id") long id);

	@SqlQuery("SELECT * FROM InterventionActions WHERE interventionId = :interventionId")
	Iterator<InterventionAction> allActionsForId(@Bind("interventionId") long interventionId);

	@SqlQuery("SELECT * FROM InterventionDetails WHERE interventionId = :interventionId")
	Iterator<InterventionDetail> allDetailsForId(@Bind("interventionId") long interventionId);

	@SqlQuery("SELECT * FROM InterventionOutcomes WHERE interventionId = :interventionId")
	Iterator<InterventionOutcome> allOutcomesForId(@Bind("interventionId") long interventionId);

	@SqlUpdate(value = "INSERT INTO Interventions VALUES (default, :patientId, :wardId, :staffId, :dateTime, :verified, :verifiedStaffId, :verifiedDateTime, :completed, :completedStaffId, :completedDateTime, :impactId)")
	@GetGeneratedKeys
	long insertIntervention(@BindBean Intervention intervention);

	@SqlUpdate(value = "UPDATE Interventions SET patientId=:patientId, wardId=:wardId, staffId=staffId, dateTime=:dateTime, verified=:verified, verifiedStaffId=:verifiedStaffId, verifiedDateTime=:verifiedDateTime, completed=:completed, completedStaffId=:completedStaffId, completedDateTime=:completedDateTime, impactId=:impactId WHERE id=:id")
	void updateIntervention(@BindBean Intervention intervention);

	@SqlUpdate(value = "INSERT INTO Hospitals VALUES (default, :name)")
	@GetGeneratedKeys
	long insertHospital(@BindBean Hospital hospital);

	@SqlUpdate(value = "UPDATE Hospitals SET name=:name WHERE id=:id")
	void updateHospital(@BindBean Hospital hospital);

	@SqlUpdate(value = "INSERT INTO Impacts VALUES (default, :name)")
	@GetGeneratedKeys
	long insertImpact(@BindBean Impact impact);

	@SqlUpdate(value = "UPDATE Impacts SET name=:name WHERE id=:id")
	void updateImpact(@BindBean Impact impact);

	@SqlUpdate(value = "INSERT INTO Logs VALUES (default, :staffId, :opcode, :description, :oldData, :newData, default)")
	@GetGeneratedKeys
	long insertLog(@BindBean Log log);

	@SqlUpdate(value = "INSERT INTO StaffRanks VALUES (default, :name, :permissions)")
	@GetGeneratedKeys
	long insertStaffRank(@BindBean StaffRank staffRank);

	@SqlUpdate(value = "UPDATE StaffRanks SET name=:name, permissions=:permissions WHERE id=:id")
	void updateStaffRank(@BindBean StaffRank staffRank);

	@SqlUpdate(value = "INSERT INTO Patients VALUES (default, :rtx, :surname, :othernames, :dob)")
	@GetGeneratedKeys
	long insertPatient(@BindBean Patient patient);

	@SqlUpdate(value = "UPDATE Patients SET rtx=:rtx, surname=:surname,othernames=:othernames, dob=:dob WHERE id=:id")
	void updatePatient(@BindBean Patient patient);

	@SqlUpdate(value = "INSERT INTO InterventionActions VALUES (default, :interventionId, :description, :detail, :dateTime)")
	void insertInterventionAction(@BindBean InterventionAction interventionAction);

	@SqlUpdate(value = "UPDATE InterventionActions SET interventionId=:interventionId, description=:description,detail=:detail, dateTime=:dateTime WHERE id=:id")
	void updateInterventionAction(@BindBean InterventionAction interventionAction);

	@SqlUpdate(value = "INSERT INTO InterventionOutcomes VALUES (default, :interventionId, :description, :detail, :dateTime)")
	void insertInterventionOutcome(@BindBean InterventionOutcome interventionOutcome);

	@SqlUpdate(value = "UPDATE InterventionOutcomes SET interventionId=:interventionId, description=:description,detail=:detail, dateTime=:dateTime WHERE id=:id")
	void updateInterventionOutcome(@BindBean InterventionOutcome interventionOutcome);

	@SqlUpdate(value = "INSERT INTO InterventionDetails VALUES (default, :interventionId, :description, :detail)")
	void insertInterventionDetail(@BindBean InterventionDetail interventionDetail);

	@SqlUpdate(value = "UPDATE InterventionDetails SET interventionId=:interventionId, description=:description,detail=:detail WHERE id=:id")
	void updateInterventionDetail(@BindBean InterventionDetail interventionDetail);

	@SqlUpdate(value = "INSERT INTO Staff VALUES (default, :username, :surname, :othernames, :passwordHash, :passwordSalt, :rankId)")
	@GetGeneratedKeys
	long insertStaff(@BindBean Staff staff);

	@SqlUpdate(value = "UPDATE Staff SET username=:username, surname=:surname, othernames=:othernames, passwordSalt=:passwordSalt, passwordHash=:passwordHash, rankId=:rankId WHERE id=:id")
	void updateStaff(@BindBean Staff staff);

	@SqlUpdate(value = "INSERT INTO Wards VALUES (default, :hospitalId, :name)")
	@GetGeneratedKeys
	long insertWard(@BindBean Ward ward);

	@SqlUpdate(value = "UPDATE Wards SET hospitalId=:hospitalId, name=:name WHERE id=:id")
	void updateWard(@BindBean Ward ward);

	@SqlUpdate(value = "DELETE FROM Hospitals WHERE id=:id")
	void deleteHospital(@BindBean Hospital hospital);

	@SqlUpdate(value = "DELETE FROM Impacts WHERE id=:id")
	void deleteImpact(@BindBean Impact impact);

	@SqlUpdate(value = "DELETE FROM StaffRanks WHERE id=:id")
	void deleteStaffRank(@BindBean StaffRank staffRank);

	@SqlUpdate(value = "DELETE FROM Wards WHERE id=:id")
	void deleteWard(@BindBean Ward ward);

	@SqlUpdate(value = "DELETE FROM Interventions WHERE id=:id")
	void deleteIntervention(@BindBean Intervention intervention);

	@SqlUpdate(value = "DELETE FROM Staff WHERE id=:id")
	void deleteStaff(@BindBean Staff staff);

	@SqlUpdate(value = "DELETE FROM InterventionActions WHERE id=:id")
	void deleteInterventionAction(@BindBean InterventionAction interventionAction);

	@SqlUpdate(value = "DELETE FROM InterventionOutcomes WHERE id=:id")
	void deleteInterventionOutcome(@BindBean InterventionOutcome interventionOutcome);

	@SqlUpdate(value = "DELETE FROM InterventionDetails WHERE id=:id")
	void deleteInterventionDetail(@BindBean InterventionDetail interventionDetail);

	@SqlUpdate(value = "DELETE FROM InterventionActions WHERE interventionId=:id")
	void deleteAllInterventionActionsForIntervention(@BindBean Intervention intervention);

	@SqlUpdate(value = "DELETE FROM InterventionDetails WHERE interventionId=:id")
	void deleteAllInterventionDetailsForIntervention(@BindBean Intervention intervention);

	@SqlUpdate(value = "DELETE FROM InterventionOutcomes WHERE interventionId=:id")
	void deleteAllInterventionOutcomesForIntervention(@BindBean Intervention intervention);

	@SqlUpdate(value = "DELETE FROM Patients WHERE id=:id")
	void deletePatient(@BindBean Patient patient);

	@SqlUpdate(value = "DELETE FROM Logs")
	void clearLogs();

}
