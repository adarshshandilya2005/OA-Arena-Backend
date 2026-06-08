package com.InterviewSimulator.Interview.Simulator.DTO.TestSubmission;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CodeRunDto {

    private String code;

    @JsonProperty("question_id")
    private Long question_id;

    private String language;
}
