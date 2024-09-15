package org.example;

import java.sql.*;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App {

    static Scanner scn = new Scanner(System.in);

    public static int getParkingLimit() {
        return parkingLimit;
    }

    private static int parkingLimit;
    private static final String URL = "jdbc:mysql://localhost:3306/carparkingsystem";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static Connection connection;
    private static PreparedStatement preparedStatement;


    public static void main( String[] args ) {
        Scanner scn = new Scanner(System.in);
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            choiceForTableCreation(); // -- creation of table
            setLimitOnSlots();
            while(true) {
                int x = selectOptions();
                switch (x) {
                    case 1:
                        String car = ParkingWork.carpark();
                        if (car != null) {
                            preparedStatement = connection.prepareStatement("insert into parkingLot(isAvaliable,car)" +
                                    "values(?,?)");
                            preparedStatement.setBoolean(1, false);
                            preparedStatement.setString(2, car);
                            int i = preparedStatement.executeUpdate();
                            if (i > 0) {
                                System.out.println("Congrats your car has been parked!!");
                            }
                        } else {
                            System.out.println("Sorry!! There is no vacant place..");
                        }
                        break;
                    case 2:
                        String toberemovecar = ParkingWork.vacant();
                        preparedStatement = connection.prepareStatement("delete from parkingLot where car=?");
                        preparedStatement.setString(1, toberemovecar);
                        int i = preparedStatement.executeUpdate();
                        if(i!=0){
                            System.out.println("your payment is $10, Thankyou!! You can take your car now");
                        }

                        break;
                          case 3:
                     preparedStatement= connection.prepareStatement("select*from parkinglot ");
                        ResultSet rs = preparedStatement.executeQuery();
                        System.out.println("spotNo\tAvailibility\tcarNo");
                        while (rs.next()) {
                            System.out.println(rs.getInt("spotNo") + "\t       " + rs.getBoolean("isAvaliable") + "\t       " + rs.getString("car"));

                        }
                        break;
                    default:
                        System.out.println("Dont Try to be Oversmart!! select from given options only!!");
                        break;

                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
        private static void setLimitOnSlots() {
        System.out.println("Enter the Limit of parking slot");
        Scanner scn = new Scanner(System.in);
        parkingLimit = scn.nextInt();
    }

   private static int selectOptions() {
        System.out.println("Please Select Options");
        System.out.println("**********************************************");
        System.out.println("1: For car Parking\n2: For car Vacant\n3: To fetch all details");
        int choice = scn.nextInt();
        return choice;
    }

    private static void choiceForTableCreation() throws SQLException {
        System.out.println("Do you want to create Table in DB\tY/N");

        String choice= scn.next();
        switch (choice) {
            case "Y":
                createParkingLotTable();
                break;
            case "N":
                System.out.println("I hope you already have a DB");
                break;
            default:
                System.out.println("please select Either \t Y or N");
                choiceForTableCreation();break;
        }
    }

    private static void createParkingLotTable() throws SQLException {
        preparedStatement= connection.prepareStatement("create table if not exists parkinglot(spotNo int auto_increment primary key," +
                "isAvaliable boolean," +
                "car varchar(100))");
       boolean rowsAffected= preparedStatement.execute();

            System.out.println("Don't rush! The table is already present in the DB.");

    }
}
