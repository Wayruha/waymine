package az.mecid.controllers;

/**
 * Created with IntelliJ IDEA.
 * User: Roman
 * Date: 01.11.14
 * Time: 0:17
 * To change this template use File | Settings | File Templates.
 */
public class product {
    public void setId(int id) {
        this.id = id;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    private int id;

    public int getId() {
        return id;
    }

    public int getCost() {
        return cost;
    }

    public int getNumber() {
        return number;
    }

    public String getAbout() {
        return about;
    }

    private int cost;
    private int number;

    public String getName() {
        return name;
    }

    private String name,about;

}
