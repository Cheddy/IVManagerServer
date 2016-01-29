package net.cheddy.ivmanager.model;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class InterventionDetail {

	private long id;
	private long interventionId;
	private String description;
	private String detail;

	public InterventionDetail() {
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
	 * @return the interventionId
	 */
	public long getInterventionId() {
		return interventionId;
	}

	/**
	 * @param interventionId the interventionId to set
	 */
	public void setInterventionId(long interventionId) {
		this.interventionId = interventionId;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the detail
	 */
	public String getDetail() {
		return detail;
	}

	/**
	 * @param detail the detail to set
	 */
	public void setDetail(String detail) {
		this.detail = detail;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj != null && obj instanceof InterventionDetail){
			ObjectMapper mapper = new ObjectMapper();
			try {
				return mapper.writeValueAsString(this).equals(mapper.writeValueAsString(obj));
			} catch (JsonProcessingException e) {
				return false;
			}
		}
		return false;
	}
}
