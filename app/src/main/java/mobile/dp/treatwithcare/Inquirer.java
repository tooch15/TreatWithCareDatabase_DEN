package mobile.dp.treatwithcare;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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



    ResultSet selectDoctor(String DID) {

        ResultSet Doctor = null;

        String query = "SELECT * FROM DOCTOR WHERE ";
        query += "DocID=" + DID +";";

        try {
            Statement stmt = DBCon.createStatement();
            Doctor = stmt.executeQuery(query);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Doctor;
    }

    ResultSet selectImages(String PID, String DID, String Approval) throws ForbiddenQueryException
    {
        ResultSet images = null;
        String query = "SELECT * FROM IMAGE AS I ";

        if(Approval == null) { // Case where selection is either by patient OR doctor ONLY
            query += "WHERE ";
            if (PID != null)
                query += "I.PatID=" + PID;
            else if (DID != null)
                query += "I.DocID=" + DID;
            else
                throw new ForbiddenQueryException();
        }
        else if(Approval != null) { // Case where selection is by approval AND patient OR Doctor
            query += ", CONDITION_INSTANCE AS C WHERE ";
            query += "C.Approval=" + Approval + "AND ";
            if (PID != null)
                query += "I.PatID=" + PID;
            else if (DID != null)
                query += "I.DocID=" + DID;
            else
                throw new ForbiddenQueryException();
        }
        query += ";";

        try {

            Statement stmt = DBCon.createStatement();
            images = stmt.executeQuery(query);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return images;
    }

    /**
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

        String query = "SELECT MName, MDosage, MInstructions, MSideEffects FROM MEDICATION WHERE ";
        if (PID != null)
            query +="PatID=" + PID;
        else if (DID != null)
            query += "DocID=" + DID;

        query += ";";

        try {
            Statement stmt = DBCon.createStatement();
            Medication = stmt.executeQuery(query);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Medication;
    }

    ResultSet selectPatient(String PID) {

        ResultSet Patient = null;

        String query = "SELECT PatID, PName, PLocation, PPhoneNo, DocID FROM "+DB+".PATIENT WHERE ";
        query += "PatID=" + PID +";";
        try {
            Statement stmt = DBCon.createStatement();
            Patient = stmt.executeQuery(query);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Patient;
    }


    /**
     * @param newDiagnosis Properly formatted tuple with the following information
     *                     'SGDate', 'PatID', 'DocID', 'Grading'<br>
     *                     <i>The Approved attribute is set to 'Pending' by default</i>
     */
    void addConditionInstance(String newDiagnosis) {
        try {
            String query = "INSERT INTO CONDITION_INSTANCE VALUES (?);";
            Statement stmt = DBCon.createStatement();
            stmt.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     *
     * @param IDate     Date the picture was taken in the format YYYY-MM-DD
     * @param DID       Doctor's ID
     * @param PID       Patient's ID
     * @param CN        Number of Clusters
     * @param original  Image uploded by patient
     * @param processed Processed image
     *
     * @throws ForbiddenQueryException if both byte arrays are empty
     */
    void addImage(String IDate, String DID, String PID, String CN, byte[] original, byte[] processed) throws ForbiddenQueryException
    {
        if(original.length == 0 && processed.length == 0 )
            throw new ForbiddenQueryException();

        try {
            String query = "INSERT INTO IMAGE VALUES (?, ?, ?, ?, ?, ?);";

            PreparedStatement stmt = DBCon.prepareStatement(query);
            stmt.setString(1, IDate);
            stmt.setString(2, DID);
            stmt.setString(3, PID);
            stmt.setString(4, CN);
            stmt.setBytes(5, original);
            stmt.setBytes(6, processed);
            stmt.execute();

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
            String query = "INSERT INTO MEDICATION VALUES ("+ newMedicationSpecs +");";
            Statement stmt = DBCon.createStatement();
            stmt.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void modifyApproval(String ImageID, String newApproval) {

        try {
            String query = "UPDATE CONDITION_INSTANCE SET Approved="+newApproval;
            query += " WHERE ImageID="+ImageID+";";
            Statement stmt = DBCon.createStatement();
            stmt.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void modifyClusterNumber(String ImageID, int newCN) {
        try {
            String query = "UPDATE IMAGE SET ClusterNo="+newCN;
            query += " WHERE ImageID="+ImageID+";";
            Statement stmt = DBCon.createStatement();
            stmt.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param ImageID
     * @param SG
     */
    void modifySeverityGrade(String ImageID, String SG) {
        try {
            String query = "UPDATE CONDITION_INSTANCE SET SeverityGrade="+SG;
            query += " WHERE ImageID="+ImageID+";";
            Statement stmt = DBCon.createStatement();
            stmt.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is used to remove an image from the database
     *
     * @param DID   Who this image belongs to
     * @param PID   This patient's doctor
     * @param IDate On which the image was taken
     */
    void removeImage(String DID, String PID, String IDate) {

        String query = "DELETE FROM IMAGE WHERE PatID="+PID;
        query += " AND DocID="+DID+"AND IDate="+IDate+";";
        try {
            Statement stmt = DBCon.createStatement();
            stmt.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void removeMedication(String MName, String PatID, String DocID) {

        String query = "DELETE FROM MEDICATION WHERE ";
        query += "MName="+MName+" AND PatID="+PatID+" AND DocID="+DocID+";";
        try {
            Statement stmt = DBCon.createStatement();
            stmt.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
