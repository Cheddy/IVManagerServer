package net.cheddy.ivmanager.model;

import net.cheddy.ivmanager.logging.Storable;
import org.hibernate.validator.constraints.NotEmpty;

public class Hospital implements Storable{

	private long id = -1;
	@NotEmpty
	private String name;

	public Hospital() {
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	@Override
	public int getType() {
		return 0;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
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
