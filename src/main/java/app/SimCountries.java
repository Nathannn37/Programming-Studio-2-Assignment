package app;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Collections;

public class SimCountries {
    public String name;
    public String com;
    public String loss;
    public String cname;
    public Double simScore = 0.0;
    public double overall = 0.0;
    public String types = "";
    public double change = 0.0;
    public SimCountries() {

    }

    public SimCountries(String name, String com, String loss, String cname) {
        this.name=name;
        this.com = com;
        this.loss = loss;
        this.cname=cname;
    }

    public double getLossPercentage() {
        return change;
    }

    public String getCategory() {
        return com;
    }

    public String getName() {
        return name;
    }

    public void setSimScore(double simScore) {
        this.simScore = simScore;
    }

    public double getSimScore() {
        return overall;
    }

    public void settypes(String types) {
        this.types = types;
    }

    public String getTypes() {
        return types;
    }

    public void setname(String name) {
        this.name = types;
    }
    
    public void setOverall(double overall) {
        this.overall = overall;
    }

    public void setloss(double change) {
        this.loss = "" + change;
    }

    public void setall(double change, double overall, String name, String types) {
        this.change = change;
        this.name = name;
        this.overall = overall;
        this.types = types;
    }
    
    
}
