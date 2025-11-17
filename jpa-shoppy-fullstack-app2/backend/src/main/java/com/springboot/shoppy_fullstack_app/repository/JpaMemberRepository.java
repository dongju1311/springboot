package com.springboot.shoppy_fullstack_app.repository;

import com.springboot.shoppy_fullstack_app.dto.MemberDto;
import com.springboot.shoppy_fullstack_app.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


//interface를 구현하는 클래스를 생성하는 작업은 Spring Data JPA --> 서버 부팅시 컨테이너에 로딩
@Repository
public interface JpaMemberRepository extends JpaRepository<Member,String> {
//    상속받은 부모 인터페이스에 save 메소드가 존재해서 생략 가능!
//    Member save(Member member);
      //새로운 메소드 정의 - 1. 네이밍 규칙 적용, 2. @Query 적용 : SQL 직접 생성
//    @Query("select count(m) from Member m where m.id = :id")
      Long countById(String id); //네이밍 규칙 적용

      //로그인 - 엔티티가 아닌 다른 객체로 결과를 출력하는 경우 new 패키지 풀주소 출력 객체명
    @Query("""
            select new com.springboot.shoppy_fullstack_app.dto.MemberDto(m.id, m.pwd, m.role)
           from Member m where m.id = :id
            """)
      Optional<MemberDto> findByMember(@Param("id") String id);
}
