package com.nes.tireso.boundedContext.auth.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import com.nes.tireso.boundedContext.auth.dto.OAuthInfoDto;
import com.nes.tireso.boundedContext.auth.dto.TokenDto;
import com.nes.tireso.boundedContext.member.entity.Member;
import com.nes.tireso.boundedContext.member.repository.MemberRepository;
import com.nimbusds.jose.shaded.gson.JsonElement;
import com.nimbusds.jose.shaded.gson.JsonParser;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
	@Value("${spring.security.oauth2.client.provider.naver.authorization-uri}")
	private String naverAuthUri;
	@Value("${spring.security.oauth2.client.registration.naver.client-id}")
	private String naverClientId;
	@Value("${spring.security.oauth2.client.registration.naver.client-secret}")
	private String clientSecret;
	@Value("${spring.security.oauth2.client.registration.naver.redirect-uri}")
	private String naverRedirectUri;
	@Value("${spring.security.oauth2.client.provider.naver.token-uri}")
	private String naverTokenUri;
	@Value("${spring.security.oauth2.client.provider.naver.user-info-uri}")
	private String naverUserInfoUri;
	private final JsonParser parser = new JsonParser();
	private final MemberRepository memberRepository;

	public String getNaverAuthUri() {
		return UriComponentsBuilder
				.fromUriString(naverAuthUri)
				.queryParam("client_id", naverClientId)
				.queryParam("response_type", "code")
				.queryParam("redirect_uri", naverRedirectUri)
				.queryParam("state", "hLiDdL2uhPtsftcU")
				.build().toString();
	}

	public TokenDto getNaverToken(String type, String code) throws IOException {
		URL url = new URL(UriComponentsBuilder
				.fromUriString(naverTokenUri)
				.queryParam("grant_type", "authorization_code")
				.queryParam("client_id", naverClientId)
				.queryParam("client_secret", clientSecret)
				.queryParam("code", code)
				.build()
				.toString());

		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");

		int responseCode = con.getResponseCode();
		BufferedReader br;

		if (responseCode == 200) { // 정상 호출
			br = new BufferedReader(new InputStreamReader(con.getInputStream()));
		} else {  // 에러 발생
			br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
		}

		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = br.readLine()) != null) {
			response.append(inputLine);
		}

		br.close();

		JsonElement element = parser.parse(response.toString());

		return TokenDto.builder()
				.accessToken(element.getAsJsonObject().get("access_token").getAsString())
				.refreshToken(element.getAsJsonObject().get("refresh_token").getAsString())
				.build();
	}

	public OAuthInfoDto getNaverUserInfo(TokenDto token) throws IOException {
		String accessToken = token.getAccessToken();

		URL url = new URL(naverUserInfoUri);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("Authorization", "Bearer " + accessToken);

		int responseCode = con.getResponseCode();
		BufferedReader br;

		if (responseCode == 200) { // 정상 호출
			br = new BufferedReader(new InputStreamReader(con.getInputStream()));
		} else {  // 에러 발생
			br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
		}

		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = br.readLine()) != null) {
			response.append(inputLine);
		}

		br.close();

		JsonElement element = parser.parse(response.toString());

		return OAuthInfoDto.builder()
				.username(element.getAsJsonObject().get("response").getAsJsonObject().get("id").getAsString())
				.name(element.getAsJsonObject().get("response").getAsJsonObject().get("name").getAsString())
				.providerTypeCode("NAVER")
				.build();
	}

	public void signIn(OAuthInfoDto oAuthInfoDto) {
		if (memberRepository.findByUsername(oAuthInfoDto.getUsername()).isEmpty()) {
			memberRepository.save(Member.builder()
					.username(oAuthInfoDto.getUsername())
					.name(oAuthInfoDto.getName())
					.providerTypeCode(oAuthInfoDto.getProviderTypeCode())
					.build());
		}
	}
}
