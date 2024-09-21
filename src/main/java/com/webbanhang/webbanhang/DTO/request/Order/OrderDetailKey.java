package com.webbanhang.webbanhang.DTO.request.Order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
public class OrderDetailKey implements Serializable {
    private String OrderID;
    private String ProductID;
    public OrderDetailKey() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDetailKey that = (OrderDetailKey) o;
        return Objects.equals(OrderID, that.OrderID) &&
                Objects.equals(ProductID, that.ProductID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(OrderID, ProductID);
    }
}
