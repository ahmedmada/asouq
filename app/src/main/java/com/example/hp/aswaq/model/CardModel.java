package com.example.hp.aswaq.model;

/**
 * Created by Ahmed AbdElQader on 30-Jun-18.
 */

public class CardModel {
    int id;
    int count;

    public CardModel() {
    }
    public CardModel(int id, int count) {
        this.id = id;
        this.count = count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
