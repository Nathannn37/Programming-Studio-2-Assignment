package app;

import java.util.Comparator;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import io.javalin.http.Context;
import io.javalin.http.Handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;

/**
 * Example Index HTML class using Javalin
 * <p>
 * Generate a static HTML page using Javalin
 * by writing the raw HTML into a Java String object
 *
 * @author Timothy Wiley, 2023. email: timothy.wiley@rmit.edu.au
 * @author Santha Sumanasekara, 2021. email: santha.sumanasekara@rmit.edu.au
 * @author Halil Ali, 2024. email: halil.ali@rmit.edu.au
 */

public class PageST3A implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/page3A.html";
    public static final DecimalFormat df = new DecimalFormat("0.00");
    // URL of this page relative to http://localhost:7001/
    public int loopcount = 0;


    @Override
    public void handle(Context context) throws Exception {
        JDBCConnection jdbc = new JDBCConnection();
        // Create a simple HTML webpage in a String
        String html = "<html>";

        // Add some Head information
        html = html + "<head>" + 
               "<title>Subtask 3.1</title>";

        // Add some CSS (external file)
        html = html + "<link rel='stylesheet' type='text/css' href='index.css'/>";
        html = html + "</head>";

        // Add the body
        html = html + "<body>";

        // Add the topnav
        // This uses a Java v15+ Text Block
        html = html + """
<body>
   <div class='navcontainer'>
        <a href="/"><img src='logo.png' alt='RMIT logo' class="image" height="33px "width='80px'></a>
      <div class="topnav">
         <a href='/'>Homepage</a>
         <a href='mission.html'>Our Mission</a>
         <a href='page2A.html'>Countries Loss</a>
         <a href='page2B.html'>Food Group Loss</a>
         <a href='page3A.html'>Similarities by Country</a>
         <a href='page3B.html'>Similarities by Food Group</a>
      </div>
  </div>
        """;

        html = html + """
            <div class='header'>
                <h1>Locations with similiar food loss/waste percentages</h1>
            </div>
        """;


        html = html + """

        <div class='content'>

        <div class='forms'>
        <form action='/page3A.html' method='post'>

       
        <div class="form-group">
        <label for="country_select">Select Country</label>
         <div>--------------------------</div>
        <select id="country_select" name="country_select">
        </select>
        
        <div>--------------------------</div>

        
        <label for="end_year_select">Select End Year:</label>
        <select id="end_year_select" name="end_year_select">
        </select>

        <div>--------------------------</div>
        
        <label><input type="checkbox" name="food_select" id="food_select">Similarity by food groups</label>
        <label><input type="checkbox" name="avg_select" id="avg_select">Similarity by overall food loss</label>
        <label><input type="checkbox" name="favg_select" id="favg_select">Similarity by foodloss and food groups</label>
        
        <div>--------------------------</div>
        <label for="quantity"> Max Number of Outputs:</label>
                 <select id="colum_select" name="colum_select">
                 <option>1</option>
                 <option>2</option>
                 <option>3</option>
                 <option>4</option>
                 <option>5</option>
                 <option>6</option>
                 <option>7</option>
                 <option>8</option>
                 <option>9</option>
                 <option>10</option>
        </select>
                
        <div>--------------------------</div>
        
        </div>
        <button type='submit' class='btn btn-primary'>Get table</button>
        </div>
        </div>
    <script>

        const years = Array.from({ length: 2022 - 1965 + 1 }, (_, i) => 1965 + i);

        const endYearSelect = document.getElementById('end_year_select');

        years.forEach(year => { 

            const endOption = document.createElement('option');
            endOption.value = year;
            endOption.textContent = year;
            endYearSelect.appendChild(endOption);
        });


        const countriesRegions = [
        "Myanmar", "Burundi", "Western Africa", "Cambodia", "Algeria", "Cameroon",
            "Canada", "Central Asia", "Sri Lanka", "Western Asia", "Chad", "Northern Africa",
            "Europe", "Chile", "China", "Colombia", "Democratic Republic of the Congo",
            "Costa Rica", "Cuba", "Africa", "Sub-Saharan Africa", "Benin", "Denmark",
            "Northern America", "Dominican Republic", "Ecuador", "El Salvador", "Ethiopia",
            "Eritrea", "Angola", "Fiji", "Finland", "France", "Gabon", "Gambia", "Palestine",
            "Germany", "Ghana", "Azerbaijan", "Argentina", "Guatemala", "Guinea", "Guyana",
            "Haiti", "Southern Asia", "Honduras", "South-Eastern Asia", "India", "Australia",
            "Indonesia", "Iran (Islamic Republic of)", "Italy", "Côte d'Ivoire", "Kazakhstan",
            "Jordan", "Kenya", "Democratic People's Republic of Korea", "Republic of Korea",
            "Lao People's Democratic Republic", "Latin America and the Caribbean", "Lebanon",
            "Lesotho", "Liberia", "Libya", "Madagascar", "Malawi", "Malaysia", "Mali",
            "Mauritania", "Bahrain", "Mexico", "Bangladesh", "Morocco", "Mozambique",
            "Armenia", "Oman", "Namibia", "Nepal", "Australia and New Zealand", "New Zealand",
            "Nicaragua", "Niger", "Nigeria", "Norway", "Pakistan", "Panama", "Paraguay",
            "Peru", "Philippines", "Guinea-Bissau", "Timor-Leste", "Rwanda",
            "Saint Kitts and Nevis", "Saint Lucia", "Bolivia (Plurinational State of)",
            "Saudi Arabia", "Senegal", "Serbia", "Sierra Leone", "Viet Nam", "Somalia",
            "South Africa", "Zimbabwe", "Botswana", "South Su   dan", "Sudan", "Eswatini",
            "Sweden", "Switzerland", "Brazil", "Syrian Arab Republic", "Thailand", "Togo",
            "Trinidad and Tobago", "Tunisia", "Turkey", "Uganda", "Ukraine", "Egypt",
            "United Kingdom of Great Britain and Northern Ireland", "United Republic of Tanzania",
            "Belize", "United States of America", "Burkina Faso", "Uruguay",
            "Venezuela (Bolivarian Republic of)", "Zambia"
        ];

        // Get the select element
        const selectElement = document.getElementById('country_select');

        // Loop through the array and create option elements
        countriesRegions.forEach(name => {
            const option = document.createElement('option');
            option.value = name;
            option.textContent = name;
            selectElement.appendChild(option);
        });
    </script>
        """;
        //recording results from page
        String cname = "";
        String eyear = "";
        String numberout= "";
        boolean overall = false;
        boolean foodloss = false;
        boolean both = false;
        
        String country_select = context.formParam("country_select");
        String end_year_select = context.formParam("end_year_select");
        String quantity = context.formParam("colum_select");
        String food = context.formParam("food_select");
        String avg = context.formParam("avg_select");
        String favg = context.formParam("favg_select");

        if (country_select == null || country_select.equals("")) {
            cname = "";
        }
        else {
            cname = country_select;
        }

        if (end_year_select == null || end_year_select.equals("")) {

        }
        else {
            eyear = end_year_select;
        }

        if (food == null) {
           
            foodloss=false;
        } else {
            foodloss=true;
        }
        if (avg == null) {
           
            overall=false;
        } else {
            
            overall=true;
        }
        if (favg == null) {
           
            both=false;
        } else {
            
            both=true;
        }

        if (quantity == null || quantity.equals("")) {
            numberout = "0";
        }
        else {
            numberout = quantity;
        }



        System.out.println(cname);
        System.out.println(eyear);
        System.out.println(overall);
        loopcount = Integer.parseInt(numberout);
        System.out.println(loopcount);
        
        
        ArrayList<SimCountries> sims = new ArrayList<>();
        
        if (foodloss) {
        sims = jdbc.getSimCountry(cname, eyear, html, overall, foodloss, both);
        }
        else if (overall) {
        sims = jdbc.getSimLoss(cname, eyear);
        }
        else {
            sims = jdbc.getSimLoss(cname, eyear);
            sims = jdbc.getSimCountry(cname, eyear, html, overall, foodloss, both);
        }




        html = html + """
                <div class="table-content">
                <table width 500px>
                    <tr>
                        <th>Slected Country</th>
                        <th>Food Groups</th>
                        <th>Overall Loss</th>
                        <th>Similarity</th>
                </tr></div>
                """;



        
        //data output
        // if (overall) {
        html = html + getOverAll(cname, html, sims);
        // }
        // else if (foodloss) {

        // }
        // else if (both) {

        // }
        // else {
            
        //     html = html + """
        //             <tr>
        //                 <td>Null</td>
        //                 <td>Null</td>
        //                 <td>Null</td>
        //                 <td>Null</td>
        //         """;

        // }



        html = html + """
                </table></div>
                """;
        // Footer
            html = html + """
                    <div class='footer'>
                <div class="contentf">
                <a href='mission.html'>How to use and navigate</a>
                <p class:'footheader'>Contact Details: (using database)</p>
                    """;

            
            ArrayList<Student> students = jdbc.getstudents();
            Student stu = null;
            int i;
            for (i = 0; i < students.size(); i++ ) {
            stu = students.get(i);

            
            html = html + "<p>" + stu.snumber + " " + stu.name + " " + stu.email + "</>";
            };

            
            html = html + """
                </div>
                </div>
                    """;

            // Finish the HTML webpage
            html = html + "</body>" + "</html>";
        

        // DO NOT MODIFY THIS
        // Makes Javalin render the webpage
        context.html(html);
    }

    public String getall(String cname, String html, ArrayList<SimCountries> sims) {
        ArrayList<String> countries =  new ArrayList<String>();
        ArrayList<SimCountries> finaloutput =  new ArrayList<SimCountries>();
        

        for (SimCountries sim : sims) {
            countries.add(sim.name);
        }

        String finalString = "";
        //get unique countries
        HashSet<String> set = new HashSet<>(countries);
        countries = new ArrayList<>(set);

        double inichange; 
        int count = 0;
        double first = 0.0;
        double last = 0.0;
        double change = 0.0;
        ArrayList<String> foodgroup = new ArrayList<String>();
        String finalgroups = "";

        for (SimCountries sim : sims) {
            if (sim.name.equals(cname)) {
                if (count == 0) {
                    first = Double.parseDouble(sim.loss);
                    foodgroup.add(sim.com);
                }
                else {
                    last = Double.parseDouble(sim.loss);
                    foodgroup.add(sim.com);
                }
                ++count;
            }
        }
        change = Math.abs(first - last);

        set = new HashSet<>(foodgroup);
        foodgroup = new ArrayList<>(set);


        finalString = finalString + "<tr>";
        finalString = finalString + "<td>(" + cname + ")</td>";
        for (String group : foodgroup) {
            if (finalgroups.equals("")) {
                finalgroups = group;
            }
            else {
                finalgroups = finalgroups + " | " + group;
            }
        }
        finalString = finalString + "<td>" + finalgroups + "</td>";
        finalString = finalString + "<td>" + df.format(change)  + "</td>";
        finalString = finalString + "<td>" + (change/change * 100)  + "</td>";
        inichange = change;


        //for rest of countries
        for (String country : countries) {
            
            count = 0;
            int count2 = 0;
            first = 0.0;
            last = 0.0;
            change = 0.0;
            foodgroup = new ArrayList<String>();
            finalgroups = "";

        for (SimCountries sim : sims) {
            if (sim.getName().equals(country)) {
                if (count == 0) {
                    first = Double.parseDouble(sim.loss);
                    foodgroup.add(sim.com);
                }
                else {
                    last = Double.parseDouble(sim.loss);
                    foodgroup.add(sim.com);
                }
                ++count;
            }
        }

        change = Math.abs(first - last);

        SimCountries test = new SimCountries();
        set = new HashSet<>(foodgroup);
        foodgroup = new ArrayList<>(set);

        String tosend = "";
        for (String n : foodgroup) {
            if (count2 == 0) {
                tosend = n;

                ++count2;
            }
            else {
            tosend = n + " | " +tosend;
            }
        }

        test.setall(change, (change/inichange * 100), country, tosend);
        finaloutput.add(test);

    }


    finaloutput.sort(Comparator.comparingDouble(SimCountries::getSimScore).reversed());
        int counter = 0;
        for (SimCountries item : finaloutput) {
            if (counter == loopcount) {
                break;
            }
        if (item.getSimScore() < 100.00) {
        finalString = finalString + "<tr>";
        finalString = finalString + "<td>" + item.getName() + "</td>";
        finalString = finalString + "<td>" + item.getTypes() + "</td>";
        finalString = finalString + "<td>" + df.format(item.getLossPercentage())  + "</td>";
        finalString = finalString + "<td>" + df.format(item.getSimScore())  + "</td>";
        ++counter;
        }
        }
        
    // if ((change/inichange * 100) < 100.0) {
    //     finalString = finalString + "<tr>";
    //     finalString = finalString + "<td>" + country + "</td>";
    //     for (String group : foodgroup) {
    //         if (finalgroups.equals("")) {
    //             finalgroups = group;
    //         }
    //         else {
    //             finalgroups = finalgroups + ", " + group;
    //         }
    //     }
    //     finalString = finalString + "<td>" + finalgroups + "</td>";
    //     finalString = finalString + "<td>" + df.format(change)  + "</td>";
    //     finalString = finalString + "<td>" + df.format(change/inichange * 100)  + "</td>";
    //     }
        

        return finalString;
        } 



    public String getOverAll(String cname, String html, ArrayList<SimCountries> sims) {
        ArrayList<String> countries =  new ArrayList<String>();
        ArrayList<SimCountries> finaloutput =  new ArrayList<SimCountries>();
        

        for (SimCountries sim : sims) {
            countries.add(sim.name);
        }

        String finalString = "";
        //get unique countries
        HashSet<String> set = new HashSet<>(countries);
        countries = new ArrayList<>(set);

        double inichange; 
        int count = 0;
        double first = 0.0;
        double last = 0.0;
        double change = 0.0;
        ArrayList<String> foodgroup = new ArrayList<String>();
        String finalgroups = "";

        for (SimCountries sim : sims) {
            if (sim.name.equals(cname)) {
                if (count == 0) {
                    first = Double.parseDouble(sim.loss);
                    foodgroup.add(sim.com);
                }
                else {
                    last = Double.parseDouble(sim.loss);
                    foodgroup.add(sim.com);
                }
                ++count;
            }
        }
        change = Math.abs(first - last);

        set = new HashSet<>(foodgroup);
        foodgroup = new ArrayList<>(set);


        finalString = finalString + "<tr>";
        finalString = finalString + "<td>(" + cname + ")</td>";
        for (String group : foodgroup) {
            if (finalgroups.equals("")) {
                finalgroups = group;
            }
            else {
                finalgroups = finalgroups + " | " + group;
            }
        }
        finalString = finalString + "<td>" + finalgroups + "</td>";
        finalString = finalString + "<td>" + df.format(change)  + "</td>";
        finalString = finalString + "<td>" + (change/change * 100)  + "</td>";
        inichange = change;


        //for rest of countries
        for (String country : countries) {
            
            count = 0;
            int count2 = 0;
            first = 0.0;
            last = 0.0;
            change = 0.0;
            foodgroup = new ArrayList<String>();
            finalgroups = "";

        for (SimCountries sim : sims) {
            if (sim.getName().equals(country)) {
                if (count == 0) {
                    first = Double.parseDouble(sim.loss);
                    foodgroup.add(sim.com);
                }
                else {
                    last = Double.parseDouble(sim.loss);
                    foodgroup.add(sim.com);
                }
                ++count;
            }
        }

        change = Math.abs(first - last);

        SimCountries test = new SimCountries();
        set = new HashSet<>(foodgroup);
        foodgroup = new ArrayList<>(set);

        String tosend = "";
        for (String n : foodgroup) {
            if (count2 == 0) {
                tosend = n;

                ++count2;
            }
            else {
            tosend = n + " | " +tosend;
            }
        }

        test.setall(change, (change/inichange * 100), country, tosend);
        finaloutput.add(test);

    }


    finaloutput.sort(Comparator.comparingDouble(SimCountries::getSimScore).reversed());
    int counter = 0;
        for (SimCountries item : finaloutput) {
            if (counter == loopcount) {
                break;
            }
        if (item.getSimScore() < 100.00) {
        finalString = finalString + "<tr>";
        finalString = finalString + "<td>" + item.getName() + "</td>";
        finalString = finalString + "<td>" + item.getTypes() + "</td>";
        finalString = finalString + "<td>" + df.format(item.getLossPercentage())  + "</td>";
        finalString = finalString + "<td>" + df.format(item.getSimScore())  + "</td>";
        ++counter;
        }
        }
        
    // if ((change/inichange * 100) < 100.0) {
    //     finalString = finalString + "<tr>";
    //     finalString = finalString + "<td>" + country + "</td>";
    //     for (String group : foodgroup) {
    //         if (finalgroups.equals("")) {
    //             finalgroups = group;
    //         }
    //         else {
    //             finalgroups = finalgroups + ", " + group;
    //         }
    //     }
    //     finalString = finalString + "<td>" + finalgroups + "</td>";
    //     finalString = finalString + "<td>" + df.format(change)  + "</td>";
    //     finalString = finalString + "<td>" + df.format(change/inichange * 100)  + "</td>";
    //     }
        

        return finalString;
        } 
    
        public void toghther() {
            
        }
}
