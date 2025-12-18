package com.example.club.dto;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Data;

@Data
public class MemberDTO extends User {

    // member entity 정보 + 인증정보
    private String email;

    private String password;

    private String name;

    private boolean fromSocial;

    public MemberDTO(String username, String password, boolean fromSocial,
            Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.fromSocial = fromSocial;
        this.email = username;
    }
    /*
     * custom login 처리
     * 1. login Get 작성
     * - 아이디에 해당하는 요소의 이름 : usernamne
     * - 비밀번호에 해당하는 요소의 이름 : password
     * 2. login Post 작성 안함
     * 3. service 작성 : UserDetailsService 구현 클래스 작성
     * - 리턴값은 무조건 UserDetails 리턴
     * UserDetails
     * | 상속
     * |
     * User
     * | 상속
     * |
     * MemberDTO
     * -> service에서 return new MemberDTO(username, username, false, null);
     */
}
