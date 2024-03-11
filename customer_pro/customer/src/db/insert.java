package db;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;
import java.util.StringTokenizer;

//만들어진 테이블의 데이터에 파일의 데이터를 추가하는 클래스
public class insert {
	public insert() {
		Connection conn = db.connect.makeConnection("customer");
		String insert[] = {"insert into admin value(?,?,?,?,?)",
				"insert into customer value(?,?,?,?,?,?)",
				"insert into contract value(?,?,?,?,?,?)"}; 
		PreparedStatement psmt = null;
		String file[] = {"admin","customer","contract"};
		try {
			for(int i = 0 ; i < file.length ; i++) {
				Scanner fscanner = new Scanner(new FileInputStream("C:\\Users\\sj_home\\Desktop\\customer_pro\\customer\\Datafiles\\" + file[i]+".txt"));
				psmt = conn.prepareStatement(insert[i]);
				String text = fscanner.nextLine();
				StringTokenizer token = new StringTokenizer(text,"\t");
				String file_text[] = new String[token.countTokens()];
				while(fscanner.hasNext()) {
					text = fscanner.nextLine();
					token = new StringTokenizer(text,"\t");
					int i2 = 0;
					while(token.hasMoreTokens()) {
						file_text[i2] = token.nextToken();
						psmt.setString(i2+1, file_text[i2] );
						i2++;
					}
					int re = psmt.executeUpdate();
				}
			}
		} catch (Exception e) {	
		}
	}
	public static void main(String[] args) {
		new crate_db_table();
		new insert();
	}

}
