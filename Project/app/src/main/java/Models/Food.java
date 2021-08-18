package Models;

import java.io.Serializable;

public class Food implements Serializable {
    public String imageUrl;
    public  String foodname,description,category;
    public int price;


    public Food(String imageUrl, String foodname, String description, String category, int price) {
        this.imageUrl = imageUrl;
        this.foodname = foodname;
        this.description = description;
        this.category = category;
        this.price = price;
    }

    public Food() {
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getFoodname() {
        return foodname;
    }

    public void setFoodname(String foodname) {
        this.foodname = foodname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Food{" +
                "imageUrl='" + imageUrl + '\'' +
                ", foodname='" + foodname + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                '}';
    }
}
