package com.kh.demo.domain.entity;

import lombok.Data;

@Data
public class Post {

  private  Long postId;
  private  String title;
  private  String content;
  private  String name;
  private java.time.LocalDateTime dateCreated;
  private java.time.LocalDateTime revisedDate;

}
