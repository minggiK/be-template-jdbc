package com.springboot.example;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class CartResponseDto {
    private long cartId;
    private long memberId;
    private List<CartItemResponseDto> cartItems;
    private int totalQuantity;
    private int totalPrice;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;
}
