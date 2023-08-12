package com.doggy.todoappspringboot.entity;

import jakarta.persistence.*;

@Entity
public class TodoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false)
    public String name;

    @Column(nullable = false)
    public Boolean isComplete;

    public TodoEntity() {

    }

    public TodoEntity(String name, Boolean isComplete) {
        this.name = name;
        this.isComplete = isComplete;
    }

    public TodoEntity(Long id, String name, Boolean isComplete) {
        this.id = id;
        this.name = name;
        this.isComplete = isComplete;
    }

}
