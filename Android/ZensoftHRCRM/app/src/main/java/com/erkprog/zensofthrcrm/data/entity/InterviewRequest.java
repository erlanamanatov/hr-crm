package com.erkprog.zensofthrcrm.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class InterviewRequest {
  private Integer candidate;
  private List<Integer> interviewers;
  @SerializedName("begin_time")
  private String date;
  private String description;
  private String location;
  private String status;
  @SerializedName("phone")
  private List<String> phoneNumbers;


  public InterviewRequest(Integer candidate, List<Integer> interviewers, String date) {
    this.candidate = candidate;
    this.interviewers = interviewers;
    this.date = date;
    description = "interview description";
    location = "loc123";
    phoneNumbers = new ArrayList<>();
    phoneNumbers.add("12412");
    status = "you need to create default status in backend";
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public List<String> getPhoneNumbers() {
    return phoneNumbers;
  }

  public void setPhoneNumbers(List<String> phoneNumbers) {
    this.phoneNumbers = phoneNumbers;
  }

  public Integer getCandidate() {
    return candidate;
  }

  public void setCandidate(Integer candidate) {
    this.candidate = candidate;
  }

  public List<Integer> getInterviewers() {
    return interviewers;
  }

  public void setInterviewers(List<Integer> interviewers) {
    this.interviewers = interviewers;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }
}
