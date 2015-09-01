package com.demo.behavior.objectPool2;

import org.apache.commons.pool.BaseKeyedPoolableObjectFactory;
import org.apache.commons.pool.impl.GenericKeyedObjectPool;

public class Test2 {
	   public static void main(String[] args) throws Exception { 
           //创建一个对象池 
           GenericKeyedObjectPool pool = new GenericKeyedObjectPool(new BaseKeyedPoolableObjectFactory() { 
                   @Override 
                   public Object makeObject(Object o) throws Exception { 
                           if (o != null && o instanceof User) 
                                   return o; 
                           else 
                                   return null; 
                   } 
           }); 

           //添加对象到池，重复的不会重复入池 
           pool.addObject("a"); 
           pool.addObject("b"); 
           pool.addObject("x"); 
           pool.addObject(null); 
           pool.addObject(null); 
           pool.addObject(null); 
           pool.addObject(new User("zhangsan", "123")); 
           pool.addObject(new User("lisi", "112")); 
           pool.addObject(new User("lisi", "112", 32)); 
           pool.addObject(new User("lisi", "112", 32, "一个烂人！")); 


           //清除最早的对象 
           pool.clearOldest(); 

           //获取并输出对象 
           User u1 = new User("lisi", "112", 32, "一个烂人！"); 
           System.out.println(pool.borrowObject(u1)); 
           pool.returnObject(u1,u1); 

           //获取并输出对象 
           User u2 = new User("lisi", "112", 32, "一个烂人！"); 
           System.out.println(pool.borrowObject(u2)); 
           pool.returnObject(u2,u2); 

           //获取并输出对象 
           User u3 = new User("lisi", "112", 32); 
           System.out.println(pool.borrowObject(u3)); 
           pool.returnObject(u3,u3); 

           //获取并输出对象 
           User u4 = new User("lisi", "112"); 
           System.out.println(pool.borrowObject(u4)); 
           pool.returnObject(u4,u4); 

           System.out.println(pool.borrowObject(u4)); 
           pool.returnObject(u4,u4); 
           System.out.println(pool.borrowObject(u4)); 
           pool.returnObject(u4,u4); 
           System.out.println(pool.borrowObject(u4)); 
           pool.returnObject(u4,u4); 
           System.out.println(pool.borrowObject(u4)); 
           pool.returnObject(u4,u4); 
           System.out.println(pool.borrowObject(u4)); 
           pool.returnObject(u4,u4); 
           System.out.println(pool.borrowObject(u4)); 
           pool.returnObject(u4,u4); 
           System.out.println(pool.borrowObject(u4)); 
           pool.returnObject(u4,u4); 



          
           System.out.println("-----------------------"); 
           System.out.println(pool.borrowObject(new User("lisi", "112"))); 
//           System.out.println(pool.borrowObject(new User("lisi", "112"))); 

           //输出池状态 
           System.out.println(pool.getMaxIdle()); 
           System.out.println(pool.getMaxActive()); 
           pool.clearOldest(); 
           pool.close(); 

   } 
}
