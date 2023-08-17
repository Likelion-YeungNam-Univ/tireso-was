package com.nes.tireso.boundedContext.auth.entity;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.ToString;

@Data
@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@ToString
@JsonIgnoreProperties({"beanExpressionResolver"})
public class UserInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long userId;
	private String userNm;

}
