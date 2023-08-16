package com.doggy.todoappspringboot.controller;

import com.doggy.todoappspringboot.dto.TodoPatchDto;
import com.doggy.todoappspringboot.dto.TodoPostDto;
import com.doggy.todoappspringboot.dto.TodoPutDto;
import com.doggy.todoappspringboot.dto.TodoReturnDto;
import com.doggy.todoappspringboot.service.TodoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@Tag(name = "Todo")
@RestController
@RequestMapping("/api/todo")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @Operation(summary = "Get todos.")
    @GetMapping("")
    public ResponseEntity<List<TodoReturnDto>> getTodos() {
        return new ResponseEntity<>(todoService.getTodos(), HttpStatus.OK);
    }

    @Operation(summary = "Get a specific todo by id.")
    @GetMapping("{id}")
    public ResponseEntity<TodoReturnDto> getTodoById(@PathVariable(value = "id") Long id) {
        try {
            return new ResponseEntity<>(todoService.getTodoById(id), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Create a todo.")
    @ApiResponse(responseCode = "201", description = "Create successfully.")
    @PostMapping("")
    public ResponseEntity<TodoReturnDto> postTodo(@Validated @RequestBody TodoPostDto todoPostDto) {
        return new ResponseEntity<>(todoService.postTodo(todoPostDto), HttpStatus.CREATED);
    }

    @Operation(summary = "Put a specific todo.")
    @PutMapping("{id}")
    public ResponseEntity<TodoReturnDto> putTodoWithId(@PathVariable(value = "id") Long id, @Validated @RequestBody TodoPutDto todoPutDto) {
        try {
            return new ResponseEntity<>(todoService.putTodoWithId(id, todoPutDto), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Patch a specific todo.")
    @PatchMapping("{id}")
    public ResponseEntity<TodoReturnDto> patchTodoWithId(@PathVariable(value = "id") Long id, @Validated @RequestBody TodoPatchDto todoPatchDto) {
        try {
            return new ResponseEntity<>(todoService.patchTodoWithId(id, todoPatchDto), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Delete a specific todo by id.")
    @ApiResponse(responseCode = "204", description = "Delete successfully.")
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteTodoById(@PathVariable(value = "id") Long id) {
        try {
            todoService.deleteTodoById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
