package com.udemy.learn.blogging.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String imageId;
    String name;
    String userName;
    String email;
    String password;
    String profession;
    String interestAndExpertise;
    String resetCode;
    LocalDateTime codeexpirytime;
   
   
    
    @ManyToMany(fetch=FetchType.EAGER)
    Set<Role> role;
    
    @OneToMany(mappedBy = "author", fetch = FetchType.EAGER)
    Set<Post> post;
    
    @ManyToMany(mappedBy = "likedby", fetch = FetchType.EAGER)
    Set<Post> likedPost;
}
