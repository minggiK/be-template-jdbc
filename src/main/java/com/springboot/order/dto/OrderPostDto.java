package com.springboot.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@Getter
@AllArgsConstructor
public class OrderPostDto {
    @Positive
    private long memberId;

    @Valid  // OrderCoffeeDto의 유효성 검증 적용
    private List<OrderCoffeeDto> orderCoffees;



//    @Positive
//    private long coffeeId;
//
//    public long getMemberId() {
//        return memberId;
//    }
//
//    public void setMemberId(Long memberId) {
//        this.memberId = memberId;
//    }
//
//    public long getCoffeeId() {
//        return coffeeId;
//    }
//
//    public void setCoffeeId(Long coffeeId) {
//        this.coffeeId = coffeeId;
//    }
}
