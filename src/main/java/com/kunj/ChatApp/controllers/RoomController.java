package com.kunj.ChatApp.controllers;

import com.kunj.ChatApp.entites.Message;
import com.kunj.ChatApp.entites.Room;
import com.kunj.ChatApp.repositories.RoomRepository;
import com.kunj.ChatApp.services.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomController
{
    private RoomRepository roomRepository;
    private final MessageService messageService;



    public RoomController(MessageService messageService, RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
        this.messageService = messageService;
    }

    // Create room
    @PostMapping
    public ResponseEntity<?> createRoom(@RequestBody String roomId) {


        roomId = roomId.trim();

        if (roomRepository.findByRoomId(roomId) != null) {
            return ResponseEntity.badRequest().body("Room already exists!");
        }
        Room room = new Room();
        room.setRoomId(roomId);
        Room savedRoom = roomRepository.save(room);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRoom);
    }

    // Join / get room
    @GetMapping("/{roomId}")
    public ResponseEntity<?> joinRoom(@PathVariable String roomId) {
        Room room = roomRepository.findByRoomId(roomId);
        if (room == null) {
            return ResponseEntity.badRequest().body("Room not found!");
        }
        return ResponseEntity.ok(room);
    }

    // Get paginated messages for a room
    @GetMapping("/{roomId}/messages")
    public ResponseEntity<List<Message>> getMessages(
            @PathVariable String roomId,
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "size", defaultValue = "20", required = false) int size
    ) {
        Room room = roomRepository.findByRoomId(roomId);
        if (room == null) {
            return ResponseEntity.badRequest().build();
        }
        List<Message> messages = messageService.getMessages(roomId, page, size);
        return ResponseEntity.ok(messages);
    }
}
