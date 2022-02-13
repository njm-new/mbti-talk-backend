package com.mbtitalkbackend.member.controller;

import com.mbtitalkbackend.common.ApiResponse;
import com.mbtitalkbackend.member.model.dto.MemberDTO;
import com.mbtitalkbackend.member.model.vo.LoginRequestVO;
import com.mbtitalkbackend.member.model.vo.Member;
import com.mbtitalkbackend.member.model.vo.LoginResponseVO;
import com.mbtitalkbackend.member.exception.KakaoAuthenticationException;
import com.mbtitalkbackend.member.model.vo.RefreshTokenVO;
import com.mbtitalkbackend.member.service.MemberService;
import com.mbtitalkbackend.util.authrization.AccessTokenManager;
import com.mbtitalkbackend.util.authrization.Authorization;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/members")
@RequiredArgsConstructor
public class MemberController {
    private final AccessTokenManager accessTokenManager;
    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<ApiResponse> login(@RequestBody LoginRequestVO loginRequestVO) {
        try {
            MemberDTO member = memberService.login(loginRequestVO);
            final String accessToken = accessTokenManager.create(member.getMemberId());

            return new ResponseEntity<>(ApiResponse.success(LoginResponseVO.of(accessToken, member)), HttpStatus.OK);
        } catch (KakaoAuthenticationException e) {
            return new ResponseEntity<>(ApiResponse.fail(e.getMessage()), HttpStatus.FORBIDDEN);
        } catch (IllegalAccessException e) {
            return new ResponseEntity<>(ApiResponse.fail(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @Authorization
    @GetMapping(value = "/{memberId}")
    public ResponseEntity<ApiResponse> getMemberInfo(@PathVariable("memberId") String memberId) {
        MemberDTO member = memberService.getInfo(memberId);

        if (member == null) {
            return new ResponseEntity<>(ApiResponse.fail("memberId가 존재하지 않습니다."), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(ApiResponse.success(member), HttpStatus.OK);
    }

    //Update member info
    @Authorization
    @PatchMapping(value = "/{memberId}")
    public ResponseEntity<ApiResponse> updateMemberInfo(Member member, @RequestBody MemberDTO memberDto, @PathVariable("memberId") String memberId) {
        //본인 검증
        if (!memberService.checkAuth(memberId, member.getMemberId()) || !memberService.checkAuth(memberId, memberDto.getMemberId())) {
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
    public ResponseEntity<ApiResponse> refreshAccessToken(@RequestHeader("Authorization") String token, @PathVariable("memberId") String memberId) {
        //토큰의 memberId가 요청한 memberId 같으면 refresh
        if (!memberService.checkAuth(memberService.getMemberIdFromAccessToken(token), memberId)) {
            return new ResponseEntity<>(ApiResponse.fail("요청자 정보가 토큰과 불일치합니다."), HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(ApiResponse.success(RefreshTokenVO.from(accessTokenManager.create(memberId))), HttpStatus.OK);
    }

    /* Local 테스트용 임시 */
    @PostMapping("/local")
    public ResponseEntity<ApiResponse> loginForLocal(@RequestBody LoginRequestVO loginRequestVO) {
        try {
            MemberDTO member = memberService.loginForLocal(loginRequestVO);
            final String accessToken = accessTokenManager.create(member.getMemberId());

            return new ResponseEntity<>(ApiResponse.success(LoginResponseVO.of(accessToken, member)), HttpStatus.OK);
        } catch (KakaoAuthenticationException e) {
            return new ResponseEntity<>(ApiResponse.fail(e.getMessage()), HttpStatus.FORBIDDEN);
        } catch (IllegalAccessException e) {
            return new ResponseEntity<>(ApiResponse.fail(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
