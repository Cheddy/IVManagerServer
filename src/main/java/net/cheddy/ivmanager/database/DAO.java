package net.cheddy.ivmanager.database;

import java.util.Iterator;

import net.cheddy.ivmanager.model.Hospital;
import net.cheddy.ivmanager.model.Impact;
import net.cheddy.ivmanager.model.Intervention;
import net.cheddy.ivmanager.model.InterventionAction;
import net.cheddy.ivmanager.model.InterventionDetail;
import net.cheddy.ivmanager.model.InterventionOutcome;
import net.cheddy.ivmanager.model.Patient;
import net.cheddy.ivmanager.model.Staff;
import net.cheddy.ivmanager.model.StaffRank;
import net.cheddy.ivmanager.model.Ward;
import net.cheddy.ivmanager.model.mapper.HospitalMapper;
import net.cheddy.ivmanager.model.mapper.ImpactMapper;
import net.cheddy.ivmanager.model.mapper.InterventionActionMapper;
import net.cheddy.ivmanager.model.mapper.InterventionDetailMapper;
import net.cheddy.ivmanager.model.mapper.InterventionMapper;
import net.cheddy.ivmanager.model.mapper.InterventionOutcomeMapper;
import net.cheddy.ivmanager.model.mapper.PatientMapper;
import net.cheddy.ivmanager.model.mapper.StaffMapper;
import net.cheddy.ivmanager.model.mapper.StaffRankMapper;
import net.cheddy.ivmanager.model.mapper.WardMapper;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

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
		WardMapper.class })
public interface DAO {

	@SqlUpdate(value = "CREATE DATABASE IF NOT EXISTS IVManager")
	public void createDatabase();
	
	@SqlUpdate(value = "Use IVManager")
	public void useDatabase();
	
	@SqlUpdate(value = "CREATE TABLE IF NOT EXISTS Interventions (id BIGINT NOT NULL AUTO_INCREMENT, patientId BIGINT NOT NULL, wardId BIGINT NOT NULL, staffId BIGINT NOT NULL, dateTime DATETIME NOT NULL, verified BOOL NOT NULL, verifiedDateTime DATETIME, verifiedStaffId BIGINT, completed BOOL NOT NULL, completedDateTime DATETIME, completedStaffId BIGINT, impactId BIGINT NOT NULL, PRIMARY KEY (id))")
	public void createInterventionTable();

	@SqlUpdate(value = "CREATE TABLE IF NOT EXISTS Staff (id BIGINT NOT NULL AUTO_INCREMENT, username VARCHAR(255) NOT NULL UNIQUE, surname VARCHAR(255) NOT NULL, othernames VARCHAR(255) NOT NULL, passwordHash VARCHAR(32) NOT NULL, passwordSalt VARCHAR(32) NOT NULL, rankId BIGINT NOT NULL, PRIMARY KEY (id))")
	public void createStaffTable();

	@SqlUpdate(value = "CREATE TABLE IF NOT EXISTS StaffRanks (id BIGINT NOT NULL AUTO_INCREMENT, name VARCHAR(255) NOT NULL UNIQUE, permissions INT NOT NULL, PRIMARY KEY (id))")
	public void createStaffRanksTable();

	@SqlUpdate(value = "CREATE TABLE IF NOT EXISTS Patients (id BIGINT NOT NULL AUTO_INCREMENT, rtx BIGINT NOT NULL UNIQUE, surname VARCHAR(1024) NOT NULL, othernames VARCHAR(1024) NOT NULL, dob DATE NOT NULL, PRIMARY KEY (id))")
	public void createPatientsTable();

	@SqlUpdate(value = "CREATE TABLE IF NOT EXISTS Wards (id BIGINT NOT NULL AUTO_INCREMENT, hospitalId BIGINT NOT NULL, name VARCHAR(255) NOT NULL UNIQUE, PRIMARY KEY (id))")
	public void createWardsTable();

	@SqlUpdate(value = "CREATE TABLE IF NOT EXISTS Hospitals (id BIGINT NOT NULL AUTO_INCREMENT, name VARCHAR(255) NOT NULL UNIQUE, PRIMARY KEY (id))")
	public void createHospitalsTable();

	@SqlUpdate(value = "CREATE TABLE IF NOT EXISTS Impacts (id BIGINT NOT NULL AUTO_INCREMENT, name VARCHAR(255) NOT NULL UNIQUE, PRIMARY KEY (id))")
	public void createImpactsTable();

	@SqlUpdate(value = "CREATE TABLE IF NOT EXISTS InterventionActions (id BIGINT NOT NULL AUTO_INCREMENT, interventionId BIGINT NOT NULL, description VARCHAR(1024) NOT NULL, detail TEXT, dateTime DATETIME NOT NULL, PRIMARY KEY (id))")
	public void createActionsTable();

	@SqlUpdate(value = "CREATE TABLE IF NOT EXISTS InterventionOutcomes (id BIGINT NOT NULL AUTO_INCREMENT, interventionId BIGINT NOT NULL, description VARCHAR(1024) NOT NULL, detail TEXT, dateTime DATETIME NOT NULL, PRIMARY KEY (id))")
	public void createOutcomesTable();

	@SqlUpdate(value = "CREATE TABLE IF NOT EXISTS InterventionDetails (id BIGINT NOT NULL AUTO_INCREMENT, interventionId BIGINT NOT NULL, description VARCHAR(1024) NOT NULL, detail TEXT, PRIMARY KEY (id))")
	public void createDetailsTable();

	@SqlQuery("SELECT * FROM Interventions")
	public Iterator<Intervention> allInterventions();

	@SqlQuery("SELECT * FROM Interventions WHERE id = :id")
	public Intervention interventionForId(@Bind("id") long id);

	@SqlQuery("SELECT * FROM Patients")
	public Iterator<Patient> allPatients();
	
	@SqlQuery("SELECT * FROM Patients WHERE id = :id")
	public Patient patientForId(@Bind("id") long id);

	@SqlQuery("SELECT * FROM Hospitals")
	public Iterator<Hospital> allHospitals();

	@SqlQuery("SELECT * FROM Hospitals WHERE id = :id")
	public Hospital hospitalForId(@Bind("id") long id);

	@SqlQuery("SELECT * FROM Wards")
	public Iterator<Ward> allWards();

	@SqlQuery("SELECT * FROM Wards WHERE id = :id")
	public Ward wardForId(@Bind("id") long id);

	@SqlQuery("SELECT * FROM Staff")
	public Iterator<Staff> allStaff();
	
	@SqlQuery("SELECT * FROM Staff WHERE id = :id")
	public Staff staffForId(@Bind("id") long id);

	@SqlQuery("SELECT * FROM Staff WHERE username = :username")
	public Staff staffForUsername(@Bind("username") String username);

	@SqlQuery("SELECT * FROM StaffRanks")
	public Iterator<StaffRank> allStaffRanks();

	@SqlQuery("SELECT * FROM StaffRanks WHERE id = :id")
	public StaffRank staffRankForId(@Bind("id") long id);

	@SqlQuery("SELECT * FROM Impacts")
	public Iterator<Impact> allImpacts();

	@SqlQuery("SELECT * FROM Impacts WHERE id = :id")
	public Impact impactForId(@Bind("id") long id);

	@SqlQuery("SELECT * FROM InterventionActions WHERE interventionId = :interventionId")
	public InterventionAction[] allActionsForId(@Bind("interventionId") long interventionId);

	@SqlQuery("SELECT * FROM InterventionDetails WHERE interventionId = :interventionId")
	public InterventionDetail[] allDetailsForId(@Bind("interventionId") long interventionId);

	@SqlQuery("SELECT * FROM InterventionOutcomes WHERE interventionId = :interventionId")
	public InterventionOutcome[] allOutcomesForId(@Bind("interventionId") long interventionId);

	@SqlUpdate(value = "INSERT INTO Interventions VALUES (default, :patientId, :wardId, :staffId, :dateTime, :verified, :verifiedStaffId, :verifiedDateTime, :completed, :completedStaffId, :completedDateTime, :impactId)")
	public void insertIntervention(@BindBean Intervention intervention);

	@SqlUpdate(value = "UPDATE Interventions SET patientId=:patientId, wardId=:wardId, staffId=staffId, dateTime=:dateTime, verified=:verified, verifiedStaffId=:verifiedStaffId, verifiedDateTime=:verifiedDateTime, completed=:completed, completedStaffId=:completedStaffId, completedDateTime=:completedDateTime, impactId=:impactId WHERE id=:id")
	public void updateIntervention(@BindBean Intervention intervention);

	@SqlUpdate(value = "INSERT INTO Hospitals VALUES (default, :name)")
	public void insertHospital(@BindBean Hospital hospital);

	@SqlUpdate(value = "UPDATE Hospitals SET name=:name WHERE id=:id")
	public void updateHospital(@BindBean Hospital hospital);

	@SqlUpdate(value = "INSERT INTO Impacts VALUES (default, :name)")
	public void insertImpact(@BindBean Impact impact);

	@SqlUpdate(value = "UPDATE Impacts SET name=:name WHERE id=:id")
	public void updateImpact(@BindBean Impact impact);

	@SqlUpdate(value = "INSERT INTO StaffRanks VALUES (default, :name, :permissions)")
	public void insertStaffRank(@BindBean StaffRank staffRank);

	@SqlUpdate(value = "UPDATE StaffRanks SET name=:name, permissions=:permissions WHERE id=:id")
	public void updateStaffRank(@BindBean StaffRank staffRank);

	@SqlUpdate(value = "INSERT INTO Patients VALUES (default, :rtx, :surname, :othernames, :dob)")
	public void insertPatient(@BindBean Patient patient);

	@SqlUpdate(value = "UPDATE Patients SET rtx=:rtx, surname=:surname,othernames=:othernames, dob=:dob WHERE id=:id")
	public void updatePatient(@BindBean Patient patient);

	@SqlUpdate(value = "INSERT INTO InterventionActions VALUES (default, :interventionId, :description, :detail, :dateTime)")
	public void insertInterventionAction(@BindBean InterventionAction interventionAction);

	@SqlUpdate(value = "UPDATE InterventionActions SET interventionId=:interventionId, description=:description,detail=:detail, dateTime=:dateTime WHERE id=:id")
	public void updateInterventionAction(@BindBean InterventionAction interventionAction);

	@SqlUpdate(value = "INSERT INTO InterventionOutcomes VALUES (default, :interventionId, :description, :detail, :dateTime)")
	public void insertInterventionOutcome(@BindBean InterventionOutcome interventionOutcome);

	@SqlUpdate(value = "UPDATE InterventionOutcomes SET interventionId=:interventionId, description=:description,detail=:detail, dateTime=:dateTime WHERE id=:id")
	public void updateInterventionOutcome(@BindBean InterventionOutcome interventionOutcome);

	@SqlUpdate(value = "INSERT INTO InterventionDetails VALUES (default, :interventionId, :description, :detail)")
	public void insertInterventionDetail(@BindBean InterventionDetail interventionDetail);

	@SqlUpdate(value = "UPDATE InterventionDetails SET interventionId=:interventionId, description=:description,detail=:detail WHERE id=:id")
	public void updateInterventionDetail(@BindBean InterventionDetail interventionDetail);

	@SqlUpdate(value = "INSERT INTO Staff VALUES (default, :username, :surname, :othernames, :passwordSalt, :passwordHash, :rankId)")
	public void insertStaff(@BindBean Staff staff);

	@SqlUpdate(value = "UPDATE Staff SET username=:username, surname=:surname, othernames=:othernames, passwordSalt=:passwordSalt, passwordHash=:passwordHash, rankId=:rankId WHERE id=:id")
	public void updateStaff(@BindBean Staff staff);

	@SqlUpdate(value = "INSERT INTO Wards VALUES (default, :hospitalId, :name)")
	public void insertWard(@BindBean Ward ward);

	@SqlUpdate(value = "UPDATE Wards SET hospitalId=:hospitalId, name=:name WHERE id=:id")
	public void updateWard(@BindBean Ward ward);
	
	@SqlUpdate(value = "DELETE FROM Hospitals WHERE id=:id")
	public void deleteHospital(@BindBean Hospital hospital);

	@SqlUpdate(value = "DELETE FROM Impacts WHERE id=:id")
	public void deleteImpact(@BindBean Impact impact);

	@SqlUpdate(value = "DELETE FROM StaffRanks WHERE id=:id")
	public void deleteStaffRank(@BindBean StaffRank staffRank);

	@SqlUpdate(value = "DELETE FROM Wards WHERE id=:id")
	public void deleteWard(@BindBean Ward ward);

	@SqlUpdate(value = "DELETE FROM Interventions WHERE id=:id")
	public void deleteIntervention(@BindBean Intervention intervention);

	@SqlUpdate(value = "DELETE FROM Staff WHERE id=:id")
	public void deleteStaff(@BindBean Staff staff);

	@SqlUpdate(value = "DELETE FROM InterventionActions WHERE id=:id")
	public void deleteInterventionAction(@BindBean InterventionAction interventionAction);

	@SqlUpdate(value = "DELETE FROM InterventionOutcomes WHERE id=:id")
	public void deleteInterventionOutcome(@BindBean InterventionOutcome interventionOutcome);

	@SqlUpdate(value = "DELETE FROM InterventionDetails WHERE id=:id")
	public void deleteInterventionDetail(@BindBean InterventionDetail interventionDetail);

	@SqlUpdate(value = "DELETE FROM Patients WHERE id=:id")
	public void deletePatient(@BindBean Patient patient);
}
