package com.demo.behavior.security;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.pool.BasePoolableObjectFactory;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * 服务端解密
 * 
 * @author nemotan
 * 
 */
public class BehaviorSecurityServer {
	private static final String KEY_HEAD = "kdsbmwubxqkycnscnetaecposmisyqlu";
	public static final String ALGORITHM_NAME = "DESede";
	private static Map<String, SecretKey> secretKeyMap = new ConcurrentHashMap<String, SecretKey>();
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static final GenericObjectPool<Map<String, Cipher>> pool = new GenericObjectPool<Map<String, Cipher>>(
			new BasePoolableObjectFactory() {
				public Object makeObject() throws Exception {
					return new HashMap<String, SecretKey>();
				}

			});
	static {
		pool.setMaxActive(20);
	}

	/**
	 * 根据projectId进行解密
	 * 
	 * @param src
	 * @param projectId
	 * @return
	 * @throws Exception
	 */
	public static byte[] decode(byte[] src, String projectId) throws Exception {
		Map<String, Cipher> cipherMap = (Map<String, Cipher>) pool.borrowObject();
		Cipher c = (Cipher) cipherMap.get(projectId);
		if (c == null) {
			SecretKey deskey = getDeskey(projectId);
			c = Cipher.getInstance(ALGORITHM_NAME, new BouncyCastleProvider());
			c.init(Cipher.DECRYPT_MODE, deskey);
			cipherMap.put(projectId, c);
		}
		byte[] bs = c.doFinal(src);
		pool.returnObject(cipherMap);
		return bs;
	}

	/**
	 * 获取秘钥
	 * 
	 * @param projectId
	 * @return
	 * @throws Exception
	 */
	public static SecretKey getDeskey(String projectId) throws Exception {
		if (secretKeyMap.get(projectId) != null) {
			return secretKeyMap.get(projectId);
		}
		//linux will get the diffent number see http://m.blog.csdn.net/blog/jonson123654/39083261
		KeyGenerator generator = KeyGenerator.getInstance(ALGORITHM_NAME);
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed((projectId + KEY_HEAD).getBytes());
		generator.init(random);
		SecretKey deskey = generator.generateKey();
		secretKeyMap.put(projectId, deskey);
		return deskey;

	}

	/**
	 * 获取秘钥（客户端用）
	 * 
	 * @param projectId
	 * @return
	 * @throws Exception
	 */
	public static String getClientKey(String projectId) throws Exception {
		SecretKey deskey = getDeskey(projectId);
		return new String(Base64.encodeBase64(deskey.getEncoded()));
	}
}
