package com.kh.demo.web;

import com.kh.demo.domain.entity.Member;
import com.kh.demo.domain.member.svc.MemberSVC;
import com.kh.demo.web.login.JoinForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class MemberController {

  private final MemberSVC memberSVC;

  @GetMapping("/join")
  public String joinForm(Model model) {
    model.addAttribute("joinForm", new JoinForm());
    return "login/joinMem";
  }

  @PostMapping("/join")
  public String join(@Valid @ModelAttribute JoinForm joinForm,
                     BindingResult bindingResult) {
    if (!joinForm.getPasswd().equals(joinForm.getPasswdConfirm())) {
      bindingResult.rejectValue("passwdConfirm", "mismatch", "비밀번호가 일치하지 않습니다.");
    }

    if (memberSVC.isExistEmail(joinForm.getEmail())) {
      bindingResult.rejectValue("email", "duplicate", "이미 등록된 이메일입니다.");
    }

    if (bindingResult.hasErrors()) {
      return "login/joinMem";
    }

    Member member = new Member();
    member.setEmail(joinForm.getEmail());
    member.setPasswd(joinForm.getPasswd());
    member.setTel(joinForm.getTel());
    member.setNickname(joinForm.getNickname());
    member.setGender(joinForm.getGender());
    member.setHobby(joinForm.getHobby());
    member.setRegion(joinForm.getRegion());
    member.setGubun("M0101");

    memberSVC.join(member);

    return "redirect:/login";
  }

  @ResponseBody
  @GetMapping("/join/emailCheck")
  public boolean emailCheck(@RequestParam("email") String email) {
    return !memberSVC.isExistEmail(email);
  }
}