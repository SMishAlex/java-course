package com.epam.javacource.sid.spring.controller;

import com.epam.javacource.sid.spring.model.DogDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.CharEncoding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Test
@ContextConfiguration(locations = "classpath:spring-mvc-config.xml")
@WebAppConfiguration
public class MockMvcDogControllerTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private WebApplicationContext wac;

    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @BeforeMethod
    public void prepareMockMvc() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(wac)
                .alwaysDo(print())
                .build();

        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
    }

    private DogDto getValidDog() {
        return new DogDto(null, "Dog1Name", LocalDate.now().minusDays(1), 10L, 10L);
    }

    private DogDto getUpdatedDog(DogDto currentDogState) {
        return new DogDto(currentDogState.getId(), currentDogState.getName() + "Updated",
                currentDogState.getDateOfBirth().minusDays(1),
                currentDogState.getHeight() + 15L,
                currentDogState.getWeight() + 15L);
    }

    @Test
    public void whenCreatingValidDogNoExceptionsExpected() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/dog")
                .characterEncoding(CharEncoding.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(getValidDog())))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void whenGettingJustCreatedDogByIdTheBodyIsTheSame() throws Exception {
        String postResponse = createDog(getValidDog());

        DogDto dog1 = objectMapper.readValue(postResponse, DogDto.class);

        String getResponse = mockMvc.perform(MockMvcRequestBuilders.get("/dog/{dogId}", dog1.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Assert.assertEquals(postResponse, getResponse);
    }

    private String createDog(DogDto dogDto) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.post("/dog")
                .characterEncoding(CharEncoding.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dogDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    public void whenUpdateDogAllFieldsInResponseAreUpdated() throws Exception {
        String postResponse = createDog(getValidDog());

        DogDto dog1 = objectMapper.readValue(postResponse, DogDto.class);

        DogDto updatedDog1 = getUpdatedDog(dog1);

        String updatedDogResponseContent = mockMvc.perform(MockMvcRequestBuilders.put("/dog/{id}", dog1.getId())
                .characterEncoding(CharEncoding.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedDog1)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        DogDto updatedDog1Response = objectMapper.readValue(updatedDogResponseContent, DogDto.class);


        Assert.assertEquals(updatedDog1, updatedDog1Response);
    }

    @Test
    public void whenCreateInvalidDogExceptionProvided() throws Exception {
        DogDto validDog = getValidDog();
        validDog.setHeight(-10L);
        mockMvc.perform(MockMvcRequestBuilders.post("/dog")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validDog)))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }
}
