/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;

/**
 *
 * @author PC-Phong
 */
public class Neo4jContext{

    private Driver connection;
   public boolean isConnected() {
        try {
            return connection != null;
        } catch (Exception e) {
            System.out.println("Error while checking the connection status: " + e.getMessage());
            return false;
        }
    }

    // Provide a method to get the connection
    public Driver getConnection() {
        try {
            // Replace with your database credentials
            String uri = "bolt://localhost:7687";
            String user = "neo4j";     // your Neo4j username
            String password = "12345678"; // your Neo4j password

            // Establishing the connection
            connection = GraphDatabase.driver(uri, AuthTokens.basic(user, password));
            System.out.println("Connection to Neo4j has been established.");

        } catch (Exception e) {
            System.out.println("Cannot connect to Neo4j database: " + e.getMessage());
        }
        return connection;
    }
}
