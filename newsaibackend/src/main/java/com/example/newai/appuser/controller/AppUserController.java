package com.example.newai.appuser.controller;

import com.example.newai.appuser.service.AppUserService;
import com.example.newai.appuser.vo.AppUserDto;
import com.example.newai.appuser.vo.AppUserRequest;

import com.example.newai.appuser.vo.RedundancyCheckRequest;
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

@RestController
@RequestMapping("/api/app-user")
@RequiredArgsConstructor
@Tag(name = "AppUser", description = "유저관련 API")
public class AppUserController {
    private final AppUserService appUserService;

    @Operation(
        summary = "유저 생성",
        description = "유저 정보를 데이터베이스에 저장, 반환값으로 유저 객체 반환<br>조건 1 - 이메일, 아이디, 전화번호는 중복되면 안된다.<br>조건 2 - 등급은 LOW, MIDDLE, HIGH 이며 대문자이여야 한다.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = AppUserRequest.class)
            )
        ),
        responses = {
            @ApiResponse(
                responseCode = "201",
                description = "유저 생성 성공",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AppUserDto.class)
                )
            )
        }
    )
    @PostMapping("/initial/users")
    public ResponseEntity<?> createUser(@RequestBody AppUserRequest appUserRequest) {
        AppUserDto appUserDto = appUserService.createAppUser(appUserRequest);
        return new  ResponseEntity<>(appUserDto, HttpStatus.CREATED);
    }


    @Operation(
        summary = "이메일, 아이디 중복 확인",
        description = "회원가입시 이메일 혹은 아이디 중복을 확인 해줌",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = RedundancyCheckRequest.class)
            )
        ),
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "true = 중복, false = 중복x",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(type = "boolean", example = "true")
                )
            )
        }
    )
    @PostMapping("/initial/redundancy-check")
    public ResponseEntity<?> redundancyCheck(@RequestBody RedundancyCheckRequest redundancyCheckRequest) {
        Boolean check = appUserService.redundancyCheck(redundancyCheckRequest);
        return new  ResponseEntity<>(check, HttpStatus.OK);
    }

    @Operation(
        summary = "유저 정보 조회",
        description = "로그인된 사용자의 정보를 반환",
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
                description = "유저 정보 조회 성공",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AppUserDto.class)
                )
            ),
            @ApiResponse(
                responseCode = "404",
                description = "로그인이 안 되어 있거나 유저 정보 없음",
                content = @Content(
                    mediaType = "text/plain",
                    schema = @Schema(type = "string", example = "로그인이 되어있지 않거나, 존재하지 않는 계정입니다.")
                )
            )
        }
    )
    @GetMapping("/users")
    public ResponseEntity<?> readAppUser(Authentication authentication) {
        AppUserDto appUserDto = appUserService.getAppUser(authentication);

        if (appUserDto == null) {
            return new ResponseEntity<>("로그인이 되어있지 않거나, 존재하지 않는 계정입니다.", HttpStatus.NOT_FOUND);
        }

        return new  ResponseEntity<>(appUserDto, HttpStatus.OK);
    }

    @Operation(
        summary = "유저 정보 수정",
        description = "로그인된 사용자의 정보를 수정<br>부분 수정 가능하며 예시 Request Body 필드 중 하나 이상 포함시켜야 함.",
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
                schema = @Schema(implementation = AppUserRequest.class)
            )
        ),
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "유저 정보 수정 성공",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AppUserDto.class)
                )
            ),
            @ApiResponse(
                responseCode = "404",
                description = "로그인이 안 되어 있거나 유저 정보 없음",
                content = @Content(
                    mediaType = "text/plain",
                    schema = @Schema(type = "string", example = "로그인이 되어있지 않거나, 존재하지 않는 계정입니다.")
                )
            )
        }
    )
    @PatchMapping("/users")
    public ResponseEntity<?> updateAppUser(@RequestBody AppUserRequest appUserRequest, Authentication authentication) {
        AppUserDto appUserDto = appUserService.updateAppUser(authentication, appUserRequest);

        if (appUserDto == null) {
            return new ResponseEntity<>("로그인이 되어있지 않거나, 존재하지 않는 계정입니다.", HttpStatus.NOT_FOUND);
        }

        return new  ResponseEntity<>(appUserDto, HttpStatus.OK);
    }

    @Operation(
        summary = "유저 삭제",
        description = "로그인된 사용자의 계정을 삭제.",
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
                description = "유저 삭제 성공"
            )
        }
    )
    @DeleteMapping("/users")
    public ResponseEntity<?> deleteAppUser(Authentication authentication) {
        appUserService.deleteAppUser(authentication);
        return new ResponseEntity<>(HttpStatus.OK);
    }

//    @PostMapping("/test/creation")
//    public ResponseEntity<Object> testCreateUser(@RequestBody AppUserRequest userRequest) {
//        AppUser appUser = new AppUser();
//        appUser.setUsername(userRequest.getUsername());
//        appUser.setPhoneNumber(userRequest.getPhoneNumber());
//        appUser.setEmail(userRequest.getEmail());
//        appUser.setVocabularyLevel(Level.valueOf(userRequest.getVocabularyLevel()));
//
//        Member member = new Member();
//        member.setLoginId(userRequest.getLoginId());
//        member.setPassword(passwordEncoder.encode(userRequest.getPassword()));
//
//        appUser.setMember(member);
//        member.setAppUser(appUser);
//
//        appUserRepository.save(appUser);
//        memberRepository.save(member);
//
//        AppUserDto appUserDto = new AppUserDto(
//                appUser.getAppUserId(),
//                appUser.getUsername(),
//                appUser.getPhoneNumber(),
//                appUser.getEmail(),
//                appUser.getVocabularyLevel(),
//                0,
//                new MemberDto(member.getMemberId(), member.getLoginId(), member.getPassword(), null)
//        );
//
//        return new ResponseEntity<>(appUserDto, HttpStatus.CREATED);
//    }
//
//    @GetMapping("/test/my-info")
//    public ResponseEntity<Object> getMyInfo(Authentication authentication) {
//        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
//        System.out.println(customUserDetails.getUsername()+"||"+customUserDetails.getLoginId());
//        return new ResponseEntity<>("OK", HttpStatus.OK);
//    }
}
