package com.felipesilva.monitor;

import com.felipesilva.service.EmailService;
import com.felipesilva.service.StockService;
import jakarta.mail.MessagingException;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AlertMonitor {

    private final String symbol;
    private final double sellPrice;
    private final double buyPrice;
    private final StockService stockService;
    private final EmailService emailService;

    public AlertMonitor(String symbol, double sellPrice, double buyPrice,
                        StockService stockService, EmailService emailService) {
        this.symbol = symbol;
        this.sellPrice = sellPrice;
        this.buyPrice = buyPrice;
        this.stockService = stockService;
        this.emailService = emailService;
    }

    public void monitor() {
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(() -> {
            try {
                double precoAtual = stockService.getPrice(symbol);
                System.out.println(precoAtual);
                if (precoAtual > sellPrice) {
                    emailService.sendEmail(
                            "Alerta de Venda - " + symbol,
                            "O preço de " + symbol + " está em R$ " + precoAtual + ", acima do limite de venda R$ " + sellPrice
                    );
                }
                if (precoAtual < buyPrice) {
                    emailService.sendEmail(
                            "Alerta de Compra - " + symbol,
                            "O preço de " + symbol + " está em R$ " + precoAtual + ", abaixo do limite de compra R$ " + buyPrice
                    );
                }
            } catch (IOException | MessagingException e) {
                System.err.println("Erro ao verificar preço: " + e.getMessage());
            }
        }, 0, 60, TimeUnit.SECONDS);
    }
}
