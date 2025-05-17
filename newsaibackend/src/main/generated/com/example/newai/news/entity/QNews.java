package com.example.newai.news.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNews is a Querydsl query type for News
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNews extends EntityPathBase<News> {

    private static final long serialVersionUID = 1009603066L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QNews news = new QNews("news");

    public final SetPath<com.example.newai.appuser.entity.AppUser, com.example.newai.appuser.entity.QAppUser> appUsers = this.<com.example.newai.appuser.entity.AppUser, com.example.newai.appuser.entity.QAppUser>createSet("appUsers", com.example.newai.appuser.entity.AppUser.class, com.example.newai.appuser.entity.QAppUser.class, PathInits.DIRECT2);

    public final StringPath author = createString("author");

    public final ListPath<com.example.newai.comment.entity.Comment, com.example.newai.comment.entity.QComment> comments = this.<com.example.newai.comment.entity.Comment, com.example.newai.comment.entity.QComment>createList("comments", com.example.newai.comment.entity.Comment.class, com.example.newai.comment.entity.QComment.class, PathInits.DIRECT2);

    public final StringPath content = createString("content");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final ListPath<com.example.newai.domain.entity.Domain, com.example.newai.domain.entity.QDomain> domains = this.<com.example.newai.domain.entity.Domain, com.example.newai.domain.entity.QDomain>createList("domains", com.example.newai.domain.entity.Domain.class, com.example.newai.domain.entity.QDomain.class, PathInits.DIRECT2);

    public final NumberPath<Long> newsId = createNumber("newsId", Long.class);

    public final com.example.newai.pnevaluation.entity.QPNEvaluation pnEvaluation;

    public final ListPath<com.example.newai.summary.entity.Summary, com.example.newai.summary.entity.QSummary> summaries = this.<com.example.newai.summary.entity.Summary, com.example.newai.summary.entity.QSummary>createList("summaries", com.example.newai.summary.entity.Summary.class, com.example.newai.summary.entity.QSummary.class, PathInits.DIRECT2);

    public final StringPath thumbnailUrl = createString("thumbnailUrl");

    public final StringPath title = createString("title");

    public final ComparablePath<java.util.UUID> uuid = createComparable("uuid", java.util.UUID.class);

    public final NumberPath<Integer> views = createNumber("views", Integer.class);

    public final ListPath<com.example.newai.word.entity.Word, com.example.newai.word.entity.QWord> words = this.<com.example.newai.word.entity.Word, com.example.newai.word.entity.QWord>createList("words", com.example.newai.word.entity.Word.class, com.example.newai.word.entity.QWord.class, PathInits.DIRECT2);

    public QNews(String variable) {
        this(News.class, forVariable(variable), INITS);
    }

    public QNews(Path<? extends News> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QNews(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QNews(PathMetadata metadata, PathInits inits) {
        this(News.class, metadata, inits);
    }

    public QNews(Class<? extends News> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.pnEvaluation = inits.isInitialized("pnEvaluation") ? new com.example.newai.pnevaluation.entity.QPNEvaluation(forProperty("pnEvaluation"), inits.get("pnEvaluation")) : null;
    }

}

