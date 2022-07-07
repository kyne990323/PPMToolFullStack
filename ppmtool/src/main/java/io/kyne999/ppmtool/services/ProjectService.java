package io.kyne999.ppmtool.services;

import io.kyne999.ppmtool.domain.Project;
import io.kyne999.ppmtool.exceptions.ProjectIdException;
import io.kyne999.ppmtool.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    public Project saveOrUpdateProject(Project project) {

        try {
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            return projectRepository.save(project);
        } catch (Exception e) {
            throw new ProjectIdException
                    ("Project ID '" + project.getProjectIdentifier().toUpperCase() + "' already exists");
        }

    }

    public Project findProjectByIdentifier(String projectId) {

        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());

        if (project == null)
            throw new ProjectIdException
                    ("Project ID '" + projectId + "' does not exist");


        return project;
    }

    public Iterable<Project> findAllProjects() {
        return projectRepository.findAll();

    }

    public void deleteProjectByIdentifier(String projectId) {
        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
        if (project == null)
            throw new ProjectIdException(
                    "Cannot delete project with ID'" + projectId + "'. This project does not exist.");
        projectRepository.delete(project);

    }
}
