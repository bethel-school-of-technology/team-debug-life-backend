package com.debuglife.auth;

import java.sql.SQLIntegrityConstraintViolationException;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping()
public class UserController {

  @Autowired
  private MySQLUserDetailsService userService;

  @PostMapping("/register")
  public ResponseEntity<Object> register(@RequestBody User newUser) {
    try {
      userService.Save(newUser);
      System.out.println("CREATED USER: " + newUser.getUsername());
    } catch (Exception e) {
      System.out.println("ERROR CREATING USER: " + newUser.getUsername());
      return new ResponseEntity<Object>("Username taken", HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<Object>("User created with id: " + newUser.getId(), HttpStatus.OK);
  }
  
  // <Authentication> is automatically resolved by the Spring framework
  // Alternatively, you can ping the SecurityContextHolder to get the current Authentication object as done below
  // Authentication auth = SecurityContextHolder.getContext().getAuthentication();
  
  @GetMapping("/api/checktoken")
  public ResponseEntity<Boolean> checkToken(Authentication auth) {
	 if (auth != null && !(auth instanceof AnonymousAuthenticationToken) && auth.isAuthenticated()) {
		 System.out.println(auth.getName());
		 return ResponseEntity.ok(true);
	 } else {
		 return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
	 }
  }
  
  @GetMapping("/api/getuser")
  public ResponseEntity<String> getUser(Authentication auth) {
	 if (auth != null && !(auth instanceof AnonymousAuthenticationToken) && auth.isAuthenticated()) {
		 System.out.println(auth.getName());
		 return ResponseEntity.ok(auth.getName());
	 } else {
		 return new ResponseEntity<String>("", HttpStatus.NOT_FOUND);
	 }
  }

  @GetMapping("/api/getinventory")
  public ResponseEntity<JSONObject> getInventory(Authentication auth) {
	 if (auth != null && !(auth instanceof AnonymousAuthenticationToken) && auth.isAuthenticated()) {
		 System.out.println(auth.getName());
     User user = userService.loadUserById( userService.getUserIdByUsername(auth.getName()));
		 return ResponseEntity.ok( user.getInventory() );
	 } else {
		 return new ResponseEntity<JSONObject>(new JSONObject(), HttpStatus.NOT_FOUND);
	 }
  }

  @GetMapping("/api/getroom")
  public ResponseEntity<JSONObject> getRoom(Authentication auth) {
    JSONObject container = new JSONObject();
	 if (auth != null && !(auth instanceof AnonymousAuthenticationToken) && auth.isAuthenticated()) {
		 System.out.println(auth.getName());
     User user = userService.loadUserById( userService.getUserIdByUsername(auth.getName()));
     container.put("room", user.getCurrentRoom());
		 return ResponseEntity.ok(container);
	 } else {
		 return new ResponseEntity<JSONObject>(container, HttpStatus.NOT_FOUND);
	 }
  }

  @PostMapping("/api/setroom")
  public ResponseEntity<Boolean> setInventory(Authentication auth, @RequestBody Long room) {
	 if (auth != null && !(auth instanceof AnonymousAuthenticationToken) && auth.isAuthenticated()) {
		 System.out.println(auth.getName());
     User user = userService.loadUserById( userService.getUserIdByUsername(auth.getName()));
     user.setCurrentRoom(room);
     userService.Save(user);
		 return ResponseEntity.ok(true);
	 } else {
		 return new ResponseEntity<Boolean>(false, HttpStatus.NOT_FOUND);
	 }
  }

  @PostMapping("/api/setinventory")
  public ResponseEntity<Boolean> setInventory(Authentication auth, @RequestBody JSONObject inventory) {
	 if (auth != null && !(auth instanceof AnonymousAuthenticationToken) && auth.isAuthenticated()) {
		 System.out.println(auth.getName());
     User user = userService.loadUserById( userService.getUserIdByUsername(auth.getName()));
     user.setInventory(inventory);
     userService.Save(user);
		 return ResponseEntity.ok(true);
	 } else {
		 return new ResponseEntity<Boolean>(false, HttpStatus.NOT_FOUND);
	 }
  }
  
  private boolean isAuthorized(Authentication auth, String authorId) {
	  String username = auth.getName();
	  String userId = userService.getUserIdByUsername(username).toString();
	  return userId.equals(authorId);
  }
}