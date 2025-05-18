package com.example.newai.comment.service;

import com.example.newai.comment.vo.CommentDto;
import com.example.newai.comment.vo.CommentRequest;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.UUID;

public interface CommentService {
    CommentDto createComment(Authentication authentication, CommentRequest commentRequest, UUID uuid);
    List<CommentDto> readMyAllComments(Authentication authentication);
    List<CommentDto> readNewsAllComments(UUID uuid);
    CommentDto updateComment(Authentication authentication, CommentRequest commentRequest);
    void deleteComment(Authentication authentication, CommentRequest commentRequest);
}
