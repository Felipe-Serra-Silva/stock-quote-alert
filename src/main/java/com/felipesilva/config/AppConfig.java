package com.felipesilva.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class AppConfig {
    private String recipient;
    private String smtpHost;
    private int smtpPort;
    private String smtpUser;
    private String smtpPassword;
    private String apiKey;

    public AppConfig(String configPath) throws  IOException{
        Properties props = new Properties();

        props.load(new FileInputStream(configPath));

        this.recipient = props.getProperty("email.recipient");
        this.smtpHost = props.getProperty("smtp.host");
        this.smtpPort = Integer.parseInt(props.getProperty("smtp.port"));
        this.smtpUser = props.getProperty("smtp.user");
        this.smtpPassword = props.getProperty("smtp.password");
        this.apiKey = props.getProperty("api.key");
    }

    public String getRecipient() { return recipient; }
    public String getSmtpHost() { return smtpHost; }
    public int getSmtpPort() { return smtpPort; }
    public String getSmtpUser() { return smtpUser; }
    public String getSmtpPassword() { return smtpPassword; }
    public String getApiKey() { return apiKey; }
}