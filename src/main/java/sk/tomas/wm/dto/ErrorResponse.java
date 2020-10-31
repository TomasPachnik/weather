package sk.tomas.wm.dto;

import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@Builder
public class ErrorResponse {

    private OffsetDateTime created;
    private String message;

}
