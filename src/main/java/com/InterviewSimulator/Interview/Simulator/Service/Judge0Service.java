package com.InterviewSimulator.Interview.Simulator.Service;

import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

@Service
public class Judge0Service {

    private final String URL = "https://ce.judge0.com/submissions?wait=true";
    private int getLanguageId(String language) {

        if (language == null) return 62;

        switch (language.toLowerCase()) {
            case "javascript":
                return 63;

            case "python":
                return 71;

            case "java":
                return 62;

            case "c":
                return 50;

            case "cpp":
                return 54;

            case "csharp":
                return 51;

            case "go":
                return 60;

            case "php":
                return 68;

            case "kotlin":
                return 78;

            default:
                return 62;
        }
    }

    public String runCode(String code, String input, String language) {

        try {

            String json = String.format("""
                {
                  "source_code": %s,
                  "language_id": %d,
                  "stdin": %s
                }
                """,
                    quote(code),
                    getLanguageId(language),
                    quote(input)
            );

            var factory = new org.springframework.http.client.SimpleClientHttpRequestFactory();
            factory.setConnectTimeout(10000);
            factory.setReadTimeout(30000);

            RestTemplate restTemplate = new RestTemplate(factory);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);


            HttpEntity<String> entity =
                    new HttpEntity<>(json, headers);

            String response = restTemplate.postForObject(
                    URL,
                    entity,
                    String.class
            );

            System.out.println("JUDGE RESPONSE = " + response);

            return response;

        } catch (Exception e) {

            System.out.println("========== JUDGE0 ERROR ==========");
            System.out.println("ERROR CLASS = " + e.getClass().getName());
            System.out.println("ERROR MESSAGE = " + e.getMessage());


            e.printStackTrace();

            return "ERROR";
        }
    }

    private String quote(String value) {
        if (value == null) return "\"\"";

        return "\"" +
                value.replace("\\", "\\\\")
                        .replace("\"", "\\\"")
                        .replace("\n", "\\n")
                        .replace("\r", "\\r")
                + "\"";
    }
}