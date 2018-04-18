package by.entity.old;

import by.entity.event.Item;

import java.util.List;
import java.util.Date;

public class Order {
    private int orderId;
    private byte status;
    private List<Item> items;
    private Date datetimecreate;

    // поля и методы описания подробностей заказа
    public Order(int orderId, byte status, List<Item> items) {
        this.orderId = orderId;
        this.status = status;
        this.items = items;
    }

    public int getOrderId() {
        return orderId;
    }

    public byte getStatus() {
        return status;
    }

    public Date getDatetimecreate() {
        return datetimecreate;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public void setDatetimecreate(Date datetimecreate) {
        this.datetimecreate = datetimecreate;
    }

    @Override
    public String toString() {
        return "Order [orderId =" + orderId + ", status=" + status + "]";
    }
}
