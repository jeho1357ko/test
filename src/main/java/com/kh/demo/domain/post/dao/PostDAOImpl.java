package com.kh.demo.domain.post.dao;

import com.kh.demo.domain.entity.Post;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Repository
public class PostDAOImpl implements PostDAO{

  final private NamedParameterJdbcTemplate template;

  RowMapper<Post> postRowMapper(){
    return (rs, rowNum) -> {
      Post post = new Post();
      post.setName(rs.getString("name"));
      post.setTitle(rs.getString("title"));
      post.setContent(rs.getString("content"));
      post.setPostId(rs.getLong("post_id"));
      post.setDateCreated(rs.getTimestamp("Date_Created").toLocalDateTime());
      post.setRevisedDate(rs.getTimestamp("Revised_DATE").toLocalDateTime());
      return post;
    };
  }

  //  게시글 목록
  @Override
  public List<Post> findAll() {
    StringBuffer sql = new StringBuffer();
    sql.append(" SELECT post_id , title , content , name ,Date_Created ,Revised_DATE ");
    sql.append(" FROM post ");
    sql.append(" ORDER BY post_id DESC ");

    //db 요청
    List<Post> list = template.query(sql.toString(),postRowMapper());

    return list;
  }

  // 세부내용
  @Override
  public Optional<Post> detail(Long id) {
    StringBuffer sql = new StringBuffer();
    sql.append(" SELECT title,content,name,post_id,Date_Created,Revised_DATE ");
    sql.append("   FROM POST ");
    sql.append("  WHERE post_id = :id ");

    SqlParameterSource param = new MapSqlParameterSource("id",id);

    Post post = template.queryForObject(sql.toString(), param, BeanPropertyRowMapper.newInstance(Post.class));

    return Optional.of(post);
  }

  // 게시글 등록

  @Override
  public Long save(Post post) {
    StringBuffer sql = new StringBuffer();
    sql.append(" INSERT INTO post( post_id,title,content,name)  ");
    sql.append("      values( post_id_seq.nextval,:title,:content,:name) ");

    SqlParameterSource param = new BeanPropertySqlParameterSource(post);
    KeyHolder keyHolder = new GeneratedKeyHolder();

    long rows = template.update(sql.toString(), param, keyHolder,new String[]{"post_id"});

    Number pidNumber = (Number)keyHolder.getKeys().get("post_id");
    long pid = pidNumber.longValue();


    return pid;
  }
// 단건삭제
  @Override
  public Long deleteId(Long id) {
    StringBuffer sql = new StringBuffer() ;
    sql.append(" DELETE FROM post  ");
    sql.append("  WHERE post_id = :post_id ");

    SqlParameterSource param = new MapSqlParameterSource().addValue("post_id",id);
    long row = template.update(sql.toString(),param);
    return row;
  }

  @Override
  public int deleteByIds(List<Long> postId) {
    StringBuffer sql = new StringBuffer();
    sql.append("  DELETE FROM post  ");
    sql.append("        WHERE post_id in( :postId)  ");

    SqlParameterSource param = new MapSqlParameterSource().addValue("postId",postId);

    int rows = template.update(sql.toString(),param);


    return rows;
  }

  @Override
  public int updateById(Long id, Post post) {
    StringBuffer sql = new StringBuffer();
    sql.append(" UPDATE post ");
    sql.append(" SET title = :title, content = :content , name = :name , Revised_DATE = :Revised_DATE ");
    sql.append(" WHERE post_id = :id ");

    SqlParameterSource param = new MapSqlParameterSource()
        .addValue("title",post.getTitle())
        .addValue("content",post.getContent())
        .addValue("name",post.getName())
        .addValue("Revised_DATE",post.getRevisedDate())
        .addValue("id", id);

    int row = template.update(sql.toString(),param);

    return row;
  }
}
