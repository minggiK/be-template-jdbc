package com.springboot.example;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class Cart {
    @Id
    private long cartId;
    private long memberId;
    private List<CartItem> cartItems;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
