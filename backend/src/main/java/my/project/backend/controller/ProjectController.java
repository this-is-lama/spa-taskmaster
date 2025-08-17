package my.project.backend.controller;

import lombok.RequiredArgsConstructor;
import my.project.backend.entity.Project;
import my.project.backend.entity.Task;
import my.project.backend.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins="http://localhost:4200", allowedHeaders = "*")
@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping
    public List<Project> getAllProjects() {
        return projectService.getAllProjects();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable Long id) {
        Optional<Project> project = projectService.getProjectById(id);
      return project.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Project createProject(@RequestBody Project project) {
        return projectService.saveProject(project);
    }

  @PutMapping("/{id}")
  public ResponseEntity<Project> updateProject(@PathVariable Long id, @RequestBody Project projectDetails) {
    Optional<Project> project = projectService.getProjectById(id);
    if (project.isPresent()) {
      Project updatedProject = project.get();
      updatedProject.setName(projectDetails.getName());
      updatedProject.setDescription(projectDetails.getDescription());

      // Очищаем текущие задачи
      updatedProject.getTasks().clear();

      // Добавляем новые задачи
      for (Task task : projectDetails.getTasks()) {
        task.setId(null); // Убедимся, что это новые задачи
        task.setProject(updatedProject); // Устанавливаем связь
        updatedProject.addTask(task);
      }
      return ResponseEntity.ok(projectService.saveProject(updatedProject));
    } else {
      return ResponseEntity.notFound().build();
    }
  }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }
}
