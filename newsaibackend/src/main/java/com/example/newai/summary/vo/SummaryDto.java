package com.example.newai.summary.vo;

import com.example.newai.appuser.vo.Level;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SummaryDto {
    private Long summaryId;

    private Level level;

    private String summaryContent;
}
