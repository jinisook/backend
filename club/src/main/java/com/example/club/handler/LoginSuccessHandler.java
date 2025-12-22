package com.example.club.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.example.club.dto.MemberDTO;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        // 로그인 성공 후 경로 지정
        // ROLE_USER => /member/profile
        // ROLE_MANAGER => /manager/info
        // ROLE_ADMIN => admin/manage
        // authentication.getName(); // "name": "user1@gmail.com" -> @id == 로그인 폼에서
        // username
        MemberDTO dto = (MemberDTO) authentication.getPrincipal();

        List<String> roleNames = new ArrayList<>();
        dto.getAuthorities().forEach(auth -> {
            roleNames.add(auth.getAuthority());
        });

        if (roleNames.contains("ROLE_ADMIN")) {
            response.sendRedirect("/admin/manage");
            return;
        } else if (roleNames.contains("ROLE_MANAGER")) {
            response.sendRedirect("/manager/info");
            return;
        } else if (roleNames.contains("ROLE_USER")) {
            response.sendRedirect("/member/profile"); // "redirect:/member/profile"
            return;
        }

    }

}
