package com.server.commands;

import com.common.RequestStatus;
import com.common.Response;
import com.common.model.Route;
import com.server.manager.ServerCommandManager;

public final class Help extends Command {
    private final ServerCommandManager serverCommandManager;

    public Help(ServerCommandManager serverCommandManager) {
        super("help", "Вывести справку по доступным командам");
        this.serverCommandManager = serverCommandManager;
    }

    @Override
    public Response execute(String primitiveArg, Route routeArg) {
        StringBuilder helpText = new StringBuilder();

        helpText.append(String.format("%-45s%-1s%n", "execute_script {файл}", "Считать и исполнить скрипт"));
        helpText.append(String.format("%-45s%-1s%n", "exit", "Завершить программу"));

        serverCommandManager.getCommands().values().forEach(command -> {
            helpText.append(String.format("%-45s%-1s%n", command.getName(), command.getDescription()));
        });

        return new Response(RequestStatus.SUCCESS, helpText.toString(), null);
    }
}