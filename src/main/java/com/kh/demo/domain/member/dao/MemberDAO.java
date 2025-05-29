package com.kh.demo.domain.member.dao;

import com.kh.demo.domain.entity.Member;

import java.util.Optional;

public interface MemberDAO {
  Long save(Member member);
  Optional<Member> findByEmail(String email);
  boolean isExistEmail(String email);
}