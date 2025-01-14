package com.springboot.order.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Table("ORDERS")
public class Order {

    @Id
    private long orderId;
    private long memberId;
//    private long coffeeId;  외래키 아래 다르게 지정

    //오더는 커피를 알아야해, 오더커피랑은 1대다
    //외래키 역할
    @MappedCollection(idColumn = "ORDER_ID")
    private Set<OrderCoffee> orderCoffeeSet = new LinkedHashSet<>();

    private LocalDateTime createAt = LocalDateTime.now();

    private OrderStatus orderStatus = OrderStatus.ORDER_REQUEST; //Enum 클래스, 회원상태,주문상태는 안바뀐다. => 상수처럼관리가 필요한

    public enum OrderStatus {
        ORDER_REQUEST(1,"주문 요청"),
        ORDER_CONFIRM(2, "주문 확정"),
        ORDER_COMPLETE(3, "주문 완료"),
        ORDER_CANCEL(4,"주문 취소");

        @Getter
        private int stepNumber;
        @Getter
        private String stepDescription;

        OrderStatus(int stepNumber, String stepDescription) {
            this.stepNumber = stepNumber;
            this.stepDescription = stepDescription;
        }

    }

}
