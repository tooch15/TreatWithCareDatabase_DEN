package mobile.dp.treatwithcare;


import android.os.StrictMode;
import android.util.Log;

import java.sql.Array;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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



    ResultSet selectDoctor(String PID, String DID) {
        return null;
    }

    ResultSet selectImages(String PID, String DID, String Approval)
    {
        return null;
    }

    /**
     * This method retrieves information about a patient's medication records relation on
     * the MySQL web server.
     *
     * @param PID   A patient ID
     * @param DID   The doctor who prescribed/approved this medication<br>
     *              If DID = null, all medication records pertaining to a specific
     *              patient will be retrieved, regardless of who the doctor was.
     *
     * @return      a list of medication specifications, including its name, dosage,
     *              instructions, and side effects.
     *
     */
    ResultSet selectMedication(String PID, String DID) {

        ResultSet Medication = null;

        try {

            String query = "SELECT MName, MDosage, MInstructions, MSideEffects FROM MEDICATION WHERE ";
            if (PID != null)
                query +="PatID=" + PID;
            if (DID != null)
                query += ", DocID=" + DID;
            query += ";";
            Statement stmt = DBCon.createStatement();
            Medication = stmt.executeQuery(query);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Medication;
    }

    ResultSet selectPatient(String PID) {
        return null;
    }


    void addImage(String newImageSpecs) {

    }

    /**
     * This method adds a new row to the MEDICATION relation on the MySQL web server.
     *
     * @param newMedicationSpecs    Properly formatted tuple with the following information
     *                              'MName', 'PatID', 'DocID', 'MDosage', 'MInstructions', 'MSideEffects'
     */
    void addMedication(String newMedicationSpecs)
    {
        String medInfo = null;

        try {
            String query = "INSERT INTO " + DB + ".MEDICATION VALUES ("+ newMedicationSpecs +");";
            Statement stmt = DBCon.createStatement();
            stmt.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    void modifyApproval() {

    }

    void modifyClusterName() {

    }

    void modifySeverityGrade() {

    }

    void removeImage() {

    }

    void removeMedication() {

    }



}
