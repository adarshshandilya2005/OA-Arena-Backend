package com.InterviewSimulator.Interview.Simulator.Repository;

import com.InterviewSimulator.Interview.Simulator.Entity.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {

    List<Submission> findByAttemptId(Long attemptId);
}