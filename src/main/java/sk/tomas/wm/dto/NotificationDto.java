package sk.tomas.wm.dto;

import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Builder
public class NotificationDto {

    private UUID id;
    private OffsetDateTime created;
    private String message;

}
