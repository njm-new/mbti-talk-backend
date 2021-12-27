package com.mbtitalkbackend.member.controller;

import com.mbtitalkbackend.member.model.dto.MemberDTO;
import com.mbtitalkbackend.member.model.vo.LoginResponseVO;
import com.mbtitalkbackend.member.exception.KakaoAuthenticationException;
import com.mbtitalkbackend.member.service.MemberService;
import com.mbtitalkbackend.util.JsonWebToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    //Update member info
    @PatchMapping(value = "/change")
    public ResponseEntity<?> update(@RequestBody MemberDTO member) {
        memberService.update(member);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //Check nickname override
    @GetMapping(value = "/nickname/{nickname}")
    public ResponseEntity<?> checkNickname(@PathVariable("nickname") String nickname) {
        if(memberService.existNickname(nickname)){
            System.out.println("tlqkf");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        System.out.println("not tlqkf");
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
