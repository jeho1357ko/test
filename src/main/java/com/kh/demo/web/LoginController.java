package com.kh.demo.web;

import com.kh.demo.domain.entity.Member;
import com.kh.demo.domain.member.svc.MemberSVC;
import com.kh.demo.web.login.SessionConst;
import com.kh.demo.web.login.SessionConst;
import com.kh.demo.web.login.LoginForm;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class LoginController {

  private final MemberSVC memberSVC;

  @GetMapping("/login")
  public String loginForm(Model model) {
    model.addAttribute("loginForm", new LoginForm());
    return "login/login";
  }

  @PostMapping("/login")
  public String login(@Valid @ModelAttribute LoginForm loginForm,
                      BindingResult bindingResult,
                      HttpServletRequest request) {

    if (bindingResult.hasErrors()) {
      return "login/login";
    }

    return memberSVC.login(loginForm.getEmail(), loginForm.getPasswd())
        .map(member -> {
          HttpSession session = request.getSession();
          session.setMaxInactiveInterval(1800); // 30분
          session.setAttribute(SessionConst.LOGIN_MEMBER, member);
          return "redirect:/posts";
        })
        .orElseGet(() -> {
          bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
          return "login/login";
        });
  }

  @PostMapping("/logout")
  public String logout(HttpServletRequest request) {
    HttpSession session = request.getSession(false);
    if (session != null) {
      session.invalidate();
    }
    return "redirect:/posts";
  }
}