package net.cheddy.ivmanager.model.complete;

import javax.validation.Valid;

import net.cheddy.ivmanager.database.DAO;
import net.cheddy.ivmanager.model.Impact;
import net.cheddy.ivmanager.model.Intervention;
import net.cheddy.ivmanager.model.InterventionAction;
import net.cheddy.ivmanager.model.InterventionDetail;
import net.cheddy.ivmanager.model.InterventionOutcome;
import net.cheddy.ivmanager.model.Patient;

import org.joda.time.DateTime;

public class CompleteIntervention {

		private long id;
		private Patient patient;
		private CompleteWard ward;
		private CompleteStaff staff;
		private DateTime dateTime;
		private boolean verified;
		private DateTime verifiedDateTime;
		private CompleteStaff verifiedStaff;
		private boolean completed;
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
			this.actions = dao.allActionsForId(id);
			this.details = dao.allDetailsForId(id);
			this.outcomes = dao.allOutcomesForId(id);
			this.patient = dao.patientForId(intervention.getPatientId());
			this.staff = new CompleteStaff(dao, intervention.getStaffId());
			this.impact = dao.impactForId(intervention.getImpactId());
			this.ward = new CompleteWard(dao, intervention.getWardId());
			if(intervention.isCompleted()){
				this.completedDateTime = intervention.getCompletedDateTime();
				this.completed = true;
				this.completedStaff = new CompleteStaff(dao, intervention.getCompletedStaffId());
			}
			if(intervention.isVerified()){
				this.verifiedDateTime = intervention.getVerifiedDateTime();
				this.verified = true;
				this.verifiedStaff = new CompleteStaff(dao, intervention.getVerifiedStaffId());
			}
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
		public DateTime getDateTime() {
			return dateTime;
		}

		/**
		 * @param dateTime the dateTime to set
		 */
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
