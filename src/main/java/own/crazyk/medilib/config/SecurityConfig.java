package own.crazyk.medilib.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import own.crazyk.cmm.util.JWTUtil;
import own.crazyk.medilib.web.jwt.JWTRequestFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	private AuthenticationEntryPoint entryPoint;
	private final UserDetailsService userDetailsService;

	@Value("${jwt.private.key}")
	private String privateKey;
	@Value("#{ new Long('${jwt.expiry.duration}')}")
	private long expiryDuration;

	public SecurityConfig(UserDetailsService userDetailsService, AuthenticationEntryPoint entryPoint) {
		this.userDetailsService = userDetailsService;
		this.entryPoint = entryPoint;
	}

	@Bean
	public JWTUtil jwtUtil() {
		return new JWTUtil(privateKey, expiryDuration);
	}

	@Bean JWTRequestFilter jwtRequestFilter() {
		// filter 를 component 로 등록해놓고 사용하니 순환참조 문제가 생겨서 bean 등록 별도로 해줌
		return new JWTRequestFilter(userDetailsService, jwtUtil());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues())
			.and()
			.authorizeHttpRequests(authRegistry -> authRegistry
				.requestMatchers("/", "/authenticate", "/add-member").permitAll()
				.requestMatchers("/h2-console/**, /h2-console/login.jsp").permitAll()
				.anyRequest().authenticated()
			)
			.exceptionHandling()
			.authenticationEntryPoint(entryPoint)
			.and()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.addFilterBefore(jwtRequestFilter(), UsernamePasswordAuthenticationFilter.class)
		;
		return http.build();
	}

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return web -> web.ignoring().requestMatchers("/static/**", "/h2-console/**");
	}
}
