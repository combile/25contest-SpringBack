package com.example.newai.summary.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSummary is a Querydsl query type for Summary
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSummary extends EntityPathBase<Summary> {

    private static final long serialVersionUID = -1968995078L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSummary summary = new QSummary("summary");

    public final EnumPath<com.example.newai.appuser.vo.Level> level = createEnum("level", com.example.newai.appuser.vo.Level.class);

    public final com.example.newai.news.entity.QNews news;

    public final StringPath summaryContent = createString("summaryContent");

    public final NumberPath<Long> summaryId = createNumber("summaryId", Long.class);

    public QSummary(String variable) {
        this(Summary.class, forVariable(variable), INITS);
    }

    public QSummary(Path<? extends Summary> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSummary(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSummary(PathMetadata metadata, PathInits inits) {
        this(Summary.class, metadata, inits);
    }

    public QSummary(Class<? extends Summary> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.news = inits.isInitialized("news") ? new com.example.newai.news.entity.QNews(forProperty("news"), inits.get("news")) : null;
    }

}

