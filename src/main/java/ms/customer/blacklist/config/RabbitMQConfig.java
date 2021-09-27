package ms.customer.blacklist.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    private final String blacklistQ;
    private final String blacklistDLQ;
    private final String blacklistQExchange;
    private final String blacklistDLQExchange;
    private final String blacklistQKey;
    private final String blacklistDLQKey;

    public RabbitMQConfig(@Value("${rabbitmq.queue.blacklist}") String blacklistQ,
                          @Value("${rabbitmq.queue.blacklist-dlq}") String blacklistDLQ) {
        this.blacklistQ = blacklistQ;
        this.blacklistDLQ = blacklistDLQ;
        this.blacklistQExchange = blacklistQ + "_Exchange";
        this.blacklistDLQExchange = blacklistDLQ + "_Exchange";
        this.blacklistQKey = blacklistQ + "_Key";
        this.blacklistDLQKey = blacklistDLQ + "_Key";
    }

    @Bean
    DirectExchange blacklistDLQExchange() {
        return new DirectExchange(blacklistDLQExchange);
    }

    @Bean
    DirectExchange blacklistQExchange() {
        return new DirectExchange(blacklistQExchange);
    }

    @Bean
    Queue blacklistDLQ() {
        return QueueBuilder.durable(blacklistDLQ).build();
    }

    @Bean
    Queue blacklistQ() {
        return QueueBuilder.durable(blacklistQ)
                .withArgument("x-dead-letter-exchange", blacklistDLQExchange)
                .withArgument("x-dead-letter-routing-key", blacklistDLQKey)
                .build();
    }

    @Bean
    Binding blacklistDLQBinding() {
        return BindingBuilder.bind(blacklistDLQ())
                .to(blacklistDLQExchange()).with(blacklistDLQKey);
    }

    @Bean
    Binding blacklistQBinding() {
        return BindingBuilder.bind(blacklistQ())
                .to(blacklistQExchange()).with(blacklistQKey);
    }
}  
