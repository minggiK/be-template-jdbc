package com.springboot.coffee.service;

import com.springboot.coffee.entity.Coffee;
import com.springboot.coffee.repository.CoffeeRepository;
import com.springboot.exception.BusinessLogicException;
import com.springboot.exception.ExceptionCode;

import com.springboot.order.entity.Order;
import com.springboot.order.entity.OrderCoffee;
import com.springboot.order.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CoffeeService {
    private final CoffeeRepository coffeeRepository;
    private final OrderRepository orderRepository;

    public CoffeeService(CoffeeRepository coffeeRepository, OrderRepository orderRepository){
        this.coffeeRepository = coffeeRepository;
        this.orderRepository = orderRepository;
    }

    public Coffee createCoffee(Coffee coffee) {
        //예외처리할 메서드 따로 생성
//        throw new BusinessLogicException(ExceptionCode.NOT_IMPLEMENTATION);
       verifyExistsCoffee(coffee.getCoffeeCode().toUpperCase());
       coffee.setCoffeeCode(coffee.getCoffeeCode().toUpperCase());
        return coffeeRepository.save(coffee); // db에 저장
    }

    public Coffee updateCoffee(Coffee coffee) {

//        throw new BusinessLogicException(ExceptionCode.NOT_IMPLEMENTATION);
        Coffee findCoffee = findVertfiedCoffee(coffee.getCoffeeId());

        Optional.ofNullable(coffee.getKorName())
                .ifPresent(korName -> findCoffee.setKorName(korName));

        Optional.ofNullable(coffee.getEngName())
                .ifPresent(engName -> findCoffee.setEngName(engName));

        Optional.ofNullable(coffee.getPrice())
                .ifPresent(price -> findCoffee.setPrice(price));

        return coffeeRepository.save(coffee);
    }

    public Coffee findCoffee(long coffeeId) {
//        throw new BusinessLogicException(ExceptionCode.NOT_IMPLEMENTATION);
        return findVertfiedCoffee(coffeeId);
    }

    public List<Coffee> findCoffees() {
//        throw new BusinessLogicException(ExceptionCode.NOT_IMPLEMENTATION);

        return (List<Coffee>) coffeeRepository.findAll();
        //Iterable<T>  : 순화 할 수 있는 객체 -> 강제 형변환을 시킴 : 데이터의 형태가 정해져있어서 강제 형변환을 시켜도 문제가없다.
        // 검증이 생략되어도 되니 강제형변환 가능
    }

    public void deleteCoffee(long coffeeId) {

//        throw new BusinessLogicException(ExceptionCode.NOT_IMPLEMENTATION);
//        findVertfiedCoffee(coffeeId);
        coffeeRepository.delete(findVertfiedCoffee(coffeeId));

    }

    // 주문에 해당하는 커피 정보 조회
    public List<Coffee> findOrderedCoffees(Order order) {
//        throw new BusinessLogicException(ExceptionCode.NOT_IMPLEMENTATION);
//       Order foundOrder = verifyExistsOrderId(order.getOrderId());
////        Set<OrderCoffee> orderCoffeeSet = foundOrder.getOrderCoffeeSet()
//        return  foundOrder.getOrderCoffeeSet().stream()
//                .map(orderCoffee -> findVertfiedCoffee(orderCoffee.getOrderCoffeeId()))
//                .collect(Collectors.toList());

//        return order.getOrderCoffeeSet().stream()
//                .map(orderCoffee -> findVertfiedCoffee(orderCoffee.getCoffeeId()))
//                .collect(Collectors.toList());

        List<Coffee> list = new ArrayList<>();

        Set<OrderCoffee> orderCoffeeSet = order.getOrderCoffeeSet();
        for(OrderCoffee orderCoffee : orderCoffeeSet) {
            Coffee coffee = findVertfiedCoffee(orderCoffee.getCoffeeId());
            list.add(coffee);

        }
            return list;
    }

    //대소문자를 구분하여 같은 상품을 다르게 저장하면, DB의 무결성이 깨짐  -> 항상 대문자로 받는다.
    private void  verifyExistsCoffee (String coffeeCode) {
       //optionalCoffeeCode 원본데이터 -> coffeeCode로
        Optional<Coffee> optionalCoffeeCode = coffeeRepository.findByCoffeeCode(coffeeCode);
        if(optionalCoffeeCode.isPresent()) { // isPresent 반환값이 boolean (있는지없는지) ifPresent (있다면)
            throw new BusinessLogicException(ExceptionCode.COFFEE_CODE_EXISTS);
        }
    }

    //없으면 예외, 있으면 반환
    private Coffee findVertfiedCoffee(long coffeeId) {
        Optional<Coffee> optionalCoffee = coffeeRepository.findByCoffeeId(coffeeId);

        Coffee findCoffee = optionalCoffee.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.COFFEE_NOT_FOUND));

        return findCoffee;
    }

//    private Order verifyExistsOrderId (long orderId) {
//       Order order = orderRepository.findById(orderId).orElseThrow(() -> new BusinessLogicException(ExceptionCode.ORDER_NOT_FOUND));
//
//       return order;
//    }

  private Order verifyExistsOrderId(long orderId) {
        return (Order) orderRepository.findById(orderId).orElseThrow(()->new BusinessLogicException(ExceptionCode.ORDER_NOT_FOUND));

    }
    public void verifyCoffee(long coffeeId) {
        Optional<Coffee> optionalCoffee = coffeeRepository.findByCoffeeId(coffeeId);
        optionalCoffee.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.COFFEE_NOT_FOUND));


    }

}
