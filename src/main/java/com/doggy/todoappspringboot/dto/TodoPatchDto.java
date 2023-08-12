package com.doggy.todoappspringboot.dto;

import jakarta.annotation.Nullable;

public record TodoPatchDto(@Nullable String name, @Nullable Boolean isComplete) {

}
