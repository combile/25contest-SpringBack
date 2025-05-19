package com.example.newai.quizresult.controller;

import com.example.newai.quizresult.service.QuizResultService;
import com.example.newai.quizresult.vo.QuizResultAndCountDto;
import com.example.newai.quizresult.vo.QuizResultDto;
import com.example.newai.quizresult.vo.QuizResultRequest;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quiz-result")
@RequiredArgsConstructor
@Tag(name = "QuizResult", description = "퀴즈 결과 관련 API")
public class QuizResultController {
    private final QuizResultService quizResultService;

    @Operation(
        summary = "퀴즈 채점",
        description = "사용자가 제출한 퀴즈 답안을 채점하고 결과를 반환.<br>퀴즈 아이디와 그에 맞는 선택 값을 짝지어서 보낼 것(10개).",
        parameters = {
            @Parameter(
                in = ParameterIn.HEADER,
                name = HttpHeaders.AUTHORIZATION,
                required = true,
                description = "Bearer 액세스 토큰. 형식: Bearer {accessToken}",
                example = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
            )
        },
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = QuizResultRequest.class)
            )
        ),
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "퀴즈 채점 성공",
                content = @Content(schema = @Schema(implementation = QuizResultAndCountDto.class))
            ),
            @ApiResponse(
                responseCode = "400",
                description = "존재하지 않는 사용자",
                content = @Content(schema = @Schema(type = "string", example = "존재하지 않는 사용자 입니다."))
            )
        }
    )
    @PostMapping("/grading")
    public ResponseEntity<?> gradingQuiz(Authentication authentication, @RequestBody QuizResultRequest quizResultRequest) {
        QuizResultAndCountDto quizResultAndCountDto = quizResultService.gradingQuiz(authentication, quizResultRequest);

        if (quizResultAndCountDto == null)
            return new ResponseEntity<>("존재하지 않는 사용자 입니다.", HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(quizResultAndCountDto, HttpStatus.OK);
    }

    @Operation(
        summary = "내 퀴즈 결과 조회",
        description = "사용자가 푼 모든 퀴즈 결과를 조회.",
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
                description = "퀴즈 결과 조회 성공",
                content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = QuizResultDto.class))
                )
            ),
            @ApiResponse(
                responseCode = "400",
                description = "존재하지 않는 사용자",
                content = @Content(schema = @Schema(type = "string", example = "존재하지 않는 사용자 입니다."))
            )
        }
    )
    @GetMapping("/my/quiz-results")
    public ResponseEntity<?> readMyQuizResults(Authentication authentication) {
        List<QuizResultDto> quizResultDtos = quizResultService.myQuizResults(authentication);

        if (quizResultDtos == null)
            return new ResponseEntity<>("존재하지 않는 사용자 입니다.", HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(quizResultDtos, HttpStatus.OK);
    }

    @Operation(
        summary = "퀴즈 결과 단건 조회",
        description = "특정 퀴즈 결과를 ID로 조회.",
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
                description = "퀴즈 결과 ID",
                example = "1"
            )
        },
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "퀴즈 결과 조회 성공",
                content = @Content(schema = @Schema(implementation = QuizResultDto.class))
            ),
            @ApiResponse(
                responseCode = "400",
                description = "존재하지 않는 퀴즈 결과",
                content = @Content(schema = @Schema(type = "string", example = "존재하지 않는 퀴즈 결과입니다."))
            )
        }
    )
    @GetMapping("/quiz-results/{id}")
    public ResponseEntity<?> readQuizResultById(@PathVariable Long id) {
        QuizResultDto quizResultDto = quizResultService.readQuizResult(id);

        if (quizResultDto == null)
            return new ResponseEntity<>("존재하지 않는 퀴즈 결과입니다.", HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(quizResultDto, HttpStatus.OK);
    }
}
