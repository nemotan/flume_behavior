package com.demo.behavior.security;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * 客户端加密
 * 
 * @author nemotan
 * 
 */
public class BehaviorSecurityClient {
	public static final String ALGORITHM_NAME = "DESede";
	private static SecretKey deskey = null;
	private static ThreadLocal<Cipher> cipher = new ThreadLocal<Cipher>();

	/**
	 * 根据秘钥加密
	 * 
	 * @param src
	 *            待加密内容
	 * @param key
	 *            秘钥
	 * @return
	 * @throws Exception
	 */
	public static byte[] encode(byte[] src, String key) throws Exception {
		if (deskey == null) {
			deskey = new SecretKeySpec(Base64.decodeBase64(key.getBytes()),
					ALGORITHM_NAME);
		}
		Cipher c = cipher.get();
		if (c == null) {
			c = Cipher.getInstance(ALGORITHM_NAME, new BouncyCastleProvider());
			cipher.set(c);
		}
		c.init(Cipher.ENCRYPT_MODE, deskey);
		return c.doFinal(src);
	}
}
