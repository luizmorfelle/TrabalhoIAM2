package model;

import java.io.Serializable;

public class Request implements Serializable {
    private String type;
    private String price;
    private float distanceFrom;
    private float distanceTo;
    private String score;

    public Request(String type, String price, float distanceFrom, float distanceTo, String score) {
        this.type = type;
        this.price = price;
        this.distanceFrom = distanceFrom;
        this.distanceTo = distanceTo;
        this.score = score;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public float getDistanceFrom() {
        return distanceFrom;
    }

    public void setDistanceFrom(float distanceFrom) {
        this.distanceFrom = distanceFrom;
    }

    public float getDistanceTo() {
        return distanceTo;
    }

    public void setDistanceTo(float distanceTo) {
        this.distanceTo = distanceTo;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

}
