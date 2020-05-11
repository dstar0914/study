package kr.study.validation.member;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MemberControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @Autowired
    MemberService memberService;

    @Test
    public void 회원_등록_validation() throws Exception {
        //given
        String name = "name";
        String phoneNumber = "01012345678";
        String email = "email";

        MemberSaveRequestDto requestDto = MemberSaveRequestDto.builder()
                .name(name)
                .email(email)
                .phoneNumber(phoneNumber)
                .build();

        //when, then
        webTestClient.post().uri("/api/member")
                .body(Mono.just(requestDto), MemberSaveRequestDto.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody().jsonPath("name").isEqualTo(name);
    }
}