package app;

import java.util.ArrayList;

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

public class PageST3B implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/page3B.html";

    @Override
    public void handle(Context context) throws Exception {
        // Create a simple HTML webpage in a String
        String category = context.formParam("category");
        String option = context.formParam("option");
        String quantity = context.formParam("quantity");

        System.out.println("Category: " + category );
        System.out.println("Option : " + option);
        System.out.println("Quantity: " + quantity);

        String html = "<html>";

        // Add some Head information
        html = html + "<head>" + 
               "<title>Subtask 3.2</title>";

        // Add some CSS (external file)
        html = html + "<link rel='stylesheet' type='text/css' href='common copy.css' />";
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

        // Add header content block
        html = html + """
            <div class='header'>
                <h1>Exploring Food Commodities and Groups</h1>
            </div>
        """;

        // Add Div for page Content
        html = html + "<div class='content'>";

        // Add HTML for the page content
        html = html + """
                
        <div class="container">
        <div class="filter-section">
            <h2>Filter Section</h2>

            <form id='filter-form' action='/page3B.html' method='post'>
        
            <div class='form-group'>
                <div class="category">
                <label for="category">Food Group:</label>
                <select id="category" name="category">
                <option value="Wheat">Wheat</option>
                <option value="Maize (corn)">Maize (corn)</option>
                <option value="Rice">Rice</option>
                <option value="Sorghum">Sorghum</option>
                <option value="Barley">Barley</option>
                <option value="Rye">Rye</option>
                <option value="Oats">Oats</option>
                <option value="Millet">Millet</option>
                <option value="Soya beans">Soya beans</option>
                <option value="Groundnuts, excluding shelled">Groundnuts, excluding shelled</option>
                <option value="Cottonseed">Cottonseed</option>
                <option value="Hen eggs in shell, fresh">Hen eggs in shell, fresh</option>
                <option value="Fonio">Fonio</option>
                <option value="Quinoa">Quinoa</option>
                <option value="Mixed grain">Mixed grain</option>
                <option value="Asparagus">Asparagus</option>
                <option value="Cabbages">Cabbages</option>
                <option value="Cauliflowers and broccoli">Cauliflowers and broccoli</option>
                <option value="Lettuce and chicory">Lettuce and chicory</option>
                <option value="Spinach">Spinach</option>
                <option value="Artichokes">Artichokes</option>
                <option value="Watermelons">Watermelons</option>
                <option value="Cantaloupes and other melons">Cantaloupes and other melons</option>
                <option value="Chillies and peppers, green (<i>Capsicum</i> spp. and <i>Pimenta</i> spp.)">Chillies and peppers, green (<i>Capsicum</i> spp. and <i>Pimenta</i> spp.)</option>
                <option value="Cucumbers and gherkins">Cucumbers and gherkins</option>
                <option value="Eggplants (aubergines)">Eggplants (aubergines)</option>
                <option value="Tomatoes">Tomatoes</option>
                <option value="Pumpkins, squash and gourds">Pumpkins, squash and gourds</option>
                <option value="Okra">Okra</option>
                <option value="Other beans, green">Other beans, green</option>
                <option value="Peas, green">Peas, green</option>
                <option value="Broad beans and horse beans, green">Broad beans and horse beans, green</option>
                <option value="Carrots and turnips">Carrots and turnips</option>
                <option value="Green garlic">Green garlic</option>
                <option value="Onions and shallots, green">Onions and shallots, green</option>
                <option value="Leeks and other alliaceous vegetables">Leeks and other alliaceous vegetables</option>
                <option value="Mushrooms and truffles">Mushrooms and truffles</option>
                <option value="Green corn (maize)">Green corn (maize)</option>
                <option value="Other vegetables, fresh n.e.c.">Other vegetables, fresh n.e.c.</option>
                <option value="Avocados">Avocados</option>
                <option value="Bananas">Bananas</option>
                <option value="Plantains and cooking bananas">Plantains and cooking bananas</option>
                <option value="Dates">Dates</option>
                <option value="Figs">Figs</option>
                <option value="Mangoes, guavas and mangosteens">Mangoes, guavas and mangosteens</option>
                <option value="Papayas">Papayas</option>
                <option value="Pineapples">Pineapples</option>
                <option value="Other tropical and subtropical fruits, n.e.c.">Other tropical and subtropical fruits, n.e.c.</option>
                <option value="Pomelos and grapefruits">Pomelos and grapefruits</option>
                <option value="Lemons and limes">Lemons and limes</option>
                <option value="Oranges">Oranges</option>
                <option value="Tangerines, mandarins, clementines">Tangerines, mandarins, clementines</option>
                <option value="Other citrus fruit, n.e.c.">Other citrus fruit, n.e.c.</option>
                <option value="Grapes">Grapes</option>
                <option value="Apples">Apples</option>
                <option value="Pears">Pears</option>
                <option value="Apricots">Apricots</option>
                <option value="Cherries">Cherries</option>
                <option value="Peaches and nectarines">Peaches and nectarines</option>
                <option value="Plums and sloes">Plums and sloes</option>
                <option value="Other pome fruits">Other pome fruits</option>
                <option value="Other stone fruits">Other stone fruits</option>
                <option value="Kiwi fruit">Kiwi fruit</option>
                <option value="Raspberries">Raspberries</option>
                <option value="Strawberries">Strawberries</option>
                <option value="Blueberries">Blueberries</option>
                <option value="Cranberries">Cranberries</option>
                <option value="Persimmons">Persimmons</option>
                <option value="Other fruits, n.e.c.">Other fruits, n.e.c.</option>
                <option value="Almonds, in shell">Almonds, in shell</option>
                <option value="Cashew nuts, in shell">Cashew nuts, in shell</option>
                <option value="Hazelnuts, in shell">Hazelnuts, in shell</option>
                <option value="Pistachios, in shell">Pistachios, in shell</option>
                <option value="Walnuts, in shell">Walnuts, in shell</option>
                <option value="Areca nuts">Areca nuts</option>
                <option value="Mustard seed">Mustard seed</option>
                <option value="Rape or colza seed">Rape or colza seed</option>
                <option value="Sesame seed">Sesame seed</option>
                <option value="Sunflower seed">Sunflower seed</option>
                <option value="Safflower seed">Safflower seed</option>
                <option value="Olives">Olives</option>
                <option value="Coconuts, in shell">Coconuts, in shell</option>
                <option value="Potatoes">Potatoes</option>
                <option value="Cassava, fresh">Cassava, fresh</option>
                <option value="Cassava, dry">Cassava, dry</option>
                <option value="Sweet potatoes">Sweet potatoes</option>
                <option value="Yams">Yams</option>
                <option value="Edible roots and tubers with high starch or inulin content, n.e.c., fresh">Edible roots and tubers with high starch or inulin content, n.e.c., fresh</option>
                <option value="Edible roots and tubers with high starch or inulin content, n.e.c., dry">Edible roots and tubers with high starch or inulin content, n.e.c., dry</option>
                <option value="Cocoa beans">Cocoa beans</option>
                <option value="Pepper (<i>Piper</i> spp.), raw">Pepper (<i>Piper</i> spp.), raw</option>
                <option value="Chillies and peppers, dry (<i>Capsicum</i> spp., <i>Pimenta</i> spp.), raw">Chillies and peppers, dry (<i>Capsicum</i> spp., <i>Pimenta</i> spp.), raw</option>
                <option value="Anise, badian, coriander, cumin, caraway, fennel and juniper berries, raw">Anise, badian, coriander, cumin, caraway, fennel and juniper berries, raw</option>
                <option value="Other stimulant, spice and aromatic crops, n.e.c.">Other stimulant, spice and aromatic crops, n.e.c.</option>
                <option value="Beans, dry">Beans, dry</option>
                <option value="Broad beans and horse beans, dry">Broad beans and horse beans, dry</option>
                <option value="Chick peas, dry">Chick peas, dry</option>
                <option value="Lentils, dry">Lentils, dry</option>
                <option value="Peas, dry">Peas, dry</option>
                <option value="Cow peas, dry">Cow peas, dry</option>
                <option value="Pigeon peas, dry">Pigeon peas, dry</option>
                <option value="Bambara beans, dry">Bambara beans, dry</option>
                <option value="Other pulses n.e.c.">Other pulses n.e.c.</option>
                <option value="Sugar beet">Sugar beet</option>
                <option value="Sugar cane">Sugar cane</option>
                <option value="Swedes, for forage">Swedes, for forage</option>
                <option value="Other legumes, for forage">Other legumes, for forage</option>
                <option value="Vegetable products, fresh or dry n.e.c.">Vegetable products, fresh or dry n.e.c.</option>
                <option value="Cattle">Cattle</option>
                <option value="Camels">Camels</option>
                <option value="Sheep">Sheep</option>
                <option value="Goats">Goats</option>
                <option value="Soya bean oil">Soya bean oil</option>
                <option value="Raw milk of cattle">Raw milk of cattle</option>
                <option value="Snails, fresh, chilled, frozen, dried, salted or in brine, except sea snails">Snails, fresh, chilled, frozen, dried, salted or in brine, except sea snails</option>
                <option value="Meat of cattle with the bone, fresh or chilled">Meat of cattle with the bone, fresh or chilled</option>
                <option value="Meat of pig with the bone, fresh or chilled">Meat of pig with the bone, fresh or chilled</option>
                <option value="Meat of sheep, fresh or chilled">Meat of sheep, fresh or chilled</option>
                <option value="Meat of goat, fresh or chilled">Meat of goat, fresh or chilled</option>
                <option value="Meat of goat, fresh or chilled (indigenous)">Meat of goat, fresh or chilled (indigenous)</option>
                <option value="Meat of chickens, fresh or chilled">Meat of chickens, fresh or chilled</option>
                <option value="Pig meat, cuts, salted, dried or smoked (bacon and ham)">Pig meat, cuts, salted, dried or smoked (bacon and ham)</option>
                <option value="Sweet corn, frozen">Sweet corn, frozen</option>
                <option value="Canned mushrooms">Canned mushrooms</option>
                <option value="Sweet corn, prepared or preserved">Sweet corn, prepared or preserved</option>
                <option value="Raisins">Raisins</option>
                <option value="Plums, dried">Plums, dried</option>
                <option value="Groundnuts, shelled">Groundnuts, shelled</option>
                <option value="Orange juice">Orange juice</option>
                <option value="Grapefruit juice">Grapefruit juice</option>
                <option value="Pineapple juice">Pineapple juice</option>
                <option value="Grape juice">Grape juice</option>
                <option value="Apple juice">Apple juice</option>
                <option value="Juice of lemon">Juice of lemon</option>
                <option value="Juice of citrus fruit n.e.c.">Juice of citrus fruit n.e.c.</option>
                <option value="Sunflower-seed oil, crude">Sunflower-seed oil, crude</option>
                <option value="Cheese from whole cow milk">Cheese from whole cow milk</option>
                <option value="Dairy products n.e.c.">Dairy products n.e.c.</option>
                <option value="Flour of buckwheat">Flour of buckwheat</option>
                <option value="Flour of triticale">Flour of triticale</option>
                <option value="Rice, milled">Rice, milled</option>
                <option value="Flour of cassava">Flour of cassava</option>
                <option value="Sugar and syrups n.e.c.">Sugar and syrups n.e.c.</option>
                <option value="Tapioca of cassava">Tapioca of cassava</option>
                <option value="Uncooked pasta, not stuffed or otherwise prepared">Uncooked pasta, not stuffed or otherwise prepared</option>
                <option value="Eggs, dried">Eggs, dried</option>
                <option value="Wine">Wine</option>




                </select>
                </div>

                
                <div class="radio-group">
                <label><input type="radio" id="ratio" name="option" value="ratio">Similiarity in terms of the ratio of food loss to food waste (percentage average)</label>
                <label><input type="radio" id="highest" name="option" value="highest">Similiarity in terms of the highest percentage of food loss/waste product</label>
                <label><input type="radio" id="lowest" name="option" value="lowest">Similiarity in terms of the lowest percentage of food loss/waste product</label>
                </div>

                <label for="quantity">Choose number of groups to locate:</label>
                <input type="number" id="quantity" name="quantity" min="1" max="20">
                <br>

                <button type="submit" class="btn btn-primary">Search</button>
                </div>
            </form>
                    """;
            if (category != null && quantity != null && option != null) {
                html += outputSimiliarity(category, quantity, option);
            }
            else {

                    // Placeholder data table
            html += "<div class='table-section'>";
            html += "<h2>Data Table</h2>";
            html += "<table id='dataTable' class='data-table'>";
            html += "<thead>";
            html += "<tr>";
            html += "<th>Commodity</th>";
            html += "<th>CPC Code</th>";
            html += "<th>Group</th>";
            html += "<th>Loss Percentage</th>";
            html += "<th>Similiarity (%)</th>";
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
           
            html += "</tr>";

            html += "</tbody>";
            html += "</table>";
            html += "</div>";

            }
        html += """
                
        </div>
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

    public String outputSimiliarity(String category, String quantity, String option) {
        String html = "";
        JDBCConnection jdbc = new JDBCConnection();

        ArrayList<Similiarity> data = jdbc.getSimiliarity(category, quantity, option);

        if (data.isEmpty()) {
            html += "<div class='table-section'>";
            html += "<h2>Data Table</h2>";
            html += "<table>";
            html += "<thead>";
            html += "<tr>";
            html += "<th>Commodity</th>";
            html += "<th>CPC Code</th>";
            html += "<th>Group</th>";
            html += "<th>Loss Percentage</th>";
            html += "<th>Similiarity (%)</th>";
            html += "</tr>";
            html += "</thead>";
            html += "<tbody>";

            html += "<tr>";
            html += "<td>No Data Available</td>";
            html += "</tr>";
        }
        else {

            html += "<div class='table-section'>";
            html += "<h2>Data Table</h2>";
            html += "<table>";
            html += "<thead>";
            html += "<tr>";
            html += "<th>Commodity</th>";
            html += "<th>CPC Code</th>";
            html += "<th>Group</th>";
            html += "<th>Loss Percentage</th>";
            html += "<th>Similiarity (%)</th>";
            html += "</tr>";
            html += "</thead>";
            html += "<tbody>";
        

        for (Similiarity group : data) {
            html += "<tr>";
            html += "<td>" + group.getCommodity() + "</td>";
            html += "<td>" + group.getCpc_code() + "</td>";
            html += "<td>" + group.getParentCpcName() + "</td>";
            html += "<td>" + group.getLossPercentage() + "</td>";
            html += "<td>" + group.getSimilarity() + "</td>";
            html += "</tr>";
            
        }

        html += "</tbody>";
        html += "</table>";
        html += "</div>";
        }

        return html;
    }

}
