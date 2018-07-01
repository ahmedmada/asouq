
package com.example.hp.aswaq.Order;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ShippingLine {

    @SerializedName("method_id")
    @Expose
    private String methodId;
    @SerializedName("method_title")
    @Expose
    private String methodTitle;
    @SerializedName("total")
    @Expose
    private String total;

    public String getMethodId() {
        return methodId;
    }

    public void setMethodId(String methodId) {
        this.methodId = methodId;
    }

    public ShippingLine withMethodId(String methodId) {
        this.methodId = methodId;
        return this;
    }

    public String getMethodTitle() {
        return methodTitle;
    }

    public void setMethodTitle(String methodTitle) {
        this.methodTitle = methodTitle;
    }

    public ShippingLine withMethodTitle(String methodTitle) {
        this.methodTitle = methodTitle;
        return this;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public ShippingLine withTotal(String total) {
        this.total = total;
        return this;
    }

}
