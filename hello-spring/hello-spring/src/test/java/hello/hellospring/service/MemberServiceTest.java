package hello.hellospring.service;

import static org.junit.jupiter.api.Assertions.*;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MemberServiceTest {

    MemberService memberService;
    //clear를 가져오기 위해
    MemoryMemberRepository memberRepository;
    // 다른 레파지토리 이용하고 있는 꼴

    @BeforeEach
    public void beforeEach() {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }
    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    }
    @Test
    void 회원가입() {
        // given - 이게 주어졌을 때
        Member member = new Member();
        member.setName("hello");
            // "hello"를 "spring"으로 바꾸게 된다면?
            // 밑에 test함수들에서 터져버린다
            // 왜냐면 값이 누적되기 때문!

        // when - 이걸 실행했을 때
        Long saveId = memberService.join(member);

        // then - 나와야 하는 것
        Member findMember = memberService.findOne(saveId).get();
        org.assertj.core.api.Assertions.assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    public void 중복_회원_예외(){
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");
        //when
        memberService.join(member1);
        // 변수 추출하기 : ctrl + alt + v
        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> memberService.join(member2));
        org.assertj.core.api.Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다");

//        try{
//            memberService.join(member2);
//            fail("problem");
//        } catch(IllegalStateException e) {
//            org.assertj.core.api.Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다");
//        }
        //then
    }

    @Test
    void findMember() {
    }

    @Test
    void findOne() {
    }
}