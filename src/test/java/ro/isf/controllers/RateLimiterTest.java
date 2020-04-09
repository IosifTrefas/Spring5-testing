package ro.isf.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ro.isf.aspects.RateLimiterAspect;
import ro.isf.services.register.RegisterService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
@WebMvcTest(controllers = RateLimitController.class)
public class RateLimiterTest {

//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
////    @MockBean
////    private RateLimiterAspect rateLimiterAspect;
//
//
//    @Test
//    public void testRate() throws InterruptedException {
//
//        ExecutorService executorService = Executors.newFixedThreadPool(4);
//        IntStream.range(0, 20).forEach(i ->
//                executorService.submit(() -> {
//                    try {
//                        mockMvc.perform(
//                                get("/iosif/user")
//                                        .param("userId", "1")
//                                        .contentType("application/json"))
//                                .andExpect(status().isOk());
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                })
//        );
//
//        Thread.sleep(10000);
//
//    }
}
