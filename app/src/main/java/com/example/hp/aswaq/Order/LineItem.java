
package com.example.hp.aswaq.Order;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LineItem {

    @SerializedName("product_id")
    @Expose
    private Long productId;
    @SerializedName("quantity")
    @Expose
    private Long quantity;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public LineItem withProductId(Long productId) {
        this.productId = productId;
        return this;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public LineItem withQuantity(Long quantity) {
        this.quantity = quantity;
        return this;
    }

}
