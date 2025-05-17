package com.example.newai.pnevaluation.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPNEvaluation is a Querydsl query type for PNEvaluation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPNEvaluation extends EntityPathBase<PNEvaluation> {

    private static final long serialVersionUID = 284305480L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPNEvaluation pNEvaluation = new QPNEvaluation("pNEvaluation");

    public final StringPath negativeComment = createString("negativeComment");

    public final com.example.newai.news.entity.QNews news;

    public final NumberPath<Long> pnEvaluationId = createNumber("pnEvaluationId", Long.class);

    public final StringPath positiveComment = createString("positiveComment");

    public QPNEvaluation(String variable) {
        this(PNEvaluation.class, forVariable(variable), INITS);
    }

    public QPNEvaluation(Path<? extends PNEvaluation> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPNEvaluation(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPNEvaluation(PathMetadata metadata, PathInits inits) {
        this(PNEvaluation.class, metadata, inits);
    }

    public QPNEvaluation(Class<? extends PNEvaluation> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.news = inits.isInitialized("news") ? new com.example.newai.news.entity.QNews(forProperty("news"), inits.get("news")) : null;
    }

}

