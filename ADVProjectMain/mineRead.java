
import java.sql.*;

public class mineRead {

    public static int count;
    String database = "jdbc:mariadb://192.168.1.3:3306/frudnicki_mine";
    String user = "rudnickif";
    String pass = "100528614";
    Statement stmt = null;
    results[] resultArr = null;

    public mineRead() {

        String query = "SELECT Name, TimeWon, Difficulty, COUNT(Name) FROM frudnicki_minesweeper";
        try {
            Connection conn = DriverManager.getConnection(database, user, pass);
            stmt = conn.createStatement();
            System.out.println("I am connected");
            ResultSet rs = stmt.executeQuery(query);
            int count = rs.getInt("COUNT(Name)");
            while (rs.next()) {
                String name = rs.getString("Name");
                int time = rs.getInt("TimeWon");
                String difficulty = rs.getString("Difficulty");
                
                for (int i = 0; i < count; i++) {
                    resultArr[i].name  = name;
                    resultArr[i].score  = time;
                    resultArr[i].dif = difficulty;
                }


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

