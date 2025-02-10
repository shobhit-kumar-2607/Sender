	package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.context.annotation.Configuration;

@Configuration
public class ReadConfigFile {

	public static Properties getProperties() {
        Properties prop = new Properties();
        try (InputStream input = new FileInputStream(System.getProperty("user.home") + "/app-config/M_Sender/senderconfig.properties")) {
            prop.load(input);                                
        } catch (IOException ex) {
            throw new RuntimeException("Error reading config properties: " + ex.getMessage(), ex);
        }
        return prop;
    }
}
