package kr.study.restdocs.scrap;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.study.restdocs.common.RestDocsConfiguration;
import kr.study.restdocs.scrap.dto.ScrapSaveRequestDto;
import kr.study.restdocs.scrap.dto.ScrapUpdateRequestDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.stream.IntStream;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
@Import(RestDocsConfiguration.class)

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest
public class ScrapApiControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ScrapRepository scrapRepository;

    @Test
    public void 스크랩_등록_restdocs() throws Exception {
        //given
        String title  = "title1";
        String content = "content1";

        ScrapSaveRequestDto requestDto = ScrapSaveRequestDto.builder()
                .title(title)
                .content(content)
                .build();

        //when, then
        mockMvc.perform(post("/api/scrap")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").exists())
                .andDo(document(
                        "create-scrap",
                        requestHeaders(
                                headerWithName(HttpHeaders.ACCEPT).description("request header"),
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("request content type")
                        ),
                        requestFields(
                                fieldWithPath("title").description("scrap title"),
                                fieldWithPath("content").description("scrap content")
                        ),
                        responseHeaders(
                                //headerWithName(HttpHeaders.LOCATION).description("response header"), 왜 에러나는지 모르겠음
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("response content type")
                        ),
                        responseFields(
                                fieldWithPath("id").description("scrap id"),
                                fieldWithPath("title").description("scrap url"),
                                fieldWithPath("content").description("scrap data")
                        )
                ));
    }

    @Test
    public void 스크랩_업데이트_restdocs() throws Exception {
        //given
        String title  = "title1";
        String content = "content1";

        Scrap savedScrap = scrapRepository.save(Scrap.builder()
                .title(title)
                .content(content)
                .build());

        Long updateId       = savedScrap.getId();

        ScrapUpdateRequestDto requestDto = ScrapUpdateRequestDto.builder()
                .title(title)
                .content(content)
                .build();

        //when, then
        mockMvc.perform(put("/api/scrap/{id}",updateId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").exists())
                .andDo(document(
                        "update-scrap",
                        requestHeaders(
                                headerWithName(HttpHeaders.ACCEPT).description("request header"),
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("request content type")
                        ),
                        requestFields(
                                fieldWithPath("title").description("scrap url"),
                                fieldWithPath("content").description("scrap data")
                        ),
                        responseHeaders(
                                //headerWithName(HttpHeaders.LOCATION).description("response header"), 왜 에러나는지 모르겠음
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("response content type")
                        ),
                        responseFields(
                                fieldWithPath("id").description("scrap id"),
                                fieldWithPath("title").description("scrap title"),
                                fieldWithPath("content").description("scrap content")
                        )
                ));
    }

    @Test
    public void getScrap() throws Exception {
        //given
        String title  = "title1";
        String content = "content1";

        Scrap savedScrap = scrapRepository.save(Scrap.builder()
                .title(title)
                .content(content)
                .build());

        Long savedId = savedScrap.getId();

        //when, then
        mockMvc.perform(get("/api/scrap/{id}", savedId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").exists())
                .andDo(document(
                        "get-scrap",
                        responseHeaders(
                                //headerWithName(HttpHeaders.LOCATION).description("response header"), 왜 에러나는지 모르겠음
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("response content type")
                        ),
                        responseFields(
                                fieldWithPath("id").description("scrap id"),
                                fieldWithPath("title").description("scrap title"),
                                fieldWithPath("content").description("scrap content")
                        )
                ));
    }

    @Test
    public void getList() throws Exception {
        //given
        IntStream.range(0, 30).forEach(i -> {
            this.createScrap(i);
        });

        //when
        this.mockMvc.perform(get("/api/scrap"))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document(
                        "query-scrap",
                        responseHeaders(
                                //headerWithName(HttpHeaders.LOCATION).description("response header"), 왜 에러나는지 모르겠음
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("response content type")
                        ),
                        responseFields(
                                fieldWithPath("[].id").description("scrap id"),
                                fieldWithPath("[].title").description("scrap title"),
                                fieldWithPath("[].content").description("scrap content")
                        )
                ));

        //then
    }

    private void createScrap(int i) {
        Scrap scrap = Scrap.builder()
                .title("title"+i)
                .content("content"+i)
                .build();

        scrapRepository.save(scrap);
    }
}