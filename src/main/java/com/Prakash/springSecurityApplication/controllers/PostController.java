package com.Prakash.springSecurityApplication.controllers;


import com.Prakash.springSecurityApplication.dtos.PostDTO;
import com.Prakash.springSecurityApplication.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping
    public List<PostDTO> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/{postId}")
    public PostDTO getPostById(@PathVariable Long postId) {


        return postService.getPostById(postId);
    }

    @PostMapping
    public PostDTO createNewPost(@RequestBody PostDTO inputPost) {
        return postService.createNewPost(inputPost);
    }

    @PutMapping("/{postId}")
    public PostDTO updatePost(@RequestBody PostDTO inputPost,@PathVariable Long postId ) {
        return postService.updatePost(inputPost, postId);
    }


}