//
//
//package com.binhlaig.pos.task.controller;
//
//import com.binhlaig.pos.task.dto.TaskRequest;
//import com.binhlaig.pos.task.dto.TaskResponse;
//import com.binhlaig.pos.task.dto.TaskStatusUpdateRequest;
//import com.binhlaig.pos.task.service.TaskService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.server.ResponseStatusException;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/tasks")
//@RequiredArgsConstructor
//public class TaskController {
//
//    private final TaskService taskService;
//
//    @GetMapping
//    public List<TaskResponse> getAllTasks(
//            @RequestHeader("Authorization") String authorization
//    ) {
//        String token = extractBearer(authorization);
//        return taskService.getAll(token);
//    }
//
//    @GetMapping("/pool")
//    public List<TaskResponse> getPoolTasks(
//            @RequestHeader("Authorization") String authorization
//    ) {
//        String token = extractBearer(authorization);
//        return taskService.getPool(token);
//    }
//
//    @GetMapping("/staff/{staffId}")
//    public List<TaskResponse> getTasksByStaff(
//            @PathVariable Long staffId,
//            @RequestHeader("Authorization") String authorization
//    ) {
//        String token = extractBearer(authorization);
//        return taskService.getByStaff(staffId, token);
//    }
//
//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public TaskResponse createTask(
//            @RequestBody TaskRequest request,
//            @RequestHeader("Authorization") String authorization
//    ) {
//        String token = extractBearer(authorization);
//        return taskService.create(request, token);
//    }
//
//    @PatchMapping("/{taskId}/assign/{staffId}")
//    public TaskResponse assignTask(
//            @PathVariable Long taskId,
//            @PathVariable Long staffId,
//            @RequestHeader("Authorization") String authorization
//    ) {
//        String token = extractBearer(authorization);
//        return taskService.assign(taskId, staffId, token);
//    }
//
//    @PatchMapping("/{taskId}/unassign")
//    public TaskResponse unassignTask(
//            @PathVariable Long taskId,
//            @RequestHeader("Authorization") String authorization
//    ) {
//        String token = extractBearer(authorization);
//        return taskService.unassign(taskId, token);
//    }
//
//    @PatchMapping("/{taskId}/status")
//    public TaskResponse updateTaskStatus(
//            @PathVariable Long taskId,
//            @RequestBody TaskStatusUpdateRequest request,
//            @RequestHeader("Authorization") String authorization
//    ) {
//        String token = extractBearer(authorization);
//        return taskService.updateStatus(taskId, request.getStatus(), token);
//    }
//
//    @DeleteMapping("/{taskId}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void deleteTask(
//            @PathVariable Long taskId,
//            @RequestHeader("Authorization") String authorization
//    ) {
//        String token = extractBearer(authorization);
//        taskService.delete(taskId, token);
//    }
//
//    private String extractBearer(String authorization) {
//        if (authorization == null || authorization.isBlank()) {
//            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing Authorization header");
//        }
//
//        if (!authorization.startsWith("Bearer ")) {
//            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid Authorization header");
//        }
//
//        String token = authorization.substring(7).trim();
//
//        if (token.isEmpty()) {
//            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Empty bearer token");
//        }
//
//        return token;
//    }
//}






















package com.binhlaig.pos.task.controller;

import com.binhlaig.pos.shopfeature.FeatureKey;
import com.binhlaig.pos.shopfeature.ShopFeatureService;
import com.binhlaig.pos.task.dto.TaskRequest;
import com.binhlaig.pos.task.dto.TaskResponse;
import com.binhlaig.pos.task.dto.TaskStatusUpdateRequest;
import com.binhlaig.pos.task.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final ShopFeatureService shopFeatureService;

    @GetMapping("/my")
    public List<TaskResponse> getMyTasks(
            @RequestHeader("Authorization") String authorization
    ) {
        requireTasksFeature(authorization);
        String token = extractBearer(authorization);
        return taskService.getMyTasks(token);
    }

    @GetMapping
    public List<TaskResponse> getAllTasks(
            @RequestHeader("Authorization") String authorization
    ) {
        requireTasksFeature(authorization);
        String token = extractBearer(authorization);
        return taskService.getAll(token);
    }

    @GetMapping("/pool")
    public List<TaskResponse> getPoolTasks(
            @RequestHeader("Authorization") String authorization
    ) {
        requireTasksFeature(authorization);
        String token = extractBearer(authorization);
        return taskService.getPool(token);
    }

    @GetMapping("/staff/{staffId}")
    public List<TaskResponse> getTasksByStaff(
            @PathVariable Long staffId,
            @RequestHeader("Authorization") String authorization
    ) {
        requireTasksFeature(authorization);
        String token = extractBearer(authorization);
        return taskService.getByStaff(staffId, token);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ADMIN','OWNER')")
    public TaskResponse createTask(
            @RequestBody TaskRequest request,
            @RequestHeader("Authorization") String authorization
    ) {
        requireTasksFeature(authorization);
        String token = extractBearer(authorization);
        return taskService.create(request, token);
    }

    @PatchMapping("/{taskId}/assign/{staffId}")
    @PreAuthorize("hasAnyRole('ADMIN','OWNER')")
    public TaskResponse assignTask(
            @PathVariable Long taskId,
            @PathVariable Long staffId,
            @RequestHeader("Authorization") String authorization
    ) {
        requireTasksFeature(authorization);
        String token = extractBearer(authorization);
        return taskService.assign(taskId, staffId, token);
    }

    @PatchMapping("/{taskId}/unassign")
    @PreAuthorize("hasAnyRole('ADMIN','OWNER')")
    public TaskResponse unassignTask(
            @PathVariable Long taskId,
            @RequestHeader("Authorization") String authorization
    ) {
        requireTasksFeature(authorization);
        String token = extractBearer(authorization);
        return taskService.unassign(taskId, token);
    }

    @PatchMapping("/{taskId}/status")
    public TaskResponse updateTaskStatus(
            @PathVariable Long taskId,
            @RequestBody TaskStatusUpdateRequest request,
            @RequestHeader("Authorization") String authorization
    ) {
        requireTasksFeature(authorization);
        String token = extractBearer(authorization);
        return taskService.updateStatus(taskId, request.getStatus(), token);
    }

    @DeleteMapping("/{taskId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyRole('ADMIN','OWNER')")
    public void deleteTask(
            @PathVariable Long taskId,
            @RequestHeader("Authorization") String authorization
    ) {
        requireTasksFeature(authorization);
        String token = extractBearer(authorization);
        taskService.delete(taskId, token);
    }

    private void requireTasksFeature(String authorization) {
        shopFeatureService.requireFeatureFromAuthorization(authorization, FeatureKey.TASKS);
    }

    private String extractBearer(String authorization) {
        if (authorization == null || authorization.isBlank()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing Authorization header");
        }

        if (!authorization.startsWith("Bearer ")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid Authorization header");
        }

        String token = authorization.substring(7).trim();

        if (token.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Empty bearer token");
        }

        return token;
    }
}
