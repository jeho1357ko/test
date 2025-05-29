package com.kh.demo.domain.member.svc;

import com.kh.demo.domain.entity.Member;
import com.kh.demo.domain.member.dao.MemberDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberSVCImpl implements MemberSVC {

  private final MemberDAO memberDAO;

  @Override
  public Long join(Member member) {
    return memberDAO.save(member);
  }

  @Override
  public Optional<Member> login(String email, String passwd) {
    return memberDAO.findByEmail(email)
        .filter(m -> m.getPasswd().equals(passwd));
  }

  @Override
  public boolean isExistEmail(String email) {
    return memberDAO.isExistEmail(email);
  }
}