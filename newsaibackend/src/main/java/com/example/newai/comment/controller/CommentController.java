package com.example.newai.comment.controller;

import com.example.newai.comment.service.CommentService;
import com.example.newai.comment.vo.CommentDto;
import com.example.newai.comment.vo.CommentRequest;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
@Tag(name = "Comment", description = "댓글관련 API")
public class CommentController {
    private final CommentService commentService;

    @Operation(
        summary = "댓글 생성",
        description = "뉴스 UUID에 해당하는 뉴스에 댓글을 작성.",
        parameters = {
            @Parameter(name = HttpHeaders.AUTHORIZATION, in = ParameterIn.HEADER, required = true,
                       description = "Bearer 액세스 토큰. 형식: Bearer {accessToken}",
                       example = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."),
            @Parameter(name = "uuid", in = ParameterIn.PATH, required = true, description = "뉴스 UUID")
        },
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CommentRequest.class))
        ),
        responses = {
            @ApiResponse(responseCode = "200", description = "댓글 생성 성공",
                         content = @Content(mediaType = "application/json", schema = @Schema(implementation = CommentDto.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청",
                         content = @Content(schema = @Schema(type = "string", example = "존재하지 않는 사용자거나 존재하지 않는 뉴스입니다.")))
        }
    )
    @PostMapping("/comments/{uuid}")
    public ResponseEntity<?> createComment(Authentication authentication, @RequestBody CommentRequest commentRequest, @PathVariable UUID uuid) {
        CommentDto commentDto = commentService.createComment(authentication, commentRequest, uuid);

        if (commentDto == null)
            return new ResponseEntity<>("존재하지 않는 사용자거나 존재하지 않는 뉴스입니다.", HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(commentDto, HttpStatus.OK);
    }

    @Operation(
        summary = "내가 작성한 댓글 조회",
        description = "로그인된 사용자가 작성한 모든 댓글을 조회.",
        parameters = {
            @Parameter(name = HttpHeaders.AUTHORIZATION, in = ParameterIn.HEADER, required = true,
                       description = "Bearer 액세스 토큰. 형식: Bearer {accessToken}",
                       example = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
        },
        responses = {
            @ApiResponse(responseCode = "200", description = "댓글 목록 반환",
                         content = @Content(mediaType = "application/json", schema = @Schema(implementation = CommentDto.class)))
        }
    )
    @GetMapping("/my/comments")
    public ResponseEntity<?> readMyComments(Authentication authentication) {
        List<CommentDto> commentDtos = commentService.readMyAllComments(authentication);

        return new ResponseEntity<>(commentDtos, HttpStatus.OK);
    }

    @Operation(
        summary = "뉴스 댓글 전체 조회",
        description = "해당 뉴스 UUID의 모든 댓글을 조회.",
        parameters = {
            @Parameter(name = HttpHeaders.AUTHORIZATION, in = ParameterIn.HEADER, required = true,
                       description = "Bearer 액세스 토큰. 형식: Bearer {accessToken}"),
            @Parameter(name = "uuid", in = ParameterIn.PATH, required = true, description = "뉴스 UUID")
        },
        responses = {
            @ApiResponse(responseCode = "200", description = "댓글 목록 반환",
                         content = @Content(mediaType = "application/json", schema = @Schema(implementation = CommentDto.class)))
        }
    )
    @GetMapping("/comments/{uuid}")
    public ResponseEntity<?> readComment(@PathVariable UUID uuid, Authentication authentication) {
        List<CommentDto> commentDtos = commentService.readNewsAllComments(uuid);

        return new ResponseEntity<>(commentDtos, HttpStatus.OK);
    }

    @Operation(
        summary = "댓글 수정",
        description = "기존에 작성한 댓글을 수정.",
        parameters = {
            @Parameter(name = HttpHeaders.AUTHORIZATION, in = ParameterIn.HEADER, required = true,
                       description = "Bearer 액세스 토큰. 형식: Bearer {accessToken}")
        },
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CommentRequest.class))
        ),
        responses = {
            @ApiResponse(responseCode = "200", description = "댓글 수정 성공",
                         content = @Content(mediaType = "application/json", schema = @Schema(implementation = CommentDto.class))),
            @ApiResponse(responseCode = "400", description = "댓글 수정 실패",
                         content = @Content(schema = @Schema(type = "string", example = "존재하지 않은 댓글이거나 글자수가 부족합니다.(1자 이상)")))
        }
    )
    @PatchMapping("/comments")
    public ResponseEntity<?> updateComment(Authentication authentication, @RequestBody CommentRequest commentRequest) {
        CommentDto commentDto = commentService.updateComment(authentication, commentRequest);

        if (commentDto == null)
            return new ResponseEntity<>("존재하지 않은 댓글이거나 글자수가 부족합니다.(1자 이상)", HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(commentDto, HttpStatus.OK);
    }

    @Operation(
        summary = "댓글 삭제",
        description = "기존에 작성한 댓글을 삭제.",
        parameters = {
            @Parameter(name = HttpHeaders.AUTHORIZATION, in = ParameterIn.HEADER, required = true,
                       description = "Bearer 액세스 토큰. 형식: Bearer {accessToken}")
        },
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CommentRequest.class))
        ),
        responses = {
            @ApiResponse(responseCode = "200", description = "댓글 삭제 성공")
        }
    )
    @DeleteMapping("/comments")
    public ResponseEntity<?> deleteComment(Authentication authentication, @RequestBody CommentRequest commentRequest) {
        commentService.deleteComment(authentication, commentRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
