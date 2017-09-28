import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by hth on 2017/6/30.
 */
public class Mysql {

   static String driver = "com.mysql.jdbc.Driver";
   static String url = "jdbc:mysql://10.136.172.118:3307/uar4";
   static String username = "root";
   static String password = "root";
   static Connection conn;
   static PreparedStatement pstmt;


    public static String cj(String args) throws SQLException {
        conn = getConn();
        if(conn==null){
            System.out.println("与mysql数据库连接失败！");
        }else{
//            System.out.println("与mysql数据库连接成功！");
        }
        String cj = "select sum(recordCount) from uar_stat_filestat_cj where date="+args+" and InterfaceType='LTEHTTP'";
        try {
            pstmt = (PreparedStatement)conn.prepareStatement(cj);
            ResultSet rs = pstmt.executeQuery();
          if(rs.next()) return rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";

    }

    public static String hc(String args) throws SQLException {
        conn = getConn();
        if(conn==null){
            System.out.println("与mysql数据库连接失败！");
        }else{
//            System.out.println("与mysql数据库连接成功！");
        }
        String hc = "select sum(recordCount) from uar_stat_filestat where date="+args+"  and InterfaceType='LTEHTTP'";
        try {
            pstmt = (PreparedStatement)conn.prepareStatement(hc);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) return rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";

    }
    public static String ipsCJ(String args) throws SQLException {
        conn = getConn();
        if(conn==null){
            System.out.println("与mysql数据库连接失败！");
        }else{
//            System.out.println("与mysql数据库连接成功！");
        }
        String ipsCJ = "select ip, sum(recordCount) from uar_stat_filestat_cj where date="+args+" and   InterfaceType='LTEHTTP' group by ip";
        try {
            pstmt = (PreparedStatement)conn.prepareStatement(ipsCJ);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                System.out.println("采集机对应的http话单统计 ip:"+rs.getString(1) + " sum:"+rs.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";

    }
     static Connection getConn(){
        try {
            Class.forName(driver); //classLoader,加载对应驱动
            conn = (Connection) DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

}
