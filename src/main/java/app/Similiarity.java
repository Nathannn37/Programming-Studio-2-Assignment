package app;

public class Similiarity {
    public String category;
    public String quantity;
    public String option;
    public String cpc_code;
    public String commodity;
    public String parentCpcName;
    public String Similarity;
    public String lossPercentage;

    public Similiarity(String commodity, String cpc_code, String parentCpcName, String lossPercentage, String similiarity) {
        this.commodity = commodity;
        this.cpc_code = cpc_code;
        this.parentCpcName = parentCpcName;
        this.lossPercentage = lossPercentage;
        this.Similarity = similiarity;
    }

    public String getLossPercentage() {
        return lossPercentage;
    }

    public String getCategory() {
        return category;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getOption() {
        return option;
    }

    public String getCpc_code() {
        return cpc_code;
    }

    public String getCommodity() {
        return commodity;
    }

    public String getParentCpcName() {
        return parentCpcName;
    }

    public String getSimilarity() {
        return Similarity;
    }

    

    
}
