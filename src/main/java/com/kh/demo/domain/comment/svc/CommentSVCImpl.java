package com.kh.demo.domain.comment.svc;

import com.kh.demo.domain.comment.dao.CommentDAO;
import com.kh.demo.domain.entity.Comment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentSVCImpl implements CommentSVC{

  final private CommentDAO commentDAO;

  //등록
  @Override
  public Long save(Long id ,Comment comment) {

    return commentDAO.save(id ,comment);
  }


  //수정
  @Override
  public Long update(Long id, Comment comment) {
    return commentDAO.update(id, comment);
  }

  // 삭제
  @Override
  public String delete(Long id) {
    return commentDAO.delete(id);
  }

  //목록
  @Override
  public List<Comment> findAll(Integer pid,Integer pageNo , Integer numOfRows) {
    return commentDAO.findAll(pid,pageNo , numOfRows);
  }

  @Override
  public int getTotalCount(Integer pid) {
    return commentDAO.getTotalCount(pid);
  }

  //조회

  @Override
  public Optional<Comment> detail(Long id) {
    return commentDAO.detail(id);
  }
}
