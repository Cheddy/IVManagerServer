package net.cheddy.ivmanager.model;

import org.joda.time.DateTime;


public class Intervention {
	
	private long id;
	private Patient patient;
	private Staff staff;
	private InterventionDetail[] details;
	private InterventionAction[] actions;
	private InterventionOutcome[] outcomes;
	private boolean verified;
	private DateTime verifiedDateTime;
	private Staff verifiedStaff;
	private boolean completed;
	private DateTime completedDateTime;
	private Staff completedStaff;
	private Impact impact;
	
	public Intervention() {

	}
	
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
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
	 * @return the staff
	 */
	public Staff getStaff() {
		return staff;
	}

	/**
	 * @param staff the staff to set
	 */
	public void setStaff(Staff staff) {
		this.staff = staff;
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
	public DateTime getVerifiedDateTime() {
		return verifiedDateTime;
	}

	/**
	 * @param verifiedDateTime the verifiedDateTime to set
	 */
	public void setVerifiedDateTime(DateTime verifiedDateTime) {
		this.verifiedDateTime = verifiedDateTime;
	}

	/**
	 * @return the verifiedStaff
	 */
	public Staff getVerifiedStaff() {
		return verifiedStaff;
	}

	/**
	 * @param verifiedStaff the verifiedStaff to set
	 */
	public void setVerifiedStaff(Staff verifiedStaff) {
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
	public DateTime getCompletedDateTime() {
		return completedDateTime;
	}

	/**
	 * @param completedDateTime the completedDateTime to set
	 */
	public void setCompletedDateTime(DateTime completedDateTime) {
		this.completedDateTime = completedDateTime;
	}

	/**
	 * @return the completedStaff
	 */
	public Staff getCompletedStaff() {
		return completedStaff;
	}

	/**
	 * @param completedStaff the completedStaff to set
	 */
	public void setCompletedStaff(Staff completedStaff) {
		this.completedStaff = completedStaff;
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

