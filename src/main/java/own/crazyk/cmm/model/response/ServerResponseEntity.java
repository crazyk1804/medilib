package own.crazyk.cmm.model.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;

/**
 * ServerResponse 객체를 기본 응답 형식으로 처리하는 ResponseEntity 의 상속 클래스.
 * 컨트롤러에서 ResponseEntity<ServerResponse<ResponseClass>> 처럼 제너릭 클래스를
 * 이중으로 사용하는 코딩 방식을 방지하기 위한 용도.
 * @param <T>
 */
public class ServerResponseEntity<T> extends ResponseEntity<ServerResponse<T>> {
	public ServerResponseEntity(HttpStatus status) {
		super(
			ServerResponse.<T>builder()
				.hasSucceed(status.value() >= 200 && status.value() < 300)
				.build(),
			status
		);
	}

	public ServerResponseEntity(T body, HttpStatus status) {
		super(
			ServerResponse.<T>builder()
				.hasSucceed(status.value() >= 200 && status.value() < 300)
				.body(body)
				.build(),
			status
		);
	}

	//===============================================================================
	// ResponseEntity의 ok, status 에 대응하는 메소드로 대문자를 사용하도록 함
	//===============================================================================
	public static <T> ServerResponseEntity<T> OK(T body) {
		return STATUS(HttpStatus.OK).body(body);
	}

	public static ServerResponseEntityBodyBuilder STATUS(HttpStatus status) {
		Assert.notNull(status, "HttpStatus must not be null");
		return new ServerResponseEntityBuilder(status);
	}
	//===============================================================================

	public interface ServerResponseEntityBodyBuilder {
		<T> ServerResponseEntity<T> body(T body);
	}

	private static class ServerResponseEntityBuilder implements ServerResponseEntityBodyBuilder{
		private final HttpStatus status;

		public ServerResponseEntityBuilder(HttpStatus status) {
			this.status = status;
		}

		public <T> ServerResponseEntity<T> body(T body) {
			return new ServerResponseEntity<>(body, status);
		}
	}

//	private ResponseEntity<ServerResponse<T>> innerEntity;
//	private ServerResponse<T> response;
//
//	public ServerResponseEntity(T body) {
//		response = ServerResponse.<T>builder()
//			.isOK(true)
//			.body(body)
//			.build();
//		innerEntity = ResponseEntity.ok().body(response);
//	}
//
//	public ServerResponseEntity(HttpStatus status, T body) {
//		response = ServerResponse.<T>builder()
//			.isOK(status.value() >= 200 && status.value() < 300)
//			.body(body)
//			.build();
//		innerEntity = ResponseEntity.status(status).body(response);
//	}
//
//	public ResponseEntity<ServerResponse<T>> entity() {
//		return innerEntity;
//	}
}
