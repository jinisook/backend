package com.example.club.service;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.example.club.dto.MemberDTO;
import com.example.club.entity.Member;
import com.example.club.entity.constant.ClubMemberRole;
import com.example.club.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@Log4j2
@Service
public class ClubOauth2Service extends DefaultOAuth2UserService {

    // 소셜로그인 정보를 회원 가입 처리
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("useRequest {} ", userRequest);
        String clientName = userRequest.getClientRegistration().getClientName();
        // log.info("client{} ", clientName); // Google
        // log.info(userRequest.getAdditionalParameters());

        OAuth2User oAuth2User = super.loadUser(userRequest);
        // log.info("========================");
        // oAuth2User.getAttributes().forEach((k, v) -> {
        // log.info(k + ":" + v);
        // });

        // 1. google 이 넘겨준 정보에서 이메일 추출
        String email = null;
        if (clientName.equals("Google")) {
            email = oAuth2User.getAttribute("email");
        }

        // 2. 테이블에 동일한 이메일이 있는지 확인하고 없는 경우 회원가입 / 존재하는 경우 member 가져오기
        Member member = saveSocialMember(email);

        // 3. MemberDTO로 변경 후 리턴
        MemberDTO dto = new MemberDTO(member.getEmail(), member.getPassword(), member.isFromSocial(),
                member.getRoles()
                        .stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                        .collect(Collectors.toSet()),
                oAuth2User.getAttributes());

        dto.setName(member.getName());

        return oAuth2User;
    }

    private Member saveSocialMember(String email) {
        Optional<Member> result = memberRepository.findByEmailAndFromSocial(email, false);

        if (result.isPresent()) {
            return result.get();
        }

        Member member = Member.builder()
                .email(email)
                .name(email)
                .password(passwordEncoder.encode("1111"))
                .fromSocial(true)
                .build();
        member.addMemberRole(ClubMemberRole.USER);

        memberRepository.save(member);

        return member;

    }
}
