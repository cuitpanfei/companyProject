package cn.com.pfinfo.demo.conf;

import cn.com.pfinfo.demo.base.cache.ControllerMapCache;
import cn.com.pfinfo.demo.util.JsonUtil;
import cn.com.pfinfo.demo.util.NullUtil;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

@Configuration
@Slf4j
public class RabbitMQConfig {
    @Autowired
    private Environment env;

    public RabbitMQConfig() {
    }

    @Bean
    ConnectionFactory connectionFactory() {
        com.rabbitmq.client.ConnectionFactory rabbitConnectionFactory = new com.rabbitmq.client.ConnectionFactory();
        rabbitConnectionFactory.setUsername(this.env.getProperty("rabbitMQ.username"));
        rabbitConnectionFactory.setPassword(this.env.getProperty("rabbitMQ.password"));
        rabbitConnectionFactory.setHost(this.env.getProperty("rabbitMQ.host"));
        rabbitConnectionFactory.setPort(Integer.parseInt(this.env.getProperty("rabbitMQ.port")));
        ConnectionFactory connectionFactory = new CachingConnectionFactory(rabbitConnectionFactory);
        return connectionFactory;
    }

    @Bean
    public RabbitTemplate instance(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);
        rabbitTemplate.setExchange(this.env.getProperty("rabbitMQ.exchange"));
        return rabbitTemplate;
    }

    @Bean
    Queue queue() {
        return new Queue("dev", false);
    }

    @Bean
    DirectExchange exchange() {
        return new DirectExchange("dev", false, false);
    }

    @Bean
    Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("dev");
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, MessageListener listener) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(new String[]{"dev"});
        container.setMessageListener(listener);
        return container;
    }

    @Bean
    MessageListener listener() {
        return (Message message) -> {
            JSONObject mjson = null;
            try{
                 mjson=JsonUtil.bytesToJsonObject(message.getBody());
            }catch (JSONException exception){
                log.error("消息体不正确",exception);
                mjson = (JSONObject) JSONObject.toJSON("{code:500,msg:消息体不正确}");
            }
            Map<String, Object> info = (Map) ControllerMapCache.getInstance().getCache().get(mjson.get("url") + "#" + mjson.get("method"));
            if(NullUtil.isNotEmpty(info)){
                invoke((Method) info.get(ControllerMapCache.Constant.METHOD), info.get("bean"));
            }
        };

    }

    private void invoke(Method method, Object bean, Object... args) {
        try {
            Object obj = method.invoke(bean, args);
        } catch (IllegalAccessException e) {
            log.error(e.getMessage(),e);
        } catch (InvocationTargetException e) {
            log.error(e.getMessage(),e);
        }
    }
}
