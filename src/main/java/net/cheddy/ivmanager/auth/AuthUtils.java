package net.cheddy.ivmanager.auth;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.validation.constraints.NotNull;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

public class AuthUtils {

	/**
	 * Static function class so no need for a constructor
	 */
	private AuthUtils() {
	}

	public static byte[] randomSalt() {
		try {
			SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
			byte[] salt = new byte[16];
			sr.nextBytes(salt);
			return salt;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return new byte[16];
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

	private static String toHex(byte[] array) throws NoSuchAlgorithmException
	{
		BigInteger bi = new BigInteger(1, array);
		String hex = bi.toString(16);
		int paddingLength = (array.length * 2) - hex.length();
		if(paddingLength > 0)
		{
			return String.format("%0"  +paddingLength + "d", 0) + hex;
		}else{
			return hex;
		}
	}

}
