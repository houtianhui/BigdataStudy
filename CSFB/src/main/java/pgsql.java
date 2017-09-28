import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by hth on 2017/6/30.
 */
public class pgsql {

    public static void main(String[] args) throws SQLException {
        System.out.println("run");
        PostgreSQLJDBC pg =new  PostgreSQLJDBC();
        System.out.println("s1ap");
        pg.s1apModel(args[0]);
        pg.s1apModel(args[1]);

//        if(s1ap!=null)my.addMoc("s1ap",s1ap,args[0]);
        System.out.println("moc");
        pg.mocModel("moc",args[0]);
        pg.mocModel("moc",args[1]);
//        if(moc!=null)my.a性ddMoc("moc",moc,args[0]);
        System.out.println("mtc");
        pg.mocModel("mtc",args[0]);
        pg.mocModel("mtc",args[1]);
//        if(mtc!=null) my.addMoc("mtc",mtc,args[0]);
        System.out.println("mm");
        pg.mocModel("mm",args[0]);
        pg.mocModel("mm",args[1]);
//        if(mm!=null) my.addMoc("mm",mm,args[0]);
        System.out.println("sgs");
        pg.sgsModel(args[0]);
        pg.sgsModel(args[1]);
//        if(sgs!=null) my.addMoc("sgs",sgs,args[0]);


    }
}
