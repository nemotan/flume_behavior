package com.demo.behavior.objectPool1;

import org.apache.commons.pool.PoolableObjectFactory;
/**
 * 创建销毁线程池中对象的工厂                                                  
 * @author Amylh           
 */

public class MyPoolableObjectFactory implements PoolableObjectFactory {
	private boolean isDebug = true;

	public Object makeObject() throws Exception {
		SimpleThread simpleThread = new SimpleThread();
		simpleThread.start();
		debug("创建线程:" + simpleThread.getName());
		return simpleThread;
	}

	public void destroyObject(Object obj) throws Exception {
		if (obj instanceof SimpleThread) {
			SimpleThread simpleThread = (SimpleThread) obj;
			debug("销毁线程:" + simpleThread.getName());
			simpleThread.destroy();
		}
	}

	public boolean validateObject(Object obj) {
		return true;
	}

	public void activateObject(Object obj) throws Exception {
	}

	public void passivateObject(Object obj) throws Exception {
	}

	public void setIsDebug(boolean isDebug) {
		this.isDebug = isDebug;
	}

	public boolean getIsDebug() {
		return isDebug;
	}

	private void debug(String debugInfo) {
		if (isDebug) {
			System.out.println(debugInfo);
		}
	}
}