package com.example.todolist.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.sb.simpletask.ToDoListApplication;
import com.sb.simpletask.dto.TaskDto;
import com.sb.simpletask.dto.UserDto;
import com.sb.simpletask.entity.Task;
import com.sb.simpletask.entity.User;
import com.sb.simpletask.repository.TaskRepository;
import com.sb.simpletask.repository.UserRepository;
import com.sb.simpletask.service.TaskService;
import com.sb.simpletask.service.UserService;
import com.sb.simpletask.utils.CustomResponseDTO;

@Disabled
@SpringBootTest(classes = ToDoListApplication.class)
public class TaskServiceImplTest {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @MockBean
    private TaskRepository taskRepository;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void getTasksTest() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(new User(1L, "user1", "user1")));

        List<Task> tasks = Collections.singletonList(new Task(1L, "Test Task", "Test Description", false));
        when(taskRepository.findByUserId(1L)).thenReturn(tasks);

        CustomResponseDTO customResponseDTO = taskService.getTasks(1L);

        List<Task> result = (List<Task>) customResponseDTO.getResultMap().get("result");

        assertEquals(1, result.size());
        assertEquals("Test Task", result.get(0).getTitle());
    }

    @Test
    public void registerUserTest() {
        UserDto userDto = new UserDto("Shubh", "Shubh");
        User user = new User();
        user.setId(1L);
        BeanUtils.copyProperties(userDto, user);

        when(userRepository.save(user)).thenReturn(user);
        when(userRepository.findByUsername("Shubh")).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(user);

        CustomResponseDTO response = userService.registerUser(userDto);

        assertTrue(response.isSuccess());
        assertEquals("Success", response.getResultType());
        assertEquals("User register successfully!", response.getMessage());
        assertEquals(1L, response.getResultMap().get("id"));
        assertEquals("Shubh", response.getResultMap().get("username"));

        verify(userRepository).findByUsername("Shubh");
        verify(userRepository).save(any(User.class));
    }


    @Test
    public void addTaskTest() {
        TaskDto taskDto = new TaskDto("Do meditation", "Do meditation for 10 min", false);
        Task task = new Task();
        BeanUtils.copyProperties(taskDto, task);
        task.setId(1L);

        User user = new User(1L, "user1", "user1");
        task.setUser(user);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        when(taskRepository.save(any(Task.class))).thenReturn(task);
        CustomResponseDTO customResponseDTO = taskService.addTask(user.getId(), taskDto);
        assertEquals("Success", customResponseDTO.getResultType());
        assertEquals("Task added successfully!", customResponseDTO.getMessage());
    }


    @Test
    public void deleteUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(new User(1L, "user1", "user1")));
        CustomResponseDTO customResponseDTO = userService.deleteUser(1L);
        assertEquals("Success", customResponseDTO.getResultType());
        assertEquals("User deleted successfully!", customResponseDTO.getMessage());

    }

    @Test
    public void deleteTask() {
        User user = new User(1L, "user1", "user1");
        Task task = new Task(1L, "Test Task", "Test Description", false);
        task.setUser(user);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepository.findByUserId(1L)).thenReturn(Collections.singletonList(task));

        CustomResponseDTO customResponseDTO = taskService.deleteTask(1L, 1L);

        assertEquals("Success", customResponseDTO.getResultType());
        assertEquals("Task deleted successfully!", customResponseDTO.getMessage());
    }
}
