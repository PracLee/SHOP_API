package com.shop.shop;

import com.shop.shop.models.CartLineItemOption;
import com.shop.shop.models.Product;

public class TestUtils {
    public static CartLineItemOption createCartLineItemOption(
            Product product, int optionIndex, int optionItemIndex) {
        return new CartLineItemOption(
                product.option(optionIndex).id(),
                product.option(optionIndex).item(optionItemIndex).id());
    }
}