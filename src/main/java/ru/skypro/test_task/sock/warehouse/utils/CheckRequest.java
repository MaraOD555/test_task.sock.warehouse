package ru.skypro.test_task.sock.warehouse.utils;

import org.springframework.http.HttpStatus;
import ru.skypro.test_task.sock.warehouse.entity.Sock;
import ru.skypro.test_task.sock.warehouse.exception.CustomException;

/**
 * Проверка входящих запросов
 */
public class CheckRequest {
    /**
     * Проверка % содержания хлопка (вспомогательный метод)
     * @param cottonPart процент содержания хлопка
     * @exception CustomException
     */
    public static void checkCottonPart(int cottonPart) throws CustomException {
        if (cottonPart < 0 | cottonPart > 100) {
            throw new CustomException("Содержание хлопка введено некорректно", HttpStatus.BAD_REQUEST);
        }
    }
    /**
     * Проверка цвета (вспомогательный метод)
     * @param color цвет
     * @exception CustomException
     */
    public static void checkColor( String color) throws CustomException {}

    /**
     * Проверка количества (вспомогательный метод)
     * @param quantity количество
     * @exception CustomException
     */
    public static void checkQuantity( int quantity) throws CustomException{
        if (quantity <= 0) {
            throw new CustomException("Неверное количество", HttpStatus.BAD_REQUEST);
        }
    }
    /**
     * Проверка запроса
     * @param sock входящий JSON
     * @exception CustomException
     */
    public static void check(Sock sock) throws CustomException{
        checkCottonPart(sock.getCottonPart());
        checkQuantity(sock.getQuantity());
        checkColor(sock.getColor());
    }
}
