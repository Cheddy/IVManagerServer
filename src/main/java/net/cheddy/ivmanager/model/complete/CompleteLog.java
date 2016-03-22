package net.cheddy.ivmanager.model.complete;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import net.cheddy.ivmanager.database.DAO;
import net.cheddy.ivmanager.logging.Storable;
import net.cheddy.ivmanager.model.*;
import net.cheddy.ivmanager.model.mapper.DateTimeSerialiser;
import org.joda.time.DateTime;

import java.io.IOException;

/**
 * @author : Cheddy
 */
public class CompleteLog {

	private long id = -1;
	private CompleteStaff staff;
	private int opcode;
	private String description;
	private Storable oldItem, newItem;
	@JsonSerialize(using=DateTimeSerialiser.class)
	private DateTime timestamp;

	public enum Type {INSERTION, UPDATE, DELETION, CLEAR_LOGS}
	public enum StorableType {HOSPITAL, IMPACT, PATIENT, STAFF, STAFF_RANK, WARD, INTERVENTION}

	public CompleteLog(DAO dao, Log log) {
		this.id = log.getId();
		this.staff = new CompleteStaff(dao, log.getStaffId());
		this.opcode = log.getOpcode();
		this.description = log.getDescription();
		this.timestamp = log.getTimestamp();

		if(log.getOpcode() != -1) {
			Type type = Type.values()[log.getOpcode() % 3];
			StorableType storableType = StorableType.values()[log.getOpcode() / 3];
			ObjectMapper mapper = new ObjectMapper();
			mapper.registerModule(new JodaModule());
			mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
			try {
				if (type == Type.INSERTION) {
					switch (storableType) {
						case HOSPITAL:
							this.newItem = mapper.readValue(log.getNewData(), Hospital.class);
							break;
						case IMPACT:
							this.newItem = mapper.readValue(log.getNewData(), Impact.class);
							break;
						case PATIENT:
							this.newItem = mapper.readValue(log.getNewData(), Patient.class);
							break;
						case STAFF:
							this.newItem = mapper.readValue(log.getNewData(), CompleteStaff.class);
							break;
						case STAFF_RANK:
							this.newItem = mapper.readValue(log.getNewData(), StaffRank.class);
							break;
						case WARD:
							this.newItem = mapper.readValue(log.getNewData(), CompleteWard.class);
							break;
						case INTERVENTION:
							this.newItem = mapper.readValue(log.getNewData(), CompleteIntervention.class);
							break;
					}
				} else if (type == Type.UPDATE) {
					switch (storableType) {
						case HOSPITAL:
							this.newItem = mapper.readValue(log.getNewData(), Hospital.class);
							this.oldItem = mapper.readValue(log.getOldData(), Hospital.class);
							break;
						case IMPACT:
							this.newItem = mapper.readValue(log.getNewData(), Impact.class);
							this.oldItem = mapper.readValue(log.getOldData(), Impact.class);
							break;
						case PATIENT:
							this.newItem = mapper.readValue(log.getNewData(), Patient.class);
							this.oldItem = mapper.readValue(log.getOldData(), Patient.class);
							break;
						case STAFF:
							this.newItem = mapper.readValue(log.getNewData(), CompleteStaff.class);
							this.oldItem = mapper.readValue(log.getOldData(), CompleteStaff.class);
							break;
						case STAFF_RANK:
							this.newItem = mapper.readValue(log.getNewData(), StaffRank.class);
							this.oldItem = mapper.readValue(log.getOldData(), StaffRank.class);
							break;
						case WARD:
							this.newItem = mapper.readValue(log.getNewData(), CompleteWard.class);
							this.oldItem = mapper.readValue(log.getOldData(), CompleteWard.class);
							break;
						case INTERVENTION:
							this.newItem = mapper.readValue(log.getNewData(), CompleteIntervention.class);
							this.oldItem = mapper.readValue(log.getOldData(), CompleteIntervention.class);
							break;
					}
				} else if (type == Type.DELETION) {
					switch (storableType) {
						case HOSPITAL:
							this.oldItem = mapper.readValue(log.getOldData(), Hospital.class);
							break;
						case IMPACT:
							this.oldItem = mapper.readValue(log.getOldData(), Impact.class);
							break;
						case PATIENT:
							this.oldItem = mapper.readValue(log.getOldData(), Patient.class);
							break;
						case STAFF:
							this.oldItem = mapper.readValue(log.getOldData(), CompleteStaff.class);
							break;
						case STAFF_RANK:
							this.oldItem = mapper.readValue(log.getOldData(), StaffRank.class);
							break;
						case WARD:
							this.oldItem = mapper.readValue(log.getOldData(), CompleteWard.class);
							break;
						case INTERVENTION:
							this.oldItem = mapper.readValue(log.getOldData(), CompleteIntervention.class);
							break;
					}
				}
			}catch (IOException e){
				e.printStackTrace();
			}
		}
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public CompleteStaff getStaff() {
		return staff;
	}

	public void setStaff(CompleteStaff staff) {
		this.staff = staff;
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

	public Storable getOldItem() {
		return oldItem;
	}

	public void setOldItem(Storable oldItem) {
		this.oldItem = oldItem;
	}

	public Storable getNewItem() {
		return newItem;
	}

	public void setNewItem(Storable newItem) {
		this.newItem = newItem;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public DateTime getTimestamp() {
		return timestamp;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public void setTimestamp(DateTime timestamp) {
		this.timestamp = timestamp;
	}
}
