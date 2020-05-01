package kr.study.jwt.member;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    @Test
    public void 회원_저장() throws Exception {
        //given
        String email    = "email@email.com";
        String password = "password";

        MemberRequestDto requestDto = MemberRequestDto.builder()
                .email(email)
                .password(password)
                .build();

        //when, then
        mockMvc.perform(post("/api/member")
                .header("X-AUTH-TOKEN", getJwtToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("email").exists())
                .andExpect(jsonPath("password").exists());
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

        ResultActions perform = mockMvc.perform(post("/auth/token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userInfo)))
                .andDo(print());

        String responseBody =  perform.andReturn().getResponse().getContentAsString();

        return responseBody;
    }
}