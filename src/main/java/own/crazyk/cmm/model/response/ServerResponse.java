package own.crazyk.cmm.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 서버 응답 객체용 클래스. 제너릭 타입 T를 사용해서 응답형식을 결정.
 * @param <T>
 */
@Getter
@Setter
@Builder
public class ServerResponse<T> {
	private boolean hasSucceed;
	private T body;
}
