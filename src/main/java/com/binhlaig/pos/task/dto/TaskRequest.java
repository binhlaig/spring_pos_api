//// src/main/java/com/binhlaig/pos/task/dto/TaskRequest.java
//package com.binhlaig.pos.task.dto;
//
//import lombok.Getter;
//import lombok.Setter;
//
//@Getter
//@Setter
//public class TaskRequest {
//    private String title;
//    private String description;
//    private String dueDate;
//    private String status;     // Pending / In Progress / Done
//    private String priority;   // High / Medium / Low
//    private String subject;
//    private Long assignedTo;   // optional
//}










package com.binhlaig.pos.task.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskRequest {

    @NotBlank(message = "Task title is required")
    @Size(max = 150, message = "Title must be less than 150 characters")
    private String title;

    @Size(max = 1000, message = "Description too long")
    private String description;

    // optional (frontend default: "05:00 PM")
    private String dueDate;

    // Pending / In Progress / Done
    private String status;

    // High / Medium / Low
    private String priority;

    @Size(max = 100, message = "Subject too long")
    private String subject;

    // optional
    private Long assignedTo;
}