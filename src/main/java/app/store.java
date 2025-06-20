package app;
import java.util.ArrayList;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class store {
    public final DecimalFormat df = new DecimalFormat("0.00");
   public ArrayList<CountriesTable> contents = null;
    public ArrayList<String> coms = new ArrayList<String>();
    public CountriesTable first = null;
    public double swaste = 0.0;
    public double lwaste = 0.0;
    public int syear = 0;
    public int eyear = 0;
    public double change = 0.0;
    public String finalString = "";
    public store (ArrayList<CountriesTable> contents) {
        this.contents = contents;
    }


    public String output(boolean supply, boolean activity, boolean cause) {
       first = contents.get(0);
       coms.add(first.getcom());
       CountriesTable com = null;

       for (CountriesTable content : contents) {
        com = content;
        if (!coms.contains(com.getcom())) {
            coms.add(com.getcom());
        }
       }

       
       for (String content : coms) {
        int count = 0;
        for (CountriesTable comm : contents) {
            com = comm;
            if (com.getcom().equals(content)) {

                if (count == 0) {
                   swaste = Double.parseDouble(com.getlosspercent());
                   syear = com.getYear();
                   count = count + 1;
                }
                else {
                    lwaste = Double.parseDouble(com.getlosspercent());
                    eyear = com.getYear();
                }
            }
        }
        if ((swaste - lwaste) != 0) {
            change = (swaste - lwaste);
        }
        else {
            change = swaste;
        }
        if (eyear == 0) {
            eyear = syear;
        }
        finalString = finalString + "<tr>";
        finalString = finalString + "<td>" + com.getCountryName() +"</td>";
        finalString = finalString + "<td>" + syear + "-" + eyear +"</td>";
        finalString = finalString + "<td>" + (df.format(Math.abs(change))) +"</td>";
        finalString = finalString + "<td>" + content +"</td>";
        if (supply) {
            if (com.getsupplystage().equals("")) {
                finalString = finalString + "<td>N/A</td>";
            }
            else {
            finalString = finalString + "<td>" + com.getsupplystage() +"</td>";
            }
        }
        if (activity) {
            if (com.getactivity().equals("")) {
                finalString = finalString + "<td>N/A</td>";
            }
            else {
            finalString = finalString + "<td>" + com.getactivity() +"</td>";
            }
        }
        if (cause) {
            if (com.getcause().equals("")) {
                finalString = finalString + "<td>N/A</td>";
            }
            else {
            finalString = finalString + "<td>" + com.getcause() +"</td>";
            }
        }
        
       }

       return finalString;
    }


    
    public String allyears(boolean supply, boolean activity, boolean cause) {
        int priyear = 0;
        CountriesTable curr = contents.get(0);
        priyear=curr.getYear();
        int count = 0;
        finalString = "";

        for (CountriesTable content : contents) {

            if (count == 0) {
                priyear = content.getYear();
                swaste = Double.parseDouble(content.getlosspercent());
                syear = content.getYear();

                if ((swaste - lwaste) != 0) {
                    change = (swaste - lwaste);
                }
                else {
                    change = swaste;
                }
                if (eyear == 0) {
                    eyear = syear;
                }

                finalString = finalString + "<tr>";
                finalString = finalString + "<td>" + content.getCountryName() +"</td>";
                finalString = finalString + "<td>" + syear +"</td>";
                finalString = finalString + "<td>" + (df.format(Math.abs(change))) +"</td>";
                if (supply) {
                    if (content.getsupplystage().equals("")) {
                        finalString = finalString + "<td>N/A</td>";
                    }
                    else {
                    finalString = finalString + "<td>" + content.getsupplystage() +"</td>";
                    }
                }
                if (activity) {
                    if (content.getactivity().equals("")) {
                        finalString = finalString + "<td>N/A</td>";
                    }
                    else {
                    finalString = finalString + "<td>" + content.getactivity() +"</td>";
                    }
                }
                if (cause) {
                    if (content.getcause().equals("")) {
                        finalString = finalString + "<td>N/A</td>";
                    }
                    else {
                    finalString = finalString + "<td>" + content.getcause() +"</td>";
                    }
                }
                ++count;
            }

            if (!(content.getYear() == priyear)) {
                priyear = content.getYear();
                lwaste = Double.parseDouble(content.getlosspercent());
                if ((swaste - lwaste) != 0) {
                    change = (swaste - lwaste);
                }
                else {
                    change = swaste;
                }
                if (eyear == 0) {
                    eyear = syear;
                }

                finalString = finalString + "<tr>";
                finalString = finalString + "<td>" + content.getCountryName() +"</td>";
                finalString = finalString + "<td>" + content.year +"</td>";
                finalString = finalString + "<td>" + (df.format(Math.abs(lwaste))) +"</td>";
                if (supply) {
                    if (content.getsupplystage().equals("")) {
                        finalString = finalString + "<td>N/A</td>";
                    }
                    else {
                    finalString = finalString + "<td>" + content.getsupplystage() +"</td>";
                    }
                }
                if (activity) {
                    if (content.getactivity().equals("")) {
                        finalString = finalString + "<td>N/A</td>";
                    }
                    else {
                    finalString = finalString + "<td>" + content.getactivity() +"</td>";
                    }
                }
                if (cause) {
                    if (content.getcause().equals("")) {
                        finalString = finalString + "<td>N/A</td>";
                    }
                    else {
                    finalString = finalString + "<td>" + content.getcause() +"</td>";
                    }
                }
                
            }
            
        }
        return finalString;
    }


    public String yearsandcom(boolean supply, boolean activity, boolean cause) {
        int priyear = 0;
        CountriesTable curr = contents.get(0);
        priyear=curr.getYear();
        int count = 0;
        finalString = "";
        CountriesTable pricon = null;
        for (CountriesTable content : contents) {
            
            if (count == 0) {
                priyear = content.getYear();
                swaste = Double.parseDouble(content.getlosspercent());
                syear = content.getYear();

                if ((swaste - lwaste) != 0) {
                    change = (swaste - lwaste);
                }
                else {
                    change = swaste;
                }
                if (eyear == 0) {
                    eyear = syear;
                }

                finalString = finalString + "<tr>";
                finalString = finalString + "<td>" + content.getCountryName() +"</td>";
                finalString = finalString + "<td>" + syear +"</td>";
                finalString = finalString + "<td>" + (df.format(Math.abs(change))) +"</td>";
                finalString = finalString + "<td>" + content.getcom() +"</td>";
                if (supply) {
                    if (content.getsupplystage().equals("")) {
                        finalString = finalString + "<td>N/A</td>";
                    }
                    else {
                    finalString = finalString + "<td>" + content.getsupplystage() +"</td>";
                    }
                }
                if (activity) {
                    if (content.getactivity().equals("")) {
                        finalString = finalString + "<td>N/A</td>";
                    }
                    else {
                    finalString = finalString + "<td>" + content.getactivity() +"</td>";
                    }
                }
                if (cause) {
                    if (content.getcause().equals("")) {
                        finalString = finalString + "<td>N/A</td>";
                    }
                    else {
                    finalString = finalString + "<td>" + content.getcause() +"</td>";
                    }
                }
                ++count;
            }
            
            if (!(content.getYear() == priyear)) {
                priyear = content.getYear();
                lwaste = Double.parseDouble(content.getlosspercent());
                if ((swaste - lwaste) != 0) {
                    change = (swaste - lwaste);
                }
                else {
                    change = swaste;
                }
                if (eyear == 0) {
                    eyear = syear;
                }

                
                finalString = finalString + "<tr>";
                finalString = finalString + "<td>" + pricon.getCountryName() +"</td>";
                finalString = finalString + "<td>" + content.year +"</td>";
                finalString = finalString + "<td>" + (df.format(Math.abs(lwaste))) +"</td>";
                finalString = finalString + "<td>" + content.getcom() +"</td>";
                if (supply) {
                    if (content.getsupplystage().equals("")) {
                        finalString = finalString + "<td>N/A</td>";
                    }
                    else {
                    finalString = finalString + "<td>" + content.getsupplystage() +"</td>";
                    }
                }
                if (activity) {
                    if (content.getactivity()==null || content.getactivity().equals("")) {
                        finalString = finalString + "<td>N/A</td>";
                    }
                    else {
                    finalString = finalString + "<td>" + content.getactivity() +"</td>";
                    }
                }
                if (cause) {
                    if (content.getcause().equals("")) {
                        finalString = finalString + "<td>N/A</td>";
                    }
                    else {
                    finalString = finalString + "<td>" + content.getcause() +"</td>";
                    }
                }
            }
            pricon = content;
        }
        return finalString;
    }
    
}
