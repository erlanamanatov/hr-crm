package com.erkprog.zensofthrcrm.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class VacancyRequest {

  private String title;
  private Integer request;
  private String image;
  private String city;
  private String address;
  @SerializedName("work_conditions")
  private List<String> work_conditions;
  private String responsibilities;
  private String comments;
  @SerializedName("salary_min")
  private Integer salary_min;
  @SerializedName("salary_max")
  private Integer salary_max;
  @SerializedName("created_by")
  private Integer createdBy;

  public VacancyRequest(String image) {
    this.image = image;
    this.title = "Test title";
    this.request = 1;
    this.city = "Chelyabinsk";
    this.address = "dom na gore";
    List<Integer> requirements = new ArrayList<>();
    requirements.add(1);
    requirements.add(2);
    List<String> s = new ArrayList<>();
    s.add("dfsdfsd");
    work_conditions = s;
    responsibilities = "fff";
    comments = "no comments";
    salary_min = 0;
    salary_max = 10000;
    createdBy = 2;
  }

  public VacancyRequest() {
    this.title = "Interns required";
    this.request = 2;
    this.city = "Bishkek";
    this.address = "dom na gore";
    List<Integer> requirements = new ArrayList<>();
    requirements.add(1);
    List<String> s = new ArrayList<>();
    s.add("Two toilets for 100 people");
    work_conditions = s;
    responsibilities = "Work on long-term startup projects";
    comments = "Lorem ipsum";
    salary_min = 200;
    salary_max = 500;
    createdBy = 1;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Integer getRequest() {
    return request;
  }

  public void setRequest(Integer request) {
    this.request = request;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public List<String> getWork_conditions() {
    return work_conditions;
  }

  public void setWork_conditions(List<String> work_conditions) {
    this.work_conditions = work_conditions;
  }

  public String getResponsibilities() {
    return responsibilities;
  }

  public void setResponsibilities(String responsibilities) {
    this.responsibilities = responsibilities;
  }

  public String getComments() {
    return comments;
  }

  public void setComments(String comments) {
    this.comments = comments;
  }

  public Integer getSalary_min() {
    return salary_min;
  }

  public void setSalary_min(Integer salary_min) {
    this.salary_min = salary_min;
  }

  public Integer getSalary_max() {
    return salary_max;
  }

  public void setSalary_max(Integer salary_max) {
    this.salary_max = salary_max;
  }

  public Integer getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(Integer createdBy) {
    this.createdBy = createdBy;
  }
}