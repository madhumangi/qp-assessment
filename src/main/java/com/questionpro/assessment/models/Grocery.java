package com.questionpro.assessment.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Grocery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double price;
    private int quantity;
}
