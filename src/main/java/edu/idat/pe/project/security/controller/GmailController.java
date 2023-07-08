package edu.idat.pe.project.security.controller;

import edu.idat.pe.project.security.dto.MessageDto;
import edu.idat.pe.project.security.dto.UserModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/gmail")
@CrossOrigin
public class GmailController {

    @GetMapping("/hello")
    public ResponseEntity<MessageDto> hello() {
        UserModel userModel = (UserModel) SecurityContextHolder.getContext().getAuthentication();
        String message = "hello " + userModel.getName() + " from spring boot";
        String picture = userModel.getPicture();
        MessageDto dto = new MessageDto(message, picture);
        return ResponseEntity.ok(dto);
    }
}