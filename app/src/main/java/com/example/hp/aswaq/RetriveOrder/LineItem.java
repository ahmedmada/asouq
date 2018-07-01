
package com.example.hp.aswaq.RetriveOrder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LineItem {

    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("product_id")
    @Expose
    private Long productId;
    @SerializedName("variation_id")
    @Expose
    private Long variationId;
    @SerializedName("quantity")
    @Expose
    private Long quantity;
    @SerializedName("tax_class")
    @Expose
    private String taxClass;
    @SerializedName("subtotal")
    @Expose
    private String subtotal;
    @SerializedName("subtotal_tax")
    @Expose
    private String subtotalTax;
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("total_tax")
    @Expose
    private String totalTax;
    @SerializedName("taxes")
    @Expose
    private List<Object> taxes = null;
    @SerializedName("meta_data")
    @Expose
    private List<Object> metaData = null;
    @SerializedName("sku")
    @Expose
    private String sku;
    @SerializedName("price")
    @Expose
    private Long price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LineItem withId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LineItem withName(String name) {
        this.name = name;
        return this;
    }

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

    public Long getVariationId() {
        return variationId;
    }

    public void setVariationId(Long variationId) {
        this.variationId = variationId;
    }

    public LineItem withVariationId(Long variationId) {
        this.variationId = variationId;
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

    public String getTaxClass() {
        return taxClass;
    }

    public void setTaxClass(String taxClass) {
        this.taxClass = taxClass;
    }

    public LineItem withTaxClass(String taxClass) {
        this.taxClass = taxClass;
        return this;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }

    public LineItem withSubtotal(String subtotal) {
        this.subtotal = subtotal;
        return this;
    }

    public String getSubtotalTax() {
        return subtotalTax;
    }

    public void setSubtotalTax(String subtotalTax) {
        this.subtotalTax = subtotalTax;
    }

    public LineItem withSubtotalTax(String subtotalTax) {
        this.subtotalTax = subtotalTax;
        return this;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public LineItem withTotal(String total) {
        this.total = total;
        return this;
    }

    public String getTotalTax() {
        return totalTax;
    }

    public void setTotalTax(String totalTax) {
        this.totalTax = totalTax;
    }

    public LineItem withTotalTax(String totalTax) {
        this.totalTax = totalTax;
        return this;
    }

    public List<Object> getTaxes() {
        return taxes;
    }

    public void setTaxes(List<Object> taxes) {
        this.taxes = taxes;
    }

    public LineItem withTaxes(List<Object> taxes) {
        this.taxes = taxes;
        return this;
    }

    public List<Object> getMetaData() {
        return metaData;
    }

    public void setMetaData(List<Object> metaData) {
        this.metaData = metaData;
    }

    public LineItem withMetaData(List<Object> metaData) {
        this.metaData = metaData;
        return this;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public LineItem withSku(String sku) {
        this.sku = sku;
        return this;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public LineItem withPrice(Long price) {
        this.price = price;
        return this;
    }

}
