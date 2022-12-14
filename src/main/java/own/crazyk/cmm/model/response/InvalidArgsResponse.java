package own.crazyk.cmm.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Getter
public class InvalidArgsResponse {
	@Getter @Setter @Builder @AllArgsConstructor
	public static class InvalidArgument {
		private String field;
		private String message;
		private Object value;
	}

	private final List<InvalidArgument> invalidArgs;

	public InvalidArgsResponse(MethodArgumentNotValidException exception) {
		HashMap<String, InvalidArgument> fieldMap = new HashMap<>();

		exception.getBindingResult().getFieldErrors().forEach(fieldError -> {
			if(fieldMap.containsKey(fieldError.getField())) return;
			fieldMap.put(
				fieldError.getField(),
				new InvalidArgument(fieldError.getField(), fieldError.getDefaultMessage(), fieldError.getRejectedValue())
			);
		});
		invalidArgs = new ArrayList<>(fieldMap.values());
	}
}
