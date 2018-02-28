
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashSet;
import java.util.ListIterator;
import static java.util.Spliterators.iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;



public class DevamsizlikDB {

    //private String class_name;
    //private int absent_count=0;
    //ArrayList<String> lesson_list=new ArrayList<String>();
    public LinkedHashSet<String> lesson_list=new LinkedHashSet<String>();
    public LinkedHashSet<Integer> absentcount_list=new LinkedHashSet<Integer>();
    public LinkedHashSet<Integer> absentlimit_list=new LinkedHashSet<Integer>();

    final String kullanici_adi="root";
    final String parola="";
    
    final String db_isim="demo";
    final String host="localhost";
    
    final int port=3306;
    
    public Connection con=null;
    private Statement statement=null;
    private PreparedStatement preparedStatement=null;
    
    
    public void addDatabaseValues(String lesson_name,int absent_count,int absent_limit){
        
        try {
            statement=con.createStatement();
            String sorgu="Insert Into devamsizlik (ders_adi,devamsizlik_sayisi,devamsizlik_siniri) VALUES("+"'"+lesson_name+"','"+absent_count+"','"+absent_limit+"')";
            statement.executeUpdate(sorgu);
        } catch (SQLException ex) {
            Logger.getLogger(DevamsizlikDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void resetDB(){
        try {
            statement=con.createStatement();
            String sorgu="DELETE from devamsizlik where id>0";
            int deger=statement.executeUpdate(sorgu);
            System.out.println(deger+" kadar veri silindi.\n");
            
        } catch (SQLException ex) {
            Logger.getLogger(DevamsizlikDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void deleteDatabaseValue(String str){
        
        String sorgu="DELETE FROM `devamsizlik` WHERE `ders_adi` = ?";
        System.out.println(sorgu);
        try {
            preparedStatement=con.prepareStatement(sorgu);
            preparedStatement.setString(1, str);
            preparedStatement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(DevamsizlikDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void getDatabaseValues(){
        String database="Select * From devamsizlik";
        
        try {
            statement=con.createStatement();
            ResultSet rs=statement.executeQuery(database);
            
            while (rs.next()) {                
                String lesson_name=rs.getString("ders_adi");
                int absent_count=rs.getInt("devamsizlik_sayisi");
                int absent_limit=rs.getInt("devamsizlik_siniri");
                lesson_list.add(lesson_name);
                absentcount_list.add(absent_count);
                absentlimit_list.add(absent_limit);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DevamsizlikDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public DevamsizlikDB() {//Constructer Database connection made
        
        String url="jdbc:mysql://"+host+":"+port+"/"+db_isim+"?useUnicode=true&characterEncoding=utf8";//Sondaki türkçe karakter sorununu çözmek için
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DevamsizlikDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            con=DriverManager.getConnection(url,kullanici_adi,parola);
            System.out.println("Bağlantı Başarılı");
        } catch (SQLException ex) {
            System.out.println("Bağlantı başarısız.");
        }
    }
    
    
}
