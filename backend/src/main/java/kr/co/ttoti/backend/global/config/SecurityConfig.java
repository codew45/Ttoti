package kr.co.ttoti.backend.global.config;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.netty.handler.codec.http.HttpHeaderValues;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.ttoti.backend.global.auth.security.CustomOAuth2MemberSuccessHandler;
import kr.co.ttoti.backend.global.auth.security.CustomOAuth2UserService;
import kr.co.ttoti.backend.global.dto.ResponseDto;
import kr.co.ttoti.backend.global.filter.ExceptionHandlerFilter;
import kr.co.ttoti.backend.global.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.*;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import static kr.co.ttoti.backend.global.status.ErrorCode.ACCESS_DENIED;
import static kr.co.ttoti.backend.global.status.ErrorCode.UNAUTHORIZED;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	private final ExceptionHandlerFilter exceptionHandlerFilter;
	private final ObjectMapper objectMapper;
	private final CustomOAuth2UserService customOAuth2UserService;
	private final CustomOAuth2MemberSuccessHandler oAuth2MemberSuccessHandler;

	private static final List<String> CORS_ALLOWED_ORIGIN = List.of(
		"http://localhost:4173",
		"http://localhost:5173",
		"http://localhost:8080",
		"https://ttoti.co.kr",
		"http://ttoti.co.kr:8080"
	);

	private static final String[] PERMIT_PATTERNS = new String[] {
		"/api/v1/ttoti/oauth",
		"/api/v1/ttoti/auth/reissue",
		"/chat/**",
		"/pub/**",
		"/sub/**",
		"/swagger-ui/**",
		"/static/**",
		"/v3/api-docs/**",
		"/favicon.ico",
	};

	private static final List<String> PERMIT_METHODS = List.of(
		"GET",
		"POST",
		"PUT",
		"DELETE",
		"PATCH",
		"OPTIONS",
		"CONNECT",
		"MESSAGE"
	);

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http
			.httpBasic(HttpBasicConfigurer::disable)
			.formLogin(FormLoginConfigurer::disable)
			.csrf(CsrfConfigurer::disable)
			.logout(LogoutConfigurer::disable)
			.headers(HeadersConfigurer::disable)

			//                .cors(CorsConfigurer::disable)
			.cors(corsConfigurer -> {
				CorsConfigurationSource source = request -> {
					CorsConfiguration config = new CorsConfiguration();
					config.setAllowCredentials(true);
					config.setAllowedOrigins(CORS_ALLOWED_ORIGIN);
					config.setAllowedMethods(PERMIT_METHODS);
					config.addAllowedHeader("*");
					return config;
				};
				corsConfigurer.configurationSource(source);
			})

			.authorizeHttpRequests(requestRegistry -> requestRegistry
				.requestMatchers(
					Arrays.stream(PERMIT_PATTERNS)
						.map(AntPathRequestMatcher::antMatcher)
						.toArray(AntPathRequestMatcher[]::new)).permitAll()
				.anyRequest().authenticated()
			)

			.oauth2Login(oAuth2Conf -> oAuth2Conf
				.authorizationEndpoint(conf -> conf.baseUri("/api/v1/ttoti/oauth"))
				.loginProcessingUrl("/api/v1/ttoti/login/oauth/*")
				.userInfoEndpoint(conf -> conf.userService(customOAuth2UserService))
				.successHandler(oAuth2MemberSuccessHandler)
				.failureHandler(this::entryPoint))

			.exceptionHandling(exception ->
				exception.authenticationEntryPoint(this::entryPoint)
					.accessDeniedHandler(this::accessDeniedHandler))

			.sessionManagement(sessionConfigurer -> sessionConfigurer
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

			.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
			.addFilterBefore(exceptionHandlerFilter, JwtAuthenticationFilter.class)
			.build();
	}

	private void entryPoint(HttpServletRequest request, HttpServletResponse response,
		RuntimeException e) throws IOException {
		ResponseDto<Object> dto = ResponseDto.fail(UNAUTHORIZED);
		writeResponse(response, dto);
	}

	private void accessDeniedHandler(HttpServletRequest request, HttpServletResponse response,
		RuntimeException e) throws IOException {
		ResponseDto<?> dto = ResponseDto.fail(ACCESS_DENIED);
		writeResponse(response, dto);
	}

	private void writeResponse(HttpServletResponse response, ResponseDto<?> dto) throws IOException {
		response.setStatus(400);
		response.setContentType(HttpHeaderValues.APPLICATION_JSON.toString());
		response.setCharacterEncoding(StandardCharsets.UTF_8.name());

		String responseBody = objectMapper.writeValueAsString(dto);
		response.getWriter().write(responseBody);
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}