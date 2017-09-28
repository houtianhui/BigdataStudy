import java.sql.*;

/**
 * Created by hth on 2017/6/30.
 */
public class oracle {

    //orcl为oracle数据库中的数据库名，localhost表示连接本机的oracle数据库
    //1521为连接的端口号
    private static String url="jdbc:oracle:thin:@10.224.212.74:1521/bjdo";
//    private static String url="jdbc:oracle:thin:@10.224.141.183:8521/nmsz_pub";
    //system为登陆oracle数据库的用户名     10.224.141.183:8521/nmsz_pub
    private static String user="bjnoas";
    //manager为用户名system的密码
    private static String password="bjnoas%2013\n";
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

        System.out.println("全网采集 "+Mysql.cj(args[0]));
        System.out.println("全网合成 "+Mysql.hc(args[0]));
       Mysql.ipsCJ(args[0]);
        String  guigu ="select day_id, sum(XDR_COUNT) from F_Monitor_HTTP_SGW_D f where sgw_ip in ('100.71.127.80','100.71.127.81','100.71.127.86','100.71.127.87') and day_id = "+args[0]+" group by day_id";
        String  shubei ="select day_id, sum(XDR_COUNT) from F_Monitor_HTTP_SGW_D f where sgw_ip in ('100.71.125.1','100.71.125.2','100.71.125.3','100.71.125.4','100.71.125.5','100.71.125.6','100.71.125.7','100.71.125.8','100.71.125.9','100.71.125.10','100.71.125.16','100.71.125.17','100.71.125.18','100.71.125.19','100.71.125.20','100.71.125.21') and day_id = "+args[0]+" group by day_id";
        String  santai ="select day_id, sum(XDR_COUNT) from F_Monitor_HTTP_SGW_D f where sgw_ip in ('100.71.123.152','100.71.123.153','100.71.123.154','100.71.123.155','100.71.123.156','100.71.123.157','100.71.123.158','100.71.123.159','100.71.123.168','100.71.123.169','100.71.123.170','100.71.123.171','100.71.123.144','100.71.123.145','100.71.123.146','100.71.123.147','100.71.123.148','100.71.123.149','100.71.123.150','100.71.123.151','100.71.123.172','100.71.123.173','100.71.123.174','100.71.123.175','100.114.188.1','100.114.188.2','100.114.188.3','100.114.188.4','100.114.188.5','100.114.188.6','100.114.188.7','100.114.188.8','100.114.188.9','100.114.188.10','100.114.188.17','100.114.188.18','100.114.188.19','100.114.188.20','100.114.188.21','100.114.188.22','100.114.188.23','100.114.188.24','100.114.188.25','100.114.188.26') and day_id = "+args[0]+" group by day_id";
        String  caishikou ="select day_id, sum(XDR_COUNT) from F_Monitor_HTTP_SGW_D f where sgw_ip in ('100.71.124.16','100.71.124.17','100.71.124.18','100.71.124.19','100.71.124.20','100.71.124.21','100.71.124.22','100.71.124.23','100.71.124.24','100.71.124.25','100.71.124.26','100.71.124.27','100.71.124.96','100.71.124.97','100.71.124.98','100.71.124.99','100.71.124.100','100.71.124.101','100.71.124.102','100.71.124.103','100.71.124.104','100.71.124.105','100.71.124.106','100.71.124.107','100.114.189.129','100.114.189.130','100.114.189.131','100.114.189.132','100.114.189.133','100.114.189.134','100.114.189.135','100.114.189.136','100.114.189.137','100.114.189.138','100.114.189.145','100.114.189.146','100.114.189.147','100.114.189.148','100.114.189.149','100.114.189.150','100.114.189.151','100.114.189.152','100.114.189.153','100.114.189.154') and day_id = "+args[0]+" group by day_id";
        String  wangjing =" select day_id, sum(XDR_COUNT) from F_Monitor_HTTP_SGW_D f where sgw_ip in ('100.114.189.1','100.114.189.2','100.114.189.3','100.114.189.4','100.114.189.5','100.114.189.6','100.114.189.7','100.114.189.8','100.114.189.9','100.114.189.10','100.114.189.17','100.114.189.18','100.114.189.19','100.114.189.20','100.114.189.21','100.114.189.22','100.114.189.23','100.114.189.24','100.114.189.25','100.114.189.26') and day_id = "+args[0]+" group by day_id";
        String  dongzhimen ="select day_id, sum(XDR_COUNT) from F_Monitor_HTTP_SGW_D f where sgw_ip in ('100.71.122.129','100.71.122.130','100.71.122.131','100.71.122.132','100.71.122.133','100.71.122.134','100.71.122.135','100.71.122.136','100.71.122.137','100.71.122.138','100.71.122.145','100.71.122.146','100.71.122.147','100.71.122.148','100.71.122.149','100.71.122.150','100.71.122.151','100.71.122.152','100.71.122.153','100.71.122.154','100.71.122.65','100.71.122.66','100.71.122.67','100.71.122.68','100.71.122.69','100.71.122.70','100.71.122.71','100.71.122.72','100.71.122.73','100.71.122.74','100.71.122.75','100.71.122.76') and day_id = "+args[0]+" group by day_id";
        String  yizhuang ="select day_id, sum(XDR_COUNT) from F_Monitor_HTTP_SGW_D f where sgw_ip in ('100.71.123.16','100.71.123.17','100.71.123.18','100.71.123.19','100.71.123.20','100.71.123.21','100.71.123.22','100.71.123.23','100.71.123.24','100.71.123.25','100.71.123.26','100.71.123.27','100.71.123.64','100.71.123.65','100.71.123.66','100.71.123.67','100.71.123.68','100.71.123.69','100.71.123.70','100.71.123.71','100.71.123.72','100.71.123.73','100.71.123.74','100.71.123.75','100.114.188.129','100.114.188.130','100.114.188.131','100.114.188.132','100.114.188.133','100.114.188.134','100.114.188.135','100.114.188.136','100.114.188.137','100.114.188.138','100.114.188.145','100.114.188.146','100.114.188.147','100.114.188.148','100.114.188.149','100.114.188.150','100.114.188.151','100.114.188.152','100.114.188.153','100.114.188.154') and day_id = "+args[0]+" group by day_id";
        String  guiguB1 =" select day_id, sum(XDR_COUNT) from F_Monitor_HTTP_SGW_D f where sgw_ip in ('100.71.127.74','100.71.127.75','100.71.127.92','100.71.127.93') and day_id = "+args[0]+" group by day_id";
        String  changping ="select day_id, sum(XDR_COUNT) from F_Monitor_HTTP_SGW_D f where sgw_ip in ('100.71.127.200','100.71.127.201') and day_id = "+args[0]+" group by day_id";
        String  dabailou =" select day_id, sum(XDR_COUNT) from F_Monitor_HTTP_SGW_D f where sgw_ip in ('100.71.126.74','100.71.126.75','100.71.126.86','100.71.126.87') and day_id = "+args[0]+" group by day_id";
        String  mawadao ="select day_id, sum(XDR_COUNT) from F_Monitor_HTTP_SGW_D f where sgw_ip in ('100.71.126.200','100.71.126.201') and day_id = "+args[0]+" group by day_id";
        String  SGW33  ="select day_id, sum(XDR_COUNT) from F_Monitor_HTTP_SGW_D f where sgw_ip in ('100.71.127.80','100.71.127.81') and day_id = "+args[0]+" group by day_id";
        String  SGW34  ="select day_id, sum(XDR_COUNT) from F_Monitor_HTTP_SGW_D f where sgw_ip in ('100.71.127.87','100.71.127.86') and day_id = "+args[0]+"  group by day_id";
        String  SGW75  ="select day_id, sum(XDR_COUNT) from F_Monitor_HTTP_SGW_D f where sgw_ip in ('100.71.125.1','100.71.125.2','100.71.125.3','100.71.125.4','100.71.125.5','100.71.125.6','100.71.125.7','100.71.125.8','100.71.125.9','100.71.125.10') and day_id = "+args[0]+"  group by day_id";
        String  SGW76  ="select day_id, sum(XDR_COUNT) from F_Monitor_HTTP_SGW_D f where sgw_ip in ('100.71.125.16','100.71.125.17','100.71.125.18','100.71.125.19','100.71.125.20','100.71.125.21') and day_id = "+args[0]+"  group by day_id";
        String  SGW85  =" select day_id, sum(XDR_COUNT) from F_Monitor_HTTP_SGW_D f where sgw_ip in ('100.114.188.1','100.114.188.2','100.114.188.3','100.114.188.4','100.114.188.5','100.114.188.6','100.114.188.7','100.114.188.8','100.114.188.9','100.114.188.10') and day_id = "+args[0]+"  group by day_id";
        String  SGW86  ="select day_id, sum(XDR_COUNT) from F_Monitor_HTTP_SGW_D f where sgw_ip in ('100.114.188.17','100.114.188.18','100.114.188.19','100.114.188.20','100.114.188.21','100.114.188.22','100.114.188.23','100.114.188.24','100.114.188.25','100.114.188.26') and day_id = "+args[0]+"  group by day_id";
        String  SGW83  ="select day_id, sum(XDR_COUNT) from F_Monitor_HTTP_SGW_D f where sgw_ip in ('100.114.189.129','100.114.189.130','100.114.189.131','100.114.189.132','100.114.189.133','100.114.189.134','100.114.189.135','100.114.189.136','100.114.189.137','100.114.189.138') and day_id = "+args[0]+"  group by day_id";
        String  SGW84  ="select day_id, sum(XDR_COUNT) from F_Monitor_HTTP_SGW_D f where sgw_ip in ('100.114.189.145','100.114.189.146','100.114.189.147','100.114.189.148','100.114.189.149','100.114.189.150','100.114.189.151','100.114.189.152','100.114.189.153','100.114.189.154') and day_id = "+args[0]+"  group by day_id";
        String  SGW81  ="select day_id, sum(XDR_COUNT) from F_Monitor_HTTP_SGW_D f where sgw_ip in ('100.114.189.1','100.114.189.2','100.114.189.3','100.114.189.4','100.114.189.5','100.114.189.6','100.114.189.7','100.114.189.8','100.114.189.9','100.114.189.10') and day_id = "+args[0]+"  group by day_id";
        String  SGW82  ="select day_id, sum(XDR_COUNT) from F_Monitor_HTTP_SGW_D f where sgw_ip in ('100.114.189.17','100.114.189.18','100.114.189.19','100.114.189.20','100.114.189.21','100.114.189.22','100.114.189.23','100.114.189.24','100.114.189.25','100.114.189.26') and day_id = "+args[0]+"  group by day_id";
//        String  SGW68  =" select day_id, sum(XDR_COUNT) from F_Monitor_HTTP_SGW_D f where sgw_ip in ('100.71.122.65','100.71.122.66','100.71.122.67','100.71.122.68','100.71.122.69','100.71.122.70','100.71.122.71','100.71.122.72','100.71.122.73','100.71.122.74','100.71.122.75','100.71.122.76') and day_id = "+args[0]+"  group by day_id";
        String  SGW77  ="select day_id, sum(XDR_COUNT) from F_Monitor_HTTP_SGW_D f where sgw_ip in ('100.114.188.129','100.114.188.130','100.114.188.131','100.114.188.132','100.114.188.133','100.114.188.134','100.114.188.135','100.114.188.136','100.114.188.137','100.114.188.138') and day_id = "+args[0]+"  group by day_id";
        String  SGW78  ="select day_id, sum(XDR_COUNT) from F_Monitor_HTTP_SGW_D f where sgw_ip in ('100.114.188.145','100.114.188.146','100.114.188.147','100.114.188.148','100.114.188.149','100.114.188.150','100.114.188.151','100.114.188.152','100.114.188.153','100.114.188.154') and day_id = "+args[0]+"  group by day_id";
        String  SGW37  ="select day_id, sum(XDR_COUNT) from F_Monitor_HTTP_SGW_D f where sgw_ip in ('100.71.127.200','100.71.127.201') and day_id = "+args[0]+"  group by day_id";
        String  SGW31  ="select day_id, sum(XDR_COUNT) from F_Monitor_HTTP_SGW_D f where sgw_ip in ('100.71.126.74','100.71.126.75') and day_id = "+args[0]+"  group by day_id";
        String  SGW32  ="select day_id, sum(XDR_COUNT) from F_Monitor_HTTP_SGW_D f where sgw_ip in ('100.71.126.86','100.71.126.87') and day_id = "+args[0]+"  group by day_id";
        String  SGW39  ="select day_id, sum(XDR_COUNT) from F_Monitor_HTTP_SGW_D f where sgw_ip in ('100.71.126.200','100.71.126.201') and day_id = "+args[0]+"  group by day_id";
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
        rs = st.executeQuery("select day_id,sum(XDR_COUNT) from F_Monitor_HTTP_SGW_D f where day_id="+args[0]+" group by day_id  ");
        if (rs.next()) {
            System.out.println("全网htfs话数--- "+"day:"+rs.getInt("day_id")+" sum:"+rs.getLong("sum(XDR_COUNT)"));
        shujuzhiliang.quanwanghttfs = rs.getLong("sum(XDR_COUNT)");
        }
        rs = st.executeQuery(guigu);
        if (rs.next()) {
            System.out.println("硅谷--- "+"day:"+rs.getInt("day_id")+" sum:"+rs.getLong("sum(XDR_COUNT)"));
        shujuzhiliang.guigu = rs.getLong("sum(XDR_COUNT)");
        }
        rs = st.executeQuery(shubei);
        if (rs.next()) {
            System.out.println("数北--- "+"day:"+rs.getInt("day_id")+" sum:"+rs.getLong("sum(XDR_COUNT)"));
            shujuzhiliang.shubei = rs.getLong("sum(XDR_COUNT)");
        }

        rs = st.executeQuery(santai);
        if (rs.next()) {
            System.out.println("三台--- "+"day:"+rs.getInt("day_id")+" sum:"+rs.getLong("sum(XDR_COUNT)"));
            shujuzhiliang.santai = rs.getLong("sum(XDR_COUNT)");
        }
        rs = st.executeQuery(caishikou);
        if (rs.next()) {
            System.out.println("菜口--- "+"day:"+rs.getInt("day_id")+" sum:"+rs.getLong("sum(XDR_COUNT)"));
            shujuzhiliang.caishikou = rs.getLong("sum(XDR_COUNT)");
        }
        rs = st.executeQuery(wangjing);
        if (rs.next()) {
            System.out.println("望京--- "+"day:"+rs.getInt("day_id")+" sum:"+rs.getLong("sum(XDR_COUNT)"));
            shujuzhiliang.wangjing = rs.getLong("sum(XDR_COUNT)");
        }
        rs = st.executeQuery(dongzhimen);
        if (rs.next()) {
            System.out.println("东直--- "+"day:"+rs.getInt("day_id")+" sum:"+rs.getLong("sum(XDR_COUNT)"));
            shujuzhiliang.dongzhimen = rs.getLong("sum(XDR_COUNT)");
        }
        rs = st.executeQuery(yizhuang);
        if (rs.next()) {
            System.out.println("亦庄--- "+"day:"+rs.getInt("day_id")+" sum:"+rs.getLong("sum(XDR_COUNT)"));
            shujuzhiliang.yizhuang = rs.getLong("sum(XDR_COUNT)");
        }
        rs = st.executeQuery(guiguB1);
        if (rs.next()) {
            System.out.println("硅谷--- "+"day:"+rs.getInt("day_id")+" sum:"+rs.getLong("sum(XDR_COUNT)"));
            shujuzhiliang.guigusancheng = rs.getLong("sum(XDR_COUNT)");
        }
        rs = st.executeQuery(changping);
        if (rs.next()) {
            System.out.println("昌平--- "+"day:"+rs.getInt("day_id")+" sum:"+rs.getLong("sum(XDR_COUNT)"));
            shujuzhiliang.changpin = rs.getLong("sum(XDR_COUNT)");
        }
        rs = st.executeQuery(dabailou);
        if (rs.next()) {
            System.out.println("白楼--- "+"day:"+rs.getInt("day_id")+" sum:"+rs.getLong("sum(XDR_COUNT)"));
            shujuzhiliang.dabailou = rs.getLong("sum(XDR_COUNT)");
        }
        rs = st.executeQuery(mawadao);
        if (rs.next()) {
            System.out.println("马道--- "+"day:"+rs.getInt("day_id")+" sum:"+rs.getLong("sum(XDR_COUNT)"));
            shujuzhiliang.madao = rs.getLong("sum(XDR_COUNT)");
        }
        rs = st.executeQuery(SGW33);
        if (rs.next()) {
            System.out.println("SGW33--- "+"day:"+rs.getInt("day_id")+" sum:"+rs.getLong("sum(XDR_COUNT)"));
            shujuzhiliang.SGW33 = rs.getLong("sum(XDR_COUNT)");
        }
        rs = st.executeQuery(SGW34);
        if (rs.next()) {
            System.out.println("SGW34--- "+"day:"+rs.getInt("day_id")+" sum:"+rs.getLong("sum(XDR_COUNT)"));
            shujuzhiliang.SGW34 = rs.getLong("sum(XDR_COUNT)");
        }
        rs = st.executeQuery(SGW75);
        if (rs.next()) {
            System.out.println("SGW75--- "+"day:"+rs.getInt("day_id")+" sum:"+rs.getLong("sum(XDR_COUNT)"));
            shujuzhiliang.SGW75 = rs.getLong("sum(XDR_COUNT)");
        }
        rs = st.executeQuery(SGW76);
        if (rs.next()) {
            System.out.println("SGW76--- "+"day:"+rs.getInt("day_id")+" sum:"+rs.getLong("sum(XDR_COUNT)"));
            shujuzhiliang.SGW76 = rs.getLong("sum(XDR_COUNT)");
        }
        rs = st.executeQuery(SGW85);
        if (rs.next()) {
            System.out.println("SGW85--- "+"day:"+rs.getInt("day_id")+" sum:"+rs.getLong("sum(XDR_COUNT)"));
            shujuzhiliang.SGW85 = rs.getLong("sum(XDR_COUNT)");
        }
        rs = st.executeQuery(SGW86);
        if (rs.next()) {
            System.out.println("SGW86--- "+"day:"+rs.getInt("day_id")+" sum:"+rs.getLong("sum(XDR_COUNT)"));
            shujuzhiliang.SGW86 = rs.getLong("sum(XDR_COUNT)");
        }
        rs = st.executeQuery(SGW83);
        if (rs.next()) {
            System.out.println("SGW83--- "+"day:"+rs.getInt("day_id")+" sum:"+rs.getLong("sum(XDR_COUNT)"));
            shujuzhiliang.SGW83 = rs.getLong("sum(XDR_COUNT)");
        }
        rs = st.executeQuery(SGW84);
        if (rs.next()) {
            System.out.println("SGW84--- "+"day:"+rs.getInt("day_id")+" sum:"+rs.getLong("sum(XDR_COUNT)"));
            shujuzhiliang.SGW84 = rs.getLong("sum(XDR_COUNT)");
        }
        rs = st.executeQuery(SGW81);
        if (rs.next()) {
            System.out.println("SGW81--- "+"day:"+rs.getInt("day_id")+" sum:"+rs.getLong("sum(XDR_COUNT)"));
            shujuzhiliang.SGW81 = rs.getLong("sum(XDR_COUNT)");
        }
        rs = st.executeQuery(SGW82);
        if (rs.next()) {
            System.out.println("SGW82--- "+"day:"+rs.getInt("day_id")+" sum:"+rs.getLong("sum(XDR_COUNT)"));
            shujuzhiliang.SGW82 = rs.getLong("sum(XDR_COUNT)");
        }
        rs = st.executeQuery(dongzhimen);
        if (rs.next()) {
            System.out.println("SGW68--- "+"day:"+rs.getInt("day_id")+" sum:"+rs.getLong("sum(XDR_COUNT)"));
            shujuzhiliang.SGW68 = rs.getLong("sum(XDR_COUNT)");
        }
        rs = st.executeQuery(SGW77);
        if (rs.next()) {
            System.out.println("SGW77--- "+"day:"+rs.getInt("day_id")+" sum:"+rs.getLong("sum(XDR_COUNT)"));
            shujuzhiliang.SGW77 = rs.getLong("sum(XDR_COUNT)");
        }
        rs = st.executeQuery(SGW78);
        if (rs.next()) {
            System.out.println("SGW78--- "+"day:"+rs.getInt("day_id")+" sum:"+rs.getLong("sum(XDR_COUNT)"));
            shujuzhiliang.SGW78 = rs.getLong("sum(XDR_COUNT)");
        }
        rs = st.executeQuery(SGW37);
        if (rs.next()) {
            System.out.println("SGW37--- "+"day:"+rs.getInt("day_id")+" sum:"+rs.getLong("sum(XDR_COUNT)"));
            shujuzhiliang.SGW37 = rs.getLong("sum(XDR_COUNT)");
        }
        rs = st.executeQuery(SGW31);
        if (rs.next()) {
            System.out.println("SGW31--- "+"day:"+rs.getInt("day_id")+" sum:"+rs.getLong("sum(XDR_COUNT)"));
//            shujuzhiliang.guigu = rs.getLong("sum(XDR_COUNT)");
        }
        rs = st.executeQuery(SGW32);
        if (rs.next()) {
            System.out.println("SGW32--- "+"day:"+rs.getInt("day_id")+" sum:"+rs.getLong("sum(XDR_COUNT)"));
            shujuzhiliang.SGW32 = rs.getLong("sum(XDR_COUNT)");
        }
        rs = st.executeQuery(SGW39);
        if (rs.next()) {
            System.out.println("SGW39--- "+"day:"+rs.getInt("day_id")+" sum:"+rs.getLong("sum(XDR_COUNT)"));
            shujuzhiliang.SGW39 = rs.getLong("sum(XDR_COUNT)");
        }

    }




















    /* 更新符合要求的记录，并返回更新的记录数目*/
    public static void update() {
//        conn = getConnection(); //同样先要获取连接，即连接到数据库
        try {
            String sql = "update staff set wage='2200' where name = 'lucy'";// 更新数据的sql语句

            st = (Statement) conn.createStatement();    //创建用于执行静态sql语句的Statement对象，st属局部变量

            int count = st.executeUpdate(sql);// 执行更新操作的sql语句，返回更新数据的个数

            System.out.println("staff表中更新 " + count + " 条数据");      //输出更新操作的处理结果

            conn.close();   //关闭数据库连接

        } catch (SQLException e) {
            System.out.println("更新数据失败");
        }
    }

    /* 查询数据库，输出符合要求的记录的情况*/
    public static void query(String dates) {

//        conn = getConnection(); //同样先要获取连接，即连接到数据库
        try {
            String sql = "select a.hour_id,c.被叫接通率,d.被叫回落成功率,a.主叫接通率,b.主叫回落成功率,e.接收短信成功率,f.发送短信成功率 from  (select hour_id as hour_id,sum(t.lte_csfb_moc_002)/sum(t.lte_csfb_moc_003) as 主叫接通率 from f_lte_csfb_moc_h t where hour_id >= "+dates+" and t.lte_csfb_moc_003>0 group by hour_id) a  left join  (select hour_id as hour_id,sum(t.lte_csfb_moc_003)/sum(t.lte_csfb_moc_001) as 主叫回落成功率 from f_lte_csfb_moc_h t where hour_id >= "+dates+" and t.lte_csfb_moc_001>0 group by hour_id) b on a.hour_id=b.hour_id left join  (select hour_id as hour_id,sum(t.lte_csfb_mtc_008)/sum(t.lte_csfb_mtc_002) as 被叫接通率 from f_lte_csfb_mtc_h t where hour_id >= "+dates+" and t.lte_csfb_mtc_002>0 group by hour_id) c on a.hour_id = c.hour_id left join  (select hour_id as hour_id,sum(t.lte_csfb_mtc_002)/sum(t.lte_csfb_mtc_032) as 被叫回落成功率 from f_lte_csfb_mtc_h t where hour_id >= "+dates+" and t.lte_csfb_mtc_032>0 group by hour_id) d on a.hour_id = d.hour_id  left join  (select hour_id as hour_id,sum(t.lte_csfb_mtsms_002)/sum(t.lte_csfb_mtsms_001) as 接收短信成功率 from f_LTE_CSFB_MTSMS_H t  where t.hour_id >= "+dates+" and t.lte_csfb_mtsms_001>0 group by hour_id) e on a.hour_id = e.hour_id left join  (select hour_id as hour_id,sum(t.lte_csfb_mosms_002)/sum(t.lte_csfb_mosms_001) as 发送短信成功率 from f_LTE_CSFB_MOSMS_H t  where t.hour_id >= "+dates+" and t.lte_csfb_mosms_001>0 group by hour_id) f  on a.hour_id = f.hour_id order by a.hour_id";
            st = (Statement) conn.createStatement();    //创建用于执行静态sql语句的Statement对象，st属局部变量

            ResultSet rs = st.executeQuery(sql);    //执行sql查询语句，返回查询数据的结果集
            System.out.println("最后的查询结果为：");
            while (rs.next()) { // 判断是否还有下一个数据

                // 根据字段名获取相应的值
                int name = rs.getInt("hour_id");
//                int age = rs.getInt("c.被叫接通率");
//                String sex = rs.getString("d.被叫回落成功率");
//                String address = rs.getString("address");
//                String depart = rs.getString("depart");
//                String worklen = rs.getString("worklen");
//                String wage = rs.getString("wage");
                System.out.println(name);
                //输出查到的记录的各个字段的值

            }
            conn.close();   //关闭数据库连接

        } catch (SQLException e) {
            System.out.println("查询数据失败");
        }
    }

    /* 删除符合要求的记录，输出情况*/
    public static void delete() {

//        conn = getConnection(); //同样先要获取连接，即连接到数据库
        try {
            String sql = "delete from staff  where name = 'lili'";// 删除数据的sql语句
            st = (Statement) conn.createStatement();    //创建用于执行静态sql语句的Statement对象，st属局部变量

            int count = st.executeUpdate(sql);// 执行sql删除语句，返回删除数据的数量

            System.out.println("staff表中删除 " + count + " 条数据\n");    //输出删除操作的处理结果

            conn.close();   //关闭数据库连接

        } catch (SQLException e) {
            System.out.println("删除数据失败");
        }

    }
    /* 获取数据库连接的函数*/
//    public static Connection getConnection() {
//        Connection con = null;  //创建用于连接数据库的Connection对象
//        try {
//            Class.forName("com.mysql.jdbc.Driver");// 加载Mysql数据驱动
//
//            con = DriverManager.getConnection(
//                    "jdbc:mysql://localhost:3306/myuser", "root", "root");// 创建数据连接
//
//        } catch (Exception e) {
//            System.out.println("数据库连接失败" + e.getMessage());
//        }
//        return con; //返回所建立的数据库连接
//    }
}
