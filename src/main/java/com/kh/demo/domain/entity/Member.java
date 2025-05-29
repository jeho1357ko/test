package com.kh.demo.domain.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Member {
  private Long memberId;
  private String email;
  private String passwd;
  private String tel;
  private String nickname;
  private String gender;
  private String hobby;
  private String region;
  private String gubun;
  private byte[] pic;
  private LocalDateTime cdate;
  private LocalDateTime udate;
}