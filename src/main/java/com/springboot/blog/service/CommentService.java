package com.springboot.blog.service;

import com.springboot.blog.payload.CommentDto;

import java.util.List;

public interface CommentService {
CommentDto CreateComment(long postId, CommentDto commentDto);

List<CommentDto> getCommentsByPostId(long postId);
CommentDto getCommentById (long postId, long commentId);

CommentDto updateComment (long postId, long commentId, CommentDto commentRequest);

void deleteComment (Long postId, Long commentId);
}
