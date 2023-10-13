package com.myproject.registerApi.model.db;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "activity")
public class ActivityDb{

        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        private UUID id;
        @Column(nullable = false)
        private String name;

        public ActivityDb() {
        }
        public ActivityDb(String name) {
                this.name = name;
        }

        public ActivityDb(UUID id, String name) {
                this.id = id;
                this.name = name;
        }

        public UUID getId() {
                return id;
        }

        public String getName() {
                return name;
        }
}
