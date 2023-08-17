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

import com.nes.tireso.boundedContext.auth.dto.OAuthInfoDto;
import com.nes.tireso.boundedContext.auth.dto.TokenDto;
import com.nes.tireso.boundedContext.auth.repository.MemberRepository;
import com.nimbusds.jose.shaded.gson.JsonElement;
import com.nimbusds.jose.shaded.gson.JsonParser;

@Service
@PropertySource("classpath:application.yml")
public class KakaoService {
	private final MemberService memberService;
	private final MemberRepository memberRepository;
	private final String clientId;
	private final String redirectUri;
	private final String authorizationUri;
	private final String tokenUri;
	private final String userInfoUri;

	public KakaoService(MemberService memberService, MemberRepository memberRepository, @Value("${kakao.client_id}") String clientId,
			@Value("${kakao.redirect_uri}") String redirectUri,
			@Value("${kakao.authorization_uri}") String authorizationUri, @Value("${kakao.user_info_uri}") String userInfoUri,
			@Value("${kakao.token_uri}") String tokenUri) {
		this.memberService = memberService;
		this.memberRepository = memberRepository;
		this.clientId = clientId;
		this.redirectUri = redirectUri;
		this.authorizationUri = authorizationUri;
		this.userInfoUri = userInfoUri;
		this.tokenUri = tokenUri;
	}

	public String getAuthorizationUrl() {
		return UriComponentsBuilder
				.fromUriString(authorizationUri)
				.queryParam("client_id", clientId)
				.queryParam("redirect_uri", redirectUri)
				.queryParam("response_type", "code")
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
			String refreshToken = element.getAsJsonObject().get("refresh_token").getAsString();

			br.close();
			bw.close();

			tokenDto = TokenDto.builder()
					.accessToken(accessToken)
					.refreshToken(refreshToken)
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

			conn.setRequestMethod("POST");
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

			String id = Integer.toString(element.getAsJsonObject().get("id").getAsInt());
			String nickname = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("profile").getAsJsonObject().get("nickname").getAsString();
			boolean hasEmail = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("has_email").getAsBoolean();
			String email = null;
			if (hasEmail) {
				email = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("email").getAsString();
			}
			String profileImage = element.getAsJsonObject()
					.get("kakao_account")
					.getAsJsonObject()
					.get("profile")
					.getAsJsonObject()
					.get("thumbnail_image_url")
					.getAsString();

			br.close();
			return OAuthInfoDto.builder()
					.username(id)
					.name(nickname)
					.email(email)
					.profileImageUrl(profileImage)
					.oauthType("kakao")
					.build();
		} catch (Exception e) {
			throw new Exception("토큰 발급 실패");
		}
	}
}
