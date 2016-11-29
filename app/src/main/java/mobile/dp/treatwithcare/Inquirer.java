package mobile.dp.treatwithcare;


import android.graphics.Bitmap;
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



    ResultSet selectDoctor(String DID) {

        ResultSet Doctor = null;

        try {

            String query = "SELECT DocID, DName, DLocation, DPhoneNo FROM DOCTOR WHERE ";
            query += "DocID=" + DID +";";
            Statement stmt = DBCon.createStatement();
            Doctor = stmt.executeQuery(query);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Doctor;
    }

    ResultSet selectImages(String PID, String DID, String Approval)
    {
        ResultSet Images = null;

        try {

            String query = "SELECT ImageID, DateTaken, DocID, PatID, ClusterNo, Approval FROM IMAGE WHERE ";
            query += "PatID=" + PID +" AND DocID=" + DID;
            if(Approval != null)
                query += " AND Approval=" + Approval;
            else {
                // TODO Join image with condition instance to get the approval
            }
            query += ";";

            Statement stmt = DBCon.createStatement();
            Images = stmt.executeQuery(query);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Images;
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

        ResultSet Patient = null;

        try {

            String query = "SELECT PatID, PName, PLocation, PPhoneNo, DocID FROM PATIENT WHERE ";
            query += "PatID=" + PID +";";
            Statement stmt = DBCon.createStatement();
            Patient = stmt.executeQuery(query);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Patient;
    }


    void addImage(String newImageSpecs, Bitmap Original, Bitmap Acne) {

        try {
            String query = "INSERT INTO " + DB + ".IMAGE VALUES ("+ newImageSpecs +");"; // TODO How are blobs inserted into a tuple? Can we insert two at the same time?
            Statement stmt = DBCon.createStatement();
            stmt.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * This method adds a new row to the MEDICATION relation on the MySQL web server.
     *
     * @param newMedicationSpecs    Properly formatted tuple with the following information
     *                              'MName', 'PatID', 'DocID', 'MDosage', 'MInstructions', 'MSideEffects'
     */
    void addMedication(String newMedicationSpecs)
    {
        try {
            String query = "INSERT INTO " + DB + ".MEDICATION VALUES ("+ newMedicationSpecs +");";
            Statement stmt = DBCon.createStatement();
            stmt.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    void modifyApproval(String ImageID, String newApproval) {

        try {
            String query = "UPDATE " + DB + ".CONDITION_INSTANCE SET Approved="+newApproval;
            query += " WHERE ImageID="+ImageID+";";
            Statement stmt = DBCon.createStatement();
            stmt.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void modifyClusterNumber(String ImageID, int newCN) {
        try {
            String query = "UPDATE " + DB + ".IMAGE SET ClusterNo="+newCN;
            query += " WHERE ImageID="+ImageID+";";
            Statement stmt = DBCon.createStatement();
            stmt.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void modifySeverityGrade(String ImageID, String SG) {
        try {
            String query = "UPDATE " + DB + ".CONDITION_INSTANCE SET SeverityGrade="+SG;
            query += " WHERE ImageID="+ImageID+";";
            Statement stmt = DBCon.createStatement();
            stmt.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void removeImage(String ImageID) {
        try {

            // TODO Delete everything that refers to the image being deleted (i.e. Condition instance)
            String query = "DELETE FROM " + DB + ".IMAGE WHERE ImageID="+ImageID+";";
            Statement stmt = DBCon.createStatement();
            stmt.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void removeMedication(String MName, String PatID, String DocID) {
        try {
            String query = "DELETE FROM " + DB + ".MEDICATION WHERE ";
            query += "MName="+MName+" AND PatID="+PatID+" AND DocID="+DocID+";";
            Statement stmt = DBCon.createStatement();
            stmt.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}
