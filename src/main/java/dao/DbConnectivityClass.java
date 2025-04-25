package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Animal;
import model.Person;
import service.MyLogger;
import model.UserLogins;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;

public class DbConnectivityClass {
    final static String DB_NAME="DBname";
    MyLogger lg= new MyLogger();
    final static String SQL_SERVER_URL = "jdbc:mysql://csc311riven50.mysql.database.azure.com:3306/";//update this server name
    final static String DB_URL = "jdbc:mysql://csc311riven50.mysql.database.azure.com:3306/" + DB_NAME;//update this database name
    final static String USERNAME = "csc311riven";// update this username
    final static String PASSWORD = "FARM123$";// update this password

    private String username;

    // Method to retrieve all data from the database and store it into an observable list to use in the GUI tableview.
        public void connectToDatabase() {

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");

                //First, connect to MYSQL server and create the database if not created
                Connection conn = DriverManager.getConnection(SQL_SERVER_URL, USERNAME, PASSWORD);
                Statement statement = conn.createStatement();
                statement.executeUpdate("CREATE DATABASE IF NOT EXISTS "+DB_NAME+"");
                statement.close();
                conn.close();

                conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
                statement = conn.createStatement();
                String sql2 = "CREATE TABLE IF NOT EXISTS user_logins ("
                        + "username VARCHAR(200) NOT NULL UNIQUE,"
                        + "password VARCHAR(200) NOT NULL,"
                        + "CONSTRAINT pk_user_logins PRIMARY KEY(username)"
                        + ")";
                statement.executeUpdate(sql2);

                conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
                statement = conn.createStatement();
                String sql = "CREATE TABLE IF NOT EXISTS animals ("
                        + "id INT( 10 ) NOT NULL AUTO_INCREMENT,"
                        + "name VARCHAR(200) NOT NULL,"
                        + "species VARCHAR(200) NOT NULL,"
                        + "date_of_birth VARCHAR(200),"
                        + "animal_class VARCHAR(200),"
                        + "username VARCHAR(200) NOT NULL UNIQUE,"
                        + "exhibit VARCHAR(250),"
                        + "CONSTRAINT fk_user_logins FOREIGN KEY(username) REFERENCES user_logins(username),"
                        + "CONSTRAINT pk_animal PRIMARY KEY(id, username)"
                        + ")";
                statement.executeUpdate(sql);

                statement.close();
                conn.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void insertUserLogin(String username, String password) {
            connectToDatabase();
            try {
                Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
                String sql = "INSERT INTO user_logins (username, password) VALUES (?, ?)";
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);

                preparedStatement.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    public LinkedList<UserLogins> getUserLogins() {
        LinkedList<UserLogins> logins = new LinkedList<>();

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String sql = "SELECT * FROM user_logins";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                logins.add(new UserLogins(username, password));
            }

            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return logins;
    }

    public ObservableList<Animal> getAnimals() {
        ObservableList<Animal> animals = FXCollections.observableArrayList(new ArrayList<>());

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String sql = "SELECT * FROM animals WHERE username = '" + username + "'";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String animal_class = resultSet.getString("animal_class");
                String species = resultSet.getString("species");
                String date_of_birth = resultSet.getString("date_of_birth");
                String exhibit = resultSet.getString("exhibit");
                animals.add(new Animal(id, name, animal_class, species, date_of_birth, exhibit));
            }

            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return animals;
    }

    public void insertAnimal(String name, String species, String date_of_birth, String animal_class, String exhibit) {
        connectToDatabase();
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String sql = "INSERT INTO animals (name, species, date_of_birth, animal_class, exhibit, username) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, species);
            preparedStatement.setString(3, date_of_birth);
            preparedStatement.setString(4, animal_class);
            preparedStatement.setString(5, exhibit);
            preparedStatement.setString(6, username);

            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeAnimal(int id) {
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String sql = "DELETE FROM animals WHERE id = " + id + " and username = '" + username + "'";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            preparedStatement.executeUpdate();
            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void editAnimal(int id, String name, String species, String date_of_birth, String animal_class, String exhibit) {
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String sql = "UPDATE animals SET name = '" + name +
                    "', species = '" + species +
                    "', date_of_birth = '" + date_of_birth +
                    "', animal_class = '" + animal_class +
                    "' exhibit = '" + exhibit +
                    "' WHERE id = " + id + " and username = " + username;
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            preparedStatement.executeUpdate();
            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setCurrUser(String currUser) {
            username = currUser;
    }

}