import java.sql.*;

/**
 * Created by hth on 2017/8/23.
 */
public class OCsfb {
    //orcl为oracle数据库中的数据库名，localhost表示连接本机的oracle数据库
    //1521为连接的端口号
//    private static String url="jdbc:oracle:thin:@10.224.212.74:1521/bjdo";
        private static String url="jdbc:oracle:thin:@10.224.141.183:8521/nmsz_pub";
    //system为登陆oracle数据库的用户名     10.224.141.183:8521/nmsz_pub
    private static String user="bjdcenter";
    //manager为用户名system的密码
    private static String password="bjdcenter%2013";
    public static Connection conn;
    public static PreparedStatement ps;
    public static ResultSet rs;
    public static Statement st ;
    //连接数据库的方法
    public static void getConnection(){
        try {
            //初始化驱动包
            Class.forName("oracle.jdbc.driver.OracleDriver");
            //根据数据库连接字符，名称，密码给conn赋值
            conn= DriverManager.getConnection(url, user, password);

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }


    //测试能否与oracle数据库连接成功
    public static void main(String[] args) throws SQLException {
        getConnection();
        if(conn==null){
            System.out.println("与oracle数据库连接失败！");
        }else{
            System.out.println("与oracle数据库连接成功！");
        }
        try {
            st = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String ocsfb = "select a.hour_id,c.beijiaojietonglv,d.beijiaohuiluochenggonglv,a.zhujiaojietonglv,b.zhujiaohuiluochenggonglv,e.jieshouduanxinchenggonglv,f.fasongduanxinchenggonglv from  (select hour_id as hour_id,sum(t.lte_csfb_moc_002)/sum(t.lte_csfb_moc_003) as zhujiaojietonglv from f_lte_csfb_moc_h t where hour_id >= "+args[0]+" and t.lte_csfb_moc_003>0 group by hour_id) a  left join  (select hour_id as hour_id,sum(t.lte_csfb_moc_003)/sum(t.lte_csfb_moc_001) as zhujiaohuiluochenggonglv from f_lte_csfb_moc_h t where hour_id >= " + args[0] + " and t.lte_csfb_moc_001>0 group by hour_id) b on a.hour_id=b.hour_id left join  (select hour_id as hour_id,sum(t.lte_csfb_mtc_008)/sum(t.lte_csfb_mtc_002) as beijiaojietonglv from f_lte_csfb_mtc_h t where hour_id >= " + args[0] + " and t.lte_csfb_mtc_002>0 group by hour_id) c on a.hour_id = c.hour_id left join  (select hour_id as hour_id,sum(t.lte_csfb_mtc_002)/sum(t.lte_csfb_mtc_032) as beijiaohuiluochenggonglv from f_lte_csfb_mtc_h t where hour_id >= " + args[0] + " and t.lte_csfb_mtc_032>0 group by hour_id) d on a.hour_id = d.hour_id  left join  (select hour_id as hour_id,sum(t.lte_csfb_mtsms_002)/sum(t.lte_csfb_mtsms_001) as jieshouduanxinchenggonglv from f_LTE_CSFB_MTSMS_H t  where t.hour_id >= " + args[0] + " and t.lte_csfb_mtsms_001>0 group by hour_id) e on a.hour_id = e.hour_id left join  (select hour_id as hour_id,sum(t.lte_csfb_mosms_002)/sum(t.lte_csfb_mosms_001) as fasongduanxinchenggonglv from f_LTE_CSFB_MOSMS_H t  where t.hour_id >= " + args[0] + " and t.lte_csfb_mosms_001>0 group by hour_id) f  on a.hour_id = f.hour_id order by a.hour_id";
        rs = st.executeQuery(ocsfb);
        while (rs.next()) {
                System.out.println("date:" + rs.getInt("hour_id") + " 被叫接通率：" + rs.getDouble("beijiaojietonglv") + " 被叫回落成功率：" + rs.getDouble("beijiaohuiluochenggonglv") + " 主叫接通率:" + rs.getDouble("zhujiaojietonglv") + " 主叫回落成功率:" + rs.getDouble("zhujiaohuiluochenggonglv") + " 接收短信成功率:" + rs.getDouble("jieshouduanxinchenggonglv") + " 发送短信成功率:" + rs.getDouble("fasongduanxinchenggonglv"));

        }
    }
}
