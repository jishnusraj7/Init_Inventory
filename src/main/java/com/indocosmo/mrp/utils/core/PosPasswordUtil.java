package com.indocosmo.mrp.utils.core;

import java.math.BigInteger;
import java.security.MessageDigest;

public final class PosPasswordUtil {
	
	/**
	 * @param plaintext
	 * @return
	 * @throws Exception
	 */
	public static synchronized String encrypt(String plaintext) throws Exception  {
		
		return encrypt(plaintext,"MD5", "UTF-8");
	}

	/**
	 * @param plaintext
	 * @param algorithm
	 * @param encoding
	 * @return
	 * @throws Exception
	 */
	public static synchronized String encrypt(String plaintext,
			String algorithm, String encoding) throws Exception {
		MessageDigest msgDigest = null;
		String hashValue = null;
			msgDigest = MessageDigest.getInstance(algorithm);
			msgDigest.reset();
			msgDigest.update(plaintext.getBytes(encoding));
			byte rawByte[] = msgDigest.digest();
			BigInteger bigInt = new BigInteger(1,rawByte);
			hashValue = bigInt.toString(16);
			while(hashValue.length() < 32 ){
				hashValue = "0"+hashValue;
				}
		return hashValue;
	}
	
	/**
	 * Compare the given passwords.
	 * @param pwdToCheck the password entered by the user. Which needs to be encrypted before comparing.
	 * @param password 
	 * @return
	 * @throws Exception 
	 */
	public static boolean comparePassword(String pwdToCheck, String password) throws Exception{
		
		return (password==null || password.equals(""))?true:encrypt(pwdToCheck).equals(password);
	}
	
}
