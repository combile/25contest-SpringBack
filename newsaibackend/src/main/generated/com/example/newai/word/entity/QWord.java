package com.example.newai.word.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWord is a Querydsl query type for Word
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWord extends EntityPathBase<Word> {

    private static final long serialVersionUID = 1239560168L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWord word1 = new QWord("word1");

    public final SetPath<com.example.newai.appuser.entity.AppUser, com.example.newai.appuser.entity.QAppUser> appUsers = this.<com.example.newai.appuser.entity.AppUser, com.example.newai.appuser.entity.QAppUser>createSet("appUsers", com.example.newai.appuser.entity.AppUser.class, com.example.newai.appuser.entity.QAppUser.class, PathInits.DIRECT2);

    public final StringPath definition = createString("definition");

    public final EnumPath<com.example.newai.appuser.vo.Level> level = createEnum("level", com.example.newai.appuser.vo.Level.class);

    public final com.example.newai.news.entity.QNews news;

    public final StringPath sentence = createString("sentence");

    public final StringPath word = createString("word");

    public final NumberPath<Long> wordId = createNumber("wordId", Long.class);

    public QWord(String variable) {
        this(Word.class, forVariable(variable), INITS);
    }

    public QWord(Path<? extends Word> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWord(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWord(PathMetadata metadata, PathInits inits) {
        this(Word.class, metadata, inits);
    }

    public QWord(Class<? extends Word> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.news = inits.isInitialized("news") ? new com.example.newai.news.entity.QNews(forProperty("news"), inits.get("news")) : null;
    }

}

