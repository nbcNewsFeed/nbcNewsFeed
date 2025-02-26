package com.example.nbcnewsfeed.friend.controller;

import com.example.nbcnewsfeed.friend.dto.*;
import com.example.nbcnewsfeed.friend.service.FriendService;
import com.example.nbcnewsfeed.user.dto.UserResponseDto;
import com.example.nbcnewsfeed.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/friends")
@Tag(name="친구 관리 API", description = "친구를 관리하는 API 입니다.")
public class FriendController {

    private final FriendService friendService;

    // 친구 요청 전송 컨트롤러
    @PostMapping ("/request")
    @Operation(summary="친구 추가 요청 기능", description = "senderId와 receiverId를 통해 친구 요청을 전송합니다.")
    public ResponseEntity<String> sendFriendRequest (
            @RequestParam Long receiverId,
            HttpServletRequest request
    ){
        Long loginId = Long.parseLong(String.valueOf(request.getAttribute("userId")));

        if (loginId.equals(receiverId)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "자기 자신에게 친구요청 불가능합니다.");
        }

        friendService.sendFriendRequest(loginId, receiverId);

        return new ResponseEntity<>("친구 요청이 전송되었습니다.", HttpStatus.OK);

    }

    @DeleteMapping("/request-cancel")
    @Operation(summary="친구 요청 취소 기능", description = "친구 요청을 보냈던 내역을 취소할 수 있씁니다.")
    public ResponseEntity<String> cancelFriendRequest (
            @RequestParam Long cancelId,
            HttpServletRequest request
    ){
        Long loginId = Long.parseLong(String.valueOf(request.getAttribute("userId")));

        if (loginId.equals(cancelId)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "오류 : 현재 로그인된 Id와 친구요청 취소Id가 일치합니다.");
        }

        friendService.cancelFriendRequest(loginId, cancelId);

        return new ResponseEntity<>("친구 요청 취소 성공", HttpStatus.OK);

    }

    @PatchMapping("/response")
    @Operation(summary = "친구 수락 및 거절 기능", description = "친구 요청을 수락하거나 거절할 수 있습니다. 친구요청 테이블의 friendStatus가 변경됩니다.")
    public ResponseEntity<String> updateFriendRequest (
            @RequestParam Long requesterId,
            @RequestBody UpdateFriendRequestDto requestDto,
            HttpServletRequest request // 로그인된 사용자
    ) {
        Long loginId = Long.parseLong(String.valueOf(request.getAttribute("userId"))); //로그인된 사용자의 Id

        if (loginId.equals(requesterId)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "오류 : 현재 로그인된 Id와 친구요청Id가 일치합니다.");
        }

        return new ResponseEntity<>(friendService.updateFriendRequest(loginId, requesterId, requestDto), HttpStatus.OK);
    }


    @GetMapping("/request-list")
    @Operation(summary ="친구 요청 목록 조회 기능", description = "해당 id 사용자가 요청했거나/요청받은 목록을 조회합니다.")
    public ResponseEntity<List<FriendRequestListDto>> getFriendRequests (
            HttpServletRequest request
    ){
        Long loginId = Long.parseLong(String.valueOf(request.getAttribute("userId")));

        List<FriendRequestListDto> friendRequestsList = friendService.getFriendRequests(loginId);

        return new ResponseEntity<>(friendRequestsList, HttpStatus.OK);
    }


    @GetMapping("/friend-list")
    @Operation(summary = "친구 목록 조회 기능", description = "로그인 된 사용자의 친구 관계를 전체 조회합니다.")
    public ResponseEntity<List<UserResponseDto>> getFriendshipList (
            HttpServletRequest request
    ){
        Long loginId = Long.parseLong(String.valueOf(request.getAttribute("userId")));

        List<User> friendshipList = friendService.getFriendshipList(loginId);

        List<UserResponseDto> friendDtos = friendshipList.stream().map(UserResponseDto::todto).toList();
        return new ResponseEntity<>(friendDtos, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "친구 삭제 기능 (1명)", description = "로그인 된 사용자의 친구 중 한 명을 삭제할 수 있습니다.")
    public ResponseEntity<String> deleteFriend (
            @RequestParam Long deleteId, // 삭제하고 싶은 친구(사용자 Id)
            HttpServletRequest request // 로그인된 사용자
    ){
        Long loginId = Long.parseLong(String.valueOf(request.getAttribute("userId")));

        if (loginId.equals(deleteId)){ //JWT 로그인 구현 이후에 변경
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "자기 자신에게는 친구 삭제가 불가능합니다.");
        }

        friendService.deleteFriend(loginId, deleteId);
        return new ResponseEntity<>("삭제 완료", HttpStatus.OK);
    }

    @DeleteMapping("/delete-all")
    @Operation(summary = "친구 전체 삭제 기능", description = "로그인 된 사용자의 친구 관계를 전체 삭제할 수 있습니다.")
    public ResponseEntity<String> deleteAllFriendByUserId(HttpServletRequest request){
        Long loginId = Long.parseLong(String.valueOf(request.getAttribute("userId")));

        friendService.deleteAllFriendByUserId(loginId);
        return new ResponseEntity<>("로그인 사용자의 친구관계 전체 삭제 완료", HttpStatus.OK);
    }
}