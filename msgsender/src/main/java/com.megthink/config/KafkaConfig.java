package config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;

import utils.ReadConfigFile;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConfig {

    private final String bootstrapServers = ReadConfigFile.getProperties().getProperty("spring.kafka.url");
    private final String Core_Queue = ReadConfigFile.getProperties().getProperty("spring.kafka.core.queue");
    private final String OUT_QUEUE = ReadConfigFile.getProperties().getProperty("spring.kafka.out.queue");
    private final String OUTQUEUEReturnNumber = ReadConfigFile.getProperties().getProperty("spring.kafka.out.queue.number.return");
    private final String OUTQUEUEbillingresolution = ReadConfigFile.getProperties().getProperty("spring.kafka.out.queue.billing.resolution");

    
   
    @Bean
    public ProducerFactory<String, String> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, "group_id");
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(configProps);
    }

    @Bean
    public NewTopic topic1() {
        return new NewTopic(OUTQUEUEReturnNumber, 1, (short) 1);
    }

    @Bean
    public NewTopic topic2() {
        return new NewTopic(OUT_QUEUE, 1, (short) 1);
    }
    
    @Bean
    public NewTopic topic3() {
        return new NewTopic(OUTQUEUEbillingresolution, 1, (short) 1);
    }
    
    @Bean
    public NewTopic topic4() {
        return new NewTopic(Core_Queue, 1, (short) 1);
    }
    
    
    
}