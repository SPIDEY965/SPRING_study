package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// service 에서는 비즈니스에 의존적으로 생성하기
// 바로 테스트 클래스 생성 단축기 : ctrl + shift + T
public class MemberService {
    private final MemberRepository memberRepository;

    // memberrepository를 외부에서 넣어준다
    // DI - dependency injection

    public MemberService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }


//    회원 가입
    public Long join(Member member){
//        // 중복회원 안됨!
//        Optional<Member> result = memberRepository.findByName(member.getName());
//        result.ifPresent(member1 -> {
//            throw new IllegalStateException("이미 존재하는 회원입니다");
//        });

        // 단축기 : shift + ctrl + alt + T
        // Extract Method : 복잡해질 떄는 하나의 method로 생성!
        validateDuplicateMember(member); // 중복회원 검증

        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                        .ifPresent(member1 -> {
                            throw new IllegalStateException("이미 존재하는 회원입니다");
                        });
    }

    // 전체 회원 조회
    public List<Member> findMember() {
        return memberRepository.findAll();
    }
    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
