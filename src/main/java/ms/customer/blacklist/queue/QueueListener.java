package ms.customer.blacklist.queue;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ms.customer.blacklist.model.dto.BlacklistDto;
import ms.customer.blacklist.service.BlacklistService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class QueueListener {
    private final ObjectMapper objectMapper;
    private final BlacklistService blacklistService;

    @RabbitListener(queues = "${rabbitmq.queue.blacklist}")
    public void receiveMessage(String message) {
        log.info("ActionLog.receiveMessage.start message: {}", message);

        try {
            var dto = objectMapper.readValue(message, BlacklistDto.class);
            blacklistService.addIntoBlacklist(dto);
        } catch (Exception ex) {
            log.error("ActionLog.receiveMessage.error: ", ex);
        }
    }
}
