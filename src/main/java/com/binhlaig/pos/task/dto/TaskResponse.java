//
//
//
//package com.binhlaig.pos.task.dto;
//
//import com.binhlaig.pos.task.entity.Task;
//import lombok.Builder;
//import lombok.Getter;
//
//@Getter
//@Builder
//public class TaskResponse {
//
//    private String id;
//    private String title;
//    private String description;
//    private String dueDate;
//    private String status;
//    private String priority;
//    private String subject;
//    private String assignedTo;
//
//    public static TaskResponse from(Task task) {
//
//        String status = task.getStatus() != null
//                ? task.getStatus().name().replace("_", " ")
//                : "Pending";
//
//        String priority = task.getPriority() != null
//                ? task.getPriority().name()
//                : "Medium";
//
//        return TaskResponse.builder()
//                .id(task.getId() != null ? String.valueOf(task.getId()) : null)
//                .title(task.getTitle() != null ? task.getTitle() : "Untitled Task")
//                .description(task.getDescription() != null ? task.getDescription() : "")
//                .dueDate(task.getDueDate() != null ? task.getDueDate() : "05:00 PM")
//                .status(status)
//                .priority(priority)
//                .subject(task.getSubject() != null ? task.getSubject() : "Main Branch")
//                .assignedTo(
//                        task.getAssignedTo() != null
//                                ? String.valueOf(task.getAssignedTo().getId())
//                                : null
//                )
//                .build();
//    }
//}

















package com.binhlaig.pos.task.dto;

import com.binhlaig.pos.task.entity.Task;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TaskResponse {

    private String id;
    private String title;
    private String description;
    private String dueDate;
    private String status;
    private String priority;
    private String subject;

    // staff row id
    private String assignedTo;

    // staff name for frontend display
    private String assignedToName;

    public static TaskResponse from(Task task) {

        String status = task.getStatus() != null
                ? task.getStatus().name().replace("_", " ")
                : "Pending";

        String priority = task.getPriority() != null
                ? task.getPriority().name()
                : "Medium";

        String assignedToId = null;
        String assignedToName = "Unassigned";

        if (task.getAssignedTo() != null) {
            assignedToId = task.getAssignedTo().getId() != null
                    ? String.valueOf(task.getAssignedTo().getId())
                    : null;

            assignedToName = task.getAssignedTo().getFullName() != null
                    ? task.getAssignedTo().getFullName()
                    : "Staff #" + assignedToId;
        }

        return TaskResponse.builder()
                .id(task.getId() != null ? String.valueOf(task.getId()) : null)
                .title(task.getTitle() != null ? task.getTitle() : "Untitled Task")
                .description(task.getDescription() != null ? task.getDescription() : "")
                .dueDate(task.getDueDate() != null ? task.getDueDate() : "05:00 PM")
                .status(status)
                .priority(priority)
                .subject(task.getSubject() != null ? task.getSubject() : "Main Branch")
                .assignedTo(assignedToId)
                .assignedToName(assignedToName)
                .build();
    }
}