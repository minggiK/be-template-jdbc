package com.springboot.member;

import com.springboot.member.entity.Member;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface MemberRepository extends CrudRepository<Member, Long> { //랩핑클래스 long, Long(null) 포함 = 제네릭에서 레퍼런스타입만 가능
    Optional<Member> findByEmail(String email);

}
