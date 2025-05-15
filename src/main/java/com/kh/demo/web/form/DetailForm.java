package com.kh.demo.web.form;

import lombok.Data;

@Data
public class DetailForm {
  private  Long postId;
  private  String title;
  private  String content;
  private  String name;
  private java.time.LocalDateTime dateCreated;
  private java.time.LocalDateTime revisedDate;

}