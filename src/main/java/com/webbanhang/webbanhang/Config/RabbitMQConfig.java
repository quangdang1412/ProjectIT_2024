package com.webbanhang.webbanhang.Config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.web.context.support.GenericWebApplicationContext;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
@EnableRabbit
@RequiredArgsConstructor
@EnableAutoConfiguration(exclude = RabbitAutoConfiguration.class)
public class RabbitMQConfig {


    private final GenericWebApplicationContext context;

    @Value("${spring.rabbitmq.host}")
    private String rabbitHost;

    @Value("${spring.rabbitmq.port}")
    private int rabbitPort;

    @Value("${spring.rabbitmq.username}")
    private String rabbitUsername;

    @Value("${spring.rabbitmq.password}")
    private String rabbitPassword;

    @Value("${spring.rabbitmq.virtual-host}")
    private String rabbitVirtualHost;

    @Value("${spring.rabbitmq.virtual-host-job}")
    private String rabbitVirtualHostJob;

    private CachingConnectionFactory getCachingConnectionFactoryCommon() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(this.rabbitHost, this.rabbitPort);
        connectionFactory.setUsername(this.rabbitUsername);
        connectionFactory.setPassword(this.rabbitPassword);
        return connectionFactory;
    }

    @Primary
    @Bean
    public AmqpAdmin amqpAdmin(ConnectionFactory connectionFactory) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        return rabbitAdmin;
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter(ObjectMapper rabbitObjectMapper) {
        return new Jackson2JsonMessageConverter(rabbitObjectMapper);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, Jackson2JsonMessageConverter jsonMessageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter);
        return rabbitTemplate;
    }

    @Primary
    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = this.getCachingConnectionFactoryCommon();
        connectionFactory.setVirtualHost(this.rabbitVirtualHost);
        return connectionFactory;
    }

    @Primary
    @Bean("rabbitListenerContainerFactory")
    public SimpleRabbitListenerContainerFactory containerFactory(ConnectionFactory connectionFactory) {
        final SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setDefaultRequeueRejected(false);
        factory.setConnectionFactory(connectionFactory);
        factory.setDefaultRequeueRejected(false);
        return factory;
    }

//    @Bean(name = "amqpAdminJob")
//    public AmqpAdmin amqpAdminJob() {
//        RabbitAdmin rabbitAdmin = new RabbitAdmin(this.rabbitConnectionJob());
//        return rabbitAdmin;
//    }
//
//    @Bean("rabbitConnectionJob")
//    public ConnectionFactory rabbitConnectionJob() {
//        CachingConnectionFactory connectionFactory = this.getCachingConnectionFactoryCommon();
//        connectionFactory.setVirtualHost(this.rabitVirtualHostJob);
//        return connectionFactory;
//    }
//
//    @Bean("rabbitTemplateJob")
//    public RabbitTemplate rabbitTemplateJob(@Qualifier("rabbitConnectionJob") ConnectionFactory connectionFactory) {
//        return new RabbitTemplate(connectionFactory);
//    }
//
//    @Bean("containerFactoryJob")
//    public SimpleRabbitListenerContainerFactory containerFactoryJob(@Qualifier("rabbitConnectionJob") ConnectionFactory connectionFactory) {
//        final SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
//        factory.setDefaultRequeueRejected(false);
//        factory.setConnectionFactory(connectionFactory);
//        factory.setDefaultRequeueRejected(false);
//        return factory;
//    }

}

