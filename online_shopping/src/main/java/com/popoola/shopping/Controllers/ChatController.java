package com.popoola.shopping.Controllers;
import com.popoola.shopping.Models.DTOs.UpdateChatDTO;
import com.popoola.shopping.Models.User;
import com.popoola.shopping.Servuces.Implementation.ChatService;
import com.popoola.shopping.Servuces.Implementation.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@CrossOrigin
@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    ChatService chatService;

    @Autowired
    UserService userService;

    @PutMapping("/send-message")
    public ResponseEntity<Object> sendMessage(@RequestBody UpdateChatDTO updateChatDTO, Principal principal){
        User user = userService.findByEmail(principal.getName());

        try{
            chatService.userUpdate(updateChatDTO, user);
           return ResponseEntity.ok(user.getChats());
        }
        catch (Exception ex) {
            ResponseEntity.badRequest().body("Sending message failed");
        }

        return null;
    }

    @PostMapping("/reply-message")
    public ResponseEntity<Object> replyMessage( @RequestBody UpdateChatDTO updateChatDTO, Principal principal,
    BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult);
        }

        return ResponseEntity.ok(chatService.update(updateChatDTO));
    }

    @GetMapping("/get-chat")
    public ResponseEntity<Object> getChat(Principal principal){
        User user = userService.findByEmail(principal.getName());
        return ResponseEntity.ok(user.getChats());
    }

    @GetMapping("/get-chats")
    public ResponseEntity<Object> getChats(){
        return ResponseEntity.ok(chatService.findAll());
    }


}
