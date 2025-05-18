package com.example.newai.pnevaluation.controller;

import com.example.newai.pnevaluation.service.PNEvaluationService;
import com.example.newai.pnevaluation.vo.PNEvaluationDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/pnevaluation")
@RequiredArgsConstructor
@Tag(name = "PNEvaluation", description = "긍부정 평가관련 API")
public class PNEvaluationController {
    private final PNEvaluationService pNEvaluationService;

    @Operation(
        summary = "뉴스 긍부정 평가 조회",
        description = "특정 뉴스에 대한 AI 기반 긍부정 평가 결과를 반환.",
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
                name = "uuid",
                required = true,
                description = "뉴스의 UUID"
            )
        },
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "긍부정 평가 조회 성공",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PNEvaluationDto.class)
                )
            ),
            @ApiResponse(
                responseCode = "404",
                description = "뉴스 또는 긍부정 평가 정보가 없음",
                content = @Content(
                    mediaType = "text/plain",
                    schema = @Schema(type = "string", example = "뉴스가 존재하지 않거나 긍부정평가가 존재하지 않습니다.")
                )
            )
        }
    )
    @GetMapping("/reading/{uuid}")
    public ResponseEntity<?> readPNEvaluation(@PathVariable UUID uuid) {
        PNEvaluationDto pnEvaluationDto = pNEvaluationService.readPNEvaluation(uuid);

        if  (pnEvaluationDto == null)
            return new ResponseEntity<>("뉴스가 존재하지 않거나 긍부정평가가 존재하지 않습니다.", HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(pnEvaluationDto, HttpStatus.OK);
    }
}
