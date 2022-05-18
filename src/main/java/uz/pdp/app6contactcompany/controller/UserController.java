package uz.pdp.app6contactcompany.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.app6contactcompany.entity.User;
import uz.pdp.app6contactcompany.payload.ApiResponse;
import uz.pdp.app6contactcompany.payload.RoleDto;
import uz.pdp.app6contactcompany.payload.UserDto;
import uz.pdp.app6contactcompany.service.UserService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    /*
    * Tizimdagi foydalanuvchilarga lavozim berish
    * */
    @PostMapping("/addRole")
    public HttpEntity<?> setRolesToUser(@RequestBody RoleDto roleDto){
        ApiResponse apiResponse = userService.setRolesToUser(roleDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }

    //Director va managerlar uchun
    @GetMapping
    public HttpEntity<?> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    //filial rahbari uchun
    @GetMapping("/forBranchLeader/{branchId}")
    public HttpEntity<?> getAllUsersByBranchId(@PathVariable Integer branchId){
        List<User> users = userService.getAllUsersByBranchId(branchId);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getUser(@PathVariable UUID id){
        User user = userService.getUser(id);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> editUser(@PathVariable UUID id, @RequestBody UserDto userDto){
        ApiResponse apiResponse = userService.editUser(id, userDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 202 : 409).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteUser(@PathVariable UUID id){
        ApiResponse apiResponse = userService.deleteUser(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 202 : 409).body(apiResponse);
    }


}
