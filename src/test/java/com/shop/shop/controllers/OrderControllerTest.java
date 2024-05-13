package com.shop.shop.controllers;

import com.shop.shop.application.CreateOrderService;
import com.shop.shop.models.UserId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
class OrderControllerTest extends ControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreateOrderService createOrderService;

    @Test
    @DisplayName("POST /orders")
    void create() throws Exception {
        UserId userId = new UserId(USER_ID);

        String json = """
                {
                    "receiver": {
                        "name": "홍길동",
                        "address1": "서울특별시 성동구 상원12길 34",
                        "address2": "ㅇㅇㅇ호",
                        "postalCode": "04790",
                        "phoneNumber": "01012345678"
                    },
                    "payment": {
                        "merchantId": "ORDER-20230101-00000001",
                        "transactionId": "123456789012"
                    }
                }
                """;

        mockMvc.perform(post("/orders")
                        .header("Authorization", "Bearer " + userAccessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());

        verify(createOrderService).createOrder(eq(userId), any(), any());
    }
}