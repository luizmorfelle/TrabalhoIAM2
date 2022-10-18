package model;

import java.io.Serializable;

public class Gym implements Serializable {
    private int id;
    private String name;
    private String score;
    private String price;
    private String distance;
    private String type;

    public Gym(int id, String name, String score, String price, String distance, String type) {
        this.id = id;
        this.name = name;
        this.score = score;
        this.price = price;
        this.distance = distance;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Gym{" +
                "name='" + name + '\'' +
                ", score='" + score + '\'' +
                ", price='" + price + '\'' +
                ", distance='" + distance + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
