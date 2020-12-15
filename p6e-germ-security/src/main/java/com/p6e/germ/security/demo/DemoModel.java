package com.p6e.germ.security.demo;

import com.p6e.germ.security.model.P6eSecurityModel;

import java.io.Serializable;

/**
 * @author lidashuang
 * @version 1.0
 */
public class DemoModel extends P6eSecurityModel implements Serializable {
    private Integer id;
    private String name;
    private Integer age;
    private String sex;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
