package mobile.dp.treatwithcare;


import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Daniel Velasco
 * @since November 19, 2016
 * @version 1.0
 *
 *
 * This class sends requests to the Database server over an established connection.
 *
 */
public class Inquirer
{
    // Name of the Database
    String DB;

    Connection DBCon;






    public Inquirer(Connection con, String DataBase){
        DBCon = con;
        DB = DataBase;
    }


    /**
     * This method adds a new row to the MEDICATION relation on the MySQL web server.
     *
     * @param newMedicationSpecs    Properly formatted tuple with the following information
     *                              'MName', 'PatID', 'DocID', 'MDosage', 'MInstructions', 'MSideEffects'
     */
    public void addMedication(String newMedicationSpecs) //throws
    {
        try {
            String query = "INSERT INTO " + DB + ".MEDICATION VALUES ("+ newMedicationSpecs +");";
            Statement stmt = DBCon.createStatement();
            stmt.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
