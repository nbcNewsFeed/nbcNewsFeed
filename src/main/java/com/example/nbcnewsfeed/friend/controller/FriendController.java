package com.example.nbcnewsfeed.friend.controller;

import com.example.nbcnewsfeed.friend.service.FriendService;
import com.example.nbcnewsfeed.user.UserClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/friends")
public class FriendController {

    private final FriendService friendService;

    // 친구 요청 전송 컨트롤러
    @PostMapping("/{receiverId}")
    public ResponseEntity<String> sendFriendRequest (
            @PathVariable Long receiverId,
            @RequestParam Long senderId){ //JWT 로그인 구현 이후에는 @RequestHeader("Authorization") String token 으로 변경

        //JWT에서 사용자 Id 추출
//        Long senderId = extractUserIdFromToken(token);

        if (senderId == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "senderId is null");
        }

        if (senderId.equals(receiverId)){ //JWT 로그인 구현 이후에 변경
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "자기 자신에게는 친구 요청이 불가능합니다. ");
        }

        friendService.sendFriendRequest(senderId, receiverId);

        return new ResponseEntity<>("친구 요청이 전송되었습니다.", HttpStatus.OK);

    }
}
