package com.example.newai.pnevaluation.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PNEvaluationDto {
    private Long pnEvaluationId;

    private String positiveComment;
    private String negativeComment;
}
