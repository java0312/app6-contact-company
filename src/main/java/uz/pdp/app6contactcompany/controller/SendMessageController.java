package uz.pdp.app6contactcompany.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.app6contactcompany.entity.SendMessage;
import uz.pdp.app6contactcompany.payload.ApiResponse;
import uz.pdp.app6contactcompany.payload.SendMessageDto;
import uz.pdp.app6contactcompany.service.SendMessageService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/sendMessage")
public class SendMessageController {

    @Autowired
    SendMessageService sendMessageService;

    @PostMapping
    public HttpEntity<?> addSendMessage(@RequestBody SendMessageDto sendMessageDto){
        ApiResponse apiResponse = sendMessageService.addSendMessage(sendMessageDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }

    //DIRECTOR va NUMBERS_MANAGER
    @GetMapping
    public HttpEntity<?> getAllSendMessage(){
        List<SendMessage> list = sendMessageService.getAllSendMessage();
        return ResponseEntity.ok(list);
    }

    //bitta sim cartaning xabarlari
    //DIRECTOR , NUMBERS_MANAGER va simCard egasi uchun
    @GetMapping("/bySimCardId/{simCardId}")
    public HttpEntity<?> getAllSendMessageBySimCardId(@PathVariable UUID simCardId){
        List<SendMessage> list = sendMessageService.getAllSendMessageBySimCardId(simCardId);
        return ResponseEntity.ok(list);
    }

    /**
     * Director tahrirlay oladi
     */
    @PutMapping("/{id}")
    public HttpEntity<?> editSendMessage(@PathVariable UUID id, @RequestBody SendMessageDto sendMessageDto){
        ApiResponse apiResponse = sendMessageService.editSendMessage(id, sendMessageDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 202 : 409).body(apiResponse);
    }

    /**
     * DIRECTOR O'chiraoladi
     */
    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteSendMassage(@PathVariable UUID id){
        ApiResponse apiResponse = sendMessageService.delete(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 202 : 409).body(apiResponse);
    }

}
