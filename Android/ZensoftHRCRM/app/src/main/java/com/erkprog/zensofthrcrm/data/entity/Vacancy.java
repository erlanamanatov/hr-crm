package com.erkprog.zensofthrcrm.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Vacancy {
  private Integer id;
  private String name;
  private String title;
  private String city;
  private String address;
  @SerializedName("working_hours")
  private String workingHours;
  @SerializedName("salary_min")
  private Float salaryMin;
  @SerializedName("salary_max")
  private Float salaryMax;
  private String responsibilities;
  private String image;
  private String comments;
  @SerializedName("work_conditions")
  private List<WorkingCondition> workConditions;
  private String created;
  @SerializedName("last_published")
  private String lastPublished;
  private boolean facebook;
  private boolean diesel;
  private boolean jobkg;

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

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
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

  public String getWorkingHours() {
    return workingHours;
  }

  public void setWorkingHours(String workingHours) {
    this.workingHours = workingHours;
  }


  public String getResponsibilities() {
    return responsibilities;
  }

  public void setResponsibilities(String responsibilities) {
    this.responsibilities = responsibilities;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public String getComments() {
    return comments;
  }

  public void setComments(String comments) {
    this.comments = comments;
  }

  public Float getSalaryMin() {
    return salaryMin;
  }

  public void setSalaryMin(Float salaryMin) {
    this.salaryMin = salaryMin;
  }

  public Float getSalaryMax() {
    return salaryMax;
  }

  public void setSalaryMax(Float salaryMax) {
    this.salaryMax = salaryMax;
  }

  public List<WorkingCondition> getWorkConditions() {
    return workConditions;
  }

  public void setWorkConditions(List<WorkingCondition> workConditions) {
    this.workConditions = workConditions;
  }

  public String getCreated() {
    return created;
  }

  public void setCreated(String created) {
    this.created = created;
  }

  public String getLastPublished() {
    return lastPublished;
  }

  public void setLastPublished(String lastPublished) {
    this.lastPublished = lastPublished;
  }

  public boolean isFacebook() {
    return facebook;
  }

  public void setFacebook(boolean facebook) {
    this.facebook = facebook;
  }

  public boolean isDiesel() {
    return diesel;
  }

  public void setDiesel(boolean diesel) {
    this.diesel = diesel;
  }

  public boolean isJobkg() {
    return jobkg;
  }

  public void setJobkg(boolean jobkg) {
    this.jobkg = jobkg;
  }
}
