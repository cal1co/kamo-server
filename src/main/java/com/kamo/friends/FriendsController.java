package com.kamo.friends;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path="api/v1/friend")
@CrossOrigin(origins="http://localhost:3000")
@RequiredArgsConstructor
public class FriendsController {

    private final FriendsService service; 

    @PostMapping("/add")
    public ResponseEntity<String> add(@RequestBody FriendRequest request) {
        try {
            return ResponseEntity.ok(service.add(request));
        } catch (UserNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    };
    
    @PostMapping("/find")
    public ResponseEntity<FindUserResponse> find(@RequestBody FindUserRequest request) {
        return ResponseEntity.ok(service.find(request));
    };

    @GetMapping("/user/{userId}")
    public List<FriendListItem> getFriends(@PathVariable Integer userId) {
        return service.getFriends(userId);
    }
}
