package com.experis.course.springpizzeria.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

@Entity
public class Pizza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @NotBlank(message = "Prego inserisci un nome, non può essere vuoto")
    @Size(max = 255, message = "Nome troppo lungo, inserisci un max di 255 caratteri")
    private String name;
    @NotBlank(message = "Prego inserisci una descrizione, non può essere vuoto")
    @Size(max = 255, message = "Descrizione troppo lunga, inserisci un max di 255 caratteri")

    private String description;
    @Column(length = 1000)
    private String photo;
    @DecimalMin(value = "0.01", message = "Il prezzo deve essere maggiore di zero")
    BigDecimal price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
