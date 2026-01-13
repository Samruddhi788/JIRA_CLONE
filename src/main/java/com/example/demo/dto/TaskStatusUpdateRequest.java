package com.example.demo.dto;

import com.example.demo.model.Status;

public class TaskStatusUpdateRequest {
    private Status newStatus;

    public Status getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(Status newStatus) {
        this.newStatus = newStatus;
    }
    
}
 