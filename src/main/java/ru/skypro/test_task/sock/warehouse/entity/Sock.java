package ru.skypro.test_task.sock.warehouse.entity;

import javax.persistence.*;
import lombok.*;

/**
 * Сущность Sock для базы данных
 */
/*
@Table(name = "socks")
@Getter
@Setter
@EqualsAndHashCode(exclude = "id")
@NoArgsConstructor
@AllArgsConstructor */
@Table(name = "socks")
@Getter
@Setter
@Entity
public class Sock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String color;

    private int cottonPart;

    private int quantity;
}
