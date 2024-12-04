package com.example.dreambackend.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

@Service
public class GPTService {
    private static final Logger logger = LoggerFactory.getLogger(GPTService.class);
    private final RestTemplate restTemplate;

    public GPTService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getDreamInterpretation(String keyword) {
        String gptApiUrl = "https://api.openai.com/v1/chat/completions"; // OpenAI GPT API
        String apiKey = "OPENAI_API_KEY"; // OpenAI API 키 설정

        // GPT 요청 데이터
        Map<String, Object> requestBody = Map.of(
                "model", "gpt-3.5-turbo",
                "messages", List.of(
                        Map.of("role", "system", "content", "You are a dream interpretation expert."),
                        Map.of("role", "user", "content", "꿈 해몽 키워드: " + keyword)
                )
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        try {
            // GPT API 호출
            ResponseEntity<Map> response = restTemplate.postForEntity(gptApiUrl, request, Map.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                Map<String, Object> responseBody = response.getBody();

                // 응답에서 "choices" 배열을 추출
                List<Map<String, Object>> choices = (List<Map<String, Object>>) responseBody.get("choices");
                if (choices != null && !choices.isEmpty()) {
                    Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
                    if (message != null) {
                        return (String) message.get("content"); // 결과 반환
                    }
                }
                return "GPT 응답에서 결과를 찾을 수 없습니다.";
            } else {
                logger.error("GPT API 호출 실패: {}", response.getStatusCode());
                return "해몽 결과를 가져오는 데 실패했습니다.";
            }
        } catch (Exception e) {
            logger.error("GPT 호출 중 오류 발생: {}", e.getMessage(), e);
            return "GPT 호출 중 오류 발생: " + e.getMessage();
        }
    }
}
