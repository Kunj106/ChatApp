package com.kunj.ChatApp.services;

import com.kunj.ChatApp.entites.Message;
import com.kunj.ChatApp.entites.Room;
import com.kunj.ChatApp.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService
{
    private final RoomRepository roomRepository;

    @Autowired
    public MessageService( RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    // Send / add a message to a room
    public Message sendMessage(String roomId, String sender, String content) {
        Room room = getRoomOrThrow(roomId);
        Message message = new Message(sender, content);
        room.getMessages().add(message);
        roomRepository.save(room);
        return message;
    }

    // Get all messages in a room
    public List<Message> getAllMessages(String roomId) {
        return getRoomOrThrow(roomId).getMessages();
    }

    // Get paginated messages in a room
    public List<Message> getMessages(String roomId, int page, int size) {
        List<Message> messages = getRoomOrThrow(roomId).getMessages();
        int start = Math.max(0, messages.size() - (page + 1) * size);
        int end = Math.min(messages.size(), start + size);
        return messages.subList(start, end);
    }

    // Clear all messages in a room
    public void clearMessages(String roomId) {
        Room room = getRoomOrThrow(roomId);
        room.getMessages().clear();
        roomRepository.save(room);
    }

    private Room getRoomOrThrow(String roomId) {
        Room room = roomRepository.findByRoomId(roomId);
        if (room == null) {
            throw new RuntimeException("Room with ID '" + roomId + "' not found.");
        }
        return room;
    }
}

