package com.springboot.member.service;

import com.springboot.exception.BusinessLogicException;
import com.springboot.exception.ExceptionCode;
import com.springboot.member.entity.Member;
import com.springboot.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * V2
 *  - 메서드 구현
 *  - DI 적용
 */
@Service
public class MemberService {
   private final MemberRepository memberRepository;

    public MemberService (MemberRepository memberRepository) {

        this.memberRepository = memberRepository;
    }

    public Member createMember(Member member) {
        // TODO should business logic
//        Optional<Member> optionalMember = memberRepository.findByEmail(member.getEmail());
//      if(optionalMember.isPresent()) { // 존재하면,(이메일이 있다면  -> 중복여부 확인
//          throw new BusinessLogicException(ExceptionCode.MEMBER_EXISTS); // 안티패턴 : 분리해야한다
//      } 아래 private 로 예외처리 따로 설정

        verifyExistsEmail(member.getEmail());
//        Member savedMember = memberRepository.save(member); //저장하고 반환
//        return savedMember;
        return memberRepository.save(member); // DB에 저장하고 가져온다고?
//        throw new BusinessLogicException(ExceptionCode.NOT_IMPLEMENTATION); XX
    }

    public Member updateMember(Member member) {
        // TODO should business logic
        //findMember = 원본 , member = 수정해야할 정보
        Member findMember =findVertfiedMember(member.getMemberId());

        Optional.ofNullable(member.getName())
                .ifPresent(name ->findMember.setName(name)); //isPresent:  존재한다면 : != null (람다식)

        Optional.ofNullable(member.getPhone())
                .ifPresent(phone -> findMember.setPhone(phone)); //-> 변경값 DB에 저장
        //Optional의 내용과 조건문과 동일한 형태 -> Optional은 null이 기본?
//        if(member.getName() != null) { //null 이 아니라면
//            findMember.setName(member.getName()); // 원본데이터를 변경데이터로 들어온 menber로 변경해줘
//        }

        return memberRepository.save(findMember);
//        throw new BusinessLogicException(ExceptionCode.NOT_IMPLEMENTATION);
    }

    public Member findMember(long memberId) {
        // TODO should business logic

//        throw new BusinessLogicException(ExceptionCode.NOT_IMPLEMENTATION);
//        Member findMember = findVertfiedMember(memberId);
//        return findMember; 아래랑 같음
        return findVertfiedMember(memberId);
    }


    public List<Member> findMembers() {
        // TODO should business logic

//        throw new BusinessLogicException(ExceptionCode.NOT_IMPLEMENTATION);
        return (List<Member>)memberRepository.findAll(); //없으면 빈 리스트를 반환하면 됨, 조회니까!
    }

    public void deleteMember(long memberId) {
        // TODO should business logic

//        throw new BusinessLogicException(ExceptionCode.NOT_IMPLEMENTATION);
        Member findMember = findVertfiedMember(memberId);
        memberRepository.delete(findMember); // delete()<- Member 엔티티로 받음
    }


    private void verifyExistsEmail(String email) {
        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        if(optionalMember.isPresent()) {
            throw new BusinessLogicException(ExceptionCode.MEMBER_EXISTS);
        }
    }

    private Member findVertfiedMember(long memberId) {
        Optional<Member> optionalMemeber = memberRepository.findById(memberId);

        Member findMember = optionalMemeber.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

        return findMember;
    }

    public void verifyExistsMemberId(long memberId) {
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        if(!optionalMember.isPresent()) {
            throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND);
        }
    }
}
