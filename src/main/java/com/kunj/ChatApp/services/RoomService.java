package com.kunj.ChatApp.services;

import com.kunj.ChatApp.entites.Message;
import com.kunj.ChatApp.entites.Room;
import com.kunj.ChatApp.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService
{
    private final RoomRepository roomRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    // Create a new room
    public Room createRoom(String roomId) {
        if (roomExists(roomId)) {
            throw new RuntimeException("Room with ID '" + roomId + "' already exists.");
        }
        Room room = new Room();
        room.setRoomId(roomId);
        return roomRepository.save(room);
    }

    // Get a room by roomId
    public Room getRoomById(String roomId) {
        Room room = roomRepository.findByRoomId(roomId);
        if (room == null) {
            throw new RuntimeException("Room with ID '" + roomId + "' not found.");
        }
        return room;
    }

    // Get all rooms
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    // Delete a room by roomId
    public void deleteRoom(String roomId) {
        Room room = getRoomById(roomId);
        roomRepository.delete(room);
    }

    // Check if a room exists
    public boolean roomExists(String roomId) {
        return roomRepository.findByRoomId(roomId) != null;
    }
}
