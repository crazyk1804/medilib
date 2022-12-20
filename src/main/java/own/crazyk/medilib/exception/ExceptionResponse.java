package own.crazyk.medilib.exception;

import lombok.*;

@Getter
@Setter
@Builder
public class ExceptionResponse {
    protected String message;
    protected UserFriendlyRank userFriendly;

    public UserFriendlyRank getUserFriendly() {
        if(userFriendly==null) return UserFriendlyRank.notAtAll;
        return userFriendly;
    }
}
