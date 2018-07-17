package com.erkprog.zensofthrcrm.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Interview {
  private Integer id;
  @SerializedName("begin_time")
  private String date;
  private String status;
  private Candidate candidate;
  @SerializedName("interviewers")
  private List<Interviewer> interviewersList;

  public List<Interviewer> getInterviewersList() {
    return interviewersList;
  }

  public void setInterviewersList(List<Interviewer> interviewersList) {
    this.interviewersList = interviewersList;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Candidate getCandidate() {
    return candidate;
  }

  public void setCandidate(Candidate candidate) {
    this.candidate = candidate;
  }

}
