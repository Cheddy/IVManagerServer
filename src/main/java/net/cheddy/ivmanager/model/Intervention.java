package net.cheddy.ivmanager.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import net.cheddy.ivmanager.logging.Storable;
import org.joda.time.DateTime;

import javax.validation.constraints.NotNull;

public class Intervention implements Storable {

	private long id = -1;
	@NotNull
	private long patientId;
	@NotNull
	private long wardId;
	@NotNull
	private long staffId;
	@NotNull
	private DateTime dateTime;
	@NotNull
	private boolean verified;
	private DateTime verifiedDateTime;
	private long verifiedStaffId;
	@NotNull
	private boolean completed;
	private DateTime completedDateTime;
	private long completedStaffId;
	@NotNull
	private long impactId;

	public Intervention() {

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
	 * @return the patientId
	 */
	public long getPatientId() {
		return patientId;
	}

	/**
	 * @param patientId the patientId to set
	 */
	public void setPatientId(long patientId) {
		this.patientId = patientId;
	}

	/**
	 * @return the wardId
	 */
	public long getWardId() {
		return wardId;
	}

	/**
	 * @param wardId the wardId to set
	 */
	public void setWardId(long wardId) {
		this.wardId = wardId;
	}

	/**
	 * @return the staffId
	 */
	public long getStaffId() {
		return staffId;
	}

	/**
	 * @param staffId the staffId to set
	 */
	public void setStaffId(long staffId) {
		this.staffId = staffId;
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
	 * @return the verifiedStaffId
	 */
	public long getVerifiedStaffId() {
		return verifiedStaffId;
	}

	/**
	 * @param verifiedStaffId the verifiedStaffId to set
	 */
	public void setVerifiedStaffId(long verifiedStaffId) {
		this.verifiedStaffId = verifiedStaffId;
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
	 * @return the completedStaffId
	 */
	public long getCompletedStaffId() {
		return completedStaffId;
	}

	/**
	 * @param completedStaffId the completedStaffId to set
	 */
	public void setCompletedStaffId(long completedStaffId) {
		this.completedStaffId = completedStaffId;
	}

	/**
	 * @return the impactId
	 */
	public long getImpactId() {
		return impactId;
	}

	/**
	 * @param impactId the impactId to set
	 */
	public void setImpactId(long impactId) {
		this.impactId = impactId;
	}


}
