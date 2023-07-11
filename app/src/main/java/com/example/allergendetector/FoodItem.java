package com.example.allergendetector;

public class FoodItem {

    private String foodItemName;
    private String allergens;

    public FoodItem(){
        //default constructor required for Firebase
    }

    public FoodItem(String foodItemName, String allergens){
        this.foodItemName = foodItemName;
        this.allergens = allergens;

    }
    public String getFoodItemName() {
        return foodItemName;
    }

    public void setFoodItemName(String foodItemName) {
        this.foodItemName = foodItemName;
    }

    public String getAllergens() {
        return allergens;
    }

    public void setAllergens(String allergens) {
        this.allergens = allergens;
    }

}
