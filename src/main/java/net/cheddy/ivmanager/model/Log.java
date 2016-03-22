package net.cheddy.ivmanager.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import net.cheddy.ivmanager.logging.Storable;
import net.cheddy.ivmanager.model.mapper.DateTimeSerialiser;
import org.joda.time.DateTime;

/**
 * @author : Cheddy
 */
public class Log implements Storable {

	private long id = -1, staffId;
	private int opcode;
	private String description, oldData, newData;
	@JsonSerialize(using=DateTimeSerialiser.class)
	private DateTime timestamp;

	public Log() {
	}

	public Log(long staffId, int opcode, String description, String oldData, String newData) {
		this.staffId = staffId;
		this.opcode = opcode;
		this.description = description;
		this.oldData = oldData;
		this.newData = newData;
	}

	public String getOldData() {
		return oldData;
	}

	public void setOldData(String oldData) {
		this.oldData = oldData;
	}

	public long getId() {
		return id;
	}

	@Override
	public int getType() {
		return getOpcode();
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getStaffId() {
		return staffId;
	}

	public void setStaffId(long staffId) {
		this.staffId = staffId;
	}

	public int getOpcode() {
		return opcode;
	}

	public void setOpcode(int opcode) {
		this.opcode = opcode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getNewData() {
		return newData;
	}

	public void setNewData(String newData) {
		this.newData = newData;
	}

	public DateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(DateTime timestamp) {
		this.timestamp = timestamp;
	}
}
