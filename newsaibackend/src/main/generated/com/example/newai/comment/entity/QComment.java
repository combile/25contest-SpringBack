package com.example.newai.comment.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QComment is a Querydsl query type for Comment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QComment extends EntityPathBase<Comment> {

    private static final long serialVersionUID = -438821926L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QComment comment1 = new QComment("comment1");

    public final com.example.newai.appuser.entity.QAppUser appUser;

    public final StringPath comment = createString("comment");

    public final NumberPath<Long> commentId = createNumber("commentId", Long.class);

    public final com.example.newai.news.entity.QNews news;

    public QComment(String variable) {
        this(Comment.class, forVariable(variable), INITS);
    }

    public QComment(Path<? extends Comment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QComment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QComment(PathMetadata metadata, PathInits inits) {
        this(Comment.class, metadata, inits);
    }

    public QComment(Class<? extends Comment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.appUser = inits.isInitialized("appUser") ? new com.example.newai.appuser.entity.QAppUser(forProperty("appUser"), inits.get("appUser")) : null;
        this.news = inits.isInitialized("news") ? new com.example.newai.news.entity.QNews(forProperty("news"), inits.get("news")) : null;
    }

}

