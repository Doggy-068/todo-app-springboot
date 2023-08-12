package com.doggy.todoappspringboot.controller;

import com.doggy.todoappspringboot.dto.TodoPatchDto;
import com.doggy.todoappspringboot.dto.TodoPostDto;
import com.doggy.todoappspringboot.dto.TodoPutDto;
import com.doggy.todoappspringboot.dto.TodoReturnDto;
import com.doggy.todoappspringboot.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/todo")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @GetMapping("")
    public ResponseEntity<List<TodoReturnDto>> getTodos() {
        return new ResponseEntity<>(todoService.getTodos(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<TodoReturnDto> getTodoById(@PathVariable(value = "id") Long id) {
        try {
            return new ResponseEntity<>(todoService.getTodoById(id), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("")
    public ResponseEntity<TodoReturnDto> postTodo(@Validated @RequestBody TodoPostDto todoPostDto) {
        return new ResponseEntity<>(todoService.postTodo(todoPostDto), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<TodoReturnDto> putTodoWithId(@PathVariable(value = "id") Long id, @Validated @RequestBody TodoPutDto todoPutDto) {
        try {
            return new ResponseEntity<>(todoService.putTodoWithId(id, todoPutDto), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("{id}")
    public ResponseEntity<TodoReturnDto> patchTodoWithId(@PathVariable(value = "id") Long id, @Validated @RequestBody TodoPatchDto todoPatchDto) {
        try {
            return new ResponseEntity<>(todoService.patchTodoWithId(id, todoPatchDto), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

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
