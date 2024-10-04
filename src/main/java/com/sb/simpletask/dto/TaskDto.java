
package com.sb.simpletask.dto;

import lombok.Data;

@Data
public class TaskDto {
	private String title;
	private String description;
	private boolean completed;

	public TaskDto() {

	}

	public TaskDto(String title, String description, boolean completed) {
		this.title = title;
		this.description = description;
		this.completed = completed;
	}

}
