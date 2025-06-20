package app;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

import io.javalin.http.Context;
import io.javalin.http.Handler;


import java.math.RoundingMode;
import java.text.DecimalFormat;

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

public class PageST2A implements Handler {

    public static final DecimalFormat df = new DecimalFormat("0.00");
    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/page2A.html";

    @Override
    public void handle(Context context) throws Exception {
        // Create a simple HTML webpage in a String
        String html = "<html>";

        // Add some Head information
        html = html + "<head>" + 
               "<title>Subtask 2.1</title>";

        // Add some CSS (external file)
        html = html + "<link rel='stylesheet' type='text/css' href='index.css' />";
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
                <h1>Food loss/waste change by Country</h1>
            </div>
        """;

        html = html + """

        <div class='content' >

        <div class='forms'>
        <form action='/page2A.html' method='post'>

        <div>--------------------------</div>
        <div class="form-group">
        <label for="country_select">Select Country</label>
        <select id="country_select" name="country_select" multiple>
        </select>
        
        <div>--------------------------</div>
        <label for="start_year_select">Select Start Year:</label>
        <select id="start_year_select" name="start_year_select">
        </select>
        
        
        <label for="end_year_select">Select End Year:</label>
        <select id="end_year_select" name="end_year_select">
        </select>

        <div>--------------------------</div>
        <label><input type="checkbox" name="all_years" id="all_years">All Years</label>
        <label><input type="checkbox" name="commodity_select" id="commodity_select">Commodity</label>
        <div>--------------------------</div>
        <label><input type="checkbox" name="Activity" id="Activity">Activity</label>
        <label><input type="checkbox" name="Cause" id="Cause">Cause</label>
        <label><input type="checkbox" name="supply" id="supply">Supply Stage</label>
        <label><input type="checkbox" name="acc" id="acc">Toggle Decending (Accending By Default)</label>
        <div>--------------------------</div>
        
        </div>
        <button type='submit' class='btn btn-primary'>Get table</button>
        </div>
        </div>
    <script>

        const years = Array.from({ length: 2022 - 1965 + 1 }, (_, i) => 1965 + i);

        const startYearSelect = document.getElementById('start_year_select');
        const endYearSelect = document.getElementById('end_year_select');

        years.forEach(year => { 
            const startOption = document.createElement('option');
            startOption.value = year;
            startOption.textContent = year;
            startYearSelect.appendChild(startOption);

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


        // Add Div for page Content
        html = html + """
                <div class='divide'>
                <p><p>
                </div>
                """;
                String syear = "";
                String eyear = "";
                boolean activity= false;
                boolean cause = false; 
                boolean all_years = true;
                boolean com = false;
                boolean supply = false;
                
                List<String> country_select = context.formParams("country_select");
                String start_year_select=context.formParam("start_year_select");
                String end_year_select=context.formParam("end_year_select");
                String activity_box = context.formParam("Activity");
                String cause_box = context.formParam("Cause");
                String all_years_box = context.formParam("all_years");
                String com_box = context.formParam("commodity_select");
                String supply_box = context.formParam("supply");
                String acc = context.formParam("acc");
                
                String name = ""; 
                int length = country_select.size();
                // String movietype_drop = context.queryParam("movietype_drop");
                if (country_select.equals("[]") || country_select==null) {
                    // If NULL, nothing to show, therefore we make some "no results" HTML
                   name = country_select.get(0);
                } else {
                    // If NOT NULL, then lookup the movie by type!

                }

                if (start_year_select == null) {
                    // If NULL, nothing to show, therefore we make some "no results" HTML
                    syear="";
                } else {
                    // If NOT NULL, then lookup the movie by type!
                    syear = start_year_select;
                }

                if (end_year_select == null) {
                    // If NULL, nothing to show, therefore we make some "no results" HTML
                    eyear="";
                } else {
                    // If NOT NULL, then lookup the movie by type!
                    eyear = end_year_select;
                }

                if (end_year_select == null) {
                    // If NULL, nothing to show, therefore we make some "no results" HTML
                    eyear="";
                } else {
                    // If NOT NULL, then lookup the movie by type!
                    eyear = end_year_select;
                }

                if (activity_box == null) {
                    // If NULL, nothing to show, therefore we make some "no results" HTML
                    activity=false;
                } else {
                    // If NOT NULL, then lookup the movie by type!
                    activity=true;
                }

                if (cause_box == null) {
                    // If NULL, nothing to show, therefore we make some "no results" HTML
                    cause=false;
                } else {
                    // If NOT NULL, then lookup the movie by type!
                    cause=true;
                }

                if (all_years_box == null) {
                    // If NULL, nothing to show, therefore we make some "no results" HTML
                    all_years=false;
                } else {
                    // If NOT NULL, then lookup the movie by type!
                    all_years=true;
                }
                if (com_box == null) {
                    com = false;
                }
                else {
                    com = true;
                }
                if (supply_box == null) {
                    supply = false;
                }
                else {
                    supply = true;
                }
                boolean acc_b = false;
                if (acc == null) {
                    acc_b = false;
                }
                else {
                    acc_b = true; 
                }
                

                //table setup
                html = html + "</div class='table-content'>";
                    html = html + """
                            <table width=700>
                                <tr>
                                    <th width=100>Country</th>
                                    <th width=100>Year</th>
                                    <th width=100>Change In Loss Percentage(%)</th>
                            """;
                    if (com) {
                        html = html + "<th width=100>Commodity</th>";
                    }
                    if (supply) {
                        html = html + "<th width=100>Suuply Stage</th>";
                    }
                    if (activity) {
                        html = html + "<th width=100>Activity</th>";
                    }
                    if (cause) {
                        html = html + "<th width=100>Cause</th>";
                    }
                    
                    html = html + "</tr></div>";




                System.out.println("name:" + country_select);
                System.out.println("Start Year: " + syear);
                System.out.println("End Year: " + eyear);
                System.out.println("Activity: " + activity);
                System.out.println("cause: " + cause);

                boolean flag = false;

                ArrayList<CountriesTable> countries = new ArrayList<CountriesTable>();
                
                if (acc_b) {
                    Collections.sort(country_select, Collections.reverseOrder());
                }
                else {
                    Collections.sort(country_select);
                }


                int countcheck = 0;
                JDBCConnection jdbc = new JDBCConnection();
                html = html + "<div class='content'>";
                for (int i=0 ; i<length; ++i) {
                    countries = jdbc.getCountry(country_select.get(i), syear, eyear, activity, cause, acc_b);
                    ArrayList<CountriesTable> countriesToSend = new ArrayList<CountriesTable>();
                    int count = 0;
                    int lindex = countries.size()-1;
                    // System.out.println(firstcurr.getCountryName());
                    CountriesTable last = null;
                    
                    //data in
                for (CountriesTable country : countries) {
                    CountriesTable firstcurr = countries.get(0);
                        if (country.getYear() >= Integer.parseInt(syear) && country.getYear() <= Integer.parseInt(eyear) && !country.getlosspercent().equals("")) {
                        countriesToSend.add(country);
                        }
                        if(all_years && com) {
                            if (count == lindex) {
                                store content = new store(countriesToSend);
                                html = html + content.yearsandcom(supply,activity,cause);
                                flag = true;
                                countcheck ++;
                                 }
                        }
                        else if (all_years && !com) {
                            if (count == lindex) {
                                store content = new store(countriesToSend);
                                html = html + content.allyears(supply,activity,cause);
                                flag = true;
                                countcheck ++;
                                 }
                        }
                        else if (!all_years && com) {
                            if (count == lindex) {
                           store content = new store(countriesToSend);
                           html = html + content.output(supply,activity,cause);
                           flag = true;
                           countcheck ++;
                            }
                        }
                        else if (!all_years && !com) {
                            if (countriesToSend.isEmpty()) {
                                firstcurr = countries.get(0);
                            }

                            else {
                                firstcurr = countriesToSend.get(0);
                            }

                            if (count == lindex && countries.size() !=0 && countriesToSend.size() !=0) {
                                lindex = countriesToSend.size() - 1;
                                last = countriesToSend.get(lindex);
                                html = html + "<tr>";   
                                html = html + "<td>" + firstcurr.getCountryName() + "</td>";
                                html = html + "<td>" + firstcurr.getYear() + "-" + last.getYear()+ "</td>";
                                html = html + "<td>" + df.format(Math.abs(Double.parseDouble(firstcurr.getlosspercent()) - Double.parseDouble(last.getlosspercent()))) + "</td>";
                                flag = true;
                                countcheck ++;
                                if (supply) {
                                    if (firstcurr.getsupplystage().equals("")) {
                                        html = html + "<td>N/A</td>";
                                    }
                                    else {
                                    html = html + "<td>" + firstcurr.getsupplystage() +"</td>";
                                    }
                                }
                                if (activity) {
                                    if (firstcurr.getactivity()==null || firstcurr.getactivity().equals("")) {
                                        html = html + "<td>N/A</td>";
                                    }
                                    else {
                                    html = html + "<td>" + firstcurr.getactivity() +"</td>";
                                    }
                                }
                                if (cause) {
                                    if (firstcurr.getcause().equals("")) {
                                        html = html + "<td>N/A</td>";
                                    }
                                    else {
                                    html = html + "<td>" + firstcurr.getcause() +"</td>";
                                    }
                                }

                            }
                        }


                        ++count;
                }

                //just in case
                // if (firstcurr.getcom().compareTo(country.getcom()) != 0) {
                //     html = html + "<tr>";   
                //     html = html + "<td>" + firstcurr.getCountryName() + "</td>";
                //     html = html + "<td>" + firstcurr.getYear() + "-" + country.getYear()+ "</td>";
                //     html = html + "<td>" + df.format(Math.abs(Double.parseDouble(firstcurr.getlosspercent()) - Double.parseDouble(country.getlosspercent()))) + "</td>";
                //     html = html + "<td>"+ firstcurr.getcom() +"</td>";
                //     firstcurr = country;
                //     flag = true;
                // }
                // else if (count == lindex) {
                //     html = html + "<tr>";   
                //     html = html + "<td>" + firstcurr.getCountryName() + "</td>";
                //     html = html + "<td>" + firstcurr.getYear() + "-" + country.getYear()+ "</td>";
                //     html = html + "<td>" + df.format(Math.abs(Double.parseDouble(firstcurr.getlosspercent()) - Double.parseDouble(country.getlosspercent()))) + "</td>";
                //     html = html + "<td>"+ firstcurr.getcom() +"</td>";
                //     flag = true;
                // }



            //     if (all_years && !com) {
            //     if (ylast != pri.getYear() ) {
            //         html = html + "<tr>";
            //         html = html + "<td>" +pri.getCountryName()+ "</td>";
            //         html = html + "<td>"+ pri.getYear()+"</td>";
            //         if (firstcurr.getlosspercent().equals(pri.getlosspercent())) {
            //             html = html + "<td>" + df.format(Math.abs((Double.parseDouble(pri.getlosspercent())))) +"</td>";
            //         }
            //         else {
            //         html = html + "<td>" + df.format(Math.abs((Double.parseDouble(firstcurr.getlosspercent()))-(Double.parseDouble(pri.getlosspercent())))) +"</td>";
            //         }
            //     } 
            // }
            // else if (com && !all_years) {
            //     if (clast.compareTo(pri.getcom()) != 0) {
            //         html = html + "<tr>";
            //         html = html + "<td>" +pri.getCountryName()+ "</td>";
            //         html = html + "<td>"+ pri.getYear()+"</td>";
            //         if (firstcurr.getlosspercent().equals(pri.getlosspercent())) {
            //             html = html + "<td>" + df.format(Math.abs((Double.parseDouble(pri.getlosspercent())))) +"</td>";
            //         }
            //         else {
            //         html = html + "<td>" + df.format(Math.abs((Double.parseDouble(firstcurr.getlosspercent()))-(Double.parseDouble(pri.getlosspercent())))) +"</td>";
            //         }
            //         html = html + "<td>" + pri.getcom() + "</td>"; 
            //         if (supply) {
            //             html = html + "<td>"+ pri.getsupplystage()+"</td>";
            //         }
            //         if (activity) {
            //             html = html + "<td>"+ pri.getactivity()+"</td>";
            //         }
            //         if (cause) {
            //             html = html + "<td>"+ pri.getcause()+"</td>";
            //         }
            //     }
            //     else {
            //         if (lname.compareTo(pri.getCountryName()) != 0) {

            //         }
            //         else {
            //         html = html + "<tr>";
            //         html = html + "<td>" +pri.getCountryName()+ "</td>";
            //         html = html + "<td>"+ pri.getYear()+"</td>";
            //         if (firstcurr.getlosspercent().equals(pri.getlosspercent())) {
            //             html = html + "<td>" + df.format(Math.abs((Double.parseDouble(pri.getlosspercent())))) +"</td>";
            //         }
            //         else {
            //         html = html + "<td>" + df.format(Math.abs((Double.parseDouble(firstcurr.getlosspercent()))-(Double.parseDouble(pri.getlosspercent())))) +"</td>";
            //         }
            //         html = html + "<td>" + pri.getcom() + "</td>";
                    
            //         clast = pri.getcom();
            //         if (supply) {
            //             html = html + "<td>"+ pri.getsupplystage()+"</td>";
            //         }
            //         if (activity) {
            //             html = html + "<td>"+ pri.getactivity()+"</td>";
            //         }
            //         if (cause) {
            //             html = html + "<td>"+ pri.getcause()+"</td>";
            //         }
            //         }
            //         if (supply) {
            //             html = html + "<td>"+ pri.getsupplystage()+"</td>";
            //         }
            //         if (activity) {
            //             html = html + "<td>"+ pri.getactivity()+"</td>";
            //         }
            //         if (cause) {
            //             html = html + "<td>"+ pri.getcause()+"</td>";
            //         }

            //     }
            // }
                    
                }





                

                // html = html + "<div class='content'>";
                // for (CountriesTable country : countries) {
                //     if ((country.getCountryName()).compareTo(name) == 0) {
                //         if ((country.getYear() >= Integer.parseInt(syear)) && (country.getYear() <= Integer.parseInt(eyear))) {
                //             if (activity && cause) {
                //                 html = html + "<tr>";
                //                 html = html + "<td width=100>" + name + "</td>";
                //                 html = html + "<td width=100>" + country.getcom() + "</td>";
                //                 html = html + "<td width=100>" + country.getYear() + "</td>";
                //                 html = html + "<td width=100>" + country.getlosspercent() + "</td>";
                //                 html = html + "<td width=100>" + country.getsupplystage() + "</td>";
                //                 html = html + "<td width=100>" + country.getcause() + "</td>";
                //                 html = html + "<td width=100>" + country.getactivity() + "</td>";
                //                 flag = true;
                //             }
                //             else if (cause) {
                //                 html = html + "<tr>";
                //                 html = html + "<td width=100>" + name + "</td>";
                //                 html = html + "<td width=100>" + country.getcom() + "</td>";
                //                 html = html + "<td width=100>" + country.getYear() + "</td>";
                //                 html = html + "<td width=100>" + country.getlosspercent() + "</td>";
                //                 html = html + "<td width=100>" + country.getsupplystage() + "</td>";
                //                 html = html + "<td width=100>" + country.getcause() + "</td>"; 
                //                 flag = true;
                //             }
                //             else if (activity) {
                //                 html = html + "<tr>";
                //                 html = html + "<td width=100>" + name + "</td>";
                //                 html = html + "<td width=100>" + country.getcom() + "</td>";
                //                 html = html + "<td width=100>" + country.getYear() + "</td>";
                //                 html = html + "<td width=100>" + country.getlosspercent() + "</td>";
                //                 html = html + "<td width=100>" + country.getsupplystage() + "</td>";
                //                 html = html + "<td width=100>" + country.getactivity() + "</td>";
                //                 flag = true;
                //             }
                //             else {
                //                 html = html + "<tr>";
                //                 html = html + "<td width=100>" + name + "</td>";
                //                 html = html + "<td width=100>" + country.getcom() + "</td>";
                //                 html = html + "<td width=100>" + country.getYear() + "</td>";
                //                 html = html + "<td width=100>" + country.getlosspercent() + "</td>";
                //                 html = html + "<td width=100>" + country.getactivity() + "</td>";
                //                 flag = true;
                //             }
                            
                            
                //         }
                //     }
                // }

                if (countcheck == 0 && (countries.size() == 0 || flag == false || name.equals("[]")) ) {
                    html = html + "<tr>";
                    html = html + "<td width=100>NO DATA</td>";
                    html = html + "<td width=100>NO DATA</td>";
                    html = html + "<td width=100>NO DATA</td>";
                }


                html = html + "</table>";
        // Close Content div
        html = html + "</div>";

        html = html + """
            <div class='footer'>
       <div class="contentf">
       <a href='mission.html'>How to use and navigate</a>
       <p class:'footheader'>Contact Details: (using database)</p>
           """;
           
   int i;
   ArrayList<Student> students = jdbc.getstudents();
   Student stu = null;
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
