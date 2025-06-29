package com.Prakash.springSecurityApplication.services;

import com.Prakash.springSecurityApplication.dtos.PostDTO;

import java.util.List;

 public interface PostService {
     List<PostDTO> getAllPosts();

     PostDTO getPostById(Long postId);

     PostDTO createNewPost(PostDTO inputPost);

     PostDTO updatePost(PostDTO inputPost, Long postId);
}
