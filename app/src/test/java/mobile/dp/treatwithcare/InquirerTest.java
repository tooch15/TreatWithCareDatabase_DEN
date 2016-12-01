package mobile.dp.treatwithcare;


import android.support.annotation.VisibleForTesting;

import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;


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

    @Ignore
    @Test
    public void T01selectDoctor() {
        ResultSet doctor = Holmes.selectDoctor("1002");
    }

    @Ignore
    @Test
    public void T02selectImagesByPatient() {
        ResultSet patientImages = null;
        try {
            patientImages = Holmes.selectImages("1002", null, null);
        } catch(ForbiddenQueryException e) {
            fail();
        }

        assertNotNull(patientImages);
        try {
            patientImages.next();

            String IDate = patientImages.getDate("IDate").toString();
            String DocID = Integer.toString(patientImages.getInt("DocID"));
            String PatID = Integer.toString(patientImages.getInt("PatID"));
            String ClusterNo = Integer.toString(patientImages.getInt("ClusterNo"));
            byte[] Original = patientImages.getBytes("Original");
            byte[] Processed = patientImages.getBytes("Processed");

            assertEquals("2016-11-30", IDate);
            assertEquals("24", DocID);
            assertEquals("1002", PatID);
            assertEquals("8", ClusterNo);


            FileOutputStream originalOne = new FileOutputStream(new File("Original"));
            originalOne.write(Original, 0, Original.length);
            originalOne.close();

            FileOutputStream processedOne = new FileOutputStream(new File("Processed"));
            processedOne.write(Processed, 0, Processed.length);
            processedOne.close();


        } catch(SQLException e)
        {    e.printStackTrace();
            fail();   }
        catch(FileNotFoundException e)
        {   fail();   }
        catch(IOException e)
        {
            e.printStackTrace();
            fail();
        }

    }


    @Test
    public void T03selectImagesByDoctor() {

        ResultSet docImages = null;
        try {
            docImages = Holmes.selectImages(null, "24", null);
        } catch(ForbiddenQueryException e) {
            fail();
        }

        assertNotNull(docImages);
        try {
            docImages.next();

            String IDate = docImages.getDate("IDate").toString();
            String DocID = Integer.toString(docImages.getInt("DocID"));
            String PatID = Integer.toString(docImages.getInt("PatID"));
            String ClusterNo = Integer.toString(docImages.getInt("ClusterNo"));
            byte[] Original = docImages.getBytes("Original");
            byte[] Processed = docImages.getBytes("Processed");

            assertEquals("2016-11-30", IDate);
            assertEquals("24", DocID);
            assertEquals("1002", PatID);
            assertEquals("8", ClusterNo);


            FileOutputStream originalOne = new FileOutputStream(new File("Original"));
            originalOne.write(Original, 0, Original.length);
            originalOne.close();

            FileOutputStream processedOne = new FileOutputStream(new File("Processed"));
            processedOne.write(Processed, 0, Processed.length);
            processedOne.close();


        } catch(SQLException e)
        {    e.printStackTrace();
            fail();   }
        catch(FileNotFoundException e)
        {   fail();   }
        catch(IOException e)
        {
            e.printStackTrace();
            fail();
        }



    }

    @Ignore
    @Test
    public void T05selectImagesByPatientAndApproval() {
        try {

            ResultSet pendingImages = Holmes.selectImages("1002", null, "Pending");
            ResultSet approvedImages = Holmes.selectImages("970", null, "Approved");
            ResultSet rejectedImages = Holmes.selectImages("284", null, "Rejected");

        } catch(ForbiddenQueryException e) {
            fail();
        }
    }

    @Ignore
    @Test
    public void T06selectImagesByDoctorAndApproval() {
        try {

            ResultSet pendingImages = Holmes.selectImages(null, "24", "Pending");
            ResultSet approvedImages = Holmes.selectImages(null, "10", "Approved");
            ResultSet rejectedImages = Holmes.selectImages(null, "27", "Rejected");

        } catch(ForbiddenQueryException e) {
            fail();
        }
    }

    @Ignore
    @Test
    public void T07selectMedication() {

        ResultSet meds1 = Holmes.selectMedication("284", "27");
        ResultSet meds2 = Holmes.selectMedication(null, "27");
        ResultSet meds3 = Holmes.selectMedication("284", null);

    }

    @Ignore
    @Test
    public void T08selectPatient() {

    }

    @Ignore
    @Test
    public void T09addImage() {

        File image = new File("/Users/Daniels/Documents/Uni/CPSC/CPSC471/cats.jpeg");
        int size = (int) image.length();
        byte or[] = new byte[size];
        byte tmpBuff[] = new byte[size];


        try {
            FileInputStream fis= new FileInputStream(image);
            int read = fis.read(or, 0, size);
            if (read < size) {
                int remain = size - read;
                while (remain > 0) {
                    read = fis.read(tmpBuff, 0, remain);
                    System.arraycopy(tmpBuff, 0, or, size - remain, read);
                    remain -= read;
                }
            }
            fis.close();
        }  catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Holmes.addImage("2016-11-30", "24", "1002", "8", or, or);
        } catch (ForbiddenQueryException e)
        { }
    }

    @Ignore
    @Test
    public void T10addMedication() {
        String newMedicationSpecs = "'Adderall','24','1002','2mg','Consume pill with breakfast','Brain damage'";
        Holmes.addMedication(newMedicationSpecs);
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

    @Ignore
    @Test
    public void T15removeMedication() {

        String MName = "'Adderall'";
        String PatID = "'1002'";
        String DocID = "'24'";
        Holmes.removeMedication(MName, PatID, DocID);
    }

}