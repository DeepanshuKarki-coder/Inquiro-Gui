package Backend;

public class UserModel {
    private int id;
    private String name;
    private String password;
    private String branch;
    private String section;

    public UserModel() {}

    public UserModel(int id, String name, String password, String branch, String section) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.branch = branch;
        this.section = section;
    }

    //Getters and Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getBranch() {
        return branch;
    }
    public void setBranch(String branch) {
        this.branch = branch;
    }
    public String getSection() {
        return section;
    }
    public void setSection(String section) {
        this.section = section;
    }
}
