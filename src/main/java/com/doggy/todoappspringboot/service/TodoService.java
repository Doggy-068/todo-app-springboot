package com.doggy.todoappspringboot.service;

import com.doggy.todoappspringboot.dto.TodoPatchDto;
import com.doggy.todoappspringboot.dto.TodoPostDto;
import com.doggy.todoappspringboot.dto.TodoPutDto;
import com.doggy.todoappspringboot.dto.TodoReturnDto;
import com.doggy.todoappspringboot.entity.TodoEntity;
import com.doggy.todoappspringboot.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    private TodoReturnDto entity2ReturnDto(TodoEntity todoEntity) {
        return new TodoReturnDto(todoEntity.id, todoEntity.name, todoEntity.isComplete);
    }

    public List<TodoReturnDto> getTodos() {
        return todoRepository.findAll().stream().map(this::entity2ReturnDto).toList();
    }

    public TodoReturnDto getTodoById(Long id) {
        return entity2ReturnDto(todoRepository.findById(id).get());
    }

    public TodoReturnDto postTodo(TodoPostDto todoPostDto) {
        TodoEntity todoEntity = new TodoEntity(todoPostDto.name(), todoPostDto.isComplete());
        return entity2ReturnDto(todoRepository.save(todoEntity));
    }

    public TodoReturnDto putTodoWithId(Long id, TodoPutDto todoPutDto) {
        if (todoRepository.existsById(id)) {
            TodoEntity todoEntity = new TodoEntity(id, todoPutDto.name(), todoPutDto.isComplete());
            return entity2ReturnDto(todoRepository.save(todoEntity));
        }
        throw new NoSuchElementException();
    }

    public TodoReturnDto patchTodoWithId(Long id, TodoPatchDto todoPatchDto) {
        if (todoRepository.existsById(id)) {
            TodoEntity oldEntity = todoRepository.findById(id).get();
            TodoEntity newEntity = new TodoEntity(
                    id,
                    todoPatchDto.name() == null ? oldEntity.name : todoPatchDto.name(),
                    todoPatchDto.isComplete() == null ? oldEntity.isComplete : todoPatchDto.isComplete()
            );
            return entity2ReturnDto(todoRepository.save(newEntity));
        }
        throw new NoSuchElementException();
    }

    public void deleteTodoById(Long id) {
        if (todoRepository.existsById(id)) {
            todoRepository.deleteById(id);
        }
        throw new NoSuchElementException();
    }

}
