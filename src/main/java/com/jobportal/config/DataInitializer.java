package com.jobportal.config;

import com.jobportal.model.Job;
import com.jobportal.model.User;
import com.jobportal.repository.JobRepository;
import com.jobportal.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final JobRepository jobRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, JobRepository jobRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jobRepository = jobRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() > 0) return;

        // Create Employers
        User google = new User(null, "Google Inc.", "hr@google.com", passwordEncoder.encode("password123"), "ROLE_EMPLOYER");
        User microsoft = new User(null, "Microsoft", "jobs@microsoft.com", passwordEncoder.encode("password123"), "ROLE_EMPLOYER");
        User meta = new User(null, "Meta", "recruitment@meta.com", passwordEncoder.encode("password123"), "ROLE_EMPLOYER");
        userRepository.saveAll(Arrays.asList(google, microsoft, meta));

        // Create a test Student
        User student = new User(null, "John Doe", "student@example.com", passwordEncoder.encode("password123"), "ROLE_STUDENT");
        student.setSkills("Java, Spring Boot, SQL");
        student.setEducation("BS Computer Science");
        userRepository.save(student);

        // Create Job Listings
        Job job1 = new Job();
        job1.setTitle("Senior Java Developer");
        job1.setDescription("We are looking for an experienced Java developer to join our core infrastructure team. You will work on high-scale distributed systems.");
        job1.setRequiredSkills("Java, Spring Boot, Microservices, Kubernetes");
        job1.setSalary("$140,000 - $180,000");
        job1.setLocation("Mountain View, CA (Hybrid)");
        job1.setCategory("Software Development");
        job1.setExperienceRequired("5+ Years");
        job1.setPostedDate(LocalDateTime.now().minusDays(2));
        job1.setEmployer(google);

        Job job2 = new Job();
        job2.setTitle("Frontend Engineer (React)");
        job2.setDescription("Join our UI team to build beautiful and responsive web applications using React and TypeScript.");
        job2.setRequiredSkills("React, TypeScript, CSS3, Tailwind");
        job2.setSalary("$120,000 - $150,000");
        job2.setLocation("Redmond, WA (Remote)");
        job2.setCategory("Software Development");
        job2.setExperienceRequired("3-5 Years");
        job2.setPostedDate(LocalDateTime.now().minusDays(1));
        job2.setEmployer(microsoft);

        Job job3 = new Job();
        job3.setTitle("Product Designer");
        job3.setDescription("Design the future of social connection. We need a creative designer with strong UI/UX skills.");
        job3.setRequiredSkills("Figma, UI/UX, Prototyping, Adobe Suite");
        job3.setSalary("$110,000 - $140,000");
        job3.setLocation("Menlo Park, CA");
        job3.setCategory("Design");
        job3.setExperienceRequired("1-3 Years");
        job3.setPostedDate(LocalDateTime.now());
        job3.setEmployer(meta);

        Job job4 = new Job();
        job4.setTitle("Data Scientist");
        job4.setDescription("Analyze vast amounts of data to provide actionable insights. Experience with machine learning is a plus.");
        job4.setRequiredSkills("Python, R, SQL, Machine Learning");
        job4.setSalary("$130,000 - $170,000");
        job4.setLocation("London, UK");
        job4.setCategory("Data Science");
        job4.setExperienceRequired("3-5 Years");
        job4.setPostedDate(LocalDateTime.now().minusDays(5));
        job4.setEmployer(google);

        jobRepository.saveAll(Arrays.asList(job1, job2, job3, job4));

        System.out.println("Demo data initialized successfully!");
    }
}
