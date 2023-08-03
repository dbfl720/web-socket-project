package com.smartChart.controller;


import com.smartChart.storage.UserStorage;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@CrossOrigin   //to allow requests from other server  - called CORS policy
public class UsersController {

    @GetMapping("/registration/{userName}")
    public ResponseEntity<Void> register(@PathVariable String userName) {
        System.out.println("handling register user request: " + userName);
        try {
            UserStorage.getInstance().setUser(userName);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }



    @GetMapping("/fetchAllUsers")
    public Set<String> fetchAll() {  // set : 순서를 유지 안함, 데이터 중복 혀용 안함
        return UserStorage.getInstance().getUsers();
    }

}
