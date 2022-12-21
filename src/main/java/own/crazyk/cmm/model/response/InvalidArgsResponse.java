package own.crazyk.cmm.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.MethodArgumentNotValidException;
import own.crazyk.medilib.exception.ExceptionResponse;
import own.crazyk.medilib.exception.UserFriendlyRank;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Getter
public class InvalidArgsResponse {
    private String message;
    private List<InvalidArgument> invalidArgs;

    public UserFriendlyRank getUserFriendly() {
        return UserFriendlyRank.notReally;
    }

//	public static class InvalidArgsResponseBuilder {
//		public InvalidArgsResponseBuilder invalidArgs(MethodArgumentNotValidException exception) {
//			HashMap<String, InvalidArgument> fieldMap = new HashMap<>();
//			StringBuilder messageAll = new StringBuilder();
//
//			exception.getBindingResult().getFieldErrors().forEach(fieldError -> {
//				if(fieldMap.containsKey(fieldError.getField())) return;
//				String fieldErrorMessage = fieldError.getDefaultMessage();
//				fieldMap.put(
//						fieldError.getField(),
//						new InvalidArgument(fieldError.getField(), fieldErrorMessage, fieldError.getRejectedValue())
//				);
//				messageAll.append(fieldErrorMessage).append("\r\n");
//			});
//			invalidArgs = new ArrayList<>(fieldMap.values());
////			message = messageAll.toString();
//			return this;
//		}
//	}


    public InvalidArgsResponse(MethodArgumentNotValidException exception) {
        HashMap<String, InvalidArgument> fieldMap = new HashMap<>();

        exception.getBindingResult().getFieldErrors().forEach(fieldError -> {
            if (fieldMap.containsKey(fieldError.getField())) return;
            fieldMap.put(
                fieldError.getField(),
                new InvalidArgument(fieldError.getField(), fieldError.getDefaultMessage(), fieldError.getRejectedValue())
            );
        });
        invalidArgs = new ArrayList<>(fieldMap.values());
        message = String.join(
            "\r\n",
            fieldMap.values().stream().map(
                x -> String.format("%s은(는) %s.", x.getField(), x.getMessage())
            ).toList()
        );
    }

    @Getter @Setter @Builder @AllArgsConstructor
    public static class InvalidArgument {
        private String field;
        private String message;
        private Object value;
    }
}
