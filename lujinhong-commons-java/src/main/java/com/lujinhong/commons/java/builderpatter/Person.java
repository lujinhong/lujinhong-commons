/**
 * 
 */
package com.lujinhong.commons.java.builderpatter;

/**
 * date: 2016年3月4日 下午4:00:22
 * 
 * @author LUJINHONG lu_jin_hong@163.com Function: TODO ADD FUNCTION. last
 *         modified: 2016年3月4日 下午4:00:22
 */

public class Person {
	private final String name;
	private final int age;
	private final int gender;
	private final String phoneNum;
	private final String email;
	private final String city;

	private Person(Builder builder) {
       name = builder.name;
       age = builder.age;
       gender = builder.gender;
       phoneNum = builder.phoneNum;
       email = builder.email;
       city = builder.city;
	}
	
	//Person类的其它公有方法，还可以有walk(), run(), eat()等。
	public String getMessage(){
		return name + age + gender + phoneNum + email + city; 
	}

	public static class Builder {
		private String name;
		private int age = -1;
		private int gender = 1;
		private String phoneNum = "unkown";
		private String email = "unkown";
		private String city = "unkown";

		public Builder(String name) {
			this.name = name;
		}
		
		public Person build(){
			return new Person(this);
		}

		public Builder age(int age) {
			this.age = age;
			return this;
		}

		public Builder gender(int gender) {
			this.gender = gender;
			return this;
		}

		public Builder phoneNum(String phoneNum) {
			this.phoneNum = phoneNum;
			return this;
		}

		public Builder email(String email) {
			this.email = email;
			return this;
		}

		public Builder city(String city) {
			this.city = city;
			return this;
		}

	}

	//通过会有其它类中进行调用，这是为了方便，在本类中进行demo调用。
	public static void main(String[] args){
    	    Person ljh = new Person.Builder("ljh").age(5).gender(1).phoneNum("13579246810").city("gz").build();
    	    System.out.println(ljh.getMessage());
    	    
    }
}
