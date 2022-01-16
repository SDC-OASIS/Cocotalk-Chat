package com.cocotalk.chat.controller;

import com.cocotalk.chat.document.Room;
import com.cocotalk.chat.model.request.RoomRequest;
import com.cocotalk.chat.model.response.GlobalResponse;
import com.cocotalk.chat.model.response.RoomResponse;
import com.cocotalk.chat.service.RoomService;
import com.cocotalk.chat.utils.mapper.RoomMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/chat/rooms")
public class RoomController {
    private final RoomService roomService;
    private final RoomMapper roomMapper;

    @PostMapping
    public Mono<ResponseEntity<?>> createRoom(@RequestBody RoomRequest request){
        Mono<Room> roomMono = roomService.createRoom(roomMapper.toEntity(request));
        return roomMono.map(room -> {
            RoomResponse data = roomMapper.toDto(room);
            return new ResponseEntity<>(new GlobalResponse<>(data), HttpStatus.CREATED);
        });
    }

    @GetMapping("/private")
    public Mono<?> findPrivateRoom(@RequestParam Long myid, @RequestParam Long friendid){
        Mono<Room> roomMono = roomService.findPrivateRoom(myid, friendid);
        return roomMono.map(room -> {
            RoomResponse data = roomMapper.toDto(room);
            return new ResponseEntity<>(new GlobalResponse<>(data), HttpStatus.CREATED);
        }).switchIfEmpty(Mono.just(new ResponseEntity<>(new GlobalResponse<>(null), HttpStatus.CREATED)));
    }
}
