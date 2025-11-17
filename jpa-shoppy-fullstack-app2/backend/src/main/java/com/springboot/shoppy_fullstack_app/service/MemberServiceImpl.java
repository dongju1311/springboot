package com.springboot.shoppy_fullstack_app.service;

import com.springboot.shoppy_fullstack_app.dto.MemberDto;
import com.springboot.shoppy_fullstack_app.entity.Member;
import com.springboot.shoppy_fullstack_app.repository.JpaMemberRepository;
import com.springboot.shoppy_fullstack_app.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional  //: DB가 auto-commit 모드이면 생략가능
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;
    private final JpaMemberRepository jpaMemberRepository;
//    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired //IOC 컨테이너에 생성자 주입!!
    public MemberServiceImpl(MemberRepository memberRepository,
                             PasswordEncoder passwordEncoder,
                             JpaMemberRepository jpaMemberRepository) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.jpaMemberRepository = jpaMemberRepository;
    }
//    //Spring-Security를 이용하여 로그인 처리시 UserDetailsService 객체 사용하므로 login 메소드는 호출 X
//    @Override
//    public boolean login(MemberDto member) {
//        String encodePwd = memberRepository.login(member.getId());
////        System.out.println(encodePwd);
//        boolean result = passwordEncoder.matches(member.getPwd(), encodePwd);
//        return result;
//    }

    @Override
    public int signup(MemberDto memberDto){
        int result = 0;
        //패스워드 인코딩
        String encodePwd = passwordEncoder.encode(memberDto.getPwd());  //UUID 타입으로 생성됨
        memberDto.setPwd(encodePwd);
        memberDto.setRole("USER");
        //memberDto --> member 엔티티로 저장
        Member member = new Member(memberDto);
        Member saveMember = memberRepository.save(member);
        if(saveMember != null) result = 1;
        return result;
//        System.out.println("encodePwd ==>> " + encodePwd);
//        return memberRepository.save(memberDto);
    }

    @Override
    public boolean idCheck(String id) {
        boolean result = true;
        Long count = memberRepository.countById(id);
        System.out.println("count==>" + count);
        if(count == 0) result = false;
        return result;
    }

}
