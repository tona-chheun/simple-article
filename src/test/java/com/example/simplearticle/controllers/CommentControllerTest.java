package com.example.simplearticle.controllers;

import com.example.simplearticle.requests.CommentRequest;
import com.example.simplearticle.response.ArticleResponse;
import com.example.simplearticle.response.CommentResponse;
import com.example.simplearticle.services.CommentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CommentController.class)
@AutoConfigureMockMvc
public class CommentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private CommentService commentService;

    @Test
    void shouldGetCommentsByArticle() throws Exception {
        Long articleId = 1L;
        CommentResponse commentResponse = mockCommentResponse("Good article", articleId);

        Mockito.when(commentService.findByArticle(articleId))
                .thenReturn(List.of(commentResponse));

        mockMvc.perform(get("/api/articles/{articleId}/comments", articleId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Comments retrieved successfully"))
                .andExpect(jsonPath("$.data[0].content").value("Good article"))
                .andExpect(jsonPath("$.data[0].article.id").value(articleId));
    }

    @Test
    void shouldCreateComment() throws Exception {
        Long articleId = 1L;

        CommentRequest request = new CommentRequest("Nice content");

        CommentResponse response = mockCommentResponse("Nice content", articleId);

        Mockito.when(commentService.create(eq(articleId), any(CommentRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post("/api/articles/{articleId}/comments", articleId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Comment created successfully"))
                .andExpect(jsonPath("$.data.content").value("Nice content"))
                .andExpect(jsonPath("$.data.article.id").value(articleId));
    }

    @Test
    void shouldDeleteComment() throws Exception {
        Long articleId = 1L;
        UUID commentId = UUID.randomUUID();

        Mockito.doNothing()
                .when(commentService)
                .delete(articleId, commentId);

        mockMvc.perform(delete(
                        "/api/articles/{articleId}/comments/{commentId}",
                        articleId,
                        commentId
                ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Comment deleted successfully"))
                .andExpect(jsonPath("$.data").doesNotExist());

        Mockito.verify(commentService).delete(articleId, commentId);
    }

    private CommentResponse mockCommentResponse(String content, Long articleId) {
        return new CommentResponse(
                UUID.randomUUID(),
                content,
                LocalDateTime.of(2026, 6, 21, 11, 50, 30),
                LocalDateTime.of(2026, 6, 21, 11, 50, 30),
                new ArticleResponse(
                        articleId,
                        "Java",
                        "Java content",
                        LocalDateTime.of(2026, 6, 21, 11, 50, 30),
                        LocalDateTime.of(2026, 6, 21, 11, 50, 30)
                )
        );
    }
}
