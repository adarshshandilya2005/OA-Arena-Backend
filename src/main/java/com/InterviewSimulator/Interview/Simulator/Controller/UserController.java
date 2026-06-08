package com.InterviewSimulator.Interview.Simulator.Controller;

import com.InterviewSimulator.Interview.Simulator.DTO.Test.TestResponse.TestResponseDto;
import com.InterviewSimulator.Interview.Simulator.DTO.TestSubmission.CodeRunDto;
import com.InterviewSimulator.Interview.Simulator.DTO.TestSubmission.TestResultDto;
import com.InterviewSimulator.Interview.Simulator.DTO.TestSubmission.TestSubmitRequestDto;
import com.InterviewSimulator.Interview.Simulator.Entity.Attempt;
import com.InterviewSimulator.Interview.Simulator.Entity.User;
import com.InterviewSimulator.Interview.Simulator.Repository.AttemptRepository;
import com.InterviewSimulator.Interview.Simulator.Service.GroqService;
import com.InterviewSimulator.Interview.Simulator.Service.TestService;
import com.InterviewSimulator.Interview.Simulator.Service.UserService;
import com.InterviewSimulator.Interview.Simulator.Repository.SubmissionRepository;
import com.InterviewSimulator.Interview.Simulator.Entity.Submission;
import com.InterviewSimulator.Interview.Simulator.Enumerations.QuestionType;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    @Autowired
    private final UserService userService;

    @Autowired
    private final TestService testService;

    @Autowired
    private AttemptRepository attemptRepository;

    @Autowired
    private SubmissionRepository submissionRepository;

    @Autowired
    GroqService groqService;

    @GetMapping("/all")
    public List<User> findAll() {
        return userService.findAll();
    }

    @GetMapping("/test/today")
    public TestResponseDto testToday() {
        return testService.getTodayTest();
    }

    @PostMapping("/test/submit")
    public TestResultDto submitTest(@RequestBody TestSubmitRequestDto dto) {
        return testService.getTodayTestResult(dto);
    }

    @PostMapping("/test/run")
    public ResponseEntity<?> runTest(@RequestBody CodeRunDto dto)
    {
        System.out.println("DTO = " + dto);

        return testService.RunCode(
                dto.getQuestion_id(),
                dto.getCode(),
                dto.getLanguage()
        );
    }

    @GetMapping("/attempts")
    public List<Attempt> getAttempts() {

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userService.findByName(username);

        return attemptRepository.findByUser(user);
    }
    @GetMapping("/{attemptId}/attempt")
    public Attempt getAttempt(@PathVariable Long attemptId) {

        return attemptRepository.findById(attemptId)
                .orElseThrow();
    }
    @GetMapping("/debug/{attemptId}")
    public String debug(@PathVariable Long attemptId) {

        Attempt a = attemptRepository.findById(attemptId)
                .orElseThrow();

        System.out.println("STARTED = " + a.getStartedAt());
        System.out.println("SUBMITTED = " + a.getSubmittedAt());

        return "ok";
    }

    @GetMapping("/{attemptId}/mcq")
    public List<Submission> getMcqResponses(@PathVariable Long attemptId) {

        return submissionRepository.findByAttemptId(attemptId)
                .stream()
                .filter(s -> s.getQuestion().getType() == QuestionType.MCQ)
                .toList();
    }

    @GetMapping("/{attemptId}/coding")
    public List<Submission> getCodingResponses(@PathVariable Long attemptId) {

        return submissionRepository.findByAttemptId(attemptId)
                .stream()
                .filter(s -> s.getQuestion().getType() == QuestionType.CODING)
                .toList();
    }

    @GetMapping("/{attemptId}/theory")
    public List<Submission> getTheoryResponses(@PathVariable Long attemptId) {

        return submissionRepository.findByAttemptId(attemptId)
                .stream()
                .filter(s -> s.getQuestion().getType() == QuestionType.THEORY)
                .toList();
    }
}