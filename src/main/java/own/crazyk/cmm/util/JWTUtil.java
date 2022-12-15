package own.crazyk.cmm.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class JWTUtil {
	private String privateKey;
	private long expiryDuration;

	public JWTUtil(String privateKey, long expiryDuration) {
		this.privateKey = privateKey;
		this.expiryDuration = expiryDuration;
	}

	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(privateKey).parseClaimsJws(token).getBody();
	}

	private <T> T getClaimsFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	/**
	 * 만료일시 확인
	 * @param token
	 * @return
	 */
	public Date getExpirationDateFromToken(String token) {
		return getClaimsFromToken(token, Claims::getExpiration);
	}

	/**
	 * 사용자 ID 확인
	 * @param token
	 * @return
	 */
	public String getUsernameFromToken(String token) {
		return getClaimsFromToken(token, Claims::getSubject);
	}

	/**
	 * 만료 여부 확인
	 * @param token
	 * @return
	 */
	public Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	/**
	 * 토큰 생성
	 * @param claims
	 * @param subject
	 * @return
	 */
	public String generateToken(Map<String, Object> claims, String subject) {
		long currentTimeMillis = System.currentTimeMillis();
		return Jwts.builder()
			.setClaims(claims)
			.setSubject(subject)
			.setIssuedAt(new Date(currentTimeMillis))
			.setExpiration(new Date(currentTimeMillis + expiryDuration))
			.signWith(SignatureAlgorithm.HS512, privateKey)
			.compact();
	}

	public String generateToken(UserDetails userDetails) {
		return generateToken(new HashMap<>(), userDetails.getUsername());
	}

	public String generateToken(HashMap<String, Object> claims, UserDetails userDetails) {
		return generateToken(claims, userDetails.getUsername());
	}

	public boolean isValidateToken(String token, UserDetails userDetails) {
		final String username = getUsernameFromToken(token);
		return (username.equals(userDetails.getUsername()) && isTokenExpired(token));
	}
}
