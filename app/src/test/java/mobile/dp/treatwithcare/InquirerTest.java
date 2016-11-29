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
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class InquirerTest
{



    static String user = "twc_dev";
    static String pass = "s3xyasfuck";
    static String db = "treat_with_care";
    static String server = "www.db4free.net";
    static String port = "3306";


    private Inquirer Holmes;
    private Connection con;

    public InquirerTest()
    {
//       StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//       StrictMode.setThreadPolicy(policy);
        con = null;
        String connectionURL = null;

        try {

            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            //connectionURL = "jdbc:mysql://"+server+":"+port+"/"+db;

            connectionURL = "jdbc:jtds:sqlserver://"+server+":"+port+"/"+db;
            con = DriverManager.getConnection(connectionURL, user, pass);
            if(con == null)
                throw new Exception();

            con.setAutoCommit(true);
            con.toString();

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
    public void T04selectImagesByApproval() {

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

    @Test
    public void T10addMedication() {
    }

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

    }

}