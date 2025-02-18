package com.example.nbcnewsfeed.friend.controller;

import com.example.nbcnewsfeed.friend.dto.CreateFriendRequestDto;
import com.example.nbcnewsfeed.friend.dto.DeleteFriendshipDto;
import com.example.nbcnewsfeed.friend.dto.FriendshipListDto;
import com.example.nbcnewsfeed.friend.dto.UpdateFriendRequestDto;
import com.example.nbcnewsfeed.friend.service.FriendService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/friends")
@Tag(name="친구 관리 API", description = "친구를 관리하는 API 입니다.")
public class FriendController {

    private final FriendService friendService;

    // 친구 요청 전송 컨트롤러
    @PostMapping
    @Operation(summary="친구 추가 요청 기능", description = "senderId와 receiverId를 통해 친구 요청을 전송합니다.")
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
    @Operation(summary = "친구 수락 및 거절 기능", description = "친구 요청을 수락하거나 거절할 수 있습니다. 친구요청 테이블의 friendStatus가 변경됩니다.")
    public ResponseEntity<String> updateFriendRequest (
            @RequestBody UpdateFriendRequestDto requestDto
    ) {
        if (requestDto.getSenderId().equals(requestDto.getReceiverId())){ //JWT 로그인 구현 이후에 변경
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "senderId와 receiverId가 일치할 수 없습니다. ");
        }

        return new ResponseEntity<>(friendService.updateFriendRequest(requestDto), HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    @Operation(summary = "친구 목록 조회 기능", description = "해당 id 사용자의 친구 관계를 전체 조회합니다.")
    public ResponseEntity<List<FriendshipListDto>> getFriendshipList (@PathVariable Long userId){
        List<FriendshipListDto> frinendshipList = friendService.getFriendshipList(userId);
        return new ResponseEntity<>(frinendshipList, HttpStatus.OK);
    }

    @DeleteMapping
    @Operation(summary = "친구 삭제 기능 (1명)", description = "해당 id 사용자의 친구 중 한 명을 삭제할 수 있습니다.")
    public ResponseEntity<String> deleteFriend (@RequestBody DeleteFriendshipDto requestDto){
        if (requestDto.getUser1Id().equals(requestDto.getUser2Id())){ //JWT 로그인 구현 이후에 변경
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "자기 자신에게는 친구 삭제 요청이 불가능합니다.");
        }

        friendService.deleteFriend(requestDto);
        return new ResponseEntity<>("삭제 완료", HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    @Operation(summary = "친구 전체 삭제 기능", description = "해당 id 사용자의 친구 관계를 전체 삭제할 수 있습니다.")
    public ResponseEntity<String> deleteAllFriendByUserId(@PathVariable Long userId){
        friendService.deleteAllFriendByUserId(userId);
        return new ResponseEntity<>("해당 아이디의 친구관계 모두 삭제 완료", HttpStatus.OK);
    }
}