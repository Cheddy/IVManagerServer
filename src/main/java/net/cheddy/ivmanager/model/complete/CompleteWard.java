package net.cheddy.ivmanager.model.complete;

import net.cheddy.ivmanager.database.DAO;
import net.cheddy.ivmanager.logging.Storable;
import net.cheddy.ivmanager.model.Hospital;
import net.cheddy.ivmanager.model.Ward;


public class CompleteWard implements Storable {

	private long id;
	private Hospital hospital;
	private String name;

	public CompleteWard() {

	}

	public CompleteWard(DAO dao, long id) {
		this.id = id;
		Ward ward = dao.wardForId(id);
		this.setHospital(dao.hospitalForId(ward.getHospitalId()));
		this.name = ward.getName();
	}

	public CompleteWard(DAO dao, Ward ward) {
		this.id = ward.getId();
		this.hospital = dao.hospitalForId(ward.getHospitalId());
		this.name = ward.getName();
	}

	public Ward toWard() {
		if(getHospital() == null)
			return null;
		Ward ward = new Ward();
		ward.setId(getId());
		ward.setHospitalId(getHospital().getId());
		ward.setName(getName());
		return ward;
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
	 * @return the hospital
	 */
	public Hospital getHospital() {
		return hospital;
	}

	/**
	 * @param hospital the hospital to set
	 */
	public void setHospital(Hospital hospital) {
		this.hospital = hospital;
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
