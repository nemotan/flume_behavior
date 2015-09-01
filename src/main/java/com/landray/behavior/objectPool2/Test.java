package com.landray.behavior.objectPool2;
import org.apache.commons.pool.BasePoolableObjectFactory;
import org.apache.commons.pool.impl.GenericObjectPool;
public class Test {
    public static void main(String[] args) {
        // GenericObjectPool把我们需要的东西基本都实现了，可能我们要做的只是了解其中的参数含义，然后具体设置一下就行了
        final GenericObjectPool pool = new GenericObjectPool(
                new TestPoolableObjectFactory());
        pool.setMaxActive(2);
        // pool.setMaxWait(1000);
        for (int i = 0; i < 40; i++) {
            new Thread(new Runnable() {
                public void run() {
                    try {
                    	Resource obj = (Resource)pool.borrowObject();// 注意，如果对象池没有空余的对象，那么这里会block，可以设置block的超时时间
                    	obj.getRid();
                        System.out.println(obj);
                        Thread.sleep(1000);
                        Thread.interrupted();
                        pool.returnObject(obj);// 申请的资源用完了记得归还，不然其他人要申请时可能就没有资源用了
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
    static class TestPoolableObjectFactory extends BasePoolableObjectFactory {
        public Object makeObject() throws Exception {
            return new Resource();
        }
    }
    static class Resource {
        public static int id;
        private int rid;
        public Resource() {
            synchronized (this) {
                rid = id++;
            }
        }
        public int getRid() {
            return rid;
        }
        @Override
        public String toString() {
            return "id:" + rid;
        }
    }
}
