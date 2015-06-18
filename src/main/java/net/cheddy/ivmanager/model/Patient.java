package net.cheddy.ivmanager.model;

public class Patient {
	
	private long id;
	private long rtx;
	private String surname;
	private String othernames;

	public Patient() {

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
	 * @return the rtx
	 */
	public long getRtx() {
		return rtx;
	}

	/**
	 * @param rtx the rtx to set
	 */
	public void setRtx(long rtx) {
		this.rtx = rtx;
	}

	/**
	 * @return the surname
	 */
	public String getSurname() {
		return surname;
	}

	/**
	 * @param surname the surname to set
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}

	/**
	 * @return the othernames
	 */
	public String getOthernames() {
		return othernames;
	}

	/**
	 * @param othernames the othernames to set
	 */
	public void setOthernames(String othernames) {
		this.othernames = othernames;
	}

}