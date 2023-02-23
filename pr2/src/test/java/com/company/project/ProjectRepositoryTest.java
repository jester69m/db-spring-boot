package com.company.project;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataMongoTest
public class ProjectRepositoryTest {

    @Autowired
    private ProjectRepository repository;

    @AfterEach
    void clearData() {
        repository.deleteAll();
    }

    @Test
    void findById_shouldReturnProject() {
        Project savedProject = repository.save(new Project(null, "Project name"));

        Optional<Project> foundProject = repository.findById(savedProject.getId());
        assertTrue(foundProject.isPresent());
        assertEquals(savedProject, foundProject.get());
    }

    @Test
    void findAll_shouldReturnProjects() {
        List<Project> savedProjects = repository.saveAll(List.of(
                new Project(null, "Project 1"),
                new Project(null, "Project 2"),
                new Project(null, "Project 3"),
                new Project(null, "Project 4")
        ));

        List<Project> foundProjects = repository.findAll();
        assertEquals(savedProjects.size(), foundProjects.size());
    }

    @Test
    void saveProject_shouldReturnValidProject() {
        Project projectToSave = new Project(null, "Project name");

        Project savedProject = repository.save(projectToSave);

        assertNotNull(savedProject.getId());
        assertEquals(projectToSave.getName(), savedProject.getName());
    }

    @Test
    void deleteProject_shouldThrowException() {
        Project projectToDelete = repository.save(new Project(null, "project name"));
        assertTrue(repository.existsById(projectToDelete.getId()));

        repository.deleteById(projectToDelete.getId());
        assertFalse(repository.existsById(projectToDelete.getId()));
    }
}
