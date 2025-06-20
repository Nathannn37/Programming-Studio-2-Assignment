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

public class PageMission implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/mission.html";

    @Override
    public void handle(Context context) throws Exception {
        // Create a simple HTML webpage in a String
        String html = "<html>";

        // Add some Head information
        html = html + "<head>" + 
               "<title>Our Mission</title>";

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
                <h1>Our Mission</h1>
            </div>
        """;

        // Add Div for page Content
        html = html + "<div class='content'>";

        // Add HTML for the page content
        html = html + """
            <h2>How we are addressing the social challenge of food loss/waste</h2>
            """;

        html = html + """
            <p>Food Wastage's goal is to present reliable and unbiased information and data on the pressing issue of Food loss and waste,
            a significant social and environmental challenge. The aim of this website is to provide valueable insights and actionable data 
            to help reduce food waste, by highlighting what stage in the food process caused the food loss/waste. We are addressing the social 
            challenge by educating users on how severe the problem is and by showcasing the criticial points in the food process that need 
            immediate attention. 
            </p>
            <br>
            <p>
            Our site can be used to explore and gather data by utilising the filter options to pin point specific data sets, with the filter 
            options it allows for personal and customisable searchs. Each page displays different levels of data which accommodates for all user's levels 
            whether they are after food loss by year, country or region, food groups, food commodity, activity, cause of loss or food supply stage, we 
            provide it all. 
            </p>
            <br>
            """;

        html = html + """
            <h2>How to use and navigate through Food Wastage</h2>
            """;

        html = html + """
            <div class="slideshow-container">

            <b>
            <div class="mySlides fade">
            <div class="numbertext">1 / 5</div>
            <img src="NavBar.png" style="width:100%">
            <div class="text">Navigation bar click on desired page</div>
            </div>

            <div class="mySlides fade">
            <div class="numbertext">2 / 5</div>
            <img src="Year.png" style="width:100%">
            <div class="text">Select desired year</div>
            </div>

            <div class="mySlides fade">
            <div class="numbertext">3 / 5</div>
            <img src="FoodGroup.png" style="width:100%">
            <div class="text">To select multiple Food Groups (Hold 'ctrl' on keyboard and click desired Food Groups)</div>
            </div>

            <div class="mySlides fade">
            <div class="numbertext">4 / 5</div>
            <img src="Checkbox.png" style="width:100%">
            <div class="text">Check the box you want to display in data</div>
            </div>

            <div class="mySlides fade">
            <div class="numbertext">5 / 5</div>
            <img src="Button.png" style="width:100%">
            <div class="text">Click button to apply filter options</div>
            </div>
            </b>


            <a class="prev" onclick="plusSlides(-1)"><</a>
            <a class="next" onclick="plusSlides(1)">></a>

            </div>
            <br>

            <div style="text-align:center">
            <span class="dot" onclick="currentSlide(1)"></span> 
            <span class="dot" onclick="currentSlide(2)"></span> 
            <span class="dot" onclick="currentSlide(3)"></span> 
            <span class="dot" onclick="currentSlide(4)"></span> 
            <span class="dot" onclick="currentSlide(5)"></span> 
            </div>

            """;

        html = html + "<br>";

        html = html + "<form action='/mission.html' method='post'>";
        
        html = html + "   <div class='form-group'>";
        html = html + "      <label for='personatype_drop' >Select the persona (Dropdown):</label>";
        html = html + "      <select id='personatype_drop' name='personatype_drop'>";
        html = html + "         <option value= disabled selected hidden>Choose Persona</option>";
        html = html + "         <option>Aaliyah Giordano</option>";
        html = html + "         <option>Joe Fixit</option>";
        html = html + "         <option>Nelson Klerk</option>";
        html = html + "         <option>ALL-PERSONAS</option>";
        html = html + "      </select>";
        html = html + "   </div>";

        html = html + "   <button type='submit' class='btn btn-primary'>Display persona</button>";

        String personatype_drop = context.formParam("personatype_drop");
        // String movietype_drop = context.queryParam("movietype_drop");
        if (personatype_drop == null) {
            // If NULL, nothing to show, therefore we make some "no results" HTML
            html = html + "<h2><i>Select a persona</i></h2>";
        } else {
            // If NOT NULL, then lookup the movie by type!
            html = html + outputPersona(personatype_drop);
        }

        html += """
            <script>
            let slideIndex = 1;
            showSlides(slideIndex);
            
            function plusSlides(n) {
              showSlides(slideIndex += n);
            }
            
            function currentSlide(n) {
              showSlides(slideIndex = n);
            }
            
            function showSlides(n) {
              let i;
              let slides = document.getElementsByClassName("mySlides");
              let dots = document.getElementsByClassName("dot");
              if (n > slides.length) {slideIndex = 1}    
              if (n < 1) {slideIndex = slides.length}
              for (i = 0; i < slides.length; i++) {
                slides[i].style.display = "none";  
              }
              for (i = 0; i < dots.length; i++) {
                dots[i].className = dots[i].className.replace(" active", "");
              }
              slides[slideIndex-1].style.display = "block";  
              dots[slideIndex-1].className += " active";
            }
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

    public String outputPersona(String type) {
        String html = "";
        JDBCConnection jdbc = new JDBCConnection();
        
        ArrayList<Persona> personas = jdbc.getPersona(type);
                      
        for (Persona persona : personas) {
            html += "<br>";
            html += "<div style='overflow: hidden; margin-bottom: 20px;'>"; // Container for image and text
            html += "<img src='" + persona.image + "' alt='" + type + "' style='float:right; width:40%; height:70%; margin-left: 20px;'>";
            html += persona.description;
            html += "</div>";
            html += "<br>";
        }
        return html;
    
    }

}
