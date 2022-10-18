package com.itheima.entity;

public class Regular {
    private String tele;
    private String password;
    private String sex;
    private Integer age;
    private String address;
    private String pic;
    private Integer num;
    private Integer status;
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTele() {
        return tele;
    }

    public void setTele(String tele) {
        this.tele = tele;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    public String toString() {
        return "User{" +
                "telephone=" + tele +
                ", password='" + password + '\'' +
                ", sex='" + sex + '\'' +
                ", age=" + age + '\'' +
                ", address=" + address + '\'' +
                ", pic=" + pic + '\'' +
                ", num=" + num + '\'' +
                ", status=" + status + '\'' +
                ", username=" + username + '\'' +
                '}';
    }
}
