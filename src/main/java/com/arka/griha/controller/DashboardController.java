package com.arka.griha.controller;

import com.arka.griha.model.RepairTask;
import com.arka.griha.model.User;
import com.arka.griha.repository.RepairTaskRepository;
import com.arka.griha.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping({"/", "/dashboard"})
public class DashboardController {

    private final RepairTaskRepository repairTaskRepository;
    private final UserRepository userRepository;

    public DashboardController(RepairTaskRepository repairTaskRepository, UserRepository userRepository) {
        this.repairTaskRepository = repairTaskRepository;
        this.userRepository = userRepository;
    }

    private User getAuthenticatedUser(Authentication authentication) {
        return userRepository.findByUsername(authentication.getName()).orElseThrow();
    }

    @GetMapping
    public String dashboard(Model model, Authentication authentication) {
        User user = getAuthenticatedUser(authentication);
        List<RepairTask> tasks = repairTaskRepository.findByUser(user);
        model.addAttribute("tasks", tasks);
        model.addAttribute("username", user.getUsername());
        return "dashboard";
    }

    @GetMapping("/task/new")
    public String newTaskForm(Model model) {
        model.addAttribute("task", new RepairTask());
        return "task-form";
    }

    @PostMapping("/task")
    public String saveTask(@ModelAttribute("task") RepairTask task, Authentication authentication) {
        User user = getAuthenticatedUser(authentication);
        if (task.getId() != null) {
            RepairTask existing = repairTaskRepository.findById(task.getId()).orElseThrow();
            if (!existing.getUser().getId().equals(user.getId())) {
                return "redirect:/dashboard"; 
            }
            existing.setTitle(task.getTitle());
            existing.setDescription(task.getDescription());
            existing.setCategory(task.getCategory());
            existing.setStatus(task.getStatus());
            repairTaskRepository.save(existing);
        } else {
            task.setUser(user);
            if (task.getStatus() == null || task.getStatus().isEmpty()) {
                task.setStatus("Pending");
            }
            repairTaskRepository.save(task);
        }
        return "redirect:/dashboard";
    }

    @GetMapping("/task/edit/{id}")
    public String editTaskForm(@PathVariable Long id, Model model, Authentication authentication) {
        User user = getAuthenticatedUser(authentication);
        RepairTask task = repairTaskRepository.findById(id).orElseThrow();
        if (!task.getUser().getId().equals(user.getId())) {
            return "redirect:/dashboard";
        }
        model.addAttribute("task", task);
        return "task-form";
    }

    @PostMapping("/task/delete/{id}")
    public String deleteTask(@PathVariable Long id, Authentication authentication) {
        User user = getAuthenticatedUser(authentication);
        RepairTask task = repairTaskRepository.findById(id).orElseThrow();
        if (task.getUser().getId().equals(user.getId())) {
            repairTaskRepository.delete(task);
        }
        return "redirect:/dashboard";
    }
}
