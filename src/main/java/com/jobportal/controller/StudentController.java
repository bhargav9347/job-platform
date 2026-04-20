package com.jobportal.controller;

import com.jobportal.model.Application;
import com.jobportal.model.Job;
import com.jobportal.model.User;
import com.jobportal.service.ApplicationService;
import com.jobportal.service.JobService;
import com.jobportal.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/student")
public class StudentController {

    private final UserService userService;
    private final JobService jobService;
    private final ApplicationService applicationService;

    public StudentController(UserService userService, JobService jobService, ApplicationService applicationService) {
        this.userService = userService;
        this.jobService = jobService;
        this.applicationService = applicationService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, Authentication auth) {
        User student = userService.findByEmail(auth.getName());
        List<Application> applications = applicationService.getApplicationsByStudent(student);
        model.addAttribute("user", student);
        model.addAttribute("applications", applications);
        return "student/dashboard";
    }

    @GetMapping("/profile")
    public String profile(Model model, Authentication auth) {
        User student = userService.findByEmail(auth.getName());
        model.addAttribute("user", student);
        return "student/profile";
    }

    @PostMapping("/profile/update")
    public String updateProfile(User user, @RequestParam(required = false) MultipartFile resume, Authentication auth) throws IOException {
        User student = userService.findByEmail(auth.getName());
        user.setId(student.getId()); // Ensure we are updating the logged-in user
        userService.updateProfile(user, resume);
        return "redirect:/student/profile?success";
    }

    @PostMapping("/apply/{jobId}")
    public String apply(@PathVariable Long jobId, @RequestParam String coverLetter, Authentication auth, Model model) {
        try {
            User student = userService.findByEmail(auth.getName());
            Job job = jobService.getJobById(jobId);
            applicationService.applyForJob(job, student, coverLetter);
            return "redirect:/student/dashboard?applied";
        } catch (Exception e) {
            return "redirect:/?error=" + e.getMessage();
        }
    }
}
