package BDD;

import java.sql.*;

public class db_connection {
    
    Connection connection;
    Statement statement;
    String SQL;
    
    
    public Connection connexionDatabase() throws SQLException {

        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Patisserie","root","");
    	return connection;
 
    }
      
    public ResultSet exécutionQuery(String sql) throws SQLException {
       
        connexionDatabase();
        ResultSet resultSet = null;
        statement = connection.createStatement();
        resultSet = statement.executeQuery(sql);
        return resultSet;
    }
    public String exécutionUpdate(String sql) throws SQLException {
        
        connexionDatabase();
        String result = "";
        statement = connection.createStatement();
        statement.executeUpdate(sql);
        result = sql;
        return result;

    }
    
    public ResultSet querySelect(String[] nomColonne, String nomTable) throws SQLException {

        connexionDatabase();
        int i;
        SQL = "SELECT ";

        for (i = 0; i <= nomColonne.length - 1; i++) {
            SQL += nomColonne[i];
            if (i < nomColonne.length - 1) {
                SQL += ",";
            }
        }

        SQL += " FROM " + nomTable;
        return this.exécutionQuery(SQL);

    }
    public ResultSet querySelectAll(String nomTable, String état) throws SQLException {

        connexionDatabase();
        SQL = "SELECT * FROM " + nomTable + " WHERE " + état;
        return this.exécutionQuery(SQL);

    }
    
    public ResultSet querySelectAll(String nomTable) throws SQLException {

        connexionDatabase();
        SQL = "SELECT * FROM " + nomTable;
        return this.exécutionQuery(SQL);

    }
    public String queryInsert(String nomTable, String[] contenuTableau) throws SQLException {

        connexionDatabase();
        int i;
        SQL = "INSERT INTO " + nomTable + " VALUES(";

        for (i = 0; i <= contenuTableau.length - 1; i++) {
            SQL += "'" + contenuTableau[i] + "'";
            if (i < contenuTableau.length - 1) {
                SQL += ",";
            }
        }

        SQL += ")";
        return this.exécutionUpdate(SQL);

    }
    public String queryInsert(String nomTable, String[] nomColonne, String[] contenuTableau) throws SQLException {

        connexionDatabase();
        int i;
        SQL = "INSERT INTO " + nomTable + "(";
        for (i = 0; i <= nomColonne.length - 1; i++) {
            SQL += nomColonne[i];
            if (i < nomColonne.length - 1) {
                SQL += ",";
            }
        }
        SQL += ") VALUES(";
        for (i = 0; i <= contenuTableau.length - 1; i++) {
            SQL += "'" + contenuTableau[i] + "'";
            if (i < contenuTableau.length - 1) {
                SQL += ",";
            }
        }

        SQL += ")";
        return this.exécutionUpdate(SQL);

    }
    public String queryUpdate(String nomTable, String[] nomColonne, String[] contenuTableau, String état) throws SQLException {

        connexionDatabase();
        int i;
        SQL = "UPDATE " + nomTable + " SET ";

        for (i = 0; i <= nomColonne.length - 1; i++) {
            SQL += nomColonne[i] + "='" + contenuTableau[i] + "'";
            if (i < nomColonne.length - 1) {
                SQL += ",";
            }
        }

        SQL += " WHERE " + état;
        return this.exécutionUpdate(SQL);

    }
    public String queryDelete(String nomtable) throws SQLException {

        connexionDatabase();
        SQL = "DELETE FROM " + nomtable;
        return this.exécutionUpdate(SQL);

    }
    public String queryDelete(String nomTable, String état) throws SQLException {

        connexionDatabase();
        SQL = "DELETE FROM " + nomTable + " WHERE " + état;
        return this.exécutionUpdate(SQL);

    }

     
      
      
      
      
}
