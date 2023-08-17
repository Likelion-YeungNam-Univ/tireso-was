package com.nes.tireso.boundedContext.auth.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import com.nes.tireso.base.jwt.JwtProvider;
import com.nes.tireso.boundedContext.auth.dto.OAuthInfoDto;
import com.nes.tireso.boundedContext.auth.dto.TokenDto;
import com.nes.tireso.boundedContext.auth.repository.MemberRepository;
import com.nimbusds.jose.shaded.gson.JsonElement;
import com.nimbusds.jose.shaded.gson.JsonParser;

@Service
@PropertySource("classpath:application.yml")
public class GoogleService {
	private final MemberService memberService;
	private final MemberRepository memberRepository;
	private final RefreshTokenService refreshTokenService;
	private final JwtProvider jwtProvider;
	private final String clientId;
	private final String redirectUri;
	private final String clientSecret;

	private final String tokenUri;

	private final String userInfoUri;
	private final String authorizationUri;

	public GoogleService(MemberService memberService, MemberRepository memberRepository, RefreshTokenService refreshTokenService, JwtProvider jwtProvider,
			@Value("${google.client_id}") String clientId, @Value("${google.client_secret}") String clientSecret,
			@Value("${google.redirect_uri}") String redirectUri,
			@Value("${google.token_uri}") String tokenUri, @Value("${google.user_info_uri}") String userInfoUri,
			@Value("${google.authorization_uri}") String authorizationUri) {
		this.memberService = memberService;
		this.memberRepository = memberRepository;
		this.refreshTokenService = refreshTokenService;
		this.jwtProvider = jwtProvider;
		this.clientId = clientId;
		this.redirectUri = redirectUri;
		this.clientSecret = clientSecret;
		this.tokenUri = tokenUri;
		this.userInfoUri = userInfoUri;
		this.authorizationUri = authorizationUri;
	}

	public String getAuthorizationUrl() {
		return UriComponentsBuilder
				.fromUriString(authorizationUri)
				.queryParam("client_id", clientId)
				.queryParam("redirect_uri", redirectUri)
				.queryParam("response_type", "code")
				.queryParam("scope", "email profile")
				.build()
				.toString();
	}

	public TokenDto getToken(String code) throws Exception {
		TokenDto tokenDto = null;

		try {
			URL url = new URL(tokenUri);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();

			conn.setRequestMethod("POST");
			conn.setDoOutput(true);

			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
			StringBuilder sb = new StringBuilder();

			sb.append("grant_type=authorization_code");
			sb.append("&client_id=" + clientId);
			sb.append("&client_secret=" + clientSecret);
			sb.append("&redirect_uri=" + redirectUri);
			sb.append("&code=" + code);

			bw.write(sb.toString());
			bw.flush();

			int responseCode = conn.getResponseCode();

			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			String line = "";
			String result = "";

			while ((line = br.readLine()) != null) {
				result += line;
			}

			JsonParser parser = new JsonParser();
			JsonElement element = parser.parse(result);

			String accessToken = element.getAsJsonObject().get("access_token").getAsString();

			br.close();
			bw.close();
			tokenDto = TokenDto.builder()
					.accessToken(accessToken)
					.build();
		} catch (Exception e) {
			throw new Exception("토큰 발급 실패");
		}
		return tokenDto;
	}

	public OAuthInfoDto getUserInfo(TokenDto tokenDto) throws Exception {
		try {
			URL url = new URL(userInfoUri);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();

			conn.setRequestMethod("GET");
			conn.setDoOutput(true);

			conn.setRequestProperty("Authorization", "Bearer " + tokenDto.getAccessToken());

			int responseCode = conn.getResponseCode();

			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = "";
			String result = "";

			while ((line = br.readLine()) != null) {
				result += line;
			}

			JsonParser parser = new JsonParser();
			JsonElement element = parser.parse(result);

			String id = element.getAsJsonObject().get("id").getAsString();
			String nickname = element.getAsJsonObject().get("name").getAsString();
			String email = element.getAsJsonObject().get("email").getAsString();
			String picture = element.getAsJsonObject().get("picture").getAsString();

			br.close();

			return OAuthInfoDto.builder()
					.username(id)
					.name(nickname)
					.email(email)
					.profileImageUrl(picture)
					.oauthType("google")
					.build();
		} catch (Exception e) {
			throw new Exception("토큰 발급 실패");
		}
	}
}
