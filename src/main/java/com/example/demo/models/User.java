package com.example.demo.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String password;
    private String email;

    @ElementCollection
    private List<String> expertise; // List of areas of expertise
    private int yearsOfExperience; // Number of years of experience
    private String location; // Mentor's location
    @ElementCollection
    private List<String> codingLanguages; // Languages the mentor knows
    @ElementCollection
    private List<String> availability; // e.g., ["Monday", "Wednesday"]
    private String meetingType; // "virtual" or "in-person"

   @ElementCollection
   @CollectionTable(name = "mentee_ids", joinColumns = @JoinColumn(name = "user_id"))
   @Column(name = "mentee_id")
   private List<Long> mentees;

   @ElementCollection
   @CollectionTable(name = "mentor_ids", joinColumns = @JoinColumn(name = "user_id"))
   @Column(name = "mentor_id")
   private List<Long> mentors;
}
