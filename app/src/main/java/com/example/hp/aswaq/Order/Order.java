
package com.example.hp.aswaq.Order;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Order {

    @SerializedName("payment_method")
    @Expose
    private String paymentMethod;
    @SerializedName("payment_method_title")
    @Expose
    private String paymentMethodTitle;
    @SerializedName("set_paid")
    @Expose
    private Boolean setPaid;
    @SerializedName("billing")
    @Expose
    private Billing billing;
    @SerializedName("shipping")
    @Expose
    private Shipping shipping;
    @SerializedName("line_items")
    @Expose
    private List<LineItem> lineItems = null;
    @SerializedName("shipping_lines")
    @Expose
    private List<ShippingLine> shippingLines = null;

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Order withPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
        return this;
    }

    public String getPaymentMethodTitle() {
        return paymentMethodTitle;
    }

    public void setPaymentMethodTitle(String paymentMethodTitle) {
        this.paymentMethodTitle = paymentMethodTitle;
    }

    public Order withPaymentMethodTitle(String paymentMethodTitle) {
        this.paymentMethodTitle = paymentMethodTitle;
        return this;
    }

    public Boolean getSetPaid() {
        return setPaid;
    }

    public void setSetPaid(Boolean setPaid) {
        this.setPaid = setPaid;
    }

    public Order withSetPaid(Boolean setPaid) {
        this.setPaid = setPaid;
        return this;
    }

    public Billing getBilling() {
        return billing;
    }

    public void setBilling(Billing billing) {
        this.billing = billing;
    }

    public Order withBilling(Billing billing) {
        this.billing = billing;
        return this;
    }

    public Shipping getShipping() {
        return shipping;
    }

    public void setShipping(Shipping shipping) {
        this.shipping = shipping;
    }

    public Order withShipping(Shipping shipping) {
        this.shipping = shipping;
        return this;
    }

    public List<LineItem> getLineItems() {
        return lineItems;
    }

    public void setLineItems(List<LineItem> lineItems) {
        this.lineItems = lineItems;
    }

    public Order withLineItems(List<LineItem> lineItems) {
        this.lineItems = lineItems;
        return this;
    }

    public List<ShippingLine> getShippingLines() {
        return shippingLines;
    }

    public void setShippingLines(List<ShippingLine> shippingLines) {
        this.shippingLines = shippingLines;
    }

    public Order withShippingLines(List<ShippingLine> shippingLines) {
        this.shippingLines = shippingLines;
        return this;
    }

}
