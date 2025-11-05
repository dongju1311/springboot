package com.springboot.shoppy_fullstack_app.repository;

import com.springboot.shoppy_fullstack_app.dto.MemberDto;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Optional;

@Repository
public class JdbcTemplateMemberRepository  implements  MemberRepository{

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateMemberRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * Spring-Security의 AuthenticationProvider 객체에 의해 UserDetailsService를 호출
     */
    @Override
    public Optional<MemberDto> findByMember(String id) {
        String sql = "select ifnull(MAX(id), null) as id, " +
                " ifnull(MAX(pwd), null) as pwd from member where id = ?";
        try {
            MemberDto memberDto = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(MemberDto.class), id);
            return Optional.ofNullable(memberDto);
        } catch (EmptyResultDataAccessException e) {
            // 조회 결과가 없을 때 null 반환 대신 Optional.empty()
            return Optional.empty();
        }
    }

    @Override
    public String login(String id) {
        String sql = "select ifnull(Max(pwd),null) as pwd from member where id = ?";
        String encodePwd = jdbcTemplate.queryForObject(sql, String.class, id);
        return encodePwd;

//        Member member = jdbcTemplate.queryForObject(sql,
//                                    new BeanPropertyRowMapper<>(Member.class),  //RowMapper<T>
//                                    id);
//        return member.getPwd();
    }

    @Override
    public int save(MemberDto memberDto) {
        String sql = "insert into member(id, pwd, name, phone, email, mdate) values(?, ?, ?, ?, ?, now())";
        Object [] param = {
                memberDto.getId(),
                memberDto.getPwd(),
                memberDto.getName(),
                memberDto.getPhone(),
                memberDto.getEmail()
        };
        return jdbcTemplate.update(sql, param);
    }

    @Override
    public Long findById(String id) {
        String sql = "select count(id) from member where id=?";
        Long count = jdbcTemplate.queryForObject(sql, Long.class, id);
        return count;
    }
}
