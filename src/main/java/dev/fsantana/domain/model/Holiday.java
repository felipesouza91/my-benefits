package dev.fsantana.domain.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "holidays")
public class Holiday {

  @Id
  private Long id;

  @Column(name = "type", nullable = false)
  private String type;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "date", nullable = false, columnDefinition = "DATE")
  private LocalDate date;

  public Holiday() {
  }

  public Holiday(String type, String name, LocalDate date) {
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

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  @Override
  public String toString() {
    return "Holiday [type=" + type + ", name=" + name + ", date=" + date + "]";
  }

}
