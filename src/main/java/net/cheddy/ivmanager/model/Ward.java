package net.cheddy.ivmanager.model;


import net.cheddy.ivmanager.logging.Storable;

public class Ward implements Storable {

	private long id;
	private long hospitalId;
	private String name;

	public Ward() {

	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	@Override
	public int getType() {
		return 15;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the hospitalId
	 */
	public long getHospitalId() {
		return hospitalId;
	}

	/**
	 * @param hospitalId the hospitalId to set
	 */
	public void setHospitalId(long hospitalId) {
		this.hospitalId = hospitalId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}


}
