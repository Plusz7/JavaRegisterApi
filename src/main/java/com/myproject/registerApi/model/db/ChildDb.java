package com.myproject.registerApi.model.db;

import com.myproject.registerApi.model.ActivityDTO;
import com.myproject.registerApi.model.ChildDTO;
import com.myproject.registerApi.model.enums.Gender;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "child")
public class ChildDb {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(nullable = false)
    private Integer age;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "social_number", nullable = false)
    private String socialNumber;

    @Column
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "child_activity_join",
            joinColumns = @JoinColumn(name = "child_id"),
            inverseJoinColumns = @JoinColumn(name = "activity_id")
    )
    List<ActivityDb> listOfActivities = new ArrayList<>();

    public ChildDb() {
    }

    public ChildDb(UUID id, String firstName, String lastName, Integer age, Gender gender, String socialNumber, List<ActivityDb> list) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
        this.socialNumber = socialNumber;
        this.listOfActivities = list;
    }

    public ChildDb(String firstName, String lastName, Integer age, Gender gender, String socialNumber, List<ActivityDb> list) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
        this.socialNumber = socialNumber;
        this.listOfActivities = list;
    }

    public UUID getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Integer getAge() {
        return age;
    }

    public Gender getGender() {
        return gender;
    }

    public String getSocialNumber() {
        return socialNumber;
    }

    public List<ActivityDb> getActivities() {
        return listOfActivities;
    }

    public ChildDTO conversionToDtoNoId() {
        return new ChildDTO()
                .firstName(this.getFirstName())
                .lastName(this.getLastName())
                .age(this.getAge())
                .gender(this.getGender().name())
                .socialNumber(this.getSocialNumber())
                .activities(convertActivityListDbToDto());
    }

    public ChildDTO conversionToDto() {
        return new ChildDTO()
                .id(this.getId())
                .firstName(this.getFirstName())
                .lastName(this.getLastName())
                .age(this.getAge())
                .gender(this.getGender().name())
                .socialNumber(this.getSocialNumber())
                .activities(convertActivityListDbToDto());
    }

    public List<ActivityDTO> convertActivityListDbToDto() {
        return this.getActivities().stream()
                .map((activityDb ->
                        new ActivityDTO()
                                .id(activityDb.getId())
                                .name(activityDb.getName())
                ))
                .toList();
    }
}
