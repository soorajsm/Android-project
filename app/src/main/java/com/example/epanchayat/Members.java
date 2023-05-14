package com.example.epanchayat;


public class Members {
    String name,gender,post,age,phoneno,area,uri;

    public Members() {
    }

    public Members(String name, String gender, String post, String age, String phoneno, String area, String uri) {
        this.name = name;
        this.gender = gender;
        this.post = post;
        this.age = age;
        this.phoneno = phoneno;
        this.area = area;
        this.uri=uri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
