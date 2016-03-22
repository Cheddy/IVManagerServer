package net.cheddy.ivmanager.model.complete;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import net.cheddy.ivmanager.database.DAO;
import net.cheddy.ivmanager.logging.Storable;
import net.cheddy.ivmanager.model.*;
import net.cheddy.ivmanager.model.mapper.DateTimeSerialiser;
import org.joda.time.DateTime;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Iterator;


public class CompleteIntervention implements Storable {

	private long id = -1;
	private Patient patient;
	private CompleteWard ward;
	private CompleteStaff staff;
	@JsonSerialize(using=DateTimeSerialiser.class)
	private DateTime dateTime;
	private boolean verified = false;
	@JsonSerialize(using=DateTimeSerialiser.class)
	private DateTime verifiedDateTime;
	private CompleteStaff verifiedStaff;
	private boolean completed = false;
	@JsonSerialize(using=DateTimeSerialiser.class)
	private DateTime completedDateTime;
	private CompleteStaff completedStaff;
	private InterventionDetail[] details;
	private InterventionAction[] actions;
	private InterventionOutcome[] outcomes;
	private Impact impact;

	public CompleteIntervention(DAO dao, long id) {
		this(dao, dao.interventionForId(id));
	}

	public CompleteIntervention() {
	}

	public CompleteIntervention(DAO dao, @Valid Intervention intervention) {
		this.id = intervention.getId();
		this.dateTime = intervention.getDateTime();
		this.patient = dao.patientForId(intervention.getPatientId());
		this.staff = new CompleteStaff(dao, intervention.getStaffId());
		this.impact = dao.impactForId(intervention.getImpactId());
		this.ward = new CompleteWard(dao, intervention.getWardId());
		if (intervention.isCompleted()) {
			this.completedDateTime = intervention.getCompletedDateTime();
			this.completed = true;
			this.completedStaff = new CompleteStaff(dao, intervention.getCompletedStaffId());
		}
		if (intervention.isVerified()) {
			this.verifiedDateTime = intervention.getVerifiedDateTime();
			this.verified = true;
			this.verifiedStaff = new CompleteStaff(dao, intervention.getVerifiedStaffId());
		}

		Iterator<InterventionDetail> itd = dao.allDetailsForId(this.id);
		final ArrayList<InterventionDetail> details1 = new ArrayList<>();
		itd.forEachRemaining(t -> details1.add(t));
		this.details =  details1.toArray(new InterventionDetail[details1.size()]);

		Iterator<InterventionAction> ita = dao.allActionsForId(this.id);
		final ArrayList<InterventionAction> actions1 = new ArrayList<>();
		ita.forEachRemaining(t -> actions1.add(t));
		this.actions =  actions1.toArray(new InterventionAction[actions1.size()]);

		Iterator<InterventionOutcome> ito = dao.allOutcomesForId(this.id);
		final ArrayList<InterventionOutcome> outcomes1 = new ArrayList<>();
		ito.forEachRemaining(t -> outcomes1.add(t));
		this.outcomes =  outcomes1.toArray(new InterventionOutcome[outcomes1.size()]);
	}

	public Intervention toIntervention() {
		Intervention intervention = new Intervention();
		intervention.setCompleted(completed);
		intervention.setVerified(verified);
		if (completed) {
			intervention.setCompletedDateTime(completedDateTime);
			if (completedStaff != null)
				intervention.setCompletedStaffId(completedStaff.getId());
		}
		if (verified) {
			intervention.setVerifiedDateTime(verifiedDateTime);
			if (verifiedStaff != null)
				intervention.setVerifiedStaffId(verifiedStaff.getId());
		}
		intervention.setDateTime(dateTime);
		intervention.setId(id);
		if (staff != null)
			intervention.setStaffId(staff.getId());
		if(impact != null)
			intervention.setImpactId(impact.getId());
		if(patient != null)
			intervention.setPatientId(patient.getId());
		if(ward != null)
			intervention.setWardId(ward.getId());
		return intervention;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	@Override
	public int getType() {
		return 18;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the patient
	 */
	public Patient getPatient() {
		return patient;
	}

	/**
	 * @param patient the patient to set
	 */
	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	/**
	 * @return the ward
	 */
	public CompleteWard getWard() {
		return ward;
	}

	/**
	 * @param ward the ward to set
	 */
	public void setWard(CompleteWard ward) {
		this.ward = ward;
	}

	/**
	 * @return the staff
	 */
	public CompleteStaff getStaff() {
		return staff;
	}

	/**
	 * @param staff the staff to set
	 */
	public void setStaff(CompleteStaff staff) {
		this.staff = staff;
	}

	/**
	 * @return the dateTime
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public DateTime getDateTime() {
		return dateTime;
	}

	/**
	 * @param dateTime the dateTime to set
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public void setDateTime(DateTime dateTime) {
		this.dateTime = dateTime;
	}

	/**
	 * @return the verified
	 */
	public boolean isVerified() {
		return verified;
	}

	/**
	 * @param verified the verified to set
	 */
	public void setVerified(boolean verified) {
		this.verified = verified;
	}

	/**
	 * @return the verifiedDateTime
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public DateTime getVerifiedDateTime() {
		return verifiedDateTime;
	}

	/**
	 * @param verifiedDateTime the verifiedDateTime to set
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public void setVerifiedDateTime(DateTime verifiedDateTime) {
		this.verifiedDateTime = verifiedDateTime;
	}

	/**
	 * @return the verifiedStaff
	 */
	public CompleteStaff getVerifiedStaff() {
		return verifiedStaff;
	}

	/**
	 * @param verifiedStaff the verifiedStaff to set
	 */
	public void setVerifiedStaff(CompleteStaff verifiedStaff) {
		this.verifiedStaff = verifiedStaff;
	}

	/**
	 * @return the completed
	 */
	public boolean isCompleted() {
		return completed;
	}

	/**
	 * @param completed the completed to set
	 */
	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	/**
	 * @return the completedDateTime
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public DateTime getCompletedDateTime() {
		return completedDateTime;
	}

	/**
	 * @param completedDateTime the completedDateTime to set
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public void setCompletedDateTime(DateTime completedDateTime) {
		this.completedDateTime = completedDateTime;
	}

	/**
	 * @return the completedStaff
	 */
	public CompleteStaff getCompletedStaff() {
		return completedStaff;
	}

	/**
	 * @param completedStaff the completedStaff to set
	 */
	public void setCompletedStaff(CompleteStaff completedStaff) {
		this.completedStaff = completedStaff;
	}

	/**
	 * @return the details
	 */
	public InterventionDetail[] getDetails() {
		return details;
	}

	/**
	 * @param details the details to set
	 */
	public void setDetails(InterventionDetail[] details) {
		this.details = details;
	}

	/**
	 * @return the actions
	 */
	public InterventionAction[] getActions() {
		return actions;
	}

	/**
	 * @param actions the actions to set
	 */
	public void setActions(InterventionAction[] actions) {
		this.actions = actions;
	}

	/**
	 * @return the outcomes
	 */
	public InterventionOutcome[] getOutcomes() {
		return outcomes;
	}

	/**
	 * @param outcomes the outcomes to set
	 */
	public void setOutcomes(InterventionOutcome[] outcomes) {
		this.outcomes = outcomes;
	}

	/**
	 * @return the impact
	 */
	public Impact getImpact() {
		return impact;
	}

	/**
	 * @param impact the impact to set
	 */
	public void setImpact(Impact impact) {
		this.impact = impact;
	}

}
