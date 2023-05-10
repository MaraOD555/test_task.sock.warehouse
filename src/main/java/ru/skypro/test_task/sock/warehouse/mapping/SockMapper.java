package ru.skypro.test_task.sock.warehouse.mapping;

import org.mapstruct.Mapper;
import ru.skypro.test_task.sock.warehouse.dto.SockDto;
import ru.skypro.test_task.sock.warehouse.entity.Sock;

@Mapper(componentModel = "spring")
public interface SockMapper {
    Sock toEntity(SockDto sockDto);
}
