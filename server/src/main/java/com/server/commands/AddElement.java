package com.server.commands;

import com.common.RequestStatus;
import com.common.Response;
import com.server.manager.CollectionManager;
import com.common.model.Route;

/**
 * Класс, представляющий консольную команду add
 * @author Ivan Kirillov
 */
public final class AddElement extends Command {
    private final CollectionManager collectionManager;

    /**
     * Конструктор класса команды add
     */
    public AddElement(CollectionManager collectionManager) {
        super("add", "Создать новый маршрут и добавить его в коллекцию");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(String primitiveArg, Route routeArg) {
        if (routeArg == null) return new Response(RequestStatus.BAD_REQUEST, "Маршрут не передан", null);

        collectionManager.inputElement(routeArg);
        return new Response(RequestStatus.SUCCESS, "Маршрут успешно добавлен в коллекцию!", null);
    }
}
