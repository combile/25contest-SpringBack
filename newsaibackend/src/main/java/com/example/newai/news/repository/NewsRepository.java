package com.example.newai.news.repository;

import com.example.newai.news.entity.News;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
    News findByUuid(UUID uuid);
    List<News> findAll(Sort sort);
}
