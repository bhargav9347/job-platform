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

import java.util.List;

@Controller
@RequestMapping("/employer")
public class EmployerController {

    private final JobService jobService;
    private final UserService userService;
    private final ApplicationService applicationService;

    public EmployerController(JobService jobService, UserService userService, ApplicationService applicationService) {
        this.jobService = jobService;
        this.userService = userService;
        this.applicationService = applicationService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, Authentication auth) {
        User employer = userService.findByEmail(auth.getName());
        List<Job> jobs = jobService.getJobsByEmployer(employer);
        model.addAttribute("jobs", jobs);
        
        // Stats for dashboard
        long totalApplicants = jobs.stream().mapToLong(j -> j.getApplications().size()).sum();
        long shortlisted = jobs.stream()
                .flatMap(j -> j.getApplications().stream())
                .filter(a -> "SHORTLISTED".equals(a.getStatus()))
                .count();
        
        model.addAttribute("totalJobs", jobs.size());
        model.addAttribute("totalApplicants", totalApplicants);
        model.addAttribute("shortlistedCount", shortlisted);
        
        return "employer/dashboard";
    }

    @GetMapping("/job/post")
    public String postJobForm(Model model) {
        model.addAttribute("job", new Job());
        return "employer/post-job";
    }

    @PostMapping("/job/post")
    public String postJob(Job job, Authentication auth) {
        User employer = userService.findByEmail(auth.getName());
        jobService.postJob(job, employer);
        return "redirect:/employer/dashboard?posted";
    }

    @GetMapping("/job/edit/{id}")
    public String editJobForm(@PathVariable Long id, Model model) {
        model.addAttribute("job", jobService.getJobById(id));
        return "employer/post-job";
    }

    @PostMapping("/job/edit/{id}")
    public String editJob(@PathVariable Long id, Job job) {
        jobService.updateJob(id, job);
        return "redirect:/employer/dashboard?updated";
    }

    @GetMapping("/job/delete/{id}")
    public String deleteJob(@PathVariable Long id) {
        jobService.deleteJob(id);
        return "redirect:/employer/dashboard?deleted";
    }

    @GetMapping("/job/{id}/applicants")
    public String viewApplicants(@PathVariable Long id, Model model) {
        Job job = jobService.getJobById(id);
        List<Application> applications = applicationService.getApplicationsByJob(job);
        model.addAttribute("job", job);
        model.addAttribute("applications", applications);
        return "employer/applicants";
    }

    @PostMapping("/application/{id}/status")
    public String updateStatus(@PathVariable Long id, @RequestParam String status) {
        Application application = applicationService.getApplicationById(id);
        applicationService.updateApplicationStatus(id, status);
        return "redirect:/employer/job/" + application.getJob().getId() + "/applicants";
    }
}
