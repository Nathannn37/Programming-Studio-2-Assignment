package app;

import java.util.HashSet;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.*;
/**
 * Class for Managing the JDBC Connection to a SQLLite Database.
 * Allows SQL queries to be used with the SQLLite Databse in Java.
 *
 * @author Timothy Wiley, 2023. email: timothy.wiley@rmit.edu.au
 * @author Santha Sumanasekara, 2021. email: santha.sumanasekara@rmit.edu.au
 * @author Halil Ali, 2024. email: halil.ali@rmit.edu.au
 */

public class JDBCConnection {

    // Name of database file (contained in database folder)
    public static final String DATABASE = "jdbc:sqlite:database/foodloss.db";

    /**
     * This creates a JDBC Object so we can keep talking to the database
     */
    public JDBCConnection() {
        System.out.println("Created JDBC Connection Object");
    }

    /**
     * Get all of the Countries in the database.
     * @return
     *    Returns an ArrayList of Country objects
     */
    public ArrayList<Country> getAllCountries() {
        // Create the ArrayList of Country objects to return
        ArrayList<Country> countries = new ArrayList<Country>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT * FROM Country";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                String m49Code     = results.getString("m49code");
                String name  = results.getString("Name");

                // Create a Country Object
                Country country = new Country(m49Code, name);

                // Add the Country object to the array
                countries.add(country);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the countries
        return countries;
    }

    // TODO: Add your required methods here

    public ArrayList<Persona> getPersona(String persona) {
        // Create the ArrayList of Country objects to return
        ArrayList<Persona> Personas = new ArrayList<Persona>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            boolean flag = false;
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);
            // connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            //String query = "SELECT * FROM persona WHERE name ='" + persona + "'";
            
            String query;
            if (persona.equals("ALL-PERSONAS")) {
                flag = true;
            }
            if (!flag) {
            query = "SELECT * FROM persona WHERE name ='" + persona + "'";
            System.out.println(query);
            

            }
            else {
                query = "SELECT * FROM persona;";
                System.out.println(query); 
            }


            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                String description     = results.getString("DESCRIPTION");
                String image  = results.getString("IMAGE");

                // Create a Country Object
                Persona Persona = new Persona(description, image);

                // Add the Country object to the array
                Personas.add(Persona);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the countries
        return Personas;
    }

    public ArrayList<FoodGroup> getGroupNameLossData(String startYear, String endYear, List<String> categories, String activity, String foodSupplyStage, String causeOfLoss, String sort) {
        // Create the ArrayList of Country objects to return
        ArrayList<FoodGroup> FoodGroups = new ArrayList<FoodGroup>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            boolean flag = false;
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            //String query = "SELECT * FROM persona WHERE name ='" + persona + "'";
            String query = "";
            boolean firstCategory = true;

            for (String category : categories) {
                if (!firstCategory) {
                    query += " UNION ";
                }
                firstCategory = false;

                query += "SELECT c.parentCPCcodename, " +
                       (activity.equals("null") ? "'' AS activity, " : "fd1.activity, ") +
                       (foodSupplyStage.equals("null") ? "'' AS food_supply_stage, " : "fd1.food_supply_stage, ") +
                       (causeOfLoss.equals("null") ? "'' AS cause_of_loss, " : "fd1.cause_of_loss, ") +
                       "AVG(fd1.loss_percentage) AS 'startYear', AVG(fd2.loss_percentage) AS 'endYear', " +
                       "abs(CAST(AVG(fd2.loss_percentage) AS FLOAT) - CAST(AVG(fd1.loss_percentage) AS FLOAT)) AS 'Difference' " +
                       "FROM (foodloss fd1 " +
                       "JOIN foodloss fd2) " +
                       "JOIN CPC c ON fd1.cpc_code = c.CPCCode " +
                       "WHERE fd1.CPC_CODE LIKE (SELECT CPCCode || '%' FROM CPC WHERE CPCName = '" + category + "') " +
                       "AND fd1.Year = '" + startYear + "' " +
                       "AND fd2.CPC_CODE LIKE (SELECT CPCCode || '%' FROM CPC WHERE CPCName = '" + category + "') " +
                       "AND fd2.Year = '" + endYear + "'";

                       if (!activity.equals("null")) {
                        query += "AND fd1.activity = fd2.activity ";
                       }
                       if (!foodSupplyStage.equals("null")) {
                            query += "AND fd1.food_supply_stage = fd2.food_supply_stage ";
                       }
                       if (!causeOfLoss.equals("null")) {
                            query += "AND fd1.cause_of_loss = fd2.cause_of_loss ";
                       }
                       

                }
                if (sort.equals("Ascending")) {
                    query += "ORDER BY difference asc" ;
                }
                else {
                    query += "ORDER BY difference desc" ;
                }
                

                // System.out.println(query);


            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                String FoodGroupName     = results.getString("parentCPCCodeName");
                String Activity  = results.getString("activity");
                String FoodSupplyStage  = results.getString("food_supply_stage");
                String CauseOfLoss  = results.getString("cause_of_loss");
                String StartYear  = results.getString("startYear");
                String EndYear  = results.getString("endYear");
                String Difference  = results.getString("Difference");

                // Create a Country Object
                FoodGroup FoodGroup = new FoodGroup(FoodGroupName, Activity, FoodSupplyStage, CauseOfLoss, StartYear, EndYear, Difference);

                // Add the Country object to the array
                FoodGroups.add(FoodGroup);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the countries
        return FoodGroups;
    }


    public ArrayList<Student> getstudents() {
        // Create the ArrayList of Country objects to return
        ArrayList<Student> students = new ArrayList<Student>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT * FROM Students";

            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                String id     = results.getString("student_number");
                String name  = results.getString("name");
                String email  = results.getString("email");

                // Create a Country Object
                Student stu = new Student(id, name, email);

                // Add the Country object to the array
                students.add(stu);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the countries
        return students;
    }

    public ArrayList<CountriesTable> getCountry(String cname, String syear, String eyear, boolean cactivity, boolean ccause, boolean acc) {
        // Create the ArrayList of Country objects to return
        ArrayList<CountriesTable> countries = new ArrayList<CountriesTable>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            boolean flag = false;
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);
            // connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            //String query = "SELECT * FROM persona WHERE name ='" + persona + "'";
            
            String query;
            
            query="SELECT c.name, fl.commodity, fl.year, fl.loss_percentage, fl.food_supply_stage, fl.cause_of_loss, fl.activity FROM foodlossdata fl JOIN country c ON c.m49code=fl.m49code WHERE name = '" + cname + "'";
            if (acc) {
                query = query + "ORDER BY c.name DESC";
            }
            else {
                query = query + "ORDER BY c.name ASC";
            }
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                String name     = results.getString("name"); 
                String comName  = results.getString("commodity");
                int year  = results.getInt("year");
                String loss  = results.getString("loss_percentage");
                String stage  = results.getString("food_supply_stage");
                String cause  = results.getString("cause_of_loss");
                String activity  = results.getString("activity");

                CountriesTable country = null;
                // Create a Country Object
                
                    country = new CountriesTable(name, comName, year, loss, stage, cause, activity);
                
                
                
                // Add the Country object to the array
                countries.add(country);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the countries
        return countries;
    }

    public ArrayList<Similiarity> getSimiliarity(String category, String quantity, String option) {
        // Create the ArrayList of Country objects to return
        ArrayList<Similiarity> Similiarities = new ArrayList<Similiarity>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            boolean flag = false;
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            //String query = "SELECT * FROM persona WHERE name ='" + persona + "'";
            String query = "";
            if (option.equals("highest")) {

                query += "SELECT DISTINCT commodity, " +
                "cpc_code, " +
                "c.parentCPCCodeName, " +
                "MAX(fd.loss_percentage) AS \"Loss Percentage\", " +
                "(1 - ( " +
                "    abs( " +
                "        CAST(( " +
                "            SELECT MAX(loss_percentage) " +
                "            FROM foodloss " +
                "            WHERE cpc_code LIKE ( " +
                "                SELECT CPCCode || '%' " +
                "                FROM CPC " +
                "                WHERE CPCName = ( " +
                "                    SELECT parentCPCcodeName " +
                "                    FROM CPC " +
                "                    WHERE CPCName =  '" + category + "'"+
                "                ) " +
                "            ) " +
                "        ) AS FLOAT) - CAST(MAX(loss_percentage) AS FLOAT) " +
                "    ) / " +
                "    ( " +
                "        CAST(( " +
                "            SELECT MAX(loss_percentage) " +
                "            FROM foodloss " +
                "            WHERE cpc_code LIKE ( " +
                "                SELECT CPCCode || '%' " +
                "                FROM CPC " +
                "                WHERE CPCName = ( " +
                "                    SELECT parentCPCcodeName " +
                "                    FROM CPC " +
                "                    WHERE CPCName = '" + category + "'" +
                "                ) " +
                "            ) " +
                "        ) AS FLOAT) + CAST(MAX(loss_percentage) AS FLOAT) " +
                "    ) " +
                ")) * 100 AS \"Similiarity Score (%)\" " +
                "FROM foodloss fd " +
                "JOIN CPC c ON fd.cpc_code = c.CPCCode " +
                "WHERE c.CPCName !=  '" + category + "'" +
                " GROUP BY cpc_code, commodity, c.parentCPCCodeName " +
                "ORDER BY \"Similiarity Score (%)\" DESC " +
                "LIMIT " + quantity;
            }
            else if (option.equals("lowest")) {
                query += "SELECT DISTINCT commodity, " +
                "cpc_code, " +
                "c.parentCPCCodeName, " +
                "MIN(fd.loss_percentage) AS \"Loss Percentage\", " +
                "(1 - ( " +
                "    abs( " +
                "        CAST(( " +
                "            SELECT MIN(loss_percentage) " +
                "            FROM foodloss " +
                "            WHERE cpc_code LIKE ( " +
                "                SELECT CPCCode || '%' " +
                "                FROM CPC " +
                "                WHERE CPCName = ( " +
                "                    SELECT parentCPCcodeName " +
                "                    FROM CPC " +
                "                    WHERE CPCName =  '" + category + "'"+
                "                ) " +
                "            ) " +
                "        ) AS FLOAT) - CAST(MIN(loss_percentage) AS FLOAT) " +
                "    ) / " +
                "    ( " +
                "        CAST(( " +
                "            SELECT MIN(loss_percentage) " +
                "            FROM foodloss " +
                "            WHERE cpc_code LIKE ( " +
                "                SELECT CPCCode || '%' " +
                "                FROM CPC " +
                "                WHERE CPCName = ( " +
                "                    SELECT parentCPCcodeName " +
                "                    FROM CPC " +
                "                    WHERE CPCName = '" + category + "'" +
                "                ) " +
                "            ) " +
                "        ) AS FLOAT) + CAST(MIN(loss_percentage) AS FLOAT) " +
                "    ) " +
                ")) * 100 AS \"Similiarity Score (%)\" " +
                "FROM foodloss fd " +
                "JOIN CPC c ON fd.cpc_code = c.CPCCode " +
                "WHERE c.CPCName !=  '" + category + "'" +
                " GROUP BY cpc_code, commodity, c.parentCPCCodeName " +
                "ORDER BY \"Similiarity Score (%)\" DESC " +
                "LIMIT " + quantity;
            }
            else if (option.equals("ratio")) {
                query += "SELECT DISTINCT commodity, " +
                "cpc_code, " +
                "c.parentCPCCodeName, " +
                "AVG(fd.loss_percentage) AS \"Loss Percentage\", " +
                "(1 - ( " +
                "    abs( " +
                "        CAST(( " +
                "            SELECT AVG(loss_percentage) " +
                "            FROM foodloss " +
                "            WHERE cpc_code LIKE ( " +
                "                SELECT CPCCode || '%' " +
                "                FROM CPC " +
                "                WHERE CPCName = ( " +
                "                    SELECT parentCPCcodeName " +
                "                    FROM CPC " +
                "                    WHERE CPCName =  '" + category + "'"+
                "                ) " +
                "            ) " +
                "        ) AS FLOAT) - CAST(AVG(loss_percentage) AS FLOAT) " +
                "    ) / " +
                "    ( " +
                "        CAST(( " +
                "            SELECT AVG(loss_percentage) " +
                "            FROM foodloss " +
                "            WHERE cpc_code LIKE ( " +
                "                SELECT CPCCode || '%' " +
                "                FROM CPC " +
                "                WHERE CPCName = ( " +
                "                    SELECT parentCPCcodeName " +
                "                    FROM CPC " +
                "                    WHERE CPCName = '" + category + "'" +
                "                ) " +
                "            ) " +
                "        ) AS FLOAT) + CAST(AVG(loss_percentage) AS FLOAT) " +
                "    ) " +
                ")) * 100 AS \"Similiarity Score (%)\" " +
                "FROM foodloss fd " +
                "JOIN CPC c ON fd.cpc_code = c.CPCCode " +
                "WHERE c.CPCName !=  '" + category + "'" +
                " GROUP BY cpc_code, commodity, c.parentCPCCodeName " +
                "ORDER BY \"Similiarity Score (%)\" DESC " +
                "LIMIT " + quantity;
            } 
                
                

            System.out.println(query);


            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                String commodity     = results.getString("commodity");
                String cpc_code  = results.getString("cpc_code");
                String parentCpcName  = results.getString("parentCPCCodeName");
                String lossPercentage = results.getString("Loss Percentage");
                String similiarity  = results.getString("Similiarity Score (%)");

                // Create a Country Object
                Similiarity Similiarity = new Similiarity(commodity, cpc_code, parentCpcName, lossPercentage, similiarity);

                // Add the Country object to the array
                Similiarities.add(Similiarity);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the countries
        return Similiarities;
    }
    

    //sim by countries
    public ArrayList<SimCountries> getSimCountry(String cname, String eyear, String html, boolean avg, boolean loss, boolean both) {
        // Create the ArrayList of Country objects to return
        ArrayList<SimCountries> Similiarity = new ArrayList<SimCountries>();
        String parentCpc = "";
        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            boolean flag = false;
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);
            // connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            
            String query;
            
            query= "SELECT cpc.parentCPCCode from foodloss fl JOIN cpc on cpc.CPCCode = fl.cpc_code JOIN country c on c.m49code=fl.m49code WHERE c.name='" + cname +"' AND year < '" + eyear + "';";

            // Get Result
            ResultSet results = statement.executeQuery(query);
            ArrayList<String> parent  = new ArrayList<String>();
            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                
              parent.add(results.getString("parentCPCCode"));

            }

            HashSet<String> set = new HashSet<>(parent);
            parent = new ArrayList<>(set);
            query = "";
            for (String code : parent) {
            query = "SELECT c.name ,cpc.CPCName as 'commodity',  ROUND(fl.loss_percentage, 2) as 'loss_percent' from foodloss fl JOIN cpc on cpc.CPCCode = fl.cpc_code JOIN country c on c.m49code=fl.m49code WHERE cpc.parentCPCCode LIKE '%" + code +"';";
            ResultSet results2 = statement.executeQuery(query);

            while (results2.next()) {
                String name =results.getString("name");
                String com = results.getString("commodity");
                String losspercent = results.getString("loss_percent");
                SimCountries sims = new SimCountries(name, com, losspercent, name);

                Similiarity.add(sims);
            }
            }

            System.out.println(Similiarity.size());

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the countries
        return Similiarity;
    }


    public ArrayList<SimCountries> getSimLoss(String cname, String eyear) {
        // Create the ArrayList of Country objects to return
        ArrayList<SimCountries> Similiarity = new ArrayList<SimCountries>();
        String parentCpc = "";
        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            boolean flag = false;
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);
            // connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            
            String query;
            
            query= "SELECT c.name ,cpc.CPCName as 'commodity',  ROUND(fl.loss_percentage, 2) as 'loss_percent' from foodloss fl JOIN cpc on cpc.CPCCode = fl.cpc_code JOIN country c on c.m49code=fl.m49code;";

            // Get Result
            ResultSet results = statement.executeQuery(query);
            ArrayList<String> parent  = new ArrayList<String>();
            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                
              
              String name =results.getString("name");
              String com = results.getString("commodity");
              String losspercent = results.getString("loss_percent");
              SimCountries sims = new SimCountries(name, com, losspercent, name);
                

              Similiarity.add(sims);
          }
        
          

            // Close the statement because we are done with it
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the countries
        return Similiarity;
    }
    
}

