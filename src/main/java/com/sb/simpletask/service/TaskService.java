package com.sb.simpletask.service;

import java.util.List;

import com.sb.simpletask.dto.TaskDto;
import com.sb.simpletask.entity.Task;
import com.sb.simpletask.utils.CustomResponseDTO;

public interface TaskService {


	CustomResponseDTO getTasks(Long userId);

	CustomResponseDTO addTask(Long userId, TaskDto taskDto);

	CustomResponseDTO updateTask(Long taskId, TaskDto taskDto, Long userId);

	CustomResponseDTO deleteTask(Long taskId, Long userId);
	
	CustomResponseDTO changeTaskStatus(Long taskId, boolean completed, Long userId);
	
	

}
