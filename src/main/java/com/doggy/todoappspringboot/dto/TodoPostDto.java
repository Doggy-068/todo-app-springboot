package com.doggy.todoappspringboot.dto;

import jakarta.validation.constraints.NotNull;

public record TodoPostDto(@NotNull String name, @NotNull Boolean isComplete) {

}
