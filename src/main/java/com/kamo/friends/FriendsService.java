package com.kamo.friends;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.kamo.kamouser.KamoUser;
import com.kamo.kamouser.KamoUserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FriendsService {
    
    private final KamoUserRepository userRepository;
    private final FriendRepository friendRepository;

    public String add(FriendRequest request) throws UserNotFoundException {
        KamoUser user1 = userRepository.findById(request.getUserId())
          .orElseThrow( () -> new UserNotFoundException(request.getUserId()) );
        KamoUser user2 = userRepository.findById(request.getRecipientId())
          .orElseThrow( () -> new UserNotFoundException(request.getRecipientId()) );

        KamoFriend friend = new KamoFriend();
        friend.setUser1(user1);
        friend.setUser2(user2);
        friend.setCreatedAt(LocalDateTime.now());
        friendRepository.save(friend);

        return "successfully added friend";
    }

    public FindUserResponse find(FindUserRequest request) {
        var user = userRepository.findByUsername(request.getUsername())
            .orElseThrow();
        return FindUserResponse.builder()
            .username(user.getUsername())
            .userId(user.getId())
            .build();
    }

    public List<FriendListItem> getFriends(Integer userId) {
        KamoUser user = userRepository.findById(userId).orElse(null);
      
        if (user == null) {
          return Collections.emptyList();
        }
      
        List<KamoFriend> friends = friendRepository.findByUser1OrUser2(user, user);
        List<FriendListItem> friendUsers = new ArrayList<>();
        for (KamoFriend friend : friends) {
          if (friend.getUser1().getId().equals(user.getId())) {
            FriendListItem listItem = new FriendListItem();
            listItem.setId(friend.getUser2().getId());
            listItem.setUsername(friend.getUser2().getUsername());
            friendUsers.add(listItem);
          } else {
            FriendListItem listItem = new FriendListItem();
            listItem.setId(friend.getUser1().getId());
            listItem.setUsername(friend.getUser1().getUsername());
            friendUsers.add(listItem);
          }
        }
        return friendUsers;
      }
}
