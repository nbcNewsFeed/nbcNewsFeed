package com.example.nbcnewsfeed.friend.controller;

import com.example.nbcnewsfeed.friend.dto.CreateFriendRequestDto;
import com.example.nbcnewsfeed.friend.dto.DeleteFriendshipDto;
import com.example.nbcnewsfeed.friend.dto.FriendshipListDto;
import com.example.nbcnewsfeed.friend.dto.UpdateFriendRequestDto;
import com.example.nbcnewsfeed.friend.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/friends")
public class FriendController {

    private final FriendService friendService;

    // 친구 요청 전송 컨트롤러
    @PostMapping
    public ResponseEntity<String> sendFriendRequest (
            @RequestBody CreateFriendRequestDto requestDto){ //JWT 로그인 구현 이후에는 @RequestHeader("Authorization") String token 으로 변경

        //JWT에서 사용자 Id 추출
//        Long senderId = extractUserIdFromToken(token);

        if (requestDto.getSenderId().equals(requestDto.getReceiverId())){ //JWT 로그인 구현 이후에 변경
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "senderId와 receiverId가 일치할 수 없습니다. (자기 자신에게 친구요청 불가능) ");
        }

        friendService.sendFriendRequest(requestDto);

        return new ResponseEntity<>("친구 요청이 전송되었습니다.", HttpStatus.OK);

    }

    @PatchMapping
    public ResponseEntity<String> updateFriendRequest (
            @RequestBody UpdateFriendRequestDto requestDto
    ) {
        if (requestDto.getSenderId().equals(requestDto.getReceiverId())){ //JWT 로그인 구현 이후에 변경
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "senderId와 receiverId가 일치할 수 없습니다. ");
        }

        return new ResponseEntity<>(friendService.updateFriendRequest(requestDto), HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<FriendshipListDto>> getFriendshipList (@PathVariable Long userId){
        List<FriendshipListDto> frinendshipList = friendService.getFrinendshipList(userId);
        return new ResponseEntity<>(frinendshipList, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteFriend (@RequestBody DeleteFriendshipDto requestDto){
        friendService.deleteFriend(requestDto);
        return new ResponseEntity<>("삭제 완료", HttpStatus.OK);
    }
}
