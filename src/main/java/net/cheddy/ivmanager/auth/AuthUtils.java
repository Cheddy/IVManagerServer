package net.cheddy.ivmanager.auth;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.validation.constraints.NotNull;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Random;

public class AuthUtils {

	/**
	 * Static function class so no need for a constructor
	 */
	private AuthUtils() {
	}

	private static final Random random = new Random();

	public static byte[] randomSalt() {
		byte[] salt = new byte[16];
		random.nextBytes(salt);
		return salt;
	}

	public static String hash(Serializable record) {
		try {
			MessageDigest algo = MessageDigest.getInstance("SHA-256");
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(out);
			oos.writeObject(record);
			oos.close();
			byte[] bytes = algo.digest(out.toByteArray());
			StringBuilder hexString = new StringBuilder();
			for (byte aByte : bytes) {
				hexString.append(Integer.toHexString(0xFF & aByte));
			}
			return hexString.toString();
		} catch (NoSuchAlgorithmException | IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static byte[] hash(@NotNull String password, byte[] salt) throws InvalidKeySpecException, NoSuchAlgorithmException {
		KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
		SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		return f.generateSecret(spec).getEncoded();
	}


}
