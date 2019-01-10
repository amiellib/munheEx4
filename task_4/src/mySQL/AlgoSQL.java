package mySQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class AlgoSQL 
{
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
			System.out.println(hashcode);
			//select data
			String allCustomersQuery = "SELECT * FROM logs where SomeDouble like '%" + hashcode + "%';";// where SomeDouble like  limit 10;";
			ResultSet resultSet = statement.executeQuery(allCustomersQuery);
		//	System.out.println("FirstID\t\tSecondID\tThirdID\t\tLogTime\t\t\t\tPoint\t\tSomeDouble");
			while(resultSet.next())
			{
				counter++;
		/*		System.out.println(resultSet.getInt("FirstID")+"\t\t" +
						resultSet.getInt("SecondID")+"\t\t" +
						resultSet.getInt("ThirdID")+"\t\t" +
						resultSet.getTimestamp("LogTime") +"\t\t\t\t" +
						resultSet.getDouble("Point") +"\t\t" +
						resultSet.getDouble("SomeDouble")); */
				System.out.println(resultSet.getDouble("Point"));
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
	public double get_max(int hashcode)
	{
		String jdbcUrl="jdbc:mysql://ariel-oop.xyz:3306/oop"; //?useUnicode=yes&characterEncoding=UTF-8&useSSL=false";
		String jdbcUser="student";
		String jdbcPassword="student";
		double max = 0.0 ,temp = 0.0;
		int counter = 0;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = 
					DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);


			Statement statement = connection.createStatement();
			System.out.println(hashcode);
			//select data
			String allCustomersQuery = "SELECT * FROM logs where SomeDouble like '%" + hashcode + "%';";// where SomeDouble like  limit 10;";
			ResultSet resultSet = statement.executeQuery(allCustomersQuery);
		//	System.out.println("FirstID\t\tSecondID\tThirdID\t\tLogTime\t\t\t\tPoint\t\tSomeDouble");
			while(resultSet.next())
			{
				counter++;
		/*		System.out.println(resultSet.getInt("FirstID")+"\t\t" +
						resultSet.getInt("SecondID")+"\t\t" +
						resultSet.getInt("ThirdID")+"\t\t" +
						resultSet.getTimestamp("LogTime") +"\t\t\t\t" +
						resultSet.getDouble("Point") +"\t\t" +
						resultSet.getDouble("SomeDouble")); */
				System.out.println(resultSet.getDouble("Point"));
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
