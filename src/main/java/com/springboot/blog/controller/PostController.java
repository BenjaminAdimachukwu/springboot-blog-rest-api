package com.springboot.blog.controller;

import com.springboot.blog.AppConstants;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping()
@Tag(
        name = "CRUD REST APIs for POST RESOURCES"
)
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // create blogpost
    @Operation(
            summary = "Create Post REST API",
            description = "create REST API is used to save post into database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 CREATED"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PostMapping("/api/v1/posts")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto){
       return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    };

    // get all post rest api
    @Operation(
            summary = "GET ALL  POST REST API",
            description = "GET ALL POST REST API is used to fetch all  the posts from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    @GetMapping("/api/v1/posts")
    public PostResponse getAllPosts(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value ="sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ){
        return postService.getAllPosts(pageNo,pageSize,sortBy, sortDir);
    }

    @Operation(
            summary = "Get Post By id REST API",
            description = "Get POST By Id REST API is used to get a single post from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    // get post by id
    @GetMapping("/api/v1/posts/{id}")
    //@GetMapping(value = "/api//posts/{id}", params = "version 1") using query params for versioning
    //@GetMapping(value = "/api/v1/posts/{id}", headers = "X-API-VERSION=1") versioning with headers
    public ResponseEntity<PostDto> getPostByIdv1(@PathVariable(name ="id") long id){
        return ResponseEntity.ok(postService.getPostById(id));
    }

   /* @GetMapping("/api/v2/posts/{id}")
    //@GetMapping(value = "/api/v2/posts/{id}", params = "version 2")
    //@GetMapping(value = "/api/v2/posts/{id}", headers = "X-API-VERSION=2")
    public ResponseEntity<PostDtov2> getPostByIdv2(@PathVariable(name ="id") long id){
        PostDto postDto = postService.getPostById(id);
        PostDtov2 postDtov2 = new PostDtov2();
        postDtov2.setId(postDto.getId());
        postDtov2.setTitle(postDto.getTitle());
        postDtov2.setDescription(postDto.getDescription());
        postDtov2.setContent(postDto.getContent());
        List<String> tags = new ArrayList<>();
        tags.add("java");
        tags.add("Spring Boot");
        tags.add("AWS");
        postDtov2.setTags(tags);

        return ResponseEntity.ok(postDtov2);

    }*/

    //update post by ID restAPI
    @Operation(
            summary = "UPDATE  POST REST API",
            description = "UPDATE POST REST API is used to UPDATE a particular post from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PutMapping("/api/v1/posts/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto,  @PathVariable(name = "id") long id){
      PostDto postResponse =  postService.updatePost(postDto, id);
      return new ResponseEntity<>(postResponse, HttpStatus.OK);

    }

    //delete post rest APi
    @Operation(
            summary = "DELETE  POST REST API",
            description = "DELETE POST REST API is used to DELETE a particular post from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @DeleteMapping("/api/v1/posts/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deletePost(@PathVariable(name = "id") long id){
        postService.deletePostById(id);
        return new ResponseEntity<>("post entity deleted successfully", HttpStatus.OK);
    }
    
    // Build get posts by category REST API
    @GetMapping("/category/{id}")
    public ResponseEntity<List<PostDto>>  getPostsByCategory(@PathVariable("id") Long categoryId){
        List<PostDto> postDtos = postService.getPostByCategory(categoryId);
        return  ResponseEntity.ok(postDtos);
    }
}
