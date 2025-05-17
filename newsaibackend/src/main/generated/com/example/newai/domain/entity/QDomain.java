package com.example.newai.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDomain is a Querydsl query type for Domain
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDomain extends EntityPathBase<Domain> {

    private static final long serialVersionUID = 1655483548L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDomain domain1 = new QDomain("domain1");

    public final StringPath domain = createString("domain");

    public final NumberPath<Long> domainId = createNumber("domainId", Long.class);

    public final com.example.newai.news.entity.QNews news;

    public QDomain(String variable) {
        this(Domain.class, forVariable(variable), INITS);
    }

    public QDomain(Path<? extends Domain> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDomain(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDomain(PathMetadata metadata, PathInits inits) {
        this(Domain.class, metadata, inits);
    }

    public QDomain(Class<? extends Domain> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.news = inits.isInitialized("news") ? new com.example.newai.news.entity.QNews(forProperty("news"), inits.get("news")) : null;
    }

}

