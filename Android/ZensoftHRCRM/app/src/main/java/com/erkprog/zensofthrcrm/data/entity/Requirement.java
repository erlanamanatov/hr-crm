package com.erkprog.zensofthrcrm.data.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Requirement implements Serializable {
  private Integer id;
  private String name;
  private Department department;
  private String type;


  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Department getDepartment() {
    return department;
  }

  public void setDepartment(Department department) {
    this.department = department;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

}
