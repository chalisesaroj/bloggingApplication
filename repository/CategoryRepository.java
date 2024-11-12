package com.udemy.learn.blogging.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.udemy.learn.blogging.entity.Category;
import com.udemy.learn.blogging.entity.Post;
import com.udemy.learn.blogging.payload.PostDto;

public interface CategoryRepository extends JpaRepository<Category, Long> {
Optional<Category>findByName(String category);
boolean existsByName(String category);
}
