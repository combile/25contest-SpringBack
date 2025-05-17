package com.example.newai.word.vo;

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
public class WordDto {
    private Long wordId;

    private String word;
    private String definition;
    private String sentence;

    private Level level;
}
