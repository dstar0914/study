package kr.study.hateoas.scrap;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.study.hateoas.scrap.dto.ScrapSaveRequestDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.IntStream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    public void 스크랩_등록() throws Exception {
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
                .accept(MediaTypes.HAL_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("_links.self.href").exists())
                .andExpect(jsonPath("_links.profile").exists());
    }

    @Transactional
    @Test
    public void 스크랩_리스트_가져오기() throws Exception {
        //given
        IntStream.range(0, 20).forEach(i -> {
            createScrap(i);
        });

        //when, then
        mockMvc.perform(get("/api/scrap")
                .param("page", "1")
                .param("size", "10")
                .param("sort", "id,DESC"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("page").exists())
                .andExpect(jsonPath("_embedded.lists[0]._links.self.href").exists())
                .andExpect(jsonPath("_links.self.href").exists())
                .andExpect(jsonPath("_links.profile").exists())
                .andDo(print());
    }

    private void createScrap(int i) {
        Scrap scrap = Scrap.builder()
                .title("title"+i)
                .content("data"+i)
                .build();

        scrapRepository.save(scrap);
    }
}