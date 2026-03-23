package Basic;

import java.sql.*;

public class DataBaseConnectivity {
	
	//Variables
	private String url = "jdbc:postgresql://localhost:5432/";

	private String tablename;
	
	private String DBname;
	private String username;
	private String password;
	
	private String date;
	private int[] id;
	private String[] name;
	private boolean[] attendance;
	
	//Setters
	public void setdate(String date) {
		this.date = date;
	}
	public void setid(int[] id) {
		this.id = id;
	}
	public void setname(String[] name) {
		this.name = name;
	}
	public void setattendance(boolean[] attendance) {
		this.attendance = attendance;
	}
	public void setDBname(String DBname) {
		this.DBname = DBname;
	}
	public void setusername(String username) {
		this.username = username;
	}
	public void setpassword(String password) {
		this.password = password;
	}
	public void settablename(String tablename) {
		this.tablename = tablename;
	}
	
	//Getters
	public String getdate() {
		return this.date;
	}
	public int[] getid() {
		return this.id;
	}
	public String[] getname() {
		return this.name;
	}
	public boolean[] getattendance() {
		return this.attendance;
	}
	public String getDBname() {
		return this.DBname;
	}
	public String getusername() {
		return this.username;
	}
	public String getpassword() {
		return this.password;
	}
	public String gettablename() {
		return this.tablename;
	}
	
	//Connection
	private Connection getconnection() throws SQLException {
		return DriverManager.getConnection(this.url+this.DBname, this.username , this.password);
	}
	
	
	public void createdatabase(String newDBname) {
		String DBquery = "CREATE DATABASE "+newDBname+";";
		try(Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", this.username, this.password);
			Statement st = con.createStatement()){
			st.execute(DBquery);
			
			System.out.println("table create successfully");
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}
	
	//CRUD Operations 
	//Create *C*
	public void createtable() {
		String tablequery = "CREATE TABLE "+this.tablename+"(\r\n"
				+ "    attendance_date DATE,\r\n"
				+ "    stud_id SERIAL PRIMARY KEY,\r\n"
				+ "    stud_name VARCHAR(100),\r\n"
				+ "    stud_attendance BOOLEAN\r\n"
				+ ");";
		
		try(Connection con = getconnection();
			Statement st = con.createStatement();) {
			st.executeUpdate(tablequery);
			
			System.out.println("table created");
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}
	
	public void insertvalues() {
		String insertquery = "INSERT INTO "+this.tablename+" (attendance_date, stud_name, stud_attendance)\r\n"
				+ "VALUES (?, ?, ?);";
		Date sqldate = Date.valueOf(date);
		try (Connection con = getconnection();
			PreparedStatement pst = con.prepareStatement(insertquery);) {
			
			if(name.length == attendance.length) {
				for(int i=0;i<name.length;i++) {
					pst.setDate(1, sqldate);
					pst.setString(2, name[i]);
					pst.setBoolean(3, attendance[i]);
					
					pst.executeUpdate();
					System.out.println(i+"-line inserted");
				}
				System.out.println("insertion completed");
			}
			else {
				int mismatch = name.length - attendance.length;
				System.out.println("get same number of name as attendance value like 10/10");
				System.out.println("insertion failed--difference "+mismatch);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//Read *R*
	public void viewtable() {
		String viewtablequery = "SELECT * FROM "+this.tablename+" ORDER BY stud_id ASC;";
		
		try (Connection con = getconnection();
			PreparedStatement pst = con.prepareStatement(viewtablequery);
			ResultSet rs = pst.executeQuery();){			
			while(rs.next()) {
				Date date = rs.getDate("attendance_date");
				int student_id = rs.getInt("stud_id");
				String studentname = rs.getString("stud_name");
				boolean attendance = rs.getBoolean("stud_attendance");
				
				System.out.println(date +"|"+ student_id +"|"+ studentname +"|" +attendance);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//Update *U*
	public void updatename(int id, String name) {
		String updatenamequery = "UPDATE "+this.tablename+"\r\n"
				+ "SET stud_name = ?\r\n"
				+ "WHERE stud_id = ?;";
		
		try (Connection con = getconnection();
			PreparedStatement pst = con.prepareStatement(updatenamequery);){
			pst.setString(1, name);
			pst.setInt(2, id);
	
			int affectedrows = pst.executeUpdate();
			System.out.println(affectedrows+" lineupdated");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void updateattendance(int id, boolean attendance) {
		String updatenamequery = "UPDATE "+this.tablename+"\r\n"
				+ "SET stud_attendance = ?\r\n"
				+ "WHERE stud_id = ?;";
		
		try (Connection con = getconnection();
			PreparedStatement pst = con.prepareStatement(updatenamequery)){
			pst.setBoolean(1, attendance);
			pst.setInt(2, id);
	
			int affectedrows = pst.executeUpdate();
			System.out.println(affectedrows+" lineupdated");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//Delete(ROW) and Drop(TABLE) *D*
	public void deleterow(int id) {
		String deleterowquery = "DELETE FROM "+this.tablename+" \r\n"
				+ "where stud_id = ?;";
		
		try (Connection con = getconnection();
			PreparedStatement pst = con.prepareStatement(deleterowquery)){
			
			pst.setInt(1, id);
			int affectedrows = pst.executeUpdate();

			System.out.println(affectedrows+" row affeted");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void droptable() {
		String droptablequery = "DROP TABLE "+this.tablename+";";
		
		try (Connection con = getconnection();
			Statement st = con.createStatement();){

			st.executeUpdate(droptablequery);
			System.out.println("table dropped");
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}
	
}
