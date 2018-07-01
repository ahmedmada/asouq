
package com.example.hp.aswaq.RetriveOrder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ShippingLine {

    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("method_title")
    @Expose
    private String methodTitle;
    @SerializedName("method_id")
    @Expose
    private String methodId;
    @SerializedName("instance_id")
    @Expose
    private String instanceId;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ShippingLine withId(Long id) {
        this.id = id;
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

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public ShippingLine withInstanceId(String instanceId) {
        this.instanceId = instanceId;
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

    public String getTotalTax() {
        return totalTax;
    }

    public void setTotalTax(String totalTax) {
        this.totalTax = totalTax;
    }

    public ShippingLine withTotalTax(String totalTax) {
        this.totalTax = totalTax;
        return this;
    }

    public List<Object> getTaxes() {
        return taxes;
    }

    public void setTaxes(List<Object> taxes) {
        this.taxes = taxes;
    }

    public ShippingLine withTaxes(List<Object> taxes) {
        this.taxes = taxes;
        return this;
    }

    public List<Object> getMetaData() {
        return metaData;
    }

    public void setMetaData(List<Object> metaData) {
        this.metaData = metaData;
    }

    public ShippingLine withMetaData(List<Object> metaData) {
        this.metaData = metaData;
        return this;
    }

}
