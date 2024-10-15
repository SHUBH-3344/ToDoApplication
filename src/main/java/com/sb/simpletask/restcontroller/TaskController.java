package com.sb.simpletask.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.sb.simpletask.dto.TaskDto;
import com.sb.simpletask.exception.JwtException;
import com.sb.simpletask.exception.UserNotFoundException;
import com.sb.simpletask.security.CustomUserDetails;
import com.sb.simpletask.service.TaskService;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;


    @GetMapping
    public ResponseEntity<?> getTasks(@AuthenticationPrincipal UserDetails userDetails) {
        try {
            Long userId = ((CustomUserDetails) userDetails).getId();
            return ResponseEntity.ok(taskService.getTasks(userId));
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
        }
    }


    @PostMapping("/addTask")
    public ResponseEntity<?> addTask(@RequestBody TaskDto taskDto, @AuthenticationPrincipal UserDetails userDetails) {
        try {
            Long userId = ((CustomUserDetails) userDetails).getId();
            return ResponseEntity.status(HttpStatus.CREATED).body(taskService.addTask(userId, taskDto));
        } catch (JwtException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid JWT token: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Something went wrong: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTask(@PathVariable Long id, @RequestBody TaskDto taskDto, @AuthenticationPrincipal UserDetails userDetails) {

        Long userId = ((CustomUserDetails) userDetails).getId();
        return ResponseEntity.ok(taskService.updateTask(id, taskDto, userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = ((CustomUserDetails) userDetails).getId();
        return ResponseEntity.ok(taskService.deleteTask(id, userId));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<?> changeTaskStatus(@PathVariable Long id, @RequestParam boolean completed, @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = ((CustomUserDetails) userDetails).getId();
        return ResponseEntity.ok(taskService.changeTaskStatus(id, completed, userId));
    }
}
