package com.demo.behavior.objectPool2;

public class User {
	private String name; 
    private String pswd; 
    private int age; 
    private String reamark; 

    User() { 
    } 

    User(String name, String pswd) { 
            this.name = name; 
            this.pswd = pswd; 
    } 

    User(String name, String pswd, int age) { 
            this.name = name; 
            this.pswd = pswd; 
            this.age = age; 
    } 

    User(String name, String pswd, int age, String reamark) { 
            this.name = name; 
            this.pswd = pswd; 
            this.age = age; 
            this.reamark = reamark; 
    } 

    public String getName() { 
            return name; 
    } 

    public void setName(String name) { 
            this.name = name; 
    } 

    public String getPswd() { 
            return pswd; 
    } 

    public void setPswd(String pswd) { 
            this.pswd = pswd; 
    } 

    public int getAge() { 
            return age; 
    } 

    public void setAge(int age) { 
            this.age = age; 
    } 

    public String getReamark() { 
            return reamark; 
    } 

    public void setReamark(String reamark) { 
            this.reamark = reamark; 
    } 

    @Override 
    public boolean equals(Object o) { 
            if (this == o) return true; 
            if (o == null || getClass() != o.getClass()) return false; 

            User user = (User) o; 

            if (!name.equals(user.name)) return false; 
            if (!pswd.equals(user.pswd)) return false; 

            return true; 
    } 

    @Override 
    public int hashCode() { 
            int result = name.hashCode(); 
            result = 31 * result + pswd.hashCode(); 
            return result; 
    } 

    @Override 
    public String toString() { 
            return "User{" + 
                            "name='" + name + '\'' + 
                            ", pswd='" + pswd + '\'' + 
                            ", age=" + age + 
                            ", reamark='" + reamark + '\'' + 
                            '}'; 
    } 
}
