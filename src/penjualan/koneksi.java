/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uas.project.penjualan;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author anthonylee
 */
public class koneksi {
    static  Connection koneksi;
    static  Connection getConnection() throws SQLException {
          try {
            if(koneksi == null){
                String url      = "jdbc:mysql://localhost:3306/dbpenjualan";
                String user     = "root";
                String password = "prikitiw123";
           
                DriverManager.registerDriver(new com.mysql.jdbc.Driver());
                koneksi = DriverManager.getConnection(url, user, password);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return koneksi;
    }
}
