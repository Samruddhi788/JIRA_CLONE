package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Project;



public interface  ProjectRepository extends JpaRepository<Project, Long> {

 Optional <Project> findByOwnerId(Long userId);

    public boolean existsByNameAndOwnerId(String name, Long id);

  

}
