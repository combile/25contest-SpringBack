package com.example.newai.quiz.controller;

import com.example.newai.quiz.service.QuizService;
import com.example.newai.quiz.vo.QuizDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/quiz")
@RequiredArgsConstructor
@Tag(name = "Quiz", description = "퀴즈 관련 API")
public class QuizController {
    private final QuizService quizService;

    @Operation(
        summary = "퀴즈 10개 제공",
        description = "사용자에게 푼 적 없는 퀴즈 또는 이전에 틀린 퀴즈 중 10개를 무작위로 제공.",
        parameters = {
            @Parameter(
                in = ParameterIn.HEADER,
                name = HttpHeaders.AUTHORIZATION,
                required = true,
                description = "Bearer 액세스 토큰. 형식: Bearer {accessToken}",
                example = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
            )
        },
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "퀴즈 제공 성공",
                content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = QuizDto.class))
                )
            ),
            @ApiResponse(
                responseCode = "404",
                description = "더 이상 제공할 퀴즈가 없음<br>전체 문제 - 내가 푼 문제중 정답인 문제 < QUIZ_SET_SIZE(10) 경우에 해당 됨.",
                content = @Content(
                    mediaType = "text/plain",
                    schema = @Schema(type = "string", example = "더이상 제공될 퀴즈가 없습니다.")
                )
            )
        }
    )
    @GetMapping("/several-quiz")
    public ResponseEntity<?> extractTenQuiz(Authentication authentication) {
        List<QuizDto> quizDtos = quizService.extractQuiz(authentication);

        if (quizDtos == null)
            return new ResponseEntity<>("더이상 제공될 퀴즈가 없습니다.", HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(quizDtos, HttpStatus.OK);
    }

    @Operation(
        summary = "퀴즈 단건 조회",
        description = "퀴즈 ID로 단건 퀴즈 정보를 조회.",
        parameters = {
            @Parameter(
                in = ParameterIn.HEADER,
                name = HttpHeaders.AUTHORIZATION,
                required = true,
                description = "Bearer 액세스 토큰. 형식: Bearer {accessToken}",
                example = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
            ),
            @Parameter(
                in = ParameterIn.PATH,
                name = "id",
                required = true,
                description = "조회할 퀴즈의 ID",
                example = "1"
            )
        },
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "퀴즈 조회 성공",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = QuizDto.class)
                )
            ),
            @ApiResponse(
                responseCode = "404",
                description = "해당 ID의 퀴즈를 찾을 수 없음"
            )
        }
    )
    @GetMapping("/reading/{id}")
    public ResponseEntity<?> readingQuiz(@PathVariable Long id) {
        QuizDto quizDto = quizService.readQuizById(id);

        if (quizDto == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(quizDto, HttpStatus.OK);
    }
}
