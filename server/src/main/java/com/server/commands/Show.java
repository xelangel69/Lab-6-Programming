package com.server.commands;

import com.common.RequestStatus;
import com.common.Response;
import com.common.model.Route;
import com.server.manager.CollectionManager;

import java.util.List;
import java.util.Comparator;
import java.util.stream.Collectors;

/**
 * Класс, представляющий консольную команду show
 * @author Ivan Kirillov
 */
public final class Show extends Command {
    private final CollectionManager collectionManager;

    /**
     * Конструктор класса команды show
     */
    public Show(CollectionManager collectionManager) {
        super("show", "Вывести все маршруты");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(String primitiveArg, Route routeArg) {
        List<Route> sortedRoutes = collectionManager.getRoutes().stream()
                .sorted(Comparator.comparing(route -> route.getFrom().getX()))
                .collect(Collectors.toList());

        return new Response(RequestStatus.SUCCESS, "Коллекция успешно получена.", sortedRoutes);
    }
}
