package com.example.newai.quiz.service;

import com.example.newai.appuser.entity.AppUser;
import com.example.newai.appuser.repository.AppUserRepository;
import com.example.newai.appuser.service.AppUserService;
import com.example.newai.login.CustomUserDetails;
import com.example.newai.member.entity.Member;
import com.example.newai.member.repository.MemberRepository;
import com.example.newai.quiz.entity.Quiz;
import com.example.newai.quiz.repository.QuizRepository;
import com.example.newai.quiz.vo.QuizDto;
import com.example.newai.quizresult.entity.QuizResult;
import com.example.newai.quizresult.repository.QuizResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class QuizServiceJpaImpl implements QuizService {
    private static final Integer QUIZ_SET_SIZE = 10;
    private final QuizRepository quizRepository;
    private final QuizResultRepository quizResultRepository;
    private final MemberRepository memberRepository;
    private final AppUserRepository appUserRepository;

    @Override
    public List<QuizDto> extractQuiz(Authentication authentication) {
        List<Quiz> quizList = improvedExtractQuizSet(authentication);
        List<QuizDto> quizDtos = quizList.stream().map(QuizServiceJpaImpl::quizToQuizDto).toList();

        return quizDtos;
    }

    @Override
    public QuizDto readQuizById(Long quizId) {
        Quiz quiz = quizRepository.findById(quizId).orElse(null);

        if (quiz == null)
            return null;

        return quizToQuizDto(quiz);
    }

    private List<Quiz> improvedExtractQuizSet(Authentication authentication) {
        Optional<AppUser> authAppUser = authenticationToAppUser(authentication);

        if (authAppUser.isEmpty())
            return null;

        AppUser appUser = authAppUser.get();

        Random random = new Random();
        List<Quiz> standardQuizSet = quizRepository.findAll();
        List<QuizResult> myQuizResults = quizResultRepository.findByAppUser(appUser);
        // 틀린 문제에 대해선 해당 배열에서 삭제

        // 만약 전체 문제 - 내가 푼 문제중 정답인 문제 < QUIZ_SET_SIZE -> null;
        // 전체 문제 - 내가 푼문제 >= QUIZ_SET_SIZE -> 내가 틀린문제거나 아직 안푼문제에 대해서만 랜덤 돌려야함.
        wrongAnswerExclusion(myQuizResults, standardQuizSet);
        if (standardQuizSet.size() - (myQuizResults.size()) < QUIZ_SET_SIZE)
            return null;

//        List<Quiz> reconstructionQuizSet = new ArrayList<>(standardQuizSet);
//        Collections.shuffle(reconstructionQuizSet, random);
        Collections.shuffle(standardQuizSet, random);

        return standardQuizSet.subList(0, QUIZ_SET_SIZE);
    }

    private List<Quiz> extractQuizSet() {
        Random random = new Random();
        List<Quiz> quizSet = quizRepository.findAll();

        if (quizSet.size() <= QUIZ_SET_SIZE)
            return quizSet;

        Collections.shuffle(quizSet, random);

        return quizSet.subList(0, QUIZ_SET_SIZE);
    }

    public static void wrongAnswerExclusion(List<QuizResult> myQuizResults, List<Quiz> standardQuizSet) {
        Iterator<QuizResult> iterator = myQuizResults.iterator();

        while (iterator.hasNext()) {
            QuizResult quizResult = iterator.next();
            if (!quizResult.isCorrect()) // 틀렷을때 삭제
                iterator.remove();
            else
                standardQuizSet.removeIf(sqs -> Objects.equals(sqs.getQuizId(), quizResult.getQuiz().getQuizId()));
        }
    }

    public static QuizDto quizToQuizDto(Quiz quiz) {
        return new QuizDto(quiz.getQuizId(), quiz.getQuestion(), quiz.getOptionOne(), quiz.getOptionTwo(), quiz.getOptionThree(), quiz.getOptionFour());
    }

    private Optional<AppUser> authenticationToAppUser(Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        if (customUserDetails == null) {
            return Optional.empty();
        }

        Member member = memberRepository.findByLoginId(customUserDetails.getLoginId());
        return appUserRepository.findById(member.getAppUser().getAppUserId());
    }
}
