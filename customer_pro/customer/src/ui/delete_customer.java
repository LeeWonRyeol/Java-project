package ui;

import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import ui.update_customer.Center;
import ui.update_customer.South;


//고객데이터 삭제를 위한 클래스
public class delete_customer extends JFrame{
	Vector<String> vt;
	public delete_customer(Vector<String> v) {
		this.vt = v;
		String name = vt.get(1);		//선택되어 넘겨진것의 내용중 고객명을 가져옴
		int var = JOptionPane.showConfirmDialog(null, name + "님을 정말 삭제하시겠습니까?","고객정보 삭제",JOptionPane.YES_NO_OPTION);	//선택된 고객의 데이터를 삭제를 할지 안할지를 묻는 팝업창
		if(var == 0) {				//확인버튼을 누르면 데이터삭제
			String sql = "delete from customer where name='"+name+"'"; //데이터 삭제를 위한 sql문
			Connection conn = null;
			Statement st = null;
			try {
				conn = db.connect.makeConnection("customer");
				st = conn.createStatement();
				st.execute(sql);
			} catch (Exception e) {
			}
		}else {						//취소버튼을 누르면 삭제되지않고 창이닫힘
			dispose();
		}
		
	}
}
