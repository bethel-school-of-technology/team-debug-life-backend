package com.debuglife.auth;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/post")
public class PostController {
	
  @Autowired
  private MySQLUserDetailsService userService;

  @Autowired
  private PostRepository postRepo;

  @PostMapping("/create")
  public ResponseEntity<Post> create(@RequestBody Post newPost, Authentication auth) {
	  Post createdPost;
	  String username = auth.getName();
	  String userId = userService.getUserIdByUsername(username).toString();
	  if (newPost.getMessage().length() <= 140) {
		  newPost.setAuthorId(userId);
		  createdPost = postRepo.save(newPost);
	  } else {
		  return ResponseEntity.badRequest().header("Post","Message over 140 characters in length").build();
	  }
	  
	  return ResponseEntity.ok(createdPost);
  }
  
  @PutMapping("/{id}")
  public ResponseEntity<Post> put(@PathVariable(value="id") Long id, @RequestBody Post updatedPost, Authentication auth) {
	  Post foundPost = postRepo.findById(id).orElse(null);
	  if(foundPost == null || !isAuthorized(auth, foundPost.getAuthorId()) ) {
          return ResponseEntity.notFound().header("Post","Nothing found with that id").build();
      }
	  foundPost.setMessage(updatedPost.getMessage());
	  postRepo.save(foundPost);
      return ResponseEntity.ok(foundPost);
  }
  
  @DeleteMapping("/{id}")
  public ResponseEntity<String> delete(@PathVariable(value="id") Long id, Authentication auth) {
	  Post foundPost = postRepo.findById(id).orElse(null);
	  if(foundPost == null || !isAuthorized(auth, foundPost.getAuthorId())) {
          return ResponseEntity.notFound().header("Post","Nothing found with that id").build();
      }
	  postRepo.delete(foundPost);
      return ResponseEntity.ok("Post " + id + " deleted");
  }
  
  @GetMapping("/{id}")
  public ResponseEntity<Post> get(@PathVariable(value="id") Long id) {
	  Post foundPost = postRepo.findById(id).orElse(null);

      if(foundPost == null) {
          return ResponseEntity.notFound().header("Post","Nothing found with that id").build();
      }
      return ResponseEntity.ok(foundPost);
  }
  
  @GetMapping("")
  public ResponseEntity<List<Post>> getAll() {
	  List<Post> foundPosts = postRepo.findAll();
      return ResponseEntity.ok(foundPosts);
  }
  
  private boolean isAuthorized(Authentication auth, String authorId) {
	  String username = auth.getName();
	  String userId = userService.getUserIdByUsername(username).toString();
	  return userId.equals(authorId);
  }
}