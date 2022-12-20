package own.crazyk.medilib.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import own.crazyk.cmm.model.response.InvalidArgsResponse;
import own.crazyk.cmm.model.response.ServerResponseEntity;

import javax.naming.AuthenticationException;

// todo 여기에 Exception handling 정의
@RestControllerAdvice
public class CMMExceptionHandler {
//	@ExceptionHandler(CustomException.class)
//	public ResponseEntity<TestModel> handleCustomException(CustomException exception) {
//		TestModel tm = new TestModel();
//		tm.setTestValue("좆돼써");
//		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(tm);
//	}

	@ExceptionHandler(Exception.class)
	public ServerResponseEntity<ExceptionResponse> handleException(Exception exception) {
		return ServerResponseEntity.STATUS(HttpStatus.INTERNAL_SERVER_ERROR).body(
			ExceptionResponse.builder()
				.message(exception.getMessage())
				.build()
		);
	}

	@ExceptionHandler(BadCredentialsException.class)
	public ServerResponseEntity<ExceptionResponse> handleBadCredentialException(BadCredentialsException exception) {
		return ServerResponseEntity.STATUS(HttpStatus.UNAUTHORIZED).body(
			ExceptionResponse.builder()
				.message("사용자 ID 혹은 비밀번호를 확인 하세요.")
				.userFriendly(UserFriendlyRank.friendly)
				.build()
		);
	}

	@ExceptionHandler(AuthenticationException.class)
	public ServerResponseEntity<ExceptionResponse> handleAuthenticationException(AuthenticationException exception) {
		return ServerResponseEntity.STATUS(HttpStatus.FORBIDDEN).body(
			ExceptionResponse.builder()
					.message(exception.getMessage())
					.build()
		);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ServerResponseEntity<InvalidArgsResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
		return ServerResponseEntity.STATUS(HttpStatus.BAD_REQUEST).body(
				new InvalidArgsResponse(exception)
		);
	}
}
