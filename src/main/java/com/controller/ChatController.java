package com.controller;

import org.springframework.web.bind.annotation.*;
import com.dto.ChatRequest;
import com.dto.ChatResponse;
import com.service.ChatService;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "http://localhost:5173")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }


    @PostMapping
    public ChatResponse chat(
            @RequestBody ChatRequest request) {

        String response =
                chatService.generateResponse(
                        request.getMessage(),
                        request.getConversationId()
                );

        return new ChatResponse(response);
    }
}
//Before
//Create prompt
//		↓
//Add user's question
//		↓
//Send to Gemini
//		↓
//Get response
//		↓
//Extract text
//		↓
//Return it

//After
//POST /api/chat
//		│
//		▼
//┌─────────────────┐
//│ ChatController  │
//└────────┬────────┘
//		   │
//		   │ ChatRequest
//		   ▼
//┌─────────────────┐
//│   ChatService   │
//└────────┬────────┘
//		   │
//         ▼
//┌─────────────────┐
//│   ChatClient    │
//└────────┬────────┘
//         │
//         ▼
//    Gemini Model
//         │
//         ▼
//      Response
//         │
//         ▼
//┌─────────────────┐
//│  ChatResponse   │
//└────────┬────────┘
//         │
//         ▼
//       React