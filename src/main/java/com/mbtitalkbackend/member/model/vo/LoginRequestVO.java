package com.mbtitalkbackend.member.model.vo;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginRequestVO {
    String snsType;
    String snsCode; // web(fe)의 경우 code를 받고 server(backend)에서 accessToken을 획득해야함
    String snsAccessToken; //app(android,ios)의 경우 accessToken을 바로 획득 가능
}
