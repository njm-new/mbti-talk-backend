package com.mbtitalkbackend.member.controller;

import com.mbtitalkbackend.member.model.dto.MemberDTO;
import com.mbtitalkbackend.member.model.vo.GetInfoResponseVO;
import com.mbtitalkbackend.member.model.vo.LoginResponseVO;
import com.mbtitalkbackend.member.exception.KakaoAuthenticationException;
import com.mbtitalkbackend.member.service.MemberService;
import com.mbtitalkbackend.util.JsonWebToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping(value = "/api/member")
@CrossOrigin(origins = "http://localhost:3000")
public class MemberController {
    private final JsonWebToken jsonWebToken;
    private final MemberService memberService;

    public MemberController(JsonWebToken jsonWebToken, MemberService memberService) {
        this.jsonWebToken = jsonWebToken;
        this.memberService = memberService;
    }

    //Login
    @PostMapping(value = "/login", produces = "application/json")
    public ResponseEntity<?> login(@RequestHeader("code") String accessCode) {
        MemberDTO member;

        try {
            member = memberService.login(accessCode);
        } catch (KakaoAuthenticationException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        String jwt = jsonWebToken.create(member.getMemberId());

        return new ResponseEntity<>(new LoginResponseVO(jwt, member), HttpStatus.OK);
    }

    @PostMapping(value = "/mbti")
    public ResponseEntity<?> updateMemberMbti(@RequestHeader("Authorization") String jwt,
                                              @RequestBody HashMap<String, String> payload) {
        int memberId = (int) jsonWebToken.validate(jwt).get("memberId");
        String mbti = payload.get("mbti");

        memberService.update(memberId, mbti);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //Update member info
    @PatchMapping(value = "/change")
    public ResponseEntity<?> updateMemberInfo(@RequestBody MemberDTO member) {
        //If nickname is exist in DB
        if (memberService.existNickname(member.getMemberId(), member.getNickname())) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        
        memberService.update(member);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //Check nickname override
    @GetMapping(value = "/nickname/{nickname}")
    public ResponseEntity<?> checkNickname(@RequestHeader("Authorization") String jwt, @PathVariable("nickname") String nickname) {
        int memberId = (int) jsonWebToken.validate(jwt).get("memberId");

        //If nickname is exist in DB
        if (memberService.existNickname(memberId, nickname)) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        memberService.updateNickname(memberId, nickname);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/info/{memberId}")
    public ResponseEntity<?> getMemberInfo(@RequestHeader("Authorization") String jwt, @PathVariable("memberId") int memberId) {
        MemberDTO member = memberService.getInfo(memberId);

        if (member == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new GetInfoResponseVO(member), HttpStatus.OK);
    }
}
