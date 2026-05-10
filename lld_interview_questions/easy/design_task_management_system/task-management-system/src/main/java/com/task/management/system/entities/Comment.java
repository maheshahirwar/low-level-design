package com.task.management.system.entities;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
public class Comment {

	private String content;
	private User author;
	private LocalDateTime timestamp;
}
