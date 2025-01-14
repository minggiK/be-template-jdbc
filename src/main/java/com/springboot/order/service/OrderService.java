package com.springboot.order.service;

import com.springboot.coffee.service.CoffeeService;
import com.springboot.exception.BusinessLogicException;
import com.springboot.exception.ExceptionCode;
import com.springboot.member.service.MemberService;
import com.springboot.order.entity.Order;
import com.springboot.order.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final CoffeeService coffeeService;
    private  final MemberService memberService;

    public OrderService(OrderRepository orderRepository, CoffeeService coffeeService, MemberService memberService) {
        this.orderRepository = orderRepository;
        this.coffeeService = coffeeService;
        this.memberService = memberService;
    }

    public Order createOrder(Order order) {
        // TODO should business logic
        verifyOrder(order);
        // TODO order 객체는 나중에 DB에 저장 후, 되돌려 받는 것으로 변경 필요.

        return orderRepository.save(order);
    }

    public Order findOrder(long orderId) {
        // TODO should business logic

        // TODO order 객체는 나중에 DB에서 조회 하는 것으로 변경 필요.
//        return
        return verifyExistsOrderId(orderId);
    }

    // 주문 수정 메서드는 사용하지 않습니다.

    public List<Order> findOrders() {
        // TODO should business logic

        // TODO order 객체는 나중에 DB에서 조회하는 것으로 변경 필요.
//        return List.of(new Order(1L, 1L),
//                new Order(2L, 2L));
        return  (List<Order>) orderRepository.findAll();
    }

    public void cancelOrder(long orderId) {
        // TODO should business logic
        Order foundOrder = verifyExistsOrderId(orderId);
        if(foundOrder.getOrderStatus().getStepNumber() < 3 ) {
            foundOrder.setOrderStatus(Order.OrderStatus.ORDER_CANCEL);
        } else {
            //4. 취소일 때도 취소가 되면 안된다.
            throw new BusinessLogicException(ExceptionCode.CANNOT_CHANGE_ORDER);
        }
        orderRepository.save(foundOrder);


    }

    public void verifyOrder(Order order) {
        memberService.verifyExistsMemberId(order.getMemberId());
        order.getOrderCoffeeSet().forEach(orderCoffee -> coffeeService.verifyCoffee(orderCoffee.getCoffeeId()));
        if( order.getOrderStatus().getStepNumber() != 1) {
            throw new BusinessLogicException(ExceptionCode.ORDER_NOT_FOUND);
        }
    }

    private Order verifyExistsOrderId(long orderId) {
        return orderRepository.findById(orderId).orElseThrow(
                ()-> new BusinessLogicException(ExceptionCode.ORDER_NOT_FOUND));

    }




}
