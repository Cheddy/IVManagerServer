package net.cheddy.ivmanager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import net.cheddy.ivmanager.logging.Storable;

public class Staff implements Storable {

	private long id;
	private String username;
	private String surname;
	private String othernames;
	@JsonIgnore
	private String passwordSalt;
	@JsonIgnore
	private String passwordHash;
	private long rankId;

	public Staff() {

	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	@Override
	public int getType() {
		return 9;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
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

	/**
	 * @return the passwordHash
	 */
	public String getPasswordHash() {
		return passwordHash;
	}

	/**
	 * @param passwordHash the passwordHash to set
	 */
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	/**
	 * @return the passwordSalt
	 */
	public String getPasswordSalt() {
		return passwordSalt;
	}

	/**
	 * @param passwordSalt the passwordSalt to set
	 */
	public void setPasswordSalt(String passwordSalt) {
		this.passwordSalt = passwordSalt;
	}

	/**
	 * @return the rankId
	 */
	public long getRankId() {
		return rankId;
	}

	/**
	 * @param rankId the rankId to set
	 */
	public void setRankId(long rankId) {
		this.rankId = rankId;
	}

}
