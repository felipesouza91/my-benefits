package dev.fsantana.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "cities")
public class City {
  @Id
  private Long id;

  @Column(nullable = false)
  private String name;

  @ManyToOne()
  @JoinColumn(name = "state_id", referencedColumnName = "id")
  private State state;

  public City() {
  }

  public City(Long id, String name, State state) {
    this.id = id;
    this.name = name;
    this.state = state;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public State getState() {
    return state;
  }

  public void setState(State state) {
    this.state = state;
  }

}
