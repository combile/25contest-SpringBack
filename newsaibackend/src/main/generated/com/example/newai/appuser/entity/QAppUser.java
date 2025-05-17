package com.example.newai.appuser.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAppUser is a Querydsl query type for AppUser
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAppUser extends EntityPathBase<AppUser> {

    private static final long serialVersionUID = -930876326L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAppUser appUser = new QAppUser("appUser");

    public final NumberPath<Long> appUserId = createNumber("appUserId", Long.class);

    public final SetPath<com.example.newai.news.entity.News, com.example.newai.news.entity.QNews> bookmarks = this.<com.example.newai.news.entity.News, com.example.newai.news.entity.QNews>createSet("bookmarks", com.example.newai.news.entity.News.class, com.example.newai.news.entity.QNews.class, PathInits.DIRECT2);

    public final ListPath<com.example.newai.comment.entity.Comment, com.example.newai.comment.entity.QComment> comments = this.<com.example.newai.comment.entity.Comment, com.example.newai.comment.entity.QComment>createList("comments", com.example.newai.comment.entity.Comment.class, com.example.newai.comment.entity.QComment.class, PathInits.DIRECT2);

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final StringPath email = createString("email");

    public final com.example.newai.member.entity.QMember member;

    public final StringPath phoneNumber = createString("phoneNumber");

    public final ListPath<com.example.newai.quizresult.entity.QuizResult, com.example.newai.quizresult.entity.QQuizResult> quizResults = this.<com.example.newai.quizresult.entity.QuizResult, com.example.newai.quizresult.entity.QQuizResult>createList("quizResults", com.example.newai.quizresult.entity.QuizResult.class, com.example.newai.quizresult.entity.QQuizResult.class, PathInits.DIRECT2);

    public final com.example.newai.login.QRefreshToken refreshToken;

    public final SetPath<com.example.newai.word.entity.Word, com.example.newai.word.entity.QWord> seenWords = this.<com.example.newai.word.entity.Word, com.example.newai.word.entity.QWord>createSet("seenWords", com.example.newai.word.entity.Word.class, com.example.newai.word.entity.QWord.class, PathInits.DIRECT2);

    public final StringPath username = createString("username");

    public final NumberPath<Integer> views = createNumber("views", Integer.class);

    public final EnumPath<com.example.newai.appuser.vo.Level> vocabularyLevel = createEnum("vocabularyLevel", com.example.newai.appuser.vo.Level.class);

    public QAppUser(String variable) {
        this(AppUser.class, forVariable(variable), INITS);
    }

    public QAppUser(Path<? extends AppUser> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAppUser(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAppUser(PathMetadata metadata, PathInits inits) {
        this(AppUser.class, metadata, inits);
    }

    public QAppUser(Class<? extends AppUser> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.example.newai.member.entity.QMember(forProperty("member"), inits.get("member")) : null;
        this.refreshToken = inits.isInitialized("refreshToken") ? new com.example.newai.login.QRefreshToken(forProperty("refreshToken"), inits.get("refreshToken")) : null;
    }

}

