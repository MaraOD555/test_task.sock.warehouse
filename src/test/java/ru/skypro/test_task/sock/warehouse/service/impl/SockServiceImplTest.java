package ru.skypro.test_task.sock.warehouse.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.skypro.test_task.sock.warehouse.entity.Operation;
import ru.skypro.test_task.sock.warehouse.entity.Sock;
import ru.skypro.test_task.sock.warehouse.exception.CustomException;
import ru.skypro.test_task.sock.warehouse.repository.SockRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SockServiceImplTest {
     @Mock
     private SockRepository sockRepository;

     @InjectMocks
     private SockServiceImpl sockService;

     @Test
     void save() {
        Sock sock = new Sock();
        sockService.save(sock);
        verify(sockRepository, times(1)).save(sock);
     }

     @Test
     void delete() {
        Sock sock = new Sock();
        sockService.delete(sock);
        verify(sockRepository, times(1)).delete(sock);
     }

     @Test
     void countSocks_withMoreThan_operation() {
        String color = "red";
        int cottonPart = 50;
        List<Sock> socks = new ArrayList<>();
        socks.add(new Sock(1, color, 60, 10));
        when(sockRepository.findByColorAndCottonPartGreaterThan(color, cottonPart)).thenReturn(socks);
        assertThat(sockService.countSocks(color, cottonPart, Operation.MORE_THAN)).isEqualTo("10");
     }

     @Test
     void countSocks_withLessThan_operation() {
         String color = "red";
         int cottonPart = 50;
         List<Sock> socks = new ArrayList<>();
         socks.add(new Sock(1, color, 40, 10));
         when(sockRepository.findByColorAndCottonPartLessThan(color, cottonPart)).thenReturn(socks);
         assertThat(sockService.countSocks(color, cottonPart, Operation.LESS_THAN)).isEqualTo("10");
     }

     @Test
     void countSocks_withEqual_operation() {
          String color = "red";
          int cottonPart = 50;
          Optional<Sock> sock = Optional.of(new Sock(1, color, 50, 10));
          when(sockRepository.findByColorAndCottonPartEquals(color, cottonPart)).thenReturn(sock);
          assertThat(sockService.countSocks(color, cottonPart, Operation.EQUAL)).isEqualTo("10");
     }

     @Test
     void income_withNewSock() {
          Sock sock = new Sock(1, "red", 50, 10);
          when(sockRepository.findByColorAndCottonPartEquals(sock.getColor(), sock.getCottonPart())).thenReturn(Optional.empty());
          sockService.income(sock);
          verify(sockRepository, times(1)).save(sock);
     }

     @Test
     void income_withExistingSock() {
          Sock sock = new Sock(1, "red", 50, 10);
          Sock foundSock = new Sock(1, "red", 50, 5);
          when(sockRepository.findByColorAndCottonPartEquals(sock.getColor(), sock.getCottonPart())).thenReturn(Optional.of(foundSock));
          sockService.income(sock);
          foundSock.setQuantity(15);
          verify(sockRepository, times(1)).save(foundSock);
     }

     @Test
     void outcome_withNotFoundSock() {
          Sock sock = new Sock(1, "red", 50, 10);
          when(sockRepository.findByColorAndCottonPartEquals(sock.getColor(), sock.getCottonPart())).thenReturn(Optional.empty());
          assertThrows(CustomException.class, () -> sockService.outcome(sock));
     }

     @Test
     void outcome_withQuantityGreaterThanFoundSock() {
          Sock sock = new Sock(1, "red", 50, 15);
          Sock foundSock = new Sock(1, "red", 50, 10);
          when(sockRepository.findByColorAndCottonPartEquals(sock.getColor(), sock.getCottonPart())).thenReturn(Optional.of(foundSock));
          assertThrows(CustomException.class, () -> sockService.outcome(sock));
     }

     @Test
     void outcome_withDeleteFoundSock() {
          Sock sock = new Sock(1, "red", 50, 10);
          Sock foundSock = new Sock(1, "red", 50, 10);
          when(sockRepository.findByColorAndCottonPartEquals(sock.getColor(), sock.getCottonPart())).thenReturn(Optional.of(foundSock));
          sockService.outcome(sock);
          verify(sockRepository, times(1)).delete(foundSock);
     }

     @Test
     void outcome_withUpdateFoundSock() {
          Sock sock = new Sock(1, "red", 50, 5);
          Sock foundSock = new Sock(1, "red", 50, 10);
          when(sockRepository.findByColorAndCottonPartEquals(sock.getColor(), sock.getCottonPart())).thenReturn(Optional.of(foundSock));
          sockService.outcome(sock);
          foundSock.setQuantity(5);
          verify(sockRepository, times(1)).save(foundSock);
    }

    @Test
    void findByColorAndCottonPartEquals() throws CustomException {
         String color = "red";
         int cottonPart = 50;
         Sock sock = new Sock(1, color, cottonPart, 10);
         when(sockRepository.findByColorAndCottonPartEquals(color, cottonPart)).thenReturn(Optional.of(sock));
         assertThat(sockService.findByColorAndCottonPartEquals(color, cottonPart).get()).isEqualTo(sock);
    }

    @Test
    void countQuantities_withEmptyList() {
         List<Sock> socks = new ArrayList<>();
         assertThat(sockService.countQuantities(socks)).isEqualTo("0");
    }

    @Test
    void countQuantities_withNonEmptyList() {
         List<Sock> socks = new ArrayList<>();
         socks.add(new Sock(1, "red", 50, 5));
         socks.add(new Sock(2, "blue", 60, 10));
         assertThat(sockService.countQuantities(socks)).isEqualTo("15");
    }
}
