package dev.fsantana.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "states")
public class State {

  @Id
  private Long id;

  @Column(name = "short_name", nullable = false, unique = true)
  private String shortName;

  @Column(name = "full_name", nullable = false, unique = true)
  private String fullName;

  public State() {
  }

  public State(Long id, String shortName, String fullName) {
    this.id = id;
    this.shortName = shortName;
    this.fullName = fullName;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getShortName() {
    return shortName;
  }

  public void setShortName(String shortName) {
    this.shortName = shortName;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

}
