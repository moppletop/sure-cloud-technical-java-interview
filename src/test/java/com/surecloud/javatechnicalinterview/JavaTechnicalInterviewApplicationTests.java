package com.surecloud.javatechnicalinterview;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.surecloud.javatechnicalinterview.model.AddExamResultDto;
import com.surecloud.javatechnicalinterview.model.Error;
import com.surecloud.javatechnicalinterview.model.ExamResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        properties = {
                "spring.datasource.url=jdbc:h2:mem:."
        }
)
class JavaTechnicalInterviewApplicationTests {

    private static final String V1_EXAM_COMMAND_ADD = "/v1/exam/command/add";
    private static final String V1_EXAM_QUERY = "/v1/exam/query";

    private final int DEFAULT_SCORE = 10;
    private static final LocalDate DEFAULT_DATE = LocalDate.of(2022, 6, 30);


    @Autowired
    WebApplicationContext applicationContext;
    @Autowired
    ObjectMapper objectMapper;

    private MockMvc mvc;

    @PostConstruct
    void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(applicationContext).build();
    }

    @Test
    void testAddResultWithId() throws Exception {
        performAdd(new AddExamResultDto()
                .id(UUID.randomUUID())
                .name("With Id")
                .score(DEFAULT_SCORE)
                .dateTaken(DEFAULT_DATE));
    }

    @Test
    void testAddResultWithoutId() throws Exception {
        performAdd(new AddExamResultDto()
                .name("Without Id")
                .score(DEFAULT_SCORE)
                .dateTaken(DEFAULT_DATE));
    }

    @Test
    void testGetOne() throws Exception {
        UUID id = UUID.randomUUID();
        String url = V1_EXAM_QUERY + '/' + id;

        mvc.perform(get(url))
                .andExpect(status().isNotFound());

        AddExamResultDto request = new AddExamResultDto()
                .id(id)
                .name("One Result")
                .score(DEFAULT_SCORE)
                .dateTaken(DEFAULT_DATE);

        performAdd(request);

        mvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(mvcResult -> {
                    ExamResult result = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ExamResult.class);

                    assertThat(result.getId()).isNotNull();
                    assertThat(result.getName()).isEqualTo(request.getName());
                    assertThat(result.getScore()).isEqualTo(request.getScore());
                    assertThat(result.getDateTaken()).isEqualTo(request.getDateTaken());
                });
    }

    @Test
    void testGetAll() throws Exception {
        AddExamResultDto request = new AddExamResultDto()
                .name("All Results")
                .score(DEFAULT_SCORE)
                .dateTaken(DEFAULT_DATE);

        performAdd(request);

        mvc.perform(get(V1_EXAM_QUERY))
                .andExpect(status().isOk())
                .andExpect(mvcResult -> {
                    ExamResult[] results = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ExamResult[].class);

                    boolean success = false;

                    for (ExamResult result : results) {
                        if (result.getName().equals(request.getName())) {
                            success = true;
                            break;
                        }
                    }

                    if (!success) {
                        fail("Result not present in list");
                    }
                });
    }

    @Test
    void testValidationFormat() throws Exception {
        AddExamResultDto request = new AddExamResultDto()
                .name(null)
                .score(DEFAULT_SCORE)
                .dateTaken(DEFAULT_DATE);
        Error response = new Error()
                .detail("Name must be between 1 and 255 characters");

        mvc.perform(post(V1_EXAM_COMMAND_ADD)
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));
    }

    private void performAdd(AddExamResultDto request) throws Exception {
        mvc.perform(post(V1_EXAM_COMMAND_ADD)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(mvcResult -> {
                    ExamResult result = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ExamResult.class);

                    if (request.getId() == null) {
                        assertThat(result.getId()).isNotNull();
                    } else {
                        assertThat(result.getId()).isEqualTo(request.getId());
                    }

                    assertThat(result.getName()).isEqualTo(request.getName());
                    assertThat(result.getScore()).isEqualTo(request.getScore());
                    assertThat(result.getDateTaken()).isEqualTo(request.getDateTaken());
                });
    }

}
