package com.felipesilva;

import com.felipesilva.config.AppConfig;
import com.felipesilva.monitor.AlertMonitor;
import com.felipesilva.service.EmailService;
import com.felipesilva.service.StockService;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length != 3) {
            System.err.println("Uso: stock-quote-alert <symbol> <sellPrice> <buyPrice>");
            System.exit(1);
        }
        String symbol = args[0];
        double sellPrice = Double.parseDouble(args[1]);
        double buyPrice = Double.parseDouble(args[2]);
        AppConfig config = new AppConfig("config.properties");
        StockService stockService = new StockService(config.getApiKey());
        EmailService emailService = new EmailService(config);
        AlertMonitor alertMonitor = new AlertMonitor(symbol, sellPrice, buyPrice, stockService, emailService);
        alertMonitor.monitor();

    };
}
