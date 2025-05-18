package com.example.newai.comment.repository;

import com.example.newai.appuser.entity.AppUser;
import com.example.newai.comment.entity.Comment;
import com.example.newai.news.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByAppUser(AppUser appUser);
    List<Comment> findByNews(News news);
}
