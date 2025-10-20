package com.springboot.shoppy_fullstack_app.repository;

import com.springboot.shoppy_fullstack_app.dto.Member;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
@Repository
public class JdbcTemplateMemberRepository implements MemberRepository {

    private final JdbcTemplate jdbcTemplate;

    //생성자
    public JdbcTemplateMemberRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public int save(Member member) {
        //jdbcTemplate 객체를 이용하여 DB의 member 테이블에 insert
        String sql = "insert into member(id,pwd,name,phone,email,mdate) values(?,?,?,?,?,now())";
        Object[] param = {member.getId(),
                member.getPwd(),
                member.getName(),
                member.getPhone(),
                member.getEmail()};
        int rows = jdbcTemplate.update(sql, param);
        return rows;
    }

    @Override
    public Long findById(String id) {
        String sql = "select count(id) from member where id = ?";
        Long count = jdbcTemplate.queryForObject(sql, Long.class, id);
        return count;
    }

    @Override
    public String login(String id) {
        String sql = "select pwd from member where id = ?";
        String encodePwd = jdbcTemplate.queryForObject(sql,String.class,id);
        return encodePwd;
//        Member member = jdbcTemplate.queryForObject(sql,
//                new BeanPropertyRowMapper<>(Member.class), //RowMapper<T>
//                id);
//        return member.getPwd();
    }
}
