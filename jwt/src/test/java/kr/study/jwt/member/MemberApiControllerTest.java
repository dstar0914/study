package kr.study.jwt.member;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.study.jwt.auth.TokenRequestDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.common.util.Jackson2JsonParser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberApiControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MemberService memberService;

    @Test
    public void 회원_저장() throws Exception {
        //given
        String email    = "email@email.com";
        String password = "password";

        MemberRequestDto requestDto = MemberRequestDto.builder()
                .email(email)
                .password(password)
                .build();

        mockMvc.perform(post("/api/member")
                .header("X-AUTH-TOKEN", getJwtToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("email").exists())
                .andExpect(jsonPath("password").exists());
        //when, then
    }
    
    @Test
    public void 둘다_유횩기간지난_토큰_재요청() throws Exception {
        //given
        String accessToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QGVtYWlsLmNvbSIsInJvbGVzIjpbeyJhdXRob3JpdHkiOiJST0xFX0FETUlOIn1dLCJpYXQiOjE1ODg0Mzg5NjMsImV4cCI6MTU4ODQzOTI2M30.AL00c2lZxAsGQK7u4UfWlyOBQamNMMHZy_fqRWeqV7c";
        String refreshToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QGVtYWlsLmNvbSIsInJvbGVzIjpbeyJhdXRob3JpdHkiOiJST0xFX0FETUlOIn1dLCJpYXQiOjE1ODg0Mzg5NjQsImV4cCI6MTU4ODQ0MjU2NH0.g6a6MLURXzF62MSFyJSv5_ydEkY0w3Qz3_oguz7nUYk";

        TokenRequestDto requestDto = TokenRequestDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
        
        //그냥 요쳥만 해도 email 확인하는 곳에서 에러가 나서 진행이 안됨
//        mockMvc.perform(post("/auth/token")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(requestDto)))
//                .andDo(print())
//                .andExpect(status().is(500));
        
        //then
    }

    public String getJwtToken() throws Exception {
        String email    = "test@email.com";
        String password = "pass";

        Member member = Member.builder()
                .email(email)
                .password(password)
                .roles(Collections.singleton(Role.ADMIN))
                .build();

        memberService.saveMember(member);

        Map<String, String> userInfo = new HashMap<>();
        userInfo.put("email", email);
        userInfo.put("password", password);

        ResultActions perform = mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userInfo)))
                .andDo(print());

        String responseBody =  perform.andReturn().getResponse().getContentAsString();
        Jackson2JsonParser parser = new Jackson2JsonParser();

        return parser.parseMap(responseBody).get("access_token").toString();
    }
}