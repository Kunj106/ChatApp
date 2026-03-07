package com.kunj.ChatApp.entites;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "rooms")  // Used to map in MongoDB
@NoArgsConstructor
@AllArgsConstructor
public class Room
{
    @Id
    private String id;       //Mongo db : unique identifier
    private String roomId;  // Provided by User
    private List<Message> messages = new ArrayList<>();

    // Getter & Setter

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
