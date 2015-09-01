package com.landray.behavior.test;

import com.landray.behavior.security.BehaviorSecurityClient;
import com.landray.behavior.security.BehaviorSecurityServer;


public class BehaviorSecurityTest {
	private static String string = "abcdefghijklmnopqrstuvwxyz";
	public static void main(String[] args) throws Exception {
		new BehaviorSecurityTest().test();
		new BehaviorSecurityTest().testPress();
		String random = getRandomString(32);
		System.out.println("random:"+random);
		System.out.println("key:"+	new BehaviorSecurityTest().getKey("123456"));
		System.out.println("key2:"+	new BehaviorSecurityTest().getKey("123456"));
	}

	private static int getRandom(int count) {
	       return (int) Math.round(Math.random()*count);
	}


	private static String getRandomString(int length) {
		StringBuffer sb = new StringBuffer();
		int len = string.length();
		for (int i = 0; i < length; i++) {
			sb.append(string.charAt(getRandom(len - 1)));
		}
		return sb.toString();
	}
	
	public void test() throws Exception{
		String projectId  = "123456";
		//String body = "1	C44355D470519C6CBE1EB5A6865DED55	1432053745926	112.97.36.239	14476e6713315df68df3b3f4942b3c8a	yh33	8	safari	4	image	/sys/attachment/sys_att_main/sysAttMain.do?method=download&fdId=14cbd0013d62a86d32a789b46cc994c0	/sys/news/sys_news_main/sysNewsMain.do?method=view&fdId=14b2f94aae35cfb323dddfc41e58255f	25";
		String body = "dwadwadwadwa";
		System.out.println("ԭʼ:"+body);
		String key = BehaviorSecurityServer.getClientKey(projectId);
	    System.out.println("秘钥:"+key);
	    byte[] enBody = BehaviorSecurityClient.encode(body.getBytes(),key);
	    System.out.println("加密:"+new String(enBody));
	    System.out.println("解密:"+new String(BehaviorSecurityServer.decode(enBody, projectId)));
	    
	}
	
	public void testPress() throws Exception{
		String projectId = "123456";
		String body = "1	C44355D470519C6CBE1EB5A6865DED55	1432053745926	112.97.36.239	14476e6713315df68df3b3f4942b3c8a	yh33	8	safari	4	image	/sys/attachment/sys_att_main/sysAttMain.do?method=download&fdId=14cbd0013d62a86d32a789b46cc994c0	/sys/news/sys_news_main/sysNewsMain.do?method=view&fdId=14b2f94aae35cfb323dddfc41e58255f	25";
		String key = BehaviorSecurityServer.getClientKey(projectId);
		byte[] enBody = BehaviorSecurityClient.encode(body.getBytes(),key);
		long t1 = System.currentTimeMillis();
		for (int i = 0; i < 10000; i++) {
			BehaviorSecurityServer.decode(enBody, projectId);
		}
		long t2 = System.currentTimeMillis();
		System.out.println("耗时:"+String.valueOf(t2-t1));
	}
	
	public String getKey(String projectId) throws Exception{
		return BehaviorSecurityServer.getClientKey(projectId);
	}
}
