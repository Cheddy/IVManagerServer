package net.cheddy.ivmanager.logging;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author : Cheddy
 */
public interface Storable {

	public long getId();
	@JsonIgnore
	public int getType();

}
