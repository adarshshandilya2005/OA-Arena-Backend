package com.InterviewSimulator.Interview.Simulator.Repository;

import com.InterviewSimulator.Interview.Simulator.Entity.Attempt;
import com.InterviewSimulator.Interview.Simulator.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttemptRepository extends JpaRepository<Attempt, Long> {

    List<Attempt> findByUser(User user);

}