package app;

import java.util.ArrayList;
import java.util.List;

import io.javalin.http.Context;
import io.javalin.http.Handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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

public class PageST2B implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/page2B.html";

    @Override
    public void handle(Context context) throws Exception {
        // Create a simple HTML webpage in a String
        String html = "<html>";

        String startYear = context.formParam("start_year");
        String endYear = context.formParam("end_year");
        List<String> categories = context.formParams("category");
        String activity = context.formParam("activity");
        String foodSupplyStage = context.formParam("foodSupplyStage");
        String causeOfLoss = context.formParam("causeOfLoss");
        String sort = context.formParam("sort");

        System.out.println("Start Year: " + startYear);
        System.out.println("End Year: " + endYear);
        System.out.println("Categories: " + categories);
        System.out.println("Activity:" + activity);
        System.out.println("Food Supply Stage:" + foodSupplyStage);
        System.out.println("Cause Of Loss:" + causeOfLoss);
        System.out.println("Sort: " + sort);



        // Add some Head information
        html = html + "<head>" + 
               "<title>Subtask 2.2</title>";

        // Add some CSS (external file)
        html = html + "<link rel='stylesheet' type='text/css' href='common copy.css' />";
        html = html + "</head>";

        // Add the body
        html = html + "<body>";

        // Add the topnav
        // This uses a Java v15+ Text Block
        html = html + """

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

        // Add header content block
        html = html + """
            <div class='header'>
                <h1>Food loss/waste change by Food Group</h1>
            </div>
        """;

        // Add Div for page Content
        html = html + "<div class='content'>";

        
        html = html + """
                
        <div class="container">
        <div class="filter-section">
            <h2>Filter Section</h2>

            <form id='filter-form' action='/page2B.html' method='post'>
        
            <div class='form-group'>
            
                
                <label for="start-year">Start Year:</label>
                <select id="start_year" name="start_year">
                    <!-- Options will be populated by JavaScript -->
                    <option value= disabled selected hidden>Choose Year</option>";
                </select>
                <br>

                <label for="end-year">End Year:  </label>
                <select id="end_year" name="end_year">
                    <!-- Options will be populated by JavaScript -->
                    <option value= disabled selected hidden>Choose Year</option>";
                </select>
                <br>

                <label for="category">Food Group:</label>
                <a href='mission.html'>To select multiple Food Groups, hold 'Ctrl' on keyboard and click desired Food Groups</a>
                <br>
                <br>
                <select id="category" name="category" multiple>
                    <option value="Cereal">Cereal</option>
                    <option value="Vegetables ">Vegetables </option>
                    <option value="Fruit and nuts ">Fruit and nuts </option>
                    <option value="Oilseeds and oleaginous fruits ">Oilseeds and oleaginous fruits </option>
                    <option value="Editble roots and tubers with high starch or inulin content">Editble roots and tubers with high starch or inulin content</option>
                    <option value="Stimulant, spice, and aromatic crops">Stimulant, spice, and aromatic crops</option>
                    <option value="Pulses (dried leguminous vegetables)">Pulses (dried leguminous vegetables)</option>
                    <option value="Sugar crops">Sugar crops</option>
                    <option value="Forage products">Forage products</option>
                    <option value="Live animals">Live animals</option>
                    <option value="Raw milk ">Raw milk </option>
                    <option value="Meat and meat products">Meat and meat products</option>
                    <option value="Prepared and preserved vegetables, pulses and potatoes ">Prepared and preserved vegetables, pulses and potatoes </option>
                    <option value="Prepared and preserved fruit and nuts">Prepared and preserved fruit and nuts</option>
                    <option value="Vegetable oils">Vegetable oils</option>
                    <option value="Other diary products">Other diary products</option>
                    <option value="Grain mill products">Grain mill products</option>
                    <option value="Starches and starch products">Starches and starch products</option>
                    <option value="Macaroni, noodles, couscous and similar fairaceous products">Macaroni, noodles, couscous and similar fairaceous products</option>
                    <option value="Food products">Food products</option>
                    <option value="Wines">Wines</option>
                </select>
                
                <div class="radio-group">
                <label><input type="checkbox" id="activity" name="activity" value="activity"> Activity</label>
                <label><input type="checkbox" id="foodSupplyStage" name="foodSupplyStage" value="foodSupplyStage"> Food Supply Stage</label>
                <label><input type="checkbox" id="causeOfLoss" name="causeOfLoss" value="causeOfLoss"> Cause of loss</label>
                </div>

                <label for="sort">Sort in:</label>
                <br>
                <select id="sort" name="sort">
                    <option value="Ascending">Ascending order</option>
                    <option value="Descending">Descending order</option>
                </select>
                <br>
                
                <button type="submit" class="btn btn-primary">Apply</button>
                </div>
            </form>
        </div>
        """;

        if (startYear != null && endYear != null && !categories.equals("[]")
                && (activity == null || "activity".equals(activity))
                && (foodSupplyStage == null || "foodSupplyStage".equals(foodSupplyStage))
                && (causeOfLoss == null || "causeOfLoss".equals(causeOfLoss))) {
            // Prepare parameters ensuring null is converted to "null" string
            String finalActivity = (activity == null) ? "null" : activity;
            String finalFoodSupplyStage = (foodSupplyStage == null) ? "null" : foodSupplyStage;
            String finalCauseOfLoss = (causeOfLoss == null) ? "null" : causeOfLoss;

            html = html + outputGroupNameLoss(startYear, endYear, categories, finalActivity, finalFoodSupplyStage, finalCauseOfLoss, sort);
        }       
        else {
                    // Placeholder data table
            html += "<div class='table-section'>";
            html += "<h2>Data Table</h2>";
            html += "<table id='dataTable' class='data-table'>";
            html += "<thead>";
            html += "<tr>";
            html += "<th>Group Name</th>";
            html += "<th>Activity</th>";
            html += "<th>Food Supply Stage</th>";
            html += "<th>Cause of Loss</th>";
            html += "<th>Start Year Percentage Loss</th>";
            html += "<th>End Year Percentage Loss</th>";
            html += "<th>Difference in Percentage Loss</th>";
            html += "</tr>";
            html += "</thead>";
            html += "<tbody>";
            
            // Placeholder row for loading state
            html += "<tr id='loadingRow'>";
            html += "<td></td>";
            html += "<td></td>";
            html += "<td></td>";
            html += "<td></td>";
            html += "<td></td>";
            html += "<td></td>";
            html += "<td></td>";
           
            html += "</tr>";

            html += "</tbody>";
            html += "</table>";
            html += "</div>";
        }

        html = html + """ 
        
    </div>

    <script>
        // JavaScript to populate the year selectors
        document.addEventListener('DOMContentLoaded', () => {
            const startYearSelect = document.getElementById('start_year');
            const endYearSelect = document.getElementById('end_year');
            const currentYear = new Date().getFullYear();
            const startYear = 1966; // Starting year for the dropdown

            for (let year = startYear; year <= currentYear; year++) {
                const optionStart = document.createElement('option');
                const optionEnd = document.createElement('option');
                optionStart.value = year;
                optionStart.textContent = year;
                optionEnd.value = year;
                optionEnd.textContent = year;
                startYearSelect.appendChild(optionStart);
                endYearSelect.appendChild(optionEnd);
            }
        });
    </script>
            """;

        // Close Content div
        html = html + "</div>";

        // Footer
        JDBCConnection jdbc = new JDBCConnection();
        ArrayList<Student> students = jdbc.getstudents();
        Student stu = null;

        // Footer
        html = html + """
                 <div class='footer'>
            <div class="contentf">
            <a href='mission.html'>How to use and navigate</a>
            <p class:'footheader'>Contact Details: (using database)</p>
                """;
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
    
    public String outputGroupNameLoss(String startYear, String endYear, List<String> categories, String activity, String foodSupplyStage, String causeOfLoss, String sort) {
        String html = "";
        JDBCConnection jdbc = new JDBCConnection();
    
        // Retrieve data from JDBC
        ArrayList<FoodGroup> data = jdbc.getGroupNameLossData(startYear, endYear, categories, activity, foodSupplyStage, causeOfLoss, sort);
    
                // Start table
            html += "<div class='table-section'>";
            html += "<h2>Data Table</h2>";
            html += "<table>";
            html += "<thead>";
            html += "<tr>";
            html += "<th>Group Name</th>";
            html += "<th>Activity</th>";
            html += "<th>Food Supply Stage</th>";
            html += "<th>Cause of Loss</th>";
            html += "<th>Start Year Percentage Loss (" + startYear + ")</th>";
            html += "<th>End Year Percentage Loss (" + endYear + ")</th>";
            html += "<th>Difference in Percentage Loss</th>";
            html += "</tr>";
            html += "</thead>";
            html += "<tbody>";

            // Check if data is empty
            if (data.isEmpty()) {
                html += "<tr>";
                html += "<td colspan='7'>No Data Available</td>";
                html += "</tr>";
            } else {
                // Populate table rows with data
                for (FoodGroup group : data) {
                    html += "<tr>";
                    html += "<td>" + group.getFoodGroupName() + "</td>";
                    html += "<td>" + group.getActivity() + "</td>";
                    html += "<td>" + group.getFoodSupplyStage() + "</td>";
                    html += "<td>" + group.getCauseOfLoss() + "</td>";
                    html += "<td>" + group.getStartYear() + "</td>";
                    html += "<td>" + group.getEndYear() + "</td>";
                    html += "<td>" + group.getDifference() + "</td>";
                    html += "</tr>";
                }
            }

            html += "</tbody>";
            html += "</table>";
            html += "</div>";

            return html;
        }
    
    
}
