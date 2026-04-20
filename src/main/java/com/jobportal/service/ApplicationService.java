package com.jobportal.service;

import com.jobportal.model.Application;
import com.jobportal.model.Job;
import com.jobportal.model.User;
import com.jobportal.repository.ApplicationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;

    public ApplicationService(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    public Application applyForJob(Job job, User student, String coverLetter) {
        if (applicationRepository.existsByJobAndStudent(job, student)) {
            throw new RuntimeException("You have already applied for this job");
        }
        Application application = new Application(job, student, "APPLIED", LocalDateTime.now(), coverLetter);
        return applicationRepository.save(application);
    }

    public List<Application> getApplicationsByStudent(User student) {
        return applicationRepository.findByStudent(student);
    }

    public List<Application> getApplicationsByJob(Job job) {
        return applicationRepository.findByJob(job);
    }

    public Application updateApplicationStatus(Long id, String status) {
        Application application = applicationRepository.findById(id).orElseThrow();
        application.setStatus(status);
        return applicationRepository.save(application);
    }

    public Application getApplicationById(Long id) {
        return applicationRepository.findById(id).orElseThrow();
    }
}
