package persistence;

import java.sql.*;
import java.util.HashMap;


public class SQLManager
{
	private static SQLManager instance = null;
	private Connection connection;
	private Statement statement;
	
	private SQLManager()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost/themis", "root", "boby34");
			statement = connection.createStatement();
			
			System.out.println("tududududu");
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static SQLManager getConnection()
	{
		if(instance == null)
			instance = new SQLManager();
		
		return instance;
	}
	
	/**
	 * 
	 * @param query : The query to be executed on the Database
	 * 
	 */
	public void query(String query)
	{
		try
		{
			System.out.println(statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS));
//			System.out.println(statement.getGeneratedKeys().getInt("id"));
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void query(String string, HashMap<String, String> correspondance)
	{
		String newQuery = string;
		for(String key: correspondance.keySet())
		{
			newQuery = newQuery.replace(key, correspondance.get(key));
		}
		System.out.println(newQuery);
		query(newQuery);
	}
	
	public int getLastID()
	{
		return 1;
	}
}
