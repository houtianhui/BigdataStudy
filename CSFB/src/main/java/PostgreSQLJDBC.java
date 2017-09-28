import java.sql.*;
import java.util.ArrayList;

public class PostgreSQLJDBC {

    private static final String URL = "jdbc:postgresql://10.224.206.37:5432/bighead";
    private static final String UNAME = "loach";
    private static final String PWD = "loach";

    private static Connection conn = null;

    static {
        try {
            // 1.加载驱动程序
            Class.forName("org.postgresql.Driver");
            // 2.获得数据库的连接
            conn = (Connection) DriverManager.getConnection(URL, UNAME, PWD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
        //4.编写查询语句，创建statement
//        String sql = "select * from t_student where id = ?";
    public ArrayList<Integer> mocModel(String model,String day)  {
        String moc = "select hour_id,count(*) from i_lte_csfb_"+model+"_cdr_"+day+" group by hour_id order by hour_id;";
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(moc);

//        ps.setObject(1, "20170629");
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {  //这里逐行打印
            arrayList.add(rs.getInt("count"));
            System.out.println("hour_id:"+rs.getString("hour_id")+"count:"+rs.getInt("count"));
        }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    public ArrayList<Integer> s1apModel(String day) {
        String s1ap = "select hour_id,count(*) from i_lte_s1ap_tau_cdr_"+day+" group by hour_id order by hour_id;";
        PreparedStatement ps = null;
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        try {
            ps = conn.prepareStatement(s1ap);
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        ps.setObject(1, "20170629");
        ResultSet rs = null;
        try {
            rs = ps.executeQuery();
        while (rs.next()) {  //这里逐行打印
            arrayList.add(rs.getInt("count"));
            System.out.println("hour_id:"+rs.getString("hour_id")+"count:"+rs.getInt("count"));
        }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    public ArrayList<Integer> sgsModel(String day)  {
        String sgs = "select hour_id,count(*) from i_lte_sgs_mm_cdr_"+day+" group by hour_id order by hour_id;";
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sgs);

//        ps.setObject(1, "20170629");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {  //这里逐行打印
            arrayList.add(rs.getInt("count"));
            System.out.println("hour_id:"+rs.getString("hour_id")+"count:"+rs.getInt("count"));
        }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return arrayList;
    }
    public PostgreSQLJDBC() throws SQLException {
    }

}
