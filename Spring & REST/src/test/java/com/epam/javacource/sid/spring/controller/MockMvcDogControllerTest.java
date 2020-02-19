package com.epam.javacource.sid.spring.controller;

import com.epam.javacource.sid.spring.dao.DogDao;
import com.epam.javacource.sid.spring.model.DogDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.CharEncoding;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Test
public class MockMvcDogControllerTest {

    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @BeforeMethod
    public void prepareMockMvc() {
        DogController dogController = new DogController(new DogDao());
        mockMvc = MockMvcBuilders.standaloneSetup(dogController).build();
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();

    }

    @Test
    public void testName() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/dog/{id}", 1))
                .andDo(MockMvcResultHandlers.print());
    }

    private DogDto getDog1() {
        return new DogDto(null, "Dog1Name", LocalDate.now().minusDays(1), 10L, 10L);
    }

    private DogDto getUpdatedDog(Integer dogId) {
        return new DogDto(dogId, "Updated Dog1Name", LocalDate.now().minusDays(1), 15L, 15L);
    }


    @Test
    public void testCreatingDog() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/dog")
                .characterEncoding(CharEncoding.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(getDog1())))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }

    @Test
    public void testGettingCreatedDogById() throws Exception {
        String postResponse = createDog(getDog1());

        DogDto dog1 = objectMapper.readValue(postResponse, DogDto.class);

        String getResponse = mockMvc.perform(MockMvcRequestBuilders.get("/dog/{dogId}", dog1.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Assert.assertEquals(postResponse, getResponse);
    }

    private String createDog(DogDto dogDto) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.post("/dog")
                .characterEncoding(CharEncoding.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dogDto)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    public void testUpdatingCreatedDogById() throws Exception {
        String postResponse = createDog(getDog1());

        DogDto dog1 = objectMapper.readValue(postResponse, DogDto.class);

        DogDto updatedDog1 = getUpdatedDog(dog1.getId());

        String updatedDogResponseContent = mockMvc.perform(MockMvcRequestBuilders.put("/dog/{id}", dog1.getId())
                .characterEncoding(CharEncoding.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedDog1)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        DogDto updatedDog1Response = objectMapper.readValue(updatedDogResponseContent, DogDto.class);


        Assert.assertEquals(updatedDog1, updatedDog1Response);
    }

    @Test
    public void testNegativeCreatingDog() throws Exception {
        DogDto validDog = getDog1();
        validDog.setHeight(-10L);
        mockMvc.perform(MockMvcRequestBuilders.post("/dog")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validDog)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is4xxClientError());
    }
}
