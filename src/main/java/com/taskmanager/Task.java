package com.taskmanager;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Task {
        private final int id;
        private final String description;
        private boolean completed;
        private LocalDateTime createdAt;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        Task(int nextId, String description) {
            this.id = nextId;
            this.description = description;
            this.createdAt = LocalDateTime.now();
        }

        int getId() {
            return this.id;
        }

//        void setId(int id) {
//            this.id = id;
//        }

        String getDescription() {
            return this.description;
        }

//        void setDescription(String description) {
//            this.description = description;
//        }

        boolean getCompleted() {
            return this.completed;
        }

        void setCompleted(boolean completed) {
            this.completed = completed;
        }

        String getCreatedAt(){
            return this.createdAt.format(formatter);
        }

        void setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
        }

        @Override
        public String toString() {
            return getId() + ". " + getDescription() + " " + getCompleted() + " " + getCreatedAt();
        }
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            return this.id == ((Task) o).id;
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.id);
        }
}
