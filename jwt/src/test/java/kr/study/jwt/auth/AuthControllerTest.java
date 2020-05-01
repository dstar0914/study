package kr.study.jwt.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.study.jwt.member.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MemberService memberService;

    @Test
    public void 토큰_생성() throws Exception {
        //given
        String email    = "email";
        String password = "password";
        Set<Role> roles = Collections.singleton(Role.USER);

        MemberRequestDto requestDto = MemberRequestDto.builder()
                .email(email)
                .password(password)
                .roles(roles)
                .build();

        Member member = memberService.saveMember(requestDto.toEntity());

        Map<String, String> userInfo = new HashMap<>();
        userInfo.put("email", email);
        userInfo.put("password", password);

        //when, then
        mockMvc.perform(post("/auth/token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userInfo)))
                .andDo(print())
                .andExpect(status().isOk());
    }
}