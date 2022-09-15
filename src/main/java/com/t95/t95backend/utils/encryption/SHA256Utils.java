package com.t95.t95backend.utils.encryption;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

import org.springframework.stereotype.Component;

@Component
public class SHA256Utils {
	
	public static String hash(String originalString) throws Exception {
		//add salt
		String string = "t95"+originalString+"2022";
		
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		byte[] encodedhash = digest.digest(
				string.getBytes(StandardCharsets.UTF_8));
		
        return bytesToHex(encodedhash);
    }
	
	private static String bytesToHex(byte[] hash) {
	    StringBuilder hexString = new StringBuilder(2 * hash.length);
	    for (int i = 0; i < hash.length; i++) {
	        String hex = Integer.toHexString(0xff & hash[i]);
	        if(hex.length() == 1) {
	            hexString.append('0');
	        }
	        hexString.append(hex);
	    }
	    return hexString.toString();
	}

}
