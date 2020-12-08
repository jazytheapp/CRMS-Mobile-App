package ca.gbc.comp3074.crms.models;

public class Restaurant {
    private String name, address, image;
    private Double rating;



    public Restaurant(){

    }

    public Restaurant(String name, String address,String image, Double rating) {
        this.name = name;
        this.address = address;
        this.image = image;
        this.rating = rating;

    }


    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public Double getRating() {
        return rating;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
}
