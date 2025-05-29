package com.kh.demo.domain.comment.svc;

import com.kh.demo.domain.entity.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentSVC {
  //등록
  Long save(Long id ,Comment comment);

  //수정
  Long update(Long id ,Comment comment);

  //삭제
  String delete (Long id);

  //목록
  List<Comment> findAll(Integer pid ,Integer pageNo , Integer numOfRows);

  //총건수
  int getTotalCount(Integer pid);

  //조회
  Optional<Comment> detail(Long id);

}
