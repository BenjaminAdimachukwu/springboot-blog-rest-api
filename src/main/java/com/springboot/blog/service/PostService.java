package com.springboot.blog.service;

import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import org.springframework.stereotype.Service;

import java.util.List;


public interface PostService {
    PostDto createPost (PostDto postDto);

    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

    PostDto getPostById(long id);

    PostDto updatePost(PostDto ostDto, long id);

    void deletePostById (long id);

    List<PostDto> getPostByCategory(Long categoryId);
}
