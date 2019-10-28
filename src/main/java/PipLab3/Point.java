package PipLab3;

import javax.persistence.*;

@Entity
public class Point {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Double x;
    private Double y;
    private Double r;
    private boolean inArea;
    private String ownerSSID;

    public Point(){

    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public Double getR() {
        return r;
    }

    public void setR(Double r) {
        this.r = r;
    }

    public boolean isInArea() {
        return inArea;
    }

    public void setInArea(boolean inArea) {
        this.inArea = inArea;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setOwnerSSID(String ownerSSID) {
        this.ownerSSID = ownerSSID;
    }

    public String getOwnerSSID(){
        return ownerSSID;
    }
}
