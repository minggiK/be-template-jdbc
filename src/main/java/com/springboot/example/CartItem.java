package com.springboot.example;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@AllArgsConstructor
public class CartItem {
    @Id
    private long cartItemId;
    private String itemName;
    private int quantity;
    private int price;
}
