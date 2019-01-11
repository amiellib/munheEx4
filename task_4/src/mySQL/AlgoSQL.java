package mySQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 
 * @author Shilo Gilor and Amiel Liberman
 *
 */
public class AlgoSQL 
{
	/**
	 * 
	 * @param hashcode is the hash code for the file uploaded
	 * @return the average
	 */
	public double get_average(int hashcode)
	{
		String jdbcUrl="jdbc:mysql://ariel-oop.xyz:3306/oop"; //?useUnicode=yes&characterEncoding=UTF-8&useSSL=false";
		String jdbcUser="student";
		String jdbcPassword="student";
		double average = 0.0;
		int counter = 0;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = 
					DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);


			Statement statement = connection.createStatement();
			//select data
			String allCustomersQuery = "SELECT * FROM logs where SomeDouble like '%" + hashcode + "%';";// where SomeDouble like  limit 10;";
			ResultSet resultSet = statement.executeQuery(allCustomersQuery);
			while(resultSet.next())
			{
				counter++;
				average = average + resultSet.getDouble("Point");
			}
			average = average/counter;
			
			resultSet.close();		
			statement.close();		
			connection.close();
		}

		catch (SQLException sqle) {
			System.out.println("SQLException: " + sqle.getMessage());
			System.out.println("Vendor Error: " + sqle.getErrorCode());
		}

		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return average;
	}
	/**
	 * 
	 * @param hashcode is the hash code for the file uploaded
	 * @return the max value
	 */
	public double get_max(int hashcode)
	{
		String jdbcUrl="jdbc:mysql://ariel-oop.xyz:3306/oop"; //?useUnicode=yes&characterEncoding=UTF-8&useSSL=false";
		String jdbcUser="student";
		String jdbcPassword="student";
		double max = 0.0 ,temp = 0.0;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = 
					DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);
			Statement statement = connection.createStatement();
			//select data
			String allCustomersQuery = "SELECT * FROM logs where SomeDouble like '%" + hashcode + "%';";
			ResultSet resultSet = statement.executeQuery(allCustomersQuery);
		
			while(resultSet.next())
			{
				temp = resultSet.getDouble("Point");
				max = (max>temp) ? max : temp;
			}
			
			resultSet.close();		
			statement.close();		
			connection.close();
		}

		catch (SQLException sqle) {
			System.out.println("SQLException: " + sqle.getMessage());
			System.out.println("Vendor Error: " + sqle.getErrorCode());
		}

		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return max;
	}

}
