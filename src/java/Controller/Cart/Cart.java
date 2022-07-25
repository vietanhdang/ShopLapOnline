/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Cart;

import java.util.ArrayList;
import java.util.List;

import model.Product;

/**
 * @author vietd
 */
public class Cart {

    private List<Item> items;
    private long orderTotal;

    public Cart() {
        items = new ArrayList<>();
    }

    public Cart(List<Item> items) {
        this.items = items;
    }

    public Cart(List<Item> items, long orderTotal) {
        this.items = items;
        this.orderTotal = orderTotal;
    }
    
    /*
     * get items in cart
     * @return items
     */
    public Item getItem(Item item) {
        for (Item a : items) {
            if (a.equals(item)) {
                return a;
            }
        }
        return null;
    }

    /*
     * add item to cart
     * @param product product to add
     * @throws Exception exception when add item to cart failed
     */
    public void addCartItem(Product product) throws Exception {
        Item item = new Item(product, 1);
        if (!items.contains(item) && product.getQuantity() > 0) {
            /* if item not in cart and product quantity is greater than 0 */
            items.add(item);
        } else {
            Item it = getItem(item);
            if (it.getQuantity() < product.getQuantity()) {
                it.setQuantity(it.getQuantity() + 1);
            } else {
                throw new Exception("Số lượng sản phẩm bạn chọn vượt quá số lượng sản phẩm hiện có");
            }
        }
        setOrderTotal(calculateOrderTotal());
    }

    /*
     * remove item from cart
     * @param product product to remove
     * @throws Exception if product is not in cart
     */
    public void deleteCartItem(Product product) throws Exception {
        Item item = new Item(product);
        if (items.contains(item)) {
            /* if product is in cart */
            items.remove(item);
        } else {
            throw new Exception("Xóa sản phẩm thất bại do trong giỏ hàng hiện không có sản phẩm này");
        }
        setOrderTotal(calculateOrderTotal());
    }

    public void deleteAllCartItem() {
        items.clear();
        this.orderTotal = 0;
    }

    /*
     * update item in cart
     * @param product product to update
     * @param quantity quantity to update
     * @throws Exception exception when update item in cart failed
     */
    public void updateCartItem(Product product, int quantity, boolean isPlus, boolean isMinus) throws Exception {
        Item item = getItem(new Item(product));
        if (product.getQuantity() > 0) {
            /* if quantity of product is greater than 0, then update quantity */
            if (isPlus) {
                if (item.getQuantity() + quantity <= product.getQuantity()) {
                    /* update quantity if quantity is less than product quantity */
                    item.setQuantity(item.getQuantity() + quantity);
                } else {
                    throw new Exception("Số lượng sản phẩm bạn chọn vượt quá số lượng sản phẩm hiện có");
                }
            }
            if (isMinus) {
                if (item.getQuantity() - quantity <= 0) {
                    /*
                     * if quantity is 0, remove item from cart
                     */
                    deleteCartItem(product);
                } else {
                    /*
                     * if quantity is not 0, update quantity
                     */
                    item.setQuantity(item.getQuantity() - quantity);
                }
            }
            if (!isPlus && !isMinus && quantity <= product.getQuantity()) {
                item.setQuantity(quantity);
            } else if (!isPlus && !isMinus && quantity > product.getQuantity()) {
                throw new Exception("Số lượng sản phẩm bạn chọn vượt quá số lượng sản phẩm hiện có");
            }
        } else {
            throw new Exception("Sản phẩm đã hết hàng nên không thể mua");
        }
        setOrderTotal(calculateOrderTotal());
    }

    /*
     * caculate order total
     * @return order total
     */
    public long calculateOrderTotal() {
        int orTotal = 0;
        for (Item item : items) {
            orTotal += item.getPrice();
        }
        return orTotal;
    }
    public int getTotalQuantityOfProduct(){
        int quantity = 0;
        for (Item item : items) {
            quantity += item.getQuantity();
        }
        return quantity;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public long getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(long orderTotal) {
        this.orderTotal = orderTotal;
    }

}
