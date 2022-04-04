
import java.sql.*;

public class mineRead {

    public static int count;
    static String database = "jdbc:mariadb://192.168.1.3:3306/frudnicki_mine";
    static String user = "rudnickif";
    static String pass = "100528614";
    static Statement stmt = null;
    static results newResult = null;
    static int i = 0;
    public static results[] resultArr ;
    public static int noScores = 0;

        
    public static results[] readDatabase () {
        try{
            Connection conn = DriverManager.getConnection(database, user, pass);
            stmt = conn.createStatement();
         String countQ = "SELECT COUNT(Name) FROM frudnicki_minesweeper";
         ResultSet rs1 = stmt.executeQuery(countQ);
         int count = 0;
         if (rs1.next()){
         count = rs1.getInt("COUNT(Name)");
         }
         //System.out.println(count);
         noScores = count;
        } catch (SQLException err) {
            System.out.println(":)" + err.getMessage());
        }
        resultArr = new results [noScores + 1];
        String query = "SELECT Name, TimeWon, Difficulty FROM frudnicki_minesweeper";
        //String countQ = "SELECT COUNT(Name) FROM frudnicki_minesweeper";
        try {
            Connection conn = DriverManager.getConnection(database, user, pass);
            stmt = conn.createStatement();

            System.out.println("I am connected");
            ResultSet rs = stmt.executeQuery(query);
            //ResultSet rs1 = stmt.executeQuery(countQ);
            //int count = rs1.getInt("COUNT(Name)");
            //System.out.println(count);
            
            while (rs.next()) {
                String name = rs.getString("Name");
                int time = rs.getInt("TimeWon");
                String difficulty = rs.getString("Difficulty");
                
                    System.out.println(name + time + difficulty);
                    resultArr[i] = new results(name, time, difficulty);
                    /* resultArr[i].name = name;
                    resultArr[i].score = time;
                    resultArr[i].dif = difficulty;
                     */
                    i++;

            }
            resultArr[i] = new results("", 0, "");
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException err) {
            System.out.println(":(" + err.getMessage());
        }
        return resultArr;
    }
    public static void main(String[] args) {
    new mineRead();
}
}

