package net.cheddy.ivmanager.util;

/**
 * @author : Cheddy
 */
public class StringUtils {

	public static String seperateCamelCase(String camelCaseString){
		return camelCaseString.replaceAll("(\\p{Ll})(\\p{Lu})","$1 $2");
	}

}
