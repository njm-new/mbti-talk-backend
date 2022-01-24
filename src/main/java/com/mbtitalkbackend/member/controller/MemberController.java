package com.mbtitalkbackend.member.controller;

import com.mbtitalkbackend.common.ApiResponse;
import com.mbtitalkbackend.member.model.dto.MemberDTO;
import com.mbtitalkbackend.member.model.vo.LoginRequestVO;
import com.mbtitalkbackend.member.model.vo.Member;
import com.mbtitalkbackend.member.model.vo.LoginResponseVO;
import com.mbtitalkbackend.member.exception.KakaoAuthenticationException;
import com.mbtitalkbackend.member.model.vo.RefreshTokenVO;
import com.mbtitalkbackend.member.service.MemberService;
import com.mbtitalkbackend.util.authrization.AccessToken;
import com.mbtitalkbackend.util.authrization.Authorization;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/members")
@CrossOrigin(origins = "http://localhost:3000")
public class MemberController {
    private final AccessToken accessToken;
    private final MemberService memberService;

    public MemberController(AccessToken accessToken, MemberService memberService) {
        this.accessToken = accessToken;
        this.memberService = memberService;
    }

    //Login
    @PostMapping(produces = "application/json")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginRequestVO loginRequestVO) {
        MemberDTO member;

        try {
            member = memberService.login(loginRequestVO);
        } catch (KakaoAuthenticationException e) {
            return new ResponseEntity<>(ApiResponse.fail(e.getMessage()), HttpStatus.FORBIDDEN);
        } catch (IllegalAccessException e) {
            return new ResponseEntity<>(ApiResponse.fail(e.getMessage()), HttpStatus.BAD_REQUEST);
        }

        String jwt = accessToken.create(member.getMemberId());

        return new ResponseEntity<>(ApiResponse.success(LoginResponseVO.of(jwt, member)), HttpStatus.OK);
    }

    @Authorization
    @GetMapping(value = "/{memberId}")
    public ResponseEntity<ApiResponse> getMemberInfo(@PathVariable("memberId") int memberId) {
        MemberDTO member = memberService.getInfo(memberId);

        if (member == null) {
            return new ResponseEntity<>(ApiResponse.fail("memberId가 존재하지 않습니다."), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(ApiResponse.success(member), HttpStatus.OK);
    }

    //Update member info
    @Authorization
    @PatchMapping(value = "/{memberId}")
    public ResponseEntity<ApiResponse> updateMemberInfo(Member member, @RequestBody MemberDTO memberDto, @PathVariable("memberId") int memberId) {
        //본인 검증
        if (memberId != member.getMemberId() || member.getMemberId() != memberDto.getMemberId()) {
            return new ResponseEntity<>(ApiResponse.fail("자신의 정보만 수정할 수 있습니다."), HttpStatus.FORBIDDEN);
        }

        //If nickname is exist in DB
        if (memberService.existNickname(memberDto.getMemberId(), memberDto.getNickname())) {
            return new ResponseEntity<>(ApiResponse.fail("해당 닉네임이 이미 존재합니다."), HttpStatus.CONFLICT);
        }

        memberService.update(memberDto);
        return new ResponseEntity<>(ApiResponse.success(), HttpStatus.OK);
    }

    //Check nickname override
    @Authorization
    @GetMapping(value = "/nickname/{nickname}")
    public ResponseEntity<ApiResponse> checkNickname(Member member, @PathVariable("nickname") String nickname) {
        //If nickname is exist in DB
        if (memberService.existNickname(member.getMemberId(), nickname)) {
            return new ResponseEntity<>(ApiResponse.fail("해당 닉네임이 이미 존재합니다."), HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(ApiResponse.success(), HttpStatus.OK);
    }

    //Refresh access token
    @GetMapping(value = "/{memberId}/access-token")
    public ResponseEntity<ApiResponse> refreshAccessToken(@RequestHeader("Authorization") String token, @PathVariable("memberId") int memberId) {
        //토큰의 memberId가 요청한 memberId 같으면 refresh
        if (memberService.getMemberIdFromAccessToken(token) != memberId) {
            return new ResponseEntity<>(ApiResponse.fail("요청자 정보가 토큰과 불일치합니다."), HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(ApiResponse.success(RefreshTokenVO.from(accessToken.create(memberId))), HttpStatus.OK);
    }
}
