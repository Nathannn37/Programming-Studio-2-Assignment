package app;

public class FoodGroup {
    public String foodGroupName;
    public String activity;
    public String foodSupplyStage;
    public String causeOfloss;
    public String startYear;
    public String endYear;
    public String differnce;
 
    public FoodGroup(String foodGroupName, String activiy, String foodSupplyStage, String causeOfloss, String startYear, String endYear, String differnce ) {
        this.foodGroupName = foodGroupName;
        this.activity = activiy;
        this.foodSupplyStage = foodSupplyStage;
        this.causeOfloss = causeOfloss;
        this.startYear = startYear;
        this.endYear = endYear;
        this.differnce = differnce;
     }

    public String getFoodGroupName() {
        return foodGroupName;
    }

    public String getActivity() {
        return activity;
    }

    public String getFoodSupplyStage() {
        return foodSupplyStage;
    }

    public String getCauseOfLoss() {
        return causeOfloss;
    }

    public String getStartYear() {
        return startYear;
    }

    public String getEndYear() {
        return endYear;
    }

    public String getDifference() {
        return differnce;
    }

 
}
