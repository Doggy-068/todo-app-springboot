package com.doggy.todoappspringboot.controller;

import com.doggy.todoappspringboot.dto.TodoPostDto;
import com.doggy.todoappspringboot.service.TodoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
public class TodoControllerTest {

    @Autowired
    private TodoService todoService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentationContextProvider) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(MockMvcRestDocumentation.documentationConfiguration(restDocumentationContextProvider))
                .build();
    }

    @Test
    void getTodos() throws Exception {
        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/todo").accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(
                        MockMvcRestDocumentation.document(
                                "todo-get",
                                PayloadDocumentation.responseFields(
                                        PayloadDocumentation.fieldWithPath("[].id").type(JsonFieldType.NUMBER).description(""),
                                        PayloadDocumentation.fieldWithPath("[].name").type(JsonFieldType.STRING).description(""),
                                        PayloadDocumentation.fieldWithPath("[].isComplete").type(JsonFieldType.BOOLEAN).description("")
                                )
                        )
                );
    }

    @Test
    void getTodoById() throws Exception {
        Long id = todoService.postTodo(new TodoPostDto("item #x", false)).id();
        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/todo/{id}", id).accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(
                        MockMvcRestDocumentation.document(
                                "todo-get-by-id",
                                RequestDocumentation.pathParameters(
                                        RequestDocumentation.parameterWithName("id").description("")
                                ),
                                PayloadDocumentation.responseFields(
                                        PayloadDocumentation.fieldWithPath("id").type(JsonFieldType.NUMBER).description(""),
                                        PayloadDocumentation.fieldWithPath("name").type(JsonFieldType.STRING).description(""),
                                        PayloadDocumentation.fieldWithPath("isComplete").type(JsonFieldType.BOOLEAN).description("")
                                )
                        )
                );
    }

    @Test
    void postTodo() throws Exception {
        mockMvc.perform(RestDocumentationRequestBuilders.post("/api/todo").contentType(MediaType.APPLICATION_JSON).content("{\"name\":\"item #1\",\"isComplete\":false}").accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(
                        MockMvcRestDocumentation.document(
                                "todo-post",
                                PayloadDocumentation.requestFields(
                                        PayloadDocumentation.fieldWithPath("name").type(JsonFieldType.STRING).description(""),
                                        PayloadDocumentation.fieldWithPath("isComplete").type(JsonFieldType.BOOLEAN).description("")
                                ), PayloadDocumentation.responseFields(
                                        PayloadDocumentation.fieldWithPath("id").type(JsonFieldType.NUMBER).description(""),
                                        PayloadDocumentation.fieldWithPath("name").type(JsonFieldType.STRING).description(""),
                                        PayloadDocumentation.fieldWithPath("isComplete").type(JsonFieldType.BOOLEAN).description("")
                                )
                        )
                );
    }

    @Test
    void putTodoWithId() throws Exception {
        Long id = todoService.postTodo(new TodoPostDto("item #", false)).id();
        mockMvc.perform(RestDocumentationRequestBuilders.put("/api/todo/{id}", id).contentType(MediaType.APPLICATION_JSON).content("{\"name\":\"item #z\",\"isComplete\":true}").accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(
                        MockMvcRestDocumentation.document(
                                "todo-put-with-id",
                                RequestDocumentation.pathParameters(
                                        RequestDocumentation.parameterWithName("id").description("")
                                ),
                                PayloadDocumentation.requestFields(
                                        PayloadDocumentation.fieldWithPath("name").type(JsonFieldType.STRING).description(""),
                                        PayloadDocumentation.fieldWithPath("isComplete").type(JsonFieldType.BOOLEAN).description("")
                                ),
                                PayloadDocumentation.responseFields(
                                        PayloadDocumentation.fieldWithPath("id").type(JsonFieldType.NUMBER).description(""),
                                        PayloadDocumentation.fieldWithPath("name").type(JsonFieldType.STRING).description(""),
                                        PayloadDocumentation.fieldWithPath("isComplete").type(JsonFieldType.BOOLEAN).description("")
                                )
                        )
                );
    }

    @Test
    void patchTodoWithId() throws Exception {
        Long id = todoService.postTodo(new TodoPostDto("item #y", false)).id();
        mockMvc.perform(RestDocumentationRequestBuilders.patch("/api/todo/{id}", id).contentType(MediaType.APPLICATION_JSON).content("{\"isComplete\":true}").accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(
                        MockMvcRestDocumentation.document(
                                "todo-patch-with-id",
                                RequestDocumentation.pathParameters(
                                        RequestDocumentation.parameterWithName("id").description("")
                                ),
                                PayloadDocumentation.requestFields(
                                        PayloadDocumentation.fieldWithPath("name").type(JsonFieldType.STRING).description("").optional(),
                                        PayloadDocumentation.fieldWithPath("isComplete").type(JsonFieldType.BOOLEAN).description("").optional()
                                ),
                                PayloadDocumentation.responseFields(
                                        PayloadDocumentation.fieldWithPath("id").type(JsonFieldType.NUMBER).description(""),
                                        PayloadDocumentation.fieldWithPath("name").type(JsonFieldType.STRING).description(""),
                                        PayloadDocumentation.fieldWithPath("isComplete").type(JsonFieldType.BOOLEAN).description("")
                                )
                        )
                );
    }

    @Test
    void deleteTodoById() throws Exception {
        Long id = todoService.postTodo(new TodoPostDto("item #", false)).id();
        mockMvc.perform(RestDocumentationRequestBuilders.delete("/api/todo/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(
                        MockMvcRestDocumentation.document(
                                "todo-delete-by-id",
                                RequestDocumentation.pathParameters(
                                        RequestDocumentation.parameterWithName("id").description("")
                                )
                        )
                );
    }

}
