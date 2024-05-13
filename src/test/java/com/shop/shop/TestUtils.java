package com.shop.shop;

import com.shop.shop.models.*;

import java.util.List;
import java.util.stream.IntStream;

public class TestUtils {
    public static CartLineItemOption createCartLineItemOption(
            Product product, int optionIndex, int optionItemIndex) {
        return new CartLineItemOption(
                product.option(optionIndex).id(),
                product.option(optionIndex).item(optionItemIndex).id());
    }

    public static List<OrderOption> createOrderOptions(
            Product product, int[] optionItemIndices) {
        assert optionItemIndices.length == product.optionSize();

        return IntStream.range(0, product.optionSize())
                .mapToObj(index -> createOrderOption(
                        product, index, optionItemIndices[index]))
                .toList();
    }

    public static OrderOption createOrderOption(
            Product product, int optionIndex, int optionItemIndex) {
        ProductOption productOption = product.option(optionIndex);

        return new OrderOption(
                OrderOptionId.generate(),
                productOption,
                productOption.item(optionItemIndex)
        );
    }
}