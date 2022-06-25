package com.projects.projects.controller;

import com.projects.projects.exception.ResourceNotFoundException;
import com.projects.projects.model.Projects;
import com.projects.projects.repository.ProjectsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/projects")
public class ProjectsController {
    @Autowired
    private ProjectsRepository projectsRepository;

    //Return all Projects in by List
    @GetMapping
    public List<Projects> getAllProjects(){
        return projectsRepository.findAll();
    }

    //build create projects REST API
    @PostMapping
    public Projects createProjects(@RequestBody Projects projects){
        return projectsRepository.save(projects);
    }

    //get projects by ID REST API
    @GetMapping("{id}")
    public ResponseEntity<Projects> getProjectsById(@PathVariable long id){
        Projects projects = projectsRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Project not exist with this id:"+id));
        return ResponseEntity.ok(projects);
    }

    //update projects REST API
    @PutMapping("{id}")
    public ResponseEntity<Projects> updateProjects(@PathVariable long id, @RequestBody Projects projectsDetails){
        Projects updateProjects = projectsRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Proejct not exist with id: "+id));
        updateProjects.setName((projectsDetails.getName()));
        updateProjects.setDescription(projectsDetails.getDescription());
        updateProjects.setYear(projectsDetails.getYear());
        updateProjects.setComments(projectsDetails.getComments());

        projectsRepository.save(updateProjects);

        return ResponseEntity.ok(updateProjects);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteProjects(@PathVariable long id){

        Projects projects = projectsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not exist with id: " + id));

        projectsRepository.delete(projects);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
}
