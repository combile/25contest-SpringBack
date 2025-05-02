package com.example.newai.summary.repository;

import com.example.newai.summary.entity.Summary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.ResponseBody;

@ResponseBody
public interface SummaryRepository extends JpaRepository<Summary, Long> {
}
