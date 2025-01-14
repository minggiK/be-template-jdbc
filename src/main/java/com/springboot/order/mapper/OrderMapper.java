package com.springboot.order.mapper;


import com.springboot.coffee.entity.Coffee;
import com.springboot.coffee.service.CoffeeService;
import com.springboot.order.dto.OrderCoffeeDto;
import com.springboot.order.dto.OrderCoffeeResponseDto;
import com.springboot.order.dto.OrderPostDto;
import com.springboot.order.dto.OrderResponseDto;
import com.springboot.order.entity.Order;
import com.springboot.order.entity.OrderCoffee;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface OrderMapper {

//OrderPostDtoToOrder
//OderPostDto의 List<OrderCoffeeDto> 를 -> Order의 Set<OrderCoffee> 매핑


    default Order orderPostDtoToOrder(OrderPostDto dto) {
       //Order로 담을
        Order order = new Order();
        //memberId를 꺼내서 담음
            order.setMemberId(dto.getMemberId());

        //OderPostDto의 List<OrderCoffeeDto> 를 -> Order의 Set<OrderCoffee> 매핑
        Set<OrderCoffee> orderCoffees = dto.getOrderCoffees().stream()
                .map(orderCoffee -> orderCoffeeDtoToOrderCoffee(orderCoffee))
                .collect(Collectors.toSet());
        //Setter를 통해 Set<OrderCoffee>를 order에 담아준다.
        order.setOrderCoffeeSet(orderCoffees);

        return order;
    }
//OrderCoffeeToOrderCoffeeResponseDto
//OrderToOrderResponseDto
    default OrderResponseDto orderToOrderResponseDto(Order order, CoffeeService coffeeService) {
        //Order - OrderResponseDto
        OrderResponseDto dto = new OrderResponseDto(
                order.getOrderId(),
                order.getMemberId(),
                order.getOrderStatus(),
                order.getOrderCoffeeSet().stream()
                        .map(orderCoffee -> orderCoffeeToOrderCoffeeResponseDto(orderCoffee, coffeeService))
                        .collect(Collectors.toList()),
                order.getCreateAt()
        );


        return dto;


    }

//OderPostDto의 List<OrderCoffeeDto> 를 -> Order의 Set<OrderCoffee>로 요소를  변경해주는 메서드
    default OrderCoffee orderCoffeeDtoToOrderCoffee (OrderCoffeeDto dto) {

          return OrderCoffee.builder()
                .coffeeId(dto.getCoffeeId())
                .quantity(dto.getQuantity())
                .build();
    }

    //Repository거쳐 데이터를 꺼네와야하는데 서비스계층을 거쳐가야함
    default OrderCoffeeResponseDto orderCoffeeToOrderCoffeeResponseDto (OrderCoffee coffee, CoffeeService coffeeService) {
        //모든 필트의 값이 초기화
        //Coffee 객체를 가져와야한다.
        Coffee foundCoffee = coffeeService.findCoffee(coffee.getCoffeeId());

            return new OrderCoffeeResponseDto(
                        foundCoffee.getCoffeeId(),
                        foundCoffee.getKorName(),
                        foundCoffee.getEngName(),
                        foundCoffee.getPrice(),
                        coffee.getQuantity()
        );
    }
}