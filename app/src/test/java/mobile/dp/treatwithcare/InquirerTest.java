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
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class InquirerTest
{



    static String user = "a5375068_CAREDB";
    static String pass = "s3xyasfuck";
    static String db = "a5375068_CAREDB";
    static String server = "http://sql9.000webhost.com/";

    private Inquirer charlesHolmes;
    private Connection con;

    public InquirerTest()
    {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        con = null;
        String connectionURL = null;

        try{
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connectionURL = "mysql8.000webhost.com/" + server + ";" + "databaseName=" + db + ";user=" + user + ";password=" + pass + ";";
            con = DriverManager.getConnection(connectionURL);

        }catch(Exception e) {
            Log.e("Error: ", e.getMessage());
        }

        charlesHolmes = new Inquirer(con, db);
    }

    @Before
    public void setup()
    {

    }


    @After
    public void teardown()
    {


    }



    @Test
    public void A_S_Medication() throws Exception
    {
        String PatID = "'8000'";
        String DocID = "'10'";
        String requestSent = "'Adderall', "+PatID+", "+DocID+", '9mg/day', 'Eat with breakfast', 'loosing your humanity'";
        charlesHolmes.addMedication(requestSent);


        ArrayList<ArrayList<String>> requestReceived = charlesHolmes.selectMedication(PatID, DocID);

        assertEquals(requestSent, requestReceived.get(0).get(0) +
                                  requestReceived.get(0).get(1) +
                                  requestReceived.get(0).get(2) +
                                  requestReceived.get(0).get(3)  );


    }
}