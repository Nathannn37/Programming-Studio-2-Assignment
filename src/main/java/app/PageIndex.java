package app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

/**
 * Example Index HTML class using Javalin
 * <p>
 * Generate a static HTML page using Javalin
 * by writing the raw HTML into a Java String object
 *
 * @author Timothy Wiley, 2023. email: timothy.wiley@rmit.edu.au
 * @author Santha Sumanasekara, 2021. email: santha.sumanasekara@rmit.edu.au
 */
public class PageIndex implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/";

    @Override
    public void handle(Context context) throws Exception {
        // Create a simple HTML webpage in a String
        String html = "<html>";

        // Add some Header information
        html = html + "<head>" + 
               "<title>Homepage</title>";

        // Add some CSS (external file)
        html = html + "<link rel='stylesheet' type='text/css' href='index.css' />";
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


        html = html + """
          <div class='header'>
              <h1>Homepage</h1>
          </div>
      """;
        
        // Add header content block
        html = html + """
   <div class="content">
    <div class="h2-box">
     <div class="text-content">
       <h2>Did You Know</h2>
       <p>In 1994 Myanmar lost 17.2% of Rice produced!</p>
       <p>An average of 4.3% of food is lost during the harvest stage!</p>
       <p>An Average of 35.5% of Yams produced were lost in 1994!</p>
       <p>In 2015 Mozambique lost the highest percentage of food in a single food stage. It lost 9.97% of Maze(corn)!</p>
       <p>In 2017 India lost the least percentage of food in a single food stage. It lost 0.01% of Meat of cattle with bone!</p>
     </div>
     <div class="video-content">
       <iframe width="700px" src="https://www.youtube.com/embed/wgLuXvtaLyQ" frameborder="0" allowfullscreen></iframe>
     </div>
    </div>
   </div>
        """;

        // Add Div for page Content
        html = html + "<div class='content'>";

        // Add HTML for the list of pages (as well as topnav)
        html = html + """
     <div class="content">
        <div class="h2-box">
         <div class="image-content">
            <img src="foodloss.png" width="700px" height="300px"></img>
        </div>
        <div class="text-content">
         <h2>Data Validity</h2>
         <p>All facts, figures and data shown is found from the database provided and this data has record of countries around the world from 1965 to 2022</p>
         <a href='mission.html'><button class="button-learn-more">Learn More</button></a>
        </div>
      </div>
     </div>
            """;

        // Close Content div
        html = html + "</div>";

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

}


//     public class PageIndex implements Handler {

//     // URL of this page relative to http://localhost:7001/
//     public static final String URL = "/";

//     // Name of the Thymeleaf HTML template page in the resources folder
//     private static final String TEMPLATE = ("index.html");

//     @Override
//     public void handle(Context context) throws Exception {
//         // The model of data to provide to Thymeleaf.
//         // In this example the model will remain empty
//         Map<String, Object> model = new HashMap<String, Object>();

//         JDBCConnection jdbc = new JDBCConnection();

//         ArrayList<Student> stu = jdbc.getstudents();
//         ArrayList<String> id = new ArrayList<String>();
//         ArrayList<String> name = new ArrayList<String>();
//         ArrayList<String> email = new ArrayList<String>();
//         for (Student student : stu ) {
//             id.add(student.snumber);
//             name.add(student.name);
//             email.add(student.email);
//         }


//         model.put("student", id + " " + name + " " + email);

//         // DO NOT MODIFY THIS
//         // Makes Javalin render the webpage using Thymeleaf
//         context.render(TEMPLATE, model);
//     }

// }
