package com.shop.shop.controllers;

import com.shop.shop.application.GetProductDetailService;
import com.shop.shop.application.GetProductListService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ProductController.class)
class ProductControllerTest extends ControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetProductListService getProductListService;

    @MockBean
    private GetProductDetailService getProductDetailService;

    @Test
    @DisplayName("GET /products")
    void list() throws Exception {

    }
}