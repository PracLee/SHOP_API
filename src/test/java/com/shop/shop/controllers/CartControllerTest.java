package com.shop.shop.controllers;

import com.shop.shop.application.GetCartService;
import com.shop.shop.dtos.CartDto;
import com.shop.shop.models.UserId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CartController.class)
class CartControllerTest extends ControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetCartService getCartService;

    @Test
    @DisplayName("GET /cart")
    void detail() throws Exception {
        CartDto cartDto = new CartDto(List.of(), 0L);

        given(getCartService.getCartDto(new UserId(USER_ID)))
                .willReturn(cartDto);
        mockMvc.perform(get("/cart")
                        .header("Authorization", "Bearer " + userAccessToken))
                .andExpect(status().isOk());
    }
}