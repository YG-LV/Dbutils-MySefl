public class Teacher {
    private Integer id;
    private String username;
    private String sex;
    private Integer age;
    private String hobbies;

    public Teacher() {}

    public Teacher(Integer id, String username, String sex, Integer age, String hobbies) {
        this.id = id;
        this.username = username;
        this.sex = sex;
        this.age = age;
        this.hobbies = hobbies;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", sex='" + sex + '\'' +
                ", age=" + age +
                ", hobbies='" + hobbies + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getHobbies() {
        return hobbies;
    }

    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }
}
