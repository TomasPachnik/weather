package sk.tomas.wm.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class BusinessException extends RuntimeException {

    @Getter
    private String message;

}
