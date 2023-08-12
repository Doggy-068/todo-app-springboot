package com.doggy.todoappspringboot.dto;

import jakarta.validation.constraints.NotNull;

public record TodoPutDto(@NotNull String name, @NotNull Boolean isComplete) {

}
