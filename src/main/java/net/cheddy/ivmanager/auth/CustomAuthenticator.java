package net.cheddy.ivmanager.auth;

import com.google.common.base.Optional;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;
import net.cheddy.ivmanager.database.DAO;
import net.cheddy.ivmanager.model.Staff;
import net.cheddy.ivmanager.model.complete.CompleteStaff;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class CustomAuthenticator implements Authenticator<BasicCredentials, UserSession> {

	final DAO dao;

	public CustomAuthenticator(DAO dao) {
		this.dao = dao;
	}

	@Override
	public Optional<UserSession> authenticate(BasicCredentials creds) throws AuthenticationException {
		if (creds.getUsername() != null && creds.getPassword() != null) {
			Staff staff = dao.staffForUsername(creds.getUsername());
			if (staff != null) {
				try {
					if (staff.getPasswordHash().equals(new String(AuthUtils.hash(creds.getPassword(), staff.getPasswordSalt().getBytes("UTF-8")), "UTF-8"))) {
						return Optional.of(new UserSession(new CompleteStaff(dao, staff)));
					}
				} catch (InvalidKeySpecException | NoSuchAlgorithmException | UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}

		return Optional.absent();
	}

}
