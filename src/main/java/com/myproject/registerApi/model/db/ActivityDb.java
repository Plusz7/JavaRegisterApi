package com.myproject.registerApi.model.db;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "activity")
public class ActivityDb{

        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        private UUID id;
        @Column(nullable = false, unique = true)
        private String name;

        public ActivityDb() {
        }
        public ActivityDb(String name) {
                this.name = name;
        }

        public UUID getId() {
                return id;
        }

        public String getName() {
                return name;
        }
}
