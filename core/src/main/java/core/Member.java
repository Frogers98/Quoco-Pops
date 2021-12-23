package core;

public class Member {
    private static final char MALE = 'M';
    private static final char FEMALE = 'F';
    private static final char UNKNOWN = 'U';

    private static final String tallaghtLib = "tall1";

    public static char getMaleChar() {
        return MALE;
    }

    public static char getFemaleChar() {
        return FEMALE;
    }

    public static char getUnknownChar() {
        return UNKNOWN;
    }

    public static String getTallaghtLib() {
        return tallaghtLib;
    }

    public Member(String name, char gender, int yearOfBirth, String password, int id, String homeLibrary, int fine) {
        this.name = name;
        this.gender = gender;
        this.yearOfBirth = yearOfBirth;
        this.password = password;
        this.id = id;
        this.homeLibrary = homeLibrary;
        this.fine = fine;
    }

    public Member() {
    }

    private String name;
    private char gender;
    private int yearOfBirth;
    private String password;
    private int id;
    private String homeLibrary;
    private int fine;

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

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHomeLibrary() {
        return this.homeLibrary;
    }

    public void setId(String homeLibrary) {
        this.homeLibrary = homeLibrary;
    }
}
