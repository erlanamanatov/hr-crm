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
  private List<String> workConditions;
  private String responsibilities;
  private String comments;
  @SerializedName("salary_min")
  private Integer salaryMin;
  @SerializedName("salary_max")
  private Integer salaryMax;
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
    workConditions = s;
    responsibilities = "fff";
    comments = "no comments";
    salaryMin = 0;
    salaryMax = 10000;
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
    workConditions = s;
    responsibilities = "Work on long-term startup projects";
    comments = "Lorem ipsum";
    salaryMin = 200;
    salaryMax = 500;
    createdBy = 1;
  }

  private VacancyRequest(Builder builder) {
    this.request = builder.requestId;
    this.createdBy = builder.createdById;
    this.title = builder.title;
    this.city = builder.city;
    this.address = builder.address;
    this.workConditions = builder.workingConditions;
    this.responsibilities = builder.responsibilities;
    this.salaryMin = builder.salaryMin;
    this.salaryMax = builder.salaryMax;
    this.comments = builder.comments;
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

  public List<String> getWorkConditions() {
    return workConditions;
  }

  public void setWorkConditions(List<String> workConditions) {
    this.workConditions = workConditions;
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

  public Integer getSalaryMin() {
    return salaryMin;
  }

  public void setSalaryMin(Integer salaryMin) {
    this.salaryMin = salaryMin;
  }

  public Integer getSalaryMax() {
    return salaryMax;
  }

  public void setSalaryMax(Integer salaryMax) {
    this.salaryMax = salaryMax;
  }

  public Integer getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(Integer createdBy) {
    this.createdBy = createdBy;
  }

  @Override
  public String toString() {
    return "VacancyRequest{" +
        "title='" + title + '\'' +
        ", request=" + request +
        ", city='" + city + '\'' +
        ", address='" + address + '\'' +
        ", workConditions=" + workConditions +
        ", responsibilities='" + responsibilities + '\'' +
        ", comments='" + comments + '\'' +
        ", salaryMin=" + salaryMin +
        ", salaryMax=" + salaryMax +
        ", createdBy=" + createdBy +
        '}';
  }

  public static class Builder {
    private int requestId;
    private int createdById;
    private String title;
    private String city;
    private String address;
    private List<String> workingConditions;
    private String responsibilities;
    private int salaryMin;
    private int salaryMax;
    private String comments;

    public Builder setRequestId(int id) {
      this.requestId = id;
      return this;
    }

    public Builder setCreatedBy(int id) {
      this.createdById = id;
      return this;
    }

    public Builder setTitle(String title) {
      this.title = title;
      return this;
    }

    public Builder setCity(String city) {
      this.city = city;
      return this;
    }

    public Builder setAddress(String address) {
      this.address = address;
      return this;
    }

    public Builder setResponsibilities(String responsibilities) {
      this.responsibilities = responsibilities;
      return this;
    }

    public Builder setComments(String comments) {
      this.comments = comments;
      return this;
    }

    public Builder setWorkingConditions(List<String> workingConditions) {
      this.workingConditions = workingConditions;
      return this;
    }

    public Builder setSalaryMin(int min) {
      this.salaryMin = min;
      return this;
    }

    public Builder setSalaryMax(int max) {
      this.salaryMax = max;
      return this;
    }

    public VacancyRequest build() {
      return new VacancyRequest(this);
    }
  }
}