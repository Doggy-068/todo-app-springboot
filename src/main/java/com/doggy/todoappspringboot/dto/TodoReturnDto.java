package com.doggy.todoappspringboot.dto;

import jakarta.validation.constraints.NotNull;

public record TodoReturnDto(@NotNull Long id, @NotNull String name, @NotNull Boolean isComplete) {

}
