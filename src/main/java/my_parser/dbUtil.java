package my_parser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lexx on 08.07.2017.
 */
public class dbUtil {

    public Connection dbConnect(){

        Connection con;
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hh_parser?serverTimezone=UTC","root", "");
        } catch(SQLException e){
            System.out.println("SQL exception occured: " + e);
            con=null;
        }
        return con;
    }

    public static void insertData(Connection con, HashMap<String,Integer> hashMap, String lang){

        Date date=new Date();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currTime = sdf.format(date);
        String query ="";
        for (Map.Entry<String,Integer> hm:hashMap.entrySet()) {
                try {
                    Statement stmt = con.createStatement();
                    query = "INSERT INTO hh_parser.tech (name, date, count, lang) \n" +
                        " VALUES ('"+hm.getKey()+"','"+currTime+"','"+hm.getValue()+"','"+lang+"');";
                    //System.out.println(query);
                    stmt.executeUpdate(query);
               } catch(SQLException e){
                   System.out.println("Error " + e+" in query="+query);
               }
        }

    }


}
