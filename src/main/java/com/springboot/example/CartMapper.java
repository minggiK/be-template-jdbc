package com.springboot.example;

import org.mapstruct.Mapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CartMapper {
//    Cart cartResponseDtoToCart(CartResponseDto cartResponseDto);
//    CartResponseDto cartToCartResponseDto(Cart cart);
//  // default 메서드 만들기

    //
    default Cart cartResponseDtoToCart(CartResponseDto dto) {
        Cart cart = new Cart();
        cart.setCartId(dto.getCartId());
        cart.setMemberId(dto.getMemberId());
        List<CartItem> cartItems = dto.getCartItems().stream()
                .map(cartItemResponseDto -> cartItemResponseDtoToCartItem(cartItemResponseDto))
                .collect(Collectors.toList());
        cart.setCartItems(cartItems);
        cart.setCreatedAt(dto.getCreateAt());
        cart.setModifiedAt(dto.getModifiedAt());

        return cart;
    }

    default CartItem cartItemResponseDtoToCartItem(CartItemResponseDto dto) {

       return new CartItem(
                dto.getCartItemId(),
                dto.getItemName(),
                dto.getQuantity(),
                dto.getPrice());


    }

    default CartItemResponseDto cartItemToCartItemResponseDto(CartItem cartItem) {

        return new CartItemResponseDto(
                cartItem.getCartItemId(),
                cartItem.getItemName(),
                cartItem.getQuantity(),
                cartItem.getPrice());

    }

    default CartResponseDto cartToCartResponseDto(Cart cart) {
        CartResponseDto dto = new CartResponseDto();
        dto.setCartId(cart.getCartId());
        dto.setMemberId(cart.getMemberId());

        List<CartItemResponseDto> list = cart.getCartItems().stream()
                .map(item -> cartItemToCartItemResponseDto(item))
                .collect(Collectors.toList());
        dto.setCartItems(list);
        dto.setCreateAt(cart.getCreatedAt());
        dto.setModifiedAt(cart.getModifiedAt());

        int[] total = calculateTotal(cart.getCartItems());

        dto.setTotalQuantity(total[0]);
        dto.setTotalPrice(total[1]);

        return dto;

    }

//    default int calculateTotalQuantity(List<CartItem> cartItems) {
//
//        return cartItems.stream().mapToInt(cartItem -> cartItem.getQuantity()).sum();
//
//    }

//    default int calculateTotalprice(List<CartItem> list) {
//
//       return list.stream().mapToInt(cartItem -> cartItem.getPrice() * cartItem.getQuantity()).sum();
//
//    }
    //배열로 받기
    default int[] calculateTotal(List<CartItem> list) {
        int totalPrice = list.stream().mapToInt(cartItem -> cartItem.getPrice() * cartItem.getQuantity()).sum();
        int totalQuantity = list.stream().mapToInt(cartItem -> cartItem.getQuantity()).sum();
        int[] result = {totalQuantity, totalPrice};

        return  result;
    }

}
