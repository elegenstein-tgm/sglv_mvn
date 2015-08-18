import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by fusions on 18.08.15.
 */
public class Connector {
    Connection con;
    public Connector(String url, String uname, String upwd) {
        try {
            con = DriverManager.getConnection(url, uname, upwd);
        }catch (SQLException e){

        }
    }

    public Connector() {
        this("jdbc:postgresql://localhost/sglv","fusions","abcc1233");
    }

    public void destroy() throws SQLException{
        con.setAutoCommit(true);
        con.commit();
        con.close();
    }

    public String[][] query(String table, String[] cols, String wherec){
        String colsw ="";
        if(wherec == null){
            wherec="";
        }
        if(cols == null){
            colsw="*";
        }else{
            for(int i = 0; i < cols.length; i++){
                if (i==0){
                    colsw = cols[i];
                }else{
                    colsw += ", "+cols[i];
                }
            }
        }
        /* Alles zusammen stopeln */
        String sql = "Select "+ colsw +" from "+table+" "+wherec;

        try{
            ResultSet rs = con.createStatement().executeQuery(sql);
            ArrayList<String[]> data = new ArrayList<String[]>();
            while (rs.next()) {
                String[] tmp = new String[rs.getMetaData().getColumnCount()];
                for (int i = 0; i < tmp.length; i++) {
                    tmp[i] = rs.getString(i + 1);
                }
                data.add(tmp);
            }
            String[][] ret = new String[data.size()][];
            for (int i = 0; i < ret.length; i++) {
                ret[i] = data.get(i);
            }
            return ret;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


}
