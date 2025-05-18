package com.example.newai.summary.repository;

import com.example.newai.appuser.vo.Level;
import com.example.newai.news.entity.News;
import com.example.newai.summary.entity.Summary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@ResponseBody
public interface SummaryRepository extends JpaRepository<Summary, Long> {
    Summary findByNewsAndLevel(News news, Level level);
}
