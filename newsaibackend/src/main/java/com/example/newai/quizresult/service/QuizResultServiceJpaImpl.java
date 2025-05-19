package com.example.newai.quizresult.service;

import com.example.newai.appuser.entity.AppUser;
import com.example.newai.appuser.repository.AppUserRepository;
import com.example.newai.appuser.vo.Level;
import com.example.newai.login.CustomUserDetails;
import com.example.newai.member.entity.Member;
import com.example.newai.member.repository.MemberRepository;
import com.example.newai.quiz.entity.Quiz;
import com.example.newai.quiz.repository.QuizRepository;
import com.example.newai.quiz.service.QuizServiceJpaImpl;
import com.example.newai.quiz.vo.QuizDto;
import com.example.newai.quizresult.entity.QuizResult;
import com.example.newai.quizresult.repository.QuizResultRepository;
import com.example.newai.quizresult.vo.QuizIdAndSelectValue;
import com.example.newai.quizresult.vo.QuizResultAndCountDto;
import com.example.newai.quizresult.vo.QuizResultDto;
import com.example.newai.quizresult.vo.QuizResultRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuizResultServiceJpaImpl implements QuizResultService {
    private static final Integer PASSED_CRITERION = 8;
    private final MemberRepository memberRepository;
    private final AppUserRepository appUserRepository;
    private final QuizResultRepository quizResultRepository;
    private final QuizRepository quizRepository;

    @Override
    @Transactional
    public QuizResultAndCountDto gradingQuiz(Authentication authentication, QuizResultRequest quizResultRequest) {
        List<QuizResult> quizResults = new ArrayList<>();
        Optional<AppUser> authAppUser = authenticationToAppUser(authentication);

        if (authAppUser.isEmpty())
            return null;

        AppUser appUser = authAppUser.get();
        Integer count = passedCounter(quizResultRequest, quizResults, appUser);

        if (count >= PASSED_CRITERION) {
            switch(appUser.getVocabularyLevel()) {
                case LOW -> appUser.setVocabularyLevel(Level.MIDDLE);
                case MIDDLE, HIGH -> appUser.setVocabularyLevel(Level.HIGH);
            }

            appUserRepository.save(appUser);
        }

        List<QuizResultDto> quizResultDtos = quizResults.stream().map(QuizResultServiceJpaImpl::quizResultToQuizResultDto).toList();
        return quizResultDtoToQuizResultAndCountDto(quizResultDtos, count);
    }

    @Override
    public List<QuizResultDto> myQuizResults(Authentication authentication) {
        Optional<AppUser> authAppUser = authenticationToAppUser(authentication);

        if (authAppUser.isEmpty())
            return null;

        AppUser appUser = authAppUser.get();
        List<QuizResultDto> quizResultDtos = quizResultRepository.findByAppUser(appUser).stream().map(QuizResultServiceJpaImpl::quizResultToQuizResultDto).toList();

        return quizResultDtos;
    }

    @Override
    public QuizResultDto readQuizResult(Long quizResultId) {
        QuizResult quizResult = quizResultRepository.findById(quizResultId).orElse(null);

        if (quizResult == null)
            return null;

        return quizResultToQuizResultDto(quizResult);
    }

    private Optional<AppUser> authenticationToAppUser(Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        if (customUserDetails == null) {
            return Optional.empty();
        }

        Member member = memberRepository.findByLoginId(customUserDetails.getLoginId());
        return appUserRepository.findById(member.getAppUser().getAppUserId());
    }

    private Integer passedCounter(QuizResultRequest quizResultRequest, List<QuizResult> quizResults, AppUser appUser) {
        Integer counter = 0;

        for (QuizIdAndSelectValue quizIdAndSelectValue : quizResultRequest.getQuizIdAndSelectValueList()) {
            Quiz quiz = quizRepository.findById(quizIdAndSelectValue.getQuizId()).orElse(null);

            if (quiz == null) {
                log.warn("None Quiz Object Access");
                continue;
            }
            QuizResult quizResult;

            // quiz 값이랑 appuser 값으로 찾아서 나온 quizresult 있으면 그걸로 정보 업데이트
            Optional<QuizResult> quizResultOptional = quizResultRepository.findByQuizAndAppUser(quiz, appUser);
            // 없으면 새로 생성
            if (quizResultOptional.isEmpty()) {
                quizResult = new QuizResult();
                quizResult.setQuiz(quiz);
                quizResult.setAppUser(appUser);
                quizResult.setSelectedValue(quizIdAndSelectValue.getSelectValue());
            } else {
                quizResult = quizResultOptional.get();
                quizResult.setSelectedValue(quizIdAndSelectValue.getSelectValue());
            }

            if (Objects.equals(quiz.getAnswer(), quizIdAndSelectValue.getSelectValue())) {
                counter++;
                quizResult.setCorrect(true);
            } else
                quizResult.setCorrect(false);

            quizResultRepository.save(quizResult);
            quizResults.add(quizResult);
        }

        return counter;
    }

    public static QuizResultDto quizResultToQuizResultDto(QuizResult quizResult) {
        QuizDto quizDto = QuizServiceJpaImpl.quizToQuizDto(quizResult.getQuiz());
        return new QuizResultDto(quizResult.getQuizResultId(), quizDto, quizResult.getSelectedValue(), quizResult.isCorrect());
    }

    public static QuizResultAndCountDto quizResultDtoToQuizResultAndCountDto(List<QuizResultDto> quizResultDtos, Integer count) {
        return new QuizResultAndCountDto(quizResultDtos, count);
    }
}
