package service.core;

import java.io.Serializable;

/**
 * Interface to define the state to be stored in ClientInfo objects
 * 
 * @author Hassan
 *
 */

public class ClientInfo implements Serializable{
	public static final char MALE				= 'M';
	public static final char FEMALE				= 'F';
	
	public ClientInfo(String name, char sex, int age, int points, int noBooks, String libraryNo) {
		this.name = name;
		this.gender = sex;
		this.age = age;
		this.points = points;
		this.noBooks = noBooks;
		this.libraryNo = libraryNo;
	}
	
	public ClientInfo() {}

	/**
	 * Public fields are used as modern best practice argues that use of set/get
	 * methods is unnecessary as (1) set/get makes the field mutable anyway, and
	 * (2) set/get introduces additional method calls, which reduces performance.
	 */
	private String name;
	private char gender;
	private int age;
	private int points;
	private int noBooks;
	private String libraryNo;

	public static char getMale() {
		return MALE;
	}

	public static char getFemale() {
		return FEMALE;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public char getGender() {
		return gender;
	}

	public void setGender(char gender) {
		this.gender = gender;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public int getnoBooks() {
		return noBooks;
	}

	public void setnoBooks(int noBooks) {
		this.noBooks = noBooks;
	}

	public String getlibraryNo() {
		return libraryNo;
	}

	public void setlibraryNo(String libraryNo) {
		this.libraryNo = libraryNo;
	}
}
