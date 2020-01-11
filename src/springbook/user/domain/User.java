package springbook.user.domain;

public class User {
    String id;
    String name;
    String password;
    Level level;
    int login;
    int recommend;

    public User() {

    }

    public User(String id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.level = Level.BASIC;
    }

    public User(String id, String name, String password, Level level) {
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

    public Level getLevel() {return this.level;}

    public void setLevel(Level level) { this.level = level; }
}
