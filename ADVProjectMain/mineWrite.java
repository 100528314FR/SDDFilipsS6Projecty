
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class mineWrite {
   static String name = menu.name;
   static int time = grid.score;
   static String diff = menu.difficulty;

   public mineWrite() {
      String database = "jdbc:mariadb://192.168.1.3:3306/frudnicki_mine";
      String user = "rudnickif";
      String pass = "100528614";
      Statement stmt = null;

      String query = "INSERT INTO frudnicki_minesweeper (Name, TimeWon, Difficulty) VALUES ('" + name + "', " + time + ", '" + diff + "') ";
      try {
         Connection conn = DriverManager.getConnection(database, user, pass);
         stmt = conn.createStatement();
         System.out.println("I am connected");
         stmt.executeQuery(query);

         if (stmt != null) {
            stmt.close();
         }
      } catch (SQLException err) {
         System.out.println("ERROR" + err.getMessage());
      }

      //System.exit(0);

   }

   public static void main(String args[]) {
      new mineWrite();
   }
}