package com.example.demo.model;

public enum Status {
    
    TO_DO,
    IN_PROGRESS,
    IN_REVIEW,
    DONE;

     public boolean canTransitionTo(Status next) {
        int diff = next.ordinal() - this.ordinal();
        return diff == 1 || diff == -1; // only next or previous
    }
}

