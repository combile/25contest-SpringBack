package com.example.newai.quiz.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QQuiz is a Querydsl query type for Quiz
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QQuiz extends EntityPathBase<Quiz> {

    private static final long serialVersionUID = -1441837570L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QQuiz quiz = new QQuiz("quiz");

    public final NumberPath<Integer> answer = createNumber("answer", Integer.class);

    public final StringPath optionFour = createString("optionFour");

    public final StringPath optionOne = createString("optionOne");

    public final StringPath optionThree = createString("optionThree");

    public final StringPath optionTwo = createString("optionTwo");

    public final StringPath question = createString("question");

    public final NumberPath<Long> quizId = createNumber("quizId", Long.class);

    public final com.example.newai.quizresult.entity.QQuizResult quizResult;

    public QQuiz(String variable) {
        this(Quiz.class, forVariable(variable), INITS);
    }

    public QQuiz(Path<? extends Quiz> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QQuiz(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QQuiz(PathMetadata metadata, PathInits inits) {
        this(Quiz.class, metadata, inits);
    }

    public QQuiz(Class<? extends Quiz> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.quizResult = inits.isInitialized("quizResult") ? new com.example.newai.quizresult.entity.QQuizResult(forProperty("quizResult"), inits.get("quizResult")) : null;
    }

}

