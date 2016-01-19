package net.cheddy.ivmanager.model.complete;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.cheddy.ivmanager.Server;
import net.cheddy.ivmanager.auth.AuthUtils;
import net.cheddy.ivmanager.database.DAO;
import net.cheddy.ivmanager.model.Staff;
import net.cheddy.ivmanager.model.StaffRank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class CompleteStaff {

	private long id;
	@NotEmpty
	private String username;
	@NotEmpty
	private String surname;
	@NotEmpty
	private String othernames;

	private String passwordHash;
	@NotNull
	private StaffRank rank;

	public CompleteStaff() {
	}

	public CompleteStaff(DAO dao, long id) {
		this.id = id;
		Staff staff = dao.staffForId(id);
		this.username = staff.getUsername();
		this.surname = staff.getSurname();
		this.othernames = staff.getOthernames();
		this.passwordHash = staff.getPasswordHash();
		this.rank = dao.staffRankForId(staff.getRankId());
	}

	public CompleteStaff(DAO dao, Staff staff) {
		this.id = staff.getId();
		this.username = staff.getUsername();
		this.surname = staff.getSurname();
		this.othernames = staff.getOthernames();
		this.passwordHash = staff.getPasswordHash();
		this.rank = dao.staffRankForId(staff.getRankId());
	}

	public Staff toStaff(DAO dao) {
		Staff staff = new Staff();
		staff.setId(id);
		staff.setUsername(username);
		staff.setOthernames(othernames);
		if (passwordHash != null && !passwordHash.isEmpty()) {
			try {
				staff.setPasswordSalt(new String(AuthUtils.randomSalt(), "UTF-8"));
				staff.setPasswordHash(new String(AuthUtils.hash(passwordHash, staff.getPasswordSalt().getBytes("UTF-8")), "UTF-8"));
			} catch (UnsupportedEncodingException | InvalidKeySpecException | NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
		} else {
			Staff dStaff = dao.staffForId(id);
			staff.setPasswordSalt(dStaff.getPasswordSalt());
			staff.setPasswordHash(dStaff.getPasswordHash());
		}
		staff.setRankId(rank.getId());
		staff.setSurname(surname);
		return staff;
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
	@JsonIgnore
	public String getPasswordHash() {
		return passwordHash;
	}

	/**
	 * @param passwordHash the passwordHash to set
	 */
	@JsonProperty
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	/**
	 * @return the rank
	 */
	public StaffRank getRank() {
		return rank;
	}

	/**
	 * @param rank the rank to set
	 */
	public void setRank(StaffRank rank) {
		this.rank = rank;
	}

}
