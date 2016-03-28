package net.cheddy.ivmanager.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.auth.Auth;
import net.cheddy.ivmanager.auth.UserSession;
import net.cheddy.ivmanager.database.DAO;
import net.cheddy.ivmanager.logging.Logger;
import net.cheddy.ivmanager.model.Intervention;
import net.cheddy.ivmanager.model.Ward;
import net.cheddy.ivmanager.model.complete.CompleteWard;
import org.joda.time.DateTime;
import org.joda.time.DateTimeComparator;
import org.joda.time.LocalDate;
import org.skife.jdbi.cglib.core.Local;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;


@Path("/audit")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuditService {

	private final DAO dao;

	public AuditService(DAO dao) {
		this.dao = dao;
	}

	@GET
	@Path("/interventioncountagainstdate")
	public Object[][] saveWard() {
        Iterator<Intervention> it = getDao().allInterventions();
        final ArrayList<Intervention> interventions = new ArrayList<>();
        it.forEachRemaining(t -> interventions.add(t));
        HashMap<LocalDate, Integer> map = new HashMap();
        interventions.forEach(intervention -> {
            LocalDate date = intervention.getDateTime().toLocalDate();
            if(!map.containsKey(date)){
                map.put(date, 1);
            }else{
                map.put(date, map.get(date) + 1);
            }
        });
        ArrayList<LocalDate> list = new ArrayList(map.keySet());
        Collections.sort(list);
        Object[][] obs = new Object[list.size() + 1][2];
        obs[0] = new Object[]{"Date", "Count"};
        for(int i = 0; i < list.size(); i++){
            obs[i + 1][0] = list.get(i).toString("dd/MM/yyyy");
            obs[i + 1][1] = map.get(list.get(i));
        }
        return obs;
	}

	/**
	 * @return the dao
	 */
	public DAO getDao() {
		return dao;
	}
}
