package db;

import java.sql.Connection;
import java.sql.Statement;

//데이터베이스, 테이블 생성    
public class crate_db_table {
	
	public crate_db_table() {
		
		Statement st=null;
		String create_db = "create database if not exists customer";
		String drop_db = "drop database if not exists customer";
				
		String createTable[] = {"create table if not exists admin(name varchar(20) not null, passwd varchar(20) not null, position varchar(20), jumin char(14),inputDate date, primary key(name,passwd))",""
						+ "create table if not exists contract(customerCode char(7) not null,contractName varchar(20) not null,regPrice int(10),regDate date not null, monthPrice int(10), adminName varchar(20) not null)",""
						+ "create table if not exists customer(code char(7) not null, name varchar(20) not null, birth date, tel varchar(20), address varchar(100),companey varchar(20), primary key(code,name))"};
		Connection con = db.connect.makeConnection("");
		try {
			st = con.createStatement();
			st.executeUpdate(create_db);
			st.executeUpdate("use customer");
			for(int i = 0 ; i < createTable.length;i++) {
				st.executeUpdate(createTable[i]);
			}
			System.out.println("ok");
		} catch (Exception e) {
			System.out.println("error");
		}
				
	}
}
