package com.server;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;

import com.common.Request;
import com.common.Response;
import com.server.manager.ServerCommandManager;
import org.apache.logging.log4j.Logger;

/**
 * Мененджер серверного приложения - управляет всем приложением
 * @author Ivan Kirillov
 */
public final class ServerApp {
    private final ServerCommandManager serverCommandManager;
    private UDPServer udpServer;
    private static final Logger logger = LogManager.getLogger(ServerApp.class);

    /**
     * Конструктор мененджера приложения
     */
    public ServerApp(ServerCommandManager serverCommandManager, UDPServer udpServer) {
        this.serverCommandManager = serverCommandManager;
        this.udpServer = udpServer;
    }

    /**
     * Запускает приложение
     *
     */
    public void run() throws IOException {
        logger.info("Сервер начал работу");
        while (true) {
            try {
                Request request = udpServer.receiveRequest();
                logger.info("Получен запрос");
                Response response = serverCommandManager.handleRequest(request);
                udpServer.sentResponse(response);
                logger.info("Ответ отправлен");
            } catch (Exception e) {
                logger.error("Возникла ошибка");
            }
        }
    }
}