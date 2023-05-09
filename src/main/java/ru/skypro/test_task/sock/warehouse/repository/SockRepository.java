package ru.skypro.test_task.sock.warehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.test_task.sock.warehouse.entity.Sock;

import java.util.List;
import java.util.Optional;

@Repository
public interface SockRepository extends JpaRepository <Sock, Integer> {

    List<Sock> findByColorAndCottonPartGreaterThan(String color, int cottonPart);

    List<Sock> findByColorAndCottonPartLessThan(String color, int cottonPart);

    Optional<Sock> findByColorAndCottonPartEquals(String color, int cottonPart);
}
