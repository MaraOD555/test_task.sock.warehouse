package ru.skypro.test_task.sock.warehouse.service;

import ru.skypro.test_task.sock.warehouse.entity.Operation;
import ru.skypro.test_task.sock.warehouse.entity.Sock;
import java.util.Optional;

/**
 * Сервис склада
 */
public interface SockService {
    /**
     * Вспомогательный метод, сохраняет носки
     *
     * @param sock     {@link Sock}
     */
    void save( Sock sock);

    /**
     * Вспомогательный метод, удаляет носки
     *
     * @param sock
     */
    void delete(Sock sock);

    /**
     * Метод для получения количества пар носков {@link Sock} по параметрам
     *
     * @param color      цвет;
     * @param operation  операция сравнения;
     * @param cottonPart процент хлопка в составе;
     * @return количество пар носков
     */
    String countSocks(String color, int cottonPart, Operation operation);

    /**
     * Метод для добавления пар носков {@link Sock}
     *
     * @param sock
     */
    void income(Sock sock);

    /**
     * Метод для уменьшения количества пар носков {@link Sock}
     *
     * @param sock
     */
    void outcome(Sock sock);

    /**
     * Возвращает носки с параметрами EQUAL
     *
     * @param color цвет
     * @param cottonPart процент содержания хлопка
     */
    Optional<Sock> findByColorAndCottonPartEquals(String color, int cottonPart);
}
