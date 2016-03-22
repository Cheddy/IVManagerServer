package net.cheddy.ivmanager.logging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import net.cheddy.ivmanager.auth.UserSession;
import net.cheddy.ivmanager.database.DAO;
import net.cheddy.ivmanager.model.Log;
import net.cheddy.ivmanager.util.StringUtils;

/**
 * @author : Cheddy
 */
public class Logger {

	public static DAO dao;

	public static void logInsertion(UserSession session, Storable storable){
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JodaModule());
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		String name = StringUtils.seperateCamelCase(storable.getClass().getSimpleName().replace("Complete", "")).toLowerCase();
		try {
			dao.insertLog(new Log(session.getStaff().getId(), storable.getType(), "New " + name + " with id: " + storable.getId(), null, mapper.writeValueAsString(storable)));
		} catch (JsonProcessingException e) {
			System.err.println("Error writing " + name + " insertion to log!");
			e.printStackTrace();
		}
	}

	public static void logUpdate(UserSession session, Storable newStorable, Storable oldStorable){
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JodaModule());
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		String name = StringUtils.seperateCamelCase(newStorable.getClass().getSimpleName().replace("Complete", "")).toLowerCase();
		try {
			dao.insertLog(new Log(session.getStaff().getId(), newStorable.getType() + 1, "Update to " + name + " with id: " + newStorable.getId(), mapper.writeValueAsString(oldStorable), mapper.writeValueAsString(newStorable)));
		} catch (JsonProcessingException e) {
			System.err.println("Error writing " + name + " update to log!");
			e.printStackTrace();
		}
	}

	public static void logDeletion(UserSession session, Storable storable){
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JodaModule());
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		String name = StringUtils.seperateCamelCase(storable.getClass().getSimpleName().replace("Complete", "")).toLowerCase();
		try {
			dao.insertLog(new Log(session.getStaff().getId(), storable.getType() + 2, "Deletion of " + name + " with id: " + storable.getId(), mapper.writeValueAsString(storable), null));
		} catch (JsonProcessingException e) {
			System.err.println("Error writing " + name + " deletion to log!");
			e.printStackTrace();
		}
	}

	public static void logClear(UserSession session){
		dao.insertLog(new Log(session.getStaff().getId(), -1, "Logs cleared", null, null));
	}
}
