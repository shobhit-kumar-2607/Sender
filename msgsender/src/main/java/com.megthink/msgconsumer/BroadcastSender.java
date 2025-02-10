package msgconsumer;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import producer.KafkaProducer;
import utils.ReadConfigFile;

@Service
public class BroadcastSender { 
	@Autowired
	private KafkaProducer producer;

    private static final Logger logger = LoggerFactory.getLogger(BroadcastSender.class);
    private final String recipientUrl = ReadConfigFile.getProperties().getProperty("BroadcastSender");
    private final String recipient = ReadConfigFile.getProperties().getProperty("Recipient");
    private final String soapRecipient = ReadConfigFile.getProperties().getProperty("SoapRecipient");
    private final String soapRecipientUrl = ReadConfigFile.getProperties().getProperty("SoapRecipientUrl");
    
    @Value("kafka.topic")
    private static final String QUEUE = ReadConfigFile.getProperties().getProperty("BroadcastSender_queue");
    @Value("kafka.group-id")
    private static final String group_id = ReadConfigFile.getProperties().getProperty("BroadcastSender_queue_groupid");


    String response = null;

    @KafkaListener(topics = "${kafka.topic}", groupId = "${kafka.group-id}")
    public void sendConfirmation(String message) {
        String sessionId = Long.toString(System.currentTimeMillis());
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        try {
            JsonNode jsonNode = mapper.readTree(message);
            String receiver = jsonNode.path("receiver").asText();
           
            if (recipient!=null&&recipientUrl!=null) {
            	if(recipient.equalsIgnoreCase(receiver))
                sendHttpRequest(jsonNode, sessionId);
            } else if (soapRecipient!=null && soapRecipientUrl!=null) {
            	if(soapRecipient.equalsIgnoreCase(receiver))
                sendSoapRequest(jsonNode, sessionId);
            }else {
            	logger.info("recipient and recipientUrl contains null value or coming receiver is null");
            }
        } catch (Exception e) {
            logger.error("Session ID: {} - An error occurred while sending the confirmation: ", sessionId, e);
        }
    }

    private void sendHttpRequest(JsonNode jsonNode, String sessionId) throws Exception {
        URL url = new URL(recipientUrl);
        HttpURLConnection connection;

        if ("https".equalsIgnoreCase(url.getProtocol())) {
            connection = (HttpsURLConnection) url.openConnection();
        } else if ("http".equalsIgnoreCase(url.getProtocol())) {
            connection = (HttpURLConnection) url.openConnection();
        } else {
            throw new IllegalArgumentException("Unsupported protocol: " + url.getProtocol());
        }

        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        String jsonPayload = jsonNode.toString();
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonPayload.getBytes("utf-8");
            os.write(input, 0, input.length);
        }
        int responseCode = connection.getResponseCode();
        logger.info("Session ID: {} - Response Code: {}", sessionId, responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) {
            logger.info("Session ID: {} - Request Successful! Response Code: {}", sessionId, responseCode);
        } else {
            logger.error("Session ID: {} - Request Failed! Response Code: {}", sessionId, responseCode);
        }
        response = String.valueOf(responseCode);
        String responseMessage = response + jsonPayload;
        producer.sendMessage(responseMessage, sessionId);
    }

    private void sendSoapRequest(JsonNode jsonNode, String sessionId) throws Exception {
        URL url = new URL(soapRecipientUrl);
        HttpURLConnection connection;

        if ("https".equalsIgnoreCase(url.getProtocol())) {
            connection = (HttpsURLConnection) url.openConnection();
        } else if ("http".equalsIgnoreCase(url.getProtocol())) {
            connection = (HttpURLConnection) url.openConnection();
        } else {
            throw new IllegalArgumentException("Unsupported protocol: " + url.getProtocol());
        }

        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
        connection.setDoOutput(true);

        String soapMessage = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<soapenv:Header/>"
                + "<soapenv:Body>"
                + "<exampleRequest>"
                + "<data>" + jsonNode.toString() + "</data>"
                + "</exampleRequest>"
                + "</soapenv:Body>"
                + "</soapenv:Envelope>";

        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = soapMessage.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int responseCode = connection.getResponseCode();
        logger.info("Session ID: {} - SOAP Response Code: {}", sessionId, responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) {
            logger.info("Session ID: {} - SOAP Request Successful! Response Code: {}", sessionId, responseCode);
        } else {
            logger.error("Session ID: {} - SOAP Request Failed! Response Code: {}", sessionId, responseCode);
        }
        response = String.valueOf(responseCode);
        String responseMessage = response + soapMessage;
        producer.sendMessage(responseMessage, sessionId);
    }
}
