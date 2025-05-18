package com.example.newai.summary.controller;

import com.example.newai.summary.service.SummaryService;
import com.example.newai.summary.vo.SummaryDto;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/summary")
@RequiredArgsConstructor
@Tag(name = "Summary", description = "요약 관련 API")
public class SummaryController {
    private final SummaryService summaryService;

    @Operation(
        summary = "사용자 어휘수준에 맞는 뉴스 요약 조회",
        description = "지정된 UUID를 가진 뉴스에 대한 AI 요약 정보를 조회.<br>요약은 난이도(LOW, MIDDLE, HIGH)별로 구성.<br>요약 기준은 사용자 어휘수준(Level)에 따라 상이<br>Low, Middle인 경우 Low에 해당하는 요약 제공<br>High인 경우 High에 해당하는 요약 제공",
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
                description = "뉴스 UUID",
                example = "123e4567-e89b-12d3-a456-426614174000"
            )
        },
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "요약 정보 조회 성공",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = SummaryDto.class)
                )
            ),
            @ApiResponse(
                responseCode = "404",
                description = "뉴스가 존재하지 않거나 요약이 존재하지 않습니다.",
                content = @Content(
                    mediaType = "text/plain",
                    schema = @Schema(type = "string", example = "뉴스가 존재하지 않거나 요약이 존재하지 않습니다.")
                )
            )
        }
    )
    @GetMapping("/summarys/{uuid}")
    public ResponseEntity<?> readSummary(Authentication authentication, @PathVariable UUID uuid) {
        SummaryDto summaryDto = summaryService.readSummary(authentication, uuid);

        if  (summaryDto == null)
            return new ResponseEntity<>("뉴스가 존재하지 않거나 요약이 존재하지 않습니다.", HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(summaryDto, HttpStatus.OK);
    }
}
