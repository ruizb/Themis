/**
 * @author Alexandre Laffaille, Nathan Marin, Benoit Ruiz, Tristan Sallé
 */

package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import common.Utilitaire;

public class SQLManager
{
	private static SQLManager	instance	= null;
	private Connection			connection;
	
	public static final String	NO_WHERE	= "no_where";
	
	private SQLManager()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		try
		{
			Properties p = Utilitaire.getPropertiesFromFile("src/persistence/local.properties");
			String url = "jdbc:mysql://"+p.getProperty("host")+"/"+p.getProperty("database");
			connection = DriverManager.getConnection(url, p.getProperty("user"), p.getProperty("password"));
		}
		catch (SQLException e)
		{
			//e.printStackTrace();
		}
		
		if (connection == null) // couldn't connect to the local database
		{
			try
			{
				Properties p = Utilitaire.getPropertiesFromFile("src/persistence/remote.properties");
				String url = "jdbc:mysql://"+p.getProperty("host")+"/"+p.getProperty("database");
				connection = DriverManager.getConnection(url, p.getProperty("user"), p.getProperty("password"));
				System.err.println("WARNING : You are using a remote database : " + p.getProperty("host") + "/" + p.getProperty("database"));
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * Get the singleton instance
	 * 
	 * @return THE instance of SQLManager that should be used for the application.
	 */
	public static SQLManager getConnection()
	{
		if (instance == null)
			instance = new SQLManager();

		return instance;
	}

	/**
	 * 
	 * @param table
	 * @param fields
	 * @param where
	 */
	public ResultSet select(String table, ArrayList<String> fields, String where)
	{
		String query = "SELECT ";
		query += Utilitaire.implode(",", fields);
		query += " FROM " + table;
		
		if(where != SQLManager.NO_WHERE)
		{
			query += " WHERE " + where;
		}
		
		System.out.println(query);
		
		try
		{
			Statement statement = connection.createStatement();
			return statement.executeQuery(query);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * 
	 * @param tableName
	 * @param where
	 * @return
	 */
	public ResultSet select(String tableName, String where)
	{
		ArrayList<String> allFields = new ArrayList<String>();
		allFields.add("*");
		
		return select(tableName, allFields, where);
	}

	/**
	 * 
	 * @param table
	 * @param fieldsValues
	 * @return
	 */
	public int insert(String table, HashMap<String, String> fieldsValues)
	{

		String query = "INSERT INTO " + table + "(";

		ArrayList<String> fields = new ArrayList<String>();
		ArrayList<String> values = new ArrayList<String>();

		for (String key : fieldsValues.keySet())
		{
			fields.add(key);
			if(fieldsValues.get(key) == "null")
				values.add("null");
			else
				values.add("'" + fieldsValues.get(key) + "'");
		}

		query += Utilitaire.implode(",", fields);
		query += ") VALUES(";
		query += Utilitaire.implode(",", values);
		query += ")";

		System.out.println(query);

		// executing the query
		Statement s = this._executeQuery(query);
		
		// return the id of the last insert
		if(s != null)
			return this.getLastID(s);
		else
			return -1;
	}

	/**
	 * 
	 * @param table
	 * @param fieldsValues
	 * @param where
	 */
	public void update(String table, HashMap<String, String> fieldsValues, String where)
	{
		String query = "UPDATE " + table + " SET ";
		String value;
		for (String column : fieldsValues.keySet())
		{
			value = fieldsValues.get(column);
			if(value == "null")
				query += column + "=" + value + ",";
			else
				query += column + "='" + value + "',";
			
		}

		query = query.substring(0, query.length() - 1); // Getting rid of the last ',

		query += " WHERE " + where;

		System.out.println(query);
		
		// executing the query
		this._executeQuery(query);
	}

	/**
	 * Creates and executes a DELETE query
	 * 
	 * @param table table in which lines have to be deleted
	 * @param where the where clause of the query
	 */
	public void delete(String table, String where)
	{

		// building the query
		String query = "DELETE FROM ";
		query += table;
		if (where != SQLManager.NO_WHERE)
			query += " WHERE " + where;

		System.out.println(query);

		// executing the query
		this._executeQuery(query);
	}
	
	/**
	 * 
	 * @param table
	 * @param where
	 * @return
	 */
	public int count(String table, String where)
	{
		String query = "SELECT COUNT(*) FROM ";
		query += table;
		
		if(where != SQLManager.NO_WHERE)
		{
			query += " WHERE " + where;
		}
		
		System.out.println(query);
		
		try
		{
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(query);
			rs.first();
			return rs.getInt(1);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		return 0;
	}

	/**
	 * 
	 * @param query
	 * @return
	 */
	private Statement _executeQuery(String query)
	{
		try
		{
			Statement statement = connection.createStatement();
			statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
			return statement;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		return null;
	}

	/**
	 * Returns the last ID updated in the Database
	 * @param statement
	 * @return int : The last ID updated
	 */
	public int getLastID(Statement statement)
	{
		ResultSet lastID = null;
		try
		{
			lastID = statement.getGeneratedKeys();
			if (lastID.next())
				return lastID.getInt(1);

		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return -1;
	}
}
