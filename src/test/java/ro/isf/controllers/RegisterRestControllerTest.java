package ro.isf.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ro.isf.services.register.RegisterService;
import ro.isf.services.register.User;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = RegisterRestController.class)
public class RegisterRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RegisterService registerService;

    @Test
    @DisplayName("Passing right arguments, method and content type, the registration returns status 200")
    void whenValidUrlAndMethodAndContentType_thenReturns200() throws Exception {
        UserResource user = new UserResource("Iosif Trefas", "iosif.tre@gmail.com");
        mockMvc.perform(
                post("/forums/42/register")
                        .param("sendWelcomeMail", "true")
                        .content(objectMapper.writeValueAsString(user))
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Passing right arguments, method and content type, a single user object is created")
    void whenValidUrlAndMethodAndContentType_aSingleUserIsCreated() throws Exception {
        UserResource user = new UserResource("Iosif Trefas", "iosif.tre@gmail.com");
        mockMvc.perform(
                post("/forums/42/register")
                        .param("sendWelcomeMail", "true")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk());

        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(registerService, times(1)).registerUser(userArgumentCaptor.capture(), eq(true));
        assertThat(userArgumentCaptor.getValue().getEmail()).isEqualTo("iosif.tre@gmail.com");
        assertThat(userArgumentCaptor.getValue().getName()).isEqualTo("Iosif Trefas");
    }

    @Test
    void whenInvalidUser_thenReturn400() throws Exception {
        UserResource userResource = new UserResource(null, "iosif.tre@gmail.com");
        mockMvc.perform(
                post("/forums/42/register")
                        .param("sendWelcomeMail", "true")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(userResource))

        ).andExpect(status().isBadRequest());
    }

    @Test
    void whenValidInput_returnUserResource_withFluentApi() throws Exception {
        UserResource userResource = new UserResource("iosif", "iosif.tre@gmail.com");
        MvcResult mvcResult = mockMvc.perform(
                post("/forums/42/register")
                        .param("sendWelcomeMail", "true")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(userResource)))
                .andExpect(status().isOk())
                .andReturn();
        UserResource userReturned = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), UserResource.class);
        assertThat(userReturned.getEmail()).isEqualTo(userResource.getEmail());
        assertThat(userReturned.getName()).isEqualTo(userResource.getName());
    }
}
