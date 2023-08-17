package com.nes.tireso.boundedContext.socialLogin.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GoogleUserInfo {
    private String id;
    private String email;
    private String name;
    private String picture;
}
