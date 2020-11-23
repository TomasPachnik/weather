package sk.tomas.wm;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;
import sk.tomas.wm.controller.NotificationController;
import sk.tomas.wm.dto.MessageHolder;

class WmApplicationTests {

    @Test
    void removeTest() {

        NotificationController controller = new NotificationController();
        controller.addNotification(new MessageHolder("message2"));
        controller.addNotification(new MessageHolder("message1"));
        controller.addNotification(new MessageHolder("message3"));
        controller.addNotification(new MessageHolder("message2"));
        controller.addNotification(new MessageHolder("message3"));

        controller.deleteFirstNotification();

        Assert.isTrue(controller.getAllNotifications().size() == 3, "List size is not correct!");
    }

}
