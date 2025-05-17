package com.example.newai.quizresult.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QQuizResult is a Querydsl query type for QuizResult
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QQuizResult extends EntityPathBase<QuizResult> {

    private static final long serialVersionUID = -1631814248L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QQuizResult quizResult = new QQuizResult("quizResult");

    public final com.example.newai.appuser.entity.QAppUser appUser;

    public final BooleanPath isCorrect = createBoolean("isCorrect");

    public final com.example.newai.quiz.entity.QQuiz quiz;

    public final NumberPath<Long> quizResultId = createNumber("quizResultId", Long.class);

    public final NumberPath<Integer> selectedValue = createNumber("selectedValue", Integer.class);

    public QQuizResult(String variable) {
        this(QuizResult.class, forVariable(variable), INITS);
    }

    public QQuizResult(Path<? extends QuizResult> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QQuizResult(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QQuizResult(PathMetadata metadata, PathInits inits) {
        this(QuizResult.class, metadata, inits);
    }

    public QQuizResult(Class<? extends QuizResult> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.appUser = inits.isInitialized("appUser") ? new com.example.newai.appuser.entity.QAppUser(forProperty("appUser"), inits.get("appUser")) : null;
        this.quiz = inits.isInitialized("quiz") ? new com.example.newai.quiz.entity.QQuiz(forProperty("quiz"), inits.get("quiz")) : null;
    }

}

