package dev.fsantana.domain.model;

import java.util.Date;

public class Holiday {
  public String type;
  public String name;
  public Date date;

  public Holiday() {
  }

  public Holiday(String type, String name, Date date) {
    this.type = type;
    this.name = name;
    this.date = date;
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

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  @Override
  public String toString() {
    return "Holiday [type=" + type + ", name=" + name + ", date=" + date + "]";
  }

}
