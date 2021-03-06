package net.cheddy.ivmanager.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import net.cheddy.ivmanager.model.mapper.DateTimeSerialiser;
import org.joda.time.DateTime;


public class InterventionAction {

	private long id;
	private long interventionId;
	private String description;
	private String detail;
	@JsonSerialize(using=DateTimeSerialiser.class)
	private DateTime dateTime;

	public InterventionAction() {
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

	/**
	 * @return the datetime
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public DateTime getDateTime() {
		return dateTime;
	}

	/**
	 * @param datetime the datetime to set
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public void setDateTime(DateTime datetime) {
		this.dateTime = datetime;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj != null && obj instanceof InterventionAction){
			ObjectMapper mapper = new ObjectMapper();
			mapper.registerModule(new JodaModule());
			mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
			try {
				return mapper.writeValueAsString(this).equals(mapper.writeValueAsString(obj));
			} catch (JsonProcessingException e) {
				return false;
			}
		}
		return false;
	}

}
