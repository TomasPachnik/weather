package sk.tomas.wm.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import sk.tomas.wm.dto.MessageHolder;
import sk.tomas.wm.dto.NotificationDto;
import sk.tomas.wm.exception.NotFoundException;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private List<NotificationDto> notifications;

    public NotificationController() {
        notifications = new ArrayList<>();
    }

    @GetMapping("/getAll")
    public List<NotificationDto> getAllNotifications() {
        return notifications;
    }

    @GetMapping("/getFirst")
    public NotificationDto getFirstNotification() {
        if (!notifications.isEmpty()) {
            return notifications.get(0);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Empty notification list", new NotFoundException("Notification not found."));
    }

    @DeleteMapping("/deleteFirst")
    public void deleteFirstNotification() {
        if (!notifications.isEmpty()) {
            notifications.remove(0);
        }
    }

    @DeleteMapping("/delete")
    public void deleteNotification(@RequestParam(name = "id") UUID id) {
        notifications.removeIf(p -> p.getId().equals(id));
    }

    @PutMapping("/add")
    public void addNotification(@RequestBody MessageHolder message) {
        if (message == null || message.getMessage() == null) {
            return;
        }

        notifications.add(NotificationDto.builder()
                .id(UUID.randomUUID())
                .created(OffsetDateTime.now())
                .message(message.getMessage())
                .build());
    }

}
