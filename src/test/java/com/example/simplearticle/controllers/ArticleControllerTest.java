package com.example.simplearticle.controllers;

import com.example.simplearticle.requests.ArticleRequest;
import com.example.simplearticle.response.ArticleResponse;
import com.example.simplearticle.services.ArticleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ArticleController.class)
@AutoConfigureMockMvc
public class ArticleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private ArticleService articleService;

    @Test
    void shouldGetAllArticles() throws Exception {
        ArticleResponse article = new ArticleResponse(
                1L,
                "Title 1",
                "Content 1",
                LocalDateTime.of(2026, 6, 21, 11, 50, 30),
                LocalDateTime.of(2026, 6, 21, 11, 50, 30)
        );

        when(articleService.getAll()).thenReturn(List.of(article));

        mockMvc.perform(get("/api/articles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Articles retrieved successfully"))
                .andExpect(jsonPath("$.data[0].id").value(1L))
                .andExpect(jsonPath("$.data[0].title").value("Title 1"))
                .andExpect(jsonPath("$.data[0].content").value("Content 1"));

        verify(articleService).getAll();
    }

    @Test
    void shouldGetArticleById() throws Exception {
        ArticleResponse article = new ArticleResponse(
                1L,
                "Title 1",
                "Content 1",
                LocalDateTime.of(2026, 6, 21, 11, 50, 30),
                LocalDateTime.of(2026, 6, 21, 11, 50, 30)
        );

        when(articleService.findById(1L)).thenReturn(article);

        mockMvc.perform(get("/api/articles/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.title").value("Title 1"));

        verify(articleService).findById(1L);
    }

    @Test
    void shouldCreateArticle() throws Exception {
        ArticleRequest request = new ArticleRequest(
                "New Title",
                "New Content"
        );

        ArticleResponse response = new ArticleResponse(
                1L,
                "New Title",
                "New Content",
                LocalDateTime.of(2026, 6, 21, 11, 50, 30),
                LocalDateTime.of(2026, 6, 21, 11, 50, 30)
        );

        when(articleService.create(any(ArticleRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/articles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Article created successfully"))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.title").value("New Title"));

        verify(articleService).create(any(ArticleRequest.class));
    }

    @Test
    void shouldUpdateArticle() throws Exception {
        ArticleRequest request = new ArticleRequest(
                "Updated Title",
                "Updated Content"
        );

        ArticleResponse response = new ArticleResponse(
                1L,
                "Updated Title",
                "Updated Content",
                LocalDateTime.of(2026, 6, 21, 11, 50, 30),
                LocalDateTime.of(2026, 6, 21, 11, 50, 30)
        );

        when(articleService.update(eq(1L), any(ArticleRequest.class))).thenReturn(response);

        mockMvc.perform(put("/api/articles/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.title").value("Updated Title"));

        verify(articleService).update(eq(1L), any(ArticleRequest.class));
    }

    @Test
    void shouldDeleteArticle() throws Exception {
        doNothing().when(articleService).delete(1L);

        mockMvc.perform(delete("/api/articles/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Article deleted successfully"));

        verify(articleService).delete(1L);
    }
}
