package springbook.user.domain;

public class User {
    private static final int BASIC = 1;
    private static final int SILVER = 2;
    private static final int GOLD = 3;

    String id;
    String name;
    String password;
    int level;

    public User() {

    }

    public User(String id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.level = BASIC;
    }

    public User(String id, String name, String password, int level) {
        this(id, name, password);
        this.setLevel(level);
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPasswrod(String password) {
        this.password = password;
    }

    public int getLevel() {return this.level;}

    public void setLevel(int level) { this.level = level; }
}
