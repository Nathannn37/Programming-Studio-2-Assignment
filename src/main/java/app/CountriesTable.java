package app;

public class CountriesTable {
    public String m49code = "";
    public String name = "";
    public String cpc = "";
    public String comName = "";
    public int year = 0;
    public String loss_percentage = "0.0";
    public String food_supply_stage = "N/A";
    public String cause_of_loss = "N/A";
    public String activity = "N/A";
    public String region = "N/A";
 

    public CountriesTable () {
        activity = "";
    }

    public CountriesTable(String name, String comName, int year, String loss_percentage,String food_supply_stage, String cause_of_loss, String activity) {
        this.name=name;
        this.comName=comName;
        this.year=year;
        this.loss_percentage=loss_percentage;
        if (food_supply_stage != null) {
        this.food_supply_stage=food_supply_stage;
        }
        if (cause_of_loss != null) {
        this.cause_of_loss=cause_of_loss;
        }
        if (activity != null) {
        this.activity=activity;
        }
     }

    public String getm49code() {
        return m49code;
    }

    public String getCountryName() {
        return name;
    }

    public int getYear() {
        return year;
    }

    public String getlosspercent() {
        return loss_percentage;
    }

    public String getsupplystage() {
        return food_supply_stage;
    }

    public String getcause() {
        return cause_of_loss;
    }

    public String getactivity() {
        return activity;
    }
    public String getcom() {
        return comName;
    }

    public void setregion(String region) {
         this.region = region;
    }

    public String getregion() {
        return region;
   }

 
}