package own.crazyk.medilib.web.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.annotation.PostConstruct;
import jakarta.security.auth.message.AuthException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import own.crazyk.cmm.util.JWTUtil;
import own.crazyk.medilib.dto.MemberDTO;

import java.io.IOException;
import java.util.Optional;

@AllArgsConstructor
public class JWTRequestFilter extends OncePerRequestFilter {
	private UserDetailsService userDetailsService;
	private JWTUtil jwtUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		// 헤더의 Authorization 을 찾는다
		final String requestTokenHeader = request.getHeader("Authorization");
		String username = null, token = null;

		// Bearer 로 시작하는지 판단하는 왜 하는건지는 잘 모르겠다.  불문율 같은건가...
		if(requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			token = requestTokenHeader.substring(7);
		} else {
			token = requestTokenHeader;
			logger.warn("JWT Token does not begin with Bearer String");
		}

		// 예외가 발생하면 request 에 해당 예외 객체를 넣어주는 작업
		Optional<AuthException> optional = Optional.empty();
		try {
			username = jwtUtil.getUsernameFromToken(token);
		} catch (ExpiredJwtException e) {
			optional = Optional.of(new AuthException("JWT Token has expired", e));
		}
		optional.ifPresent(e -> request.setAttribute("authException", e));

		if(username != null && SecurityContextHolder.getContext().getAuthentication()==null) {
			// 사용자 정보를 조회해서 SecurityContextHolder 에 적절한 값을 세팅해줌
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
			if (jwtUtil.isValidateToken(token, userDetails)) {
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
					userDetails, null, userDetails.getAuthorities()
				);
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}

		filterChain.doFilter(request, response);
	}
}
