package net.cheddy.ivmanager.auth;

import net.cheddy.ivmanager.model.complete.CompleteStaff;

public class UserSession {

	private CompleteStaff staff;

	public UserSession(CompleteStaff staff) {
		this.staff = staff;
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

}
