
import java.sql.*;

public class mineRead {

    public static int count;
    String database = "jdbc:mariadb://192.168.1.3:3306/frudnicki_mine";
    String user = "rudnickif";
    String pass = "100528614";
    Statement stmt = null;
    results newResult = null;
    int i = 1;
    static results[] resultArr = new results[50];
            

    public mineRead() {

        String query = "SELECT Name, TimeWon, Difficulty FROM frudnicki_minesweeper";
        String countQ = "SELECT COUNT(Name) FROM frudnicki_minesweeper";
        try {
            Connection conn = DriverManager.getConnection(database, user, pass);
            stmt = conn.createStatement();

            System.out.println("I am connected");
            ResultSet rs = stmt.executeQuery(query);
            ResultSet rs1 = stmt.executeQuery(countQ);
            int count = rs1.getInt("COUNT(Name)");
            System.out.println(count);
            
            while (rs.next()) {
                String name = rs.getString("Name");
                int time = rs.getInt("TimeWon");
                String difficulty = rs.getString("Difficulty");
                
                    System.out.println(name + time + difficulty);
                    newResult = new results(name, time, difficulty);
                    resultArr[i] = newResult;
                    i++;

            }
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException err) {
            System.out.println(":)" + err.getMessage());
        }
        
        //System.exit(0);

    }
    public static void main(String[] args) {
    new mineRead();
}
}

