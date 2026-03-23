package Basic;

public class Main {
	
	public static void main(String[] args) {
		
		String date = "2026-12-12";
		String names[] = {"Cecil Stedman"};
		boolean attendance[] = {true};
		
		DataBaseConnectivity dbc = new DataBaseConnectivity();
		
		dbc.setdate(date);
		dbc.setname(names);
		dbc.setattendance(attendance);
		
		dbc.setusername("postgres");
		dbc.setpassword("karan2005");
		
		dbc.setDBname("new2");
		dbc.settablename("dayone");
		
		dbc.viewtable();
	}
	
}
