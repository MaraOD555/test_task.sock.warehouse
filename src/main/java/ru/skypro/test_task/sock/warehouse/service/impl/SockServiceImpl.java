package ru.skypro.test_task.sock.warehouse.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.skypro.test_task.sock.warehouse.entity.Operation;
import ru.skypro.test_task.sock.warehouse.entity.Sock;
import ru.skypro.test_task.sock.warehouse.exception.CustomException;
import ru.skypro.test_task.sock.warehouse.repository.SockRepository;
import ru.skypro.test_task.sock.warehouse.service.SockService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static ru.skypro.test_task.sock.warehouse.utils.CheckRequest.check;
import static ru.skypro.test_task.sock.warehouse.utils.CheckRequest.checkCottonPart;

@Slf4j
@Service
@RequiredArgsConstructor
public class SockServiceImpl implements SockService {
    private final SockRepository sockRepository;

    @Override
    public void save(Sock sock) {
        sockRepository.save(sock);
    }
    @Override
    public void delete(Sock sock) {
        sockRepository.delete(sock);
    }

    @Override
    public String countSocks(String color, int cottonPart, Operation operation) {

        /*     switch (operation) {
            case MORE_THAN: return countQuantities(findByColorAndCottonPartGreaterThan(color, cottonPart));
            case LESS_THAN: return countQuantities(findByColorAndCottonPartLessThan(color, cottonPart));
            case EQUAL: return countQuantities(findByColorAndCottonPartEquals(color, cottonPart));
            default: throw new CustomException("Операция некорректна", HttpStatus.BAD_REQUEST);
        } */
        checkCottonPart(cottonPart);
        switch (operation) {
            case MORE_THAN: return countQuantities(sockRepository.findByColorAndCottonPartGreaterThan(color, cottonPart));
            case LESS_THAN: return countQuantities(sockRepository.findByColorAndCottonPartLessThan(color, cottonPart));
            case EQUAL: return countQuantities(sockRepository.findByColorAndCottonPartEquals(color, cottonPart));
            default: throw new CustomException("Операция некорректна", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public void income(Sock sock) throws CustomException {
        check(sock);
        findByColorAndCottonPartEquals(sock.getColor(), sock.getCottonPart()).
                ifPresentOrElse(
                        foundSock -> {
                            foundSock.setQuantity(sock.getQuantity() + foundSock.getQuantity());
                            save(foundSock);
                        },
                        () -> save(sock));
    }

    @Override
    public void outcome(Sock sock) throws CustomException {
        check(sock);
        Sock foundSock= findByColorAndCottonPartEquals(sock.getColor(), sock.getCottonPart()).
                orElseThrow( () -> new CustomException("Носки с заданными параметрами не найдены", HttpStatus.BAD_REQUEST));
        int difference = foundSock.getQuantity() - sock.getQuantity();
        if (difference > 0) {
            foundSock.setQuantity(difference);
            save(foundSock);
        } else if (difference == 0) {
            delete(foundSock);
        } else {
            throw new CustomException("Введенное количество превышает найденное", HttpStatus.BAD_REQUEST);
        }
    }

   /* @Override
    public List<Sock> findByColorAndCottonPartGreaterThan(String color, int cottonPart) throws CustomException {
        checkCottonPart(cottonPart);
        return sockRepository.findByColorAndCottonPartGreaterThan(color, cottonPart);
    }
    @Override
    public List<Sock> findByColorAndCottonPartLessThan(String color, int cottonPart) throws CustomException{
        checkCottonPart(cottonPart);
        return sockRepository.findByColorAndCottonPartLessThan(color, cottonPart);
    }*/
    @Override
    public Optional<Sock> findByColorAndCottonPartEquals(String color, int cottonPart) throws CustomException{
        checkCottonPart(cottonPart);
        return sockRepository.findByColorAndCottonPartEquals(color, cottonPart);
    }
    /**
     * Количество пар носков при equals (вспомогательный метод)
     * @param sock
     */
    private String countQuantities(Optional<Sock> sock) {
        int numberSocks = 0;
        if (sock.isPresent()) {
            numberSocks = sock.get().getQuantity();
        }
        return Objects.requireNonNullElse(numberSocks, 0).toString();
    }
    /**
     * Количество пар носков с диапозоном параметров (вспомогательный метод)
     * @param socks
     */
    private String countQuantities(List<Sock> socks) {
        int numberSocks = 0;
        if (socks.size() > 0) {
            for (Sock sock: socks) {
                numberSocks += sock.getQuantity();
            }
        }
        return Objects.requireNonNullElse(numberSocks, 0).toString();
    }
}
