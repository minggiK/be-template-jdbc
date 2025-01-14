package com.springboot.coffee.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@AllArgsConstructor

public class Coffee {
    @Id //없으면 테이블과 1대1 이 안된다.
    private long coffeeId;
    private String korName;
    private String engName;
    private Integer price;
    //중복 등록을 체크하기 위한 변수
    @NotBlank
    @Pattern(regexp = "^([A-Za-z]){3}$")
    private String coffeeCode;

}
