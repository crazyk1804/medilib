package own.crazyk.medilib.exception;

import org.apache.catalina.Server;
import org.springframework.http.HttpStatus;
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

	@ExceptionHandler(AuthenticationException.class)
	public ServerResponseEntity<String> handleAuthenticationException(AuthenticationException exception) {
		return ServerResponseEntity.STATUS(HttpStatus.FORBIDDEN).body(exception.getMessage());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ServerResponseEntity<InvalidArgsResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
		return ServerResponseEntity.STATUS(HttpStatus.BAD_REQUEST).body(new InvalidArgsResponse(exception));
	}
}
