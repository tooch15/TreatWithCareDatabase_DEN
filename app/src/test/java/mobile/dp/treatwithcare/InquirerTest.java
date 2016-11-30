package mobile.dp.treatwithcare;

import android.os.StrictMode;
import android.util.Log;

import org.hamcrest.core.StringContains;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.sql.DataSource;

import static org.junit.Assert.*;

/**
 * Tests the Inquirer Class, which directly sends queries to the TreatWithCare database
 */
public class InquirerTest
{



    static String user = "CPSC471_Fall2016_G2";
    static String pass = "`QP~5rWfR[}m<aV";
    static String db = "CPSC471_Fall2016_G2";
    static String server = "136.159.7.84";
    static String port = "50001";


    private Inquirer Holmes;
    private Connection con;

    public InquirerTest()
    {
        //StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        //StrictMode.setThreadPolicy(policy);
        con = null;
        String connectionURL = null;

        try {

            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connectionURL = "jdbc:jtds:sqlserver://"+server+":"+port+"/"+db;
            con = DriverManager.getConnection(connectionURL, user, pass);
            if(con == null)
                throw new Exception();

            con.setAutoCommit(true);

        } catch(Exception e) {
            e.printStackTrace();
        }

        Holmes = new Inquirer(con, db);
    }


    @Test
    public void T01selectDoctor() {

    }

    @Test
    public void T02selectImagesByPatient() {

    }

    @Test
    public void T03selectImagesByDoctor() {

    }

    @Test
    public void T05selectImagesByPatientAndApproval() {

    }

    @Test
    public void T06selectImagesByDoctorAndApproval() {

    }


    @Test
    public void T07selectMedication() {
    }

    @Test
    public void T08selectPatient(){

    }

    @Test
    public void T09addImage() {

    }

    /**
    @Test
    public void T10addMedication() {
        String newMedicationSpecs = "'Adderall','24','1002','2mg','Consume pill with breakfast','Brain damage'";
        Holmes.addMedication(newMedicationSpecs);
    }
     */

    @Test
    public void T11modifyApproval() {

    }

    @Test
    public void T12modifyClusterName() {

    }

    @Test
    public void T13modifySeverityGrade() {

    }

    @Test
    public void T14removeImage() {

    }

    @Test
    public void T15removeMedication() {

        String MName = "'Adderall'";
        String PatID = "'1002'";
        String DocID = "'24'";
        Holmes.removeMedication(MName, PatID, DocID);
    }

}