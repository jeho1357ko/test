package com.kh.demo.domain.member.dao;

import com.kh.demo.domain.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberDAOImpl implements MemberDAO {

  private final NamedParameterJdbcTemplate template;

  @Override
  public Long save(Member member) {
    String sql = "insert into member(member_id, email, passwd, tel, nickname, gender, hobby, region, gubun) " +
        "values(member_member_id_seq.nextval, :email, :passwd, :tel, :nickname, :gender, :hobby, :region, :gubun)";

    SqlParameterSource param = new BeanPropertySqlParameterSource(member);
    KeyHolder keyHolder = new GeneratedKeyHolder();
    template.update(sql, param, keyHolder, new String[]{"member_id"});

    return keyHolder.getKey().longValue();
  }

  @Override
  public Optional<Member> findByEmail(String email) {
    String sql = "select * from member where email = :email";
    SqlParameterSource param = new MapSqlParameterSource().addValue("email", email);

    return template.query(sql, param, (rs, rowNum) -> {
      Member m = new Member();
      m.setMemberId(rs.getLong("member_id"));
      m.setEmail(rs.getString("email"));
      m.setPasswd(rs.getString("passwd"));
      m.setTel(rs.getString("tel"));
      m.setNickname(rs.getString("nickname"));
      m.setGender(rs.getString("gender"));
      m.setHobby(rs.getString("hobby"));
      m.setRegion(rs.getString("region"));
      m.setGubun(rs.getString("gubun"));
      return m;
    }).stream().findFirst();
  }

  @Override
  public boolean isExistEmail(String email) {
    String sql = "select count(*) from member where email = :email";
    SqlParameterSource param = new MapSqlParameterSource().addValue("email", email);
    Integer count = template.queryForObject(sql, param, Integer.class);
    return count != null && count > 0;
  }
}