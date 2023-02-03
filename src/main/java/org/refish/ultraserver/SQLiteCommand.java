package org.refish.ultraserver;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.sql.*;

public class SQLiteCommand {
    Connection conn;

    public void setSQLiteConnection(Connection conn){
        this.conn=conn;
    }
    public void createNewTable(String sql) {
        // SQLite connection string
        // SQL statement for creating a new table
        try {
            Statement stmt = this.conn.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void insert(String Player, String Name,double LocationX,double LocationY,double LocationZ,String World) {
        String sql = "INSERT INTO PlayerHome(Player, Name, LocationX, LocationY, LocationZ, World) VALUES(?,?)";

        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, Player);
            pstmt.setString(2, Name);
            pstmt.setDouble(3,LocationX);
            pstmt.setDouble(4,LocationY);
            pstmt.setDouble(5,LocationZ);
            pstmt.setString(6,World);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public Location select(String Player, String Name) {
        String sql = "SELECT * FROM PlayerHome";
        double X = 0;
        double Y = 0;
        double Z = 0;
        String World = new String();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            // loop through the result set
            while (rs.next()) {
                if(Player.equals(rs.getString("Player"))&&Name.equals(rs.getString("Name"))){
                    X = rs.getDouble("LocationX");
                    Y = rs.getDouble("LocationY");
                    Z = rs.getDouble("LocationZ");
                    World = rs.getString("World");
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return new Location(Bukkit.getWorld(World),X,Y,Z);
    }



}
