package com.jobportal.repository;

import com.jobportal.model.Job;
import com.jobportal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {
    List<Job> findByEmployer(User employer);
    
    @Query("SELECT j FROM Job j WHERE " +
           "(:category IS NULL OR j.category = :category) AND " +
           "(:location IS NULL OR j.location LIKE %:location%) AND " +
           "(:experience IS NULL OR j.experienceRequired = :experience)")
    List<Job> findWithFilters(@Param("category") String category, 
                             @Param("location") String location, 
                             @Param("experience") String experience);

    List<Job> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String title, String description);
}
