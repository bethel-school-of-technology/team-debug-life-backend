package com.debuglife.auth;

import java.time.LocalDateTime;
import javax.persistence.*;


@Entity
@Table(name="post")
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String authorId;
	private String message;
	private LocalDateTime timeStamp = LocalDateTime.now();
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getAuthorId() {
		return authorId;
	}
	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	};
	public Long getId() {
		return id;
	}
	public String getTimeStamp() {
		return timeStamp.toString();
	}
}
