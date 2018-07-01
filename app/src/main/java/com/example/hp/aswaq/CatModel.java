package com.example.hp.aswaq;

public class CatModel
{
    private String imageUrl;
    private String name;
    private String price;
    private String sale;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSale() {
        return sale;
    }

    public void setSale(String sale) {
        this.sale = sale;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;
    public CatModel(String imageUrl,String name,String price,String sale){
        super();
        this.imageUrl=imageUrl;
        this.name=name;
        this.price=price;
        this.sale=sale;
    }
    public CatModel(){}
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }



}
