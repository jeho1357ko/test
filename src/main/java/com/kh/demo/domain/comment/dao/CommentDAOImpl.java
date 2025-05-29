package com.kh.demo.domain.comment.dao;

import com.kh.demo.domain.entity.Comment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@Slf4j
@RequiredArgsConstructor
public class CommentDAOImpl  implements CommentDAO {

  final private NamedParameterJdbcTemplate template;

  //등록
  @Override
  public Long save(Long id ,Comment comment) {
    StringBuffer sql = new StringBuffer();
    sql.append(" INSERT INTO comments (name,content,post_id) ");
    sql.append("      VALUES (:name,:content,:id) ");

    SqlParameterSource param = new MapSqlParameterSource()
        .addValue("name",comment.getName())
        .addValue("content",comment.getContent())
        .addValue("id",id);

    KeyHolder keyHolder = new GeneratedKeyHolder();

    int row = template.update(sql.toString(), param, keyHolder, new String[]{"comment_id"});

    Number commentId = (Number)keyHolder.getKeys().get("comment_id");

    long cid = commentId.longValue();


    return cid;
  }

  //수정
  @Override
  public Long update(Long id, Comment comment) {
    StringBuffer sql = new StringBuffer();
    sql.append(" UPDATE comments ");
    sql.append("    SET name = :name, content = :content, created_time = systimestamp ");
    sql.append("  WHERE comment_id = :comment_id ");

    SqlParameterSource param = new MapSqlParameterSource()
        .addValue("name",comment.getName())
        .addValue("content",comment.getContent())
        .addValue("comment_id",id);
    long row = template.update(sql.toString(),param);

    return row;
  }


  // 삭제
  @Override
  public String delete(Long id) {
    StringBuffer sql = new StringBuffer();
    sql.append(" DELETE FROM comments ");
    sql.append("  WHERE comment_id = :comment_id ");

    SqlParameterSource param = new MapSqlParameterSource()
        .addValue("comment_id",id);
    int row = template.update(sql.toString(), param);

    if(row == 1){
      return "댓글 삭제 완료";
    }else {
      return "이미 삭제된 댓글 이거나 없는 댓글입니다";
    }

  }


  RowMapper<Comment> commentRowMapper(){
    return (rs, rowNum) -> {
      Comment comment = new Comment();
      comment.setCommentId(rs.getLong("comment_id"));
      comment.setName(rs.getString("name"));
      comment.setContent(rs.getString("content"));
      comment.setCreatedTime(rs.getTimestamp("created_time").toLocalDateTime());
      comment.setPostId(rs.getLong("post_id"));

      return  comment;
    };

  }
  //목록
  @Override
  public List<Comment> findAll(Integer pid,Integer pageNo , Integer numOfRows) {

    int pageNumber = (pageNo -1) * numOfRows;

    StringBuffer sql = new StringBuffer();
    sql.append("    SELECT comment_id,name,content,created_time,post_id ");
    sql.append("      FROM Comments ");
    sql.append("     WHERE  post_id = :post_id ");
    sql.append("  ORDER BY comment_id asc ");
    sql.append("    OFFSET :pageNumber rows ");
    sql.append(" FETCH NEXT :numOfRows ROWs ONLY ");

    SqlParameterSource param = new MapSqlParameterSource()
        .addValue("pageNumber", pageNumber)
        .addValue("numOfRows", numOfRows)
        .addValue("post_id", pid);

    List<Comment> list = template.query(sql.toString(),param,commentRowMapper());
    return list;
  }

  @Override
  public int getTotalCount(Integer pid) {
    StringBuffer sql = new StringBuffer();
    sql.append(" SELECT count(post_id) ");
    sql.append("   FROM comments ");
    sql.append("  where post_id = :post_id ");

    SqlParameterSource param = new MapSqlParameterSource().addValue("post_id",pid);

    int i = template.queryForObject(sql.toString(),param,Integer.class);


    return i;
  }

  // 조회

  @Override
  public Optional<Comment> detail(Long id) {
    StringBuffer sql = new StringBuffer();
    sql.append(" SELECT comment_id, name, content, created_time ");
    sql.append("   FROM comments ");
    sql.append("  WHERE comment_id = :comment_id ");

    SqlParameterSource param = new MapSqlParameterSource().addValue("comment_id",id);
    Comment comment = new Comment();
    try{
      comment = template.queryForObject(sql.toString(),param, BeanPropertyRowMapper.newInstance(Comment.class));
    } catch (EmptyResultDataAccessException e){
      return Optional.empty();
    }
    return Optional.of(comment);
  }
}
