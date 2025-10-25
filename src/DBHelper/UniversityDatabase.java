//Victor Gomez, COP3330C, 10/25/25
//this program connects to a SQLite database and perform SQL commands to perform CRUD operations on the data in a table called EngineeringStudents
//user supplies a database created in SQLite studio and also the SQL commands to modify the data, and the results of those commands are returned to the screen
package DBHelper;

import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UniversityDatabase {
    public static void  main(String[] args){
        //create an instance of our database class, EngineeringStudents
        EngineeringStudents db1 = new EngineeringStudents();

        //create a 2D arrayList to hold the result of a query (this can hold columns and rows of any type)
        ArrayList<ArrayList<Object>> data = new ArrayList<ArrayList<Object>>();

        //perform a query to get all data from the database
        data = db1.getExecuteResult("select * from EngineeringStudents");

        //print the results of the query by printing what is stored in arraylist data
        for(List<Object> row : data){
            System.out.println(row.toString());
        }

        //delete a row of data from the database based on student_ID
        db1.delete("Student_ID","10201");
        db1.delete("Student_ID","10202");
        System.out.println("Deleted");

        data = db1.getExecuteResult("select * from EngineeringStudents");
        System.out.println("\nHere are the contents of the database: \n");
        printDatabase(data);

        //update a row of data from the database
        db1.update(db1.First_Name,"Leandro",db1.PassOutYear,"2020");
        System.out.println("Updated");
        //pull all records from the database and print
        data = db1.getExecuteResult("select * from EngineeringStudents");
        System.out.println("\nHere are the contents of the database: \n");
        printDatabase(data);


        //add a new entry to the database
        db1.execute("INSERT INTO EngineeringStudents VALUES (10201,'Music','Victor','Gomez',2025,1457)");
        db1.insert(10202,"Music","Jessy","Fernandez",2025,1120);

        //pull all records from the database and print
        data = db1.getExecuteResult("select * from EngineeringStudents");
        System.out.println("\nHere are the contents of the database: \n");
        printDatabase(data);

        //perform a specific query on the database
        data = db1.getExecuteResult("select Student_ID, Department,first_name, last_name, PassOutYear, UniversityRank from EngineeringStudents where PassOutYear = 2025");
        System.out.println("\nHere are the results of the 1st query: \n");
        printDatabase(data);

        data = db1.select("Student_ID, Department,first_name, last_name, PassOutYear","Department","Music","PassOutYear","DESC");
        System.out.println("\nHere are the results of the 2nd query: \n");
        printDatabase(data);


        DefaultTableModel table = new DefaultTableModel();
        table = db1.selectToTable("Student_ID, Department,first_name, last_name, PassOutYear","Department","Music","PassOutYear","DESC");
        String data2 = null;
        System.out.println("\n Here's the table: ");
        for(int row =0;row<table.getRowCount();row++){
            for(int column = 0;column<table.getColumnCount();column++){
                System.out.print(table.getValueAt(row,column).toString() + " | ");
            }
            System.out.println();
        }
    }



    public static void printDatabase(ArrayList<ArrayList<Object>> data){
        for(ArrayList<Object> row : data){
            System.out.println(row.toString());
        }
    }
}
