package com.sb.simpletask.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.sb.simpletask.utils.CustomResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sb.simpletask.dto.TaskDto;
import com.sb.simpletask.entity.Task;
import com.sb.simpletask.entity.User;
import com.sb.simpletask.exception.AccessDeniedException;
import com.sb.simpletask.exception.TaskNotFoundException;
import com.sb.simpletask.exception.UserNotFoundException;
import com.sb.simpletask.repository.TaskRepository;
import com.sb.simpletask.repository.UserRepository;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public CustomResponseDTO getTasks(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new UserNotFoundException("User not found with ID: " + userId);
        }

        List<Task> tasks = taskRepository.findByUserId(userId);
        CustomResponseDTO customResponseDTO = new CustomResponseDTO("Tasks retrieved successfully", "success", true, Map.of("result", tasks));
        return customResponseDTO;


    }


    @Override
    public CustomResponseDTO addTask(Long userId, TaskDto taskDto) {
        CustomResponseDTO customResponseDTO = new CustomResponseDTO();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
        Task task = new Task();
        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setCompleted(taskDto.isCompleted());
        task.setUser(user);
        taskRepository.save(task);

        customResponseDTO.setSuccess(true);
        customResponseDTO.setMessage("Task added successfully!");
        customResponseDTO.setResultType("Success");
        customResponseDTO.setResultMap(Map.of("Result", task));
        return customResponseDTO;
    }

    @Override
    public CustomResponseDTO updateTask(Long taskId, TaskDto taskDto, Long userId) {
        CustomResponseDTO customResponseDTO = new CustomResponseDTO();

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with ID: " + taskId));

        if (!task.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("You are not allowed to update this task");
        }

        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setCompleted(taskDto.isCompleted());
        Task updatedTask = taskRepository.save(task);


        customResponseDTO.setSuccess(true);
        customResponseDTO.setMessage("Task updated successfully!");
        customResponseDTO.setResultType("Success");
        customResponseDTO.setResultMap(Map.of("result", updatedTask));
        return customResponseDTO;

    }

    @Override
    public CustomResponseDTO deleteTask(Long taskId, Long userId) {
        CustomResponseDTO customResponseDTO = new CustomResponseDTO();

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with ID: " + taskId));

        if (!task.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("You are not allowed to delete this task");
        }
        taskRepository.deleteById(taskId);
        customResponseDTO.setSuccess(true);
        customResponseDTO.setMessage("Task deleted successfully!");
        customResponseDTO.setResultType("Success");
        return customResponseDTO;
    }

    @Override
    public CustomResponseDTO changeTaskStatus(Long taskId, boolean completed, Long userId) {
        CustomResponseDTO customResponseDTO = new CustomResponseDTO();
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with ID: " + taskId));
        if (!task.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("You are not allowed to change the status of this task");
        }
        task.setCompleted(completed);
        Task updatedTask = taskRepository.save(task);

        customResponseDTO.setMessage("task status changed successfully!");
        customResponseDTO.setSuccess(true);
        customResponseDTO.setResultType("Success");

        customResponseDTO.setResultMap(Map.of("resultMap", updatedTask));
        return customResponseDTO;
    }

}
