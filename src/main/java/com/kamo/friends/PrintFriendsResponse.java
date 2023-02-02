package com.kamo.friends;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PrintFriendsResponse {
    // private String response;
    private List<FriendListItem> friendList;
    // private Integer listLength;
}
