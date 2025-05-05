package com.example.newai.member.entity;

import com.example.newai.appuser.entity.AppUser;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.UniqueElements;

@Entity
@Getter
@Setter
@ToString
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @NotNull
    @NotBlank
    @Column(unique = true)
    private String loginId;
    @NotNull
    @NotBlank
    private String password;

    @OneToOne
    @JoinColumn(name = "app_user_id") // name은 외래키의 컬럼 이름이다.
    private AppUser appUser;
}
