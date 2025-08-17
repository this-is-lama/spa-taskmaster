package my.project.backend.service;

import lombok.RequiredArgsConstructor;
import my.project.backend.entity.Project;
import my.project.backend.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ProjectService {

    private final ProjectRepository projectRepository;

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Optional<Project> getProjectById(Long id) {
        return projectRepository.findById(id);
    }

    @Transactional
    public Project saveProject(Project project) {
        return projectRepository.save(project);
    }
    @Transactional
    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }
}
