package com.doranco.synthsale.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Ad {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String description;
    private double price;
    private List<String> location;
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime dateCreation;

    @ManyToOne
    private User user;

    @ManyToOne
    private Category category;

    @OneToOne
    private Synth synth;

    public Ad() {
    }

    public Ad(Long id, String title, String description, double price, List<String> location, LocalDateTime dateCreation, User user, Category category, Synth synth) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.location = location;
        this.dateCreation = dateCreation;
        this.user = user;
        this.category = category;
        this.synth = synth;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<String> getLocation() {
        return location;
    }

    public void setLocation(List<String> location) {
        this.location = location;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Synth getSynth() {
        return synth;
    }

    public void setSynth(Synth synth) {
        this.synth = synth;
    }
}
