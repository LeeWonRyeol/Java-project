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


//�������� ������ ���� Ŭ����
public class delete_customer extends JFrame{
	Vector<String> vt;
	public delete_customer(Vector<String> v) {
		this.vt = v;
		String name = vt.get(1);		//���õǾ� �Ѱ������� ������ ������ ������
		int var = JOptionPane.showConfirmDialog(null, name + "���� ���� �����Ͻðڽ��ϱ�?","������ ����",JOptionPane.YES_NO_OPTION);	//���õ� ���� �����͸� ������ ���� �������� ���� �˾�â
		if(var == 0) {				//Ȯ�ι�ư�� ������ �����ͻ���
			String sql = "delete from customer where name='"+name+"'"; //������ ������ ���� sql��
			Connection conn = null;
			Statement st = null;
			try {
				conn = db.connect.makeConnection("customer");
				st = conn.createStatement();
				st.execute(sql);
			} catch (Exception e) {
			}
		}else {						//��ҹ�ư�� ������ ���������ʰ� â�̴���
			dispose();
		}
		
	}
}
