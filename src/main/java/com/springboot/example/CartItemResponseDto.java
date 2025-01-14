package com.springboot.example;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CartItemResponseDto {
    private long cartItemId;
    private String itemName;
    private int quantity;
    private int price;
}
