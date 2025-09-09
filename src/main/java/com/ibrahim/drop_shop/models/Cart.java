package com.ibrahim.drop_shop.models;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "carts")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal totalAmount = BigDecimal.ZERO;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CartItem> cartItems;

    public void addItem(CartItem item){
        this.cartItems.add(item);
        item.setCart(this);
        updateTotalAmount();
    }
public void removeItem(CartItem item){
    this.cartItems.remove(item);
    item.setCart(this);
    updateTotalAmount();

}

    private void updateTotalAmount() {
        this.totalAmount = this.cartItems
                .stream()
                .map(it -> {
            BigDecimal unitPrice = it.getUnitPrice();
            if(unitPrice == null){
                return BigDecimal.ZERO;
            }
            return unitPrice.multiply(BigDecimal.valueOf(it.getQuantity()));
        })
                .reduce(BigDecimal.ZERO,BigDecimal::add);
    }
}
