package ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
//����ȸ â
public class Customer_inquiry extends JFrame{
	
	Vector<Vector<String>> rowData ;
	Vector<String> colData ;
	JTable table;
	JTextField jtf;
	
	public Customer_inquiry() {
		setTitle("�� ��ȸ");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
		add(new North(), BorderLayout.NORTH);
		add(new Center(), BorderLayout.CENTER);
		setVisible(true);
		setSize(700,600);
		Select_all();
	}
	
	class North extends JPanel{
		public North() {
			JLabel label  = new JLabel("����");
			jtf = new JTextField(10);
			JButton btn[] = new JButton[5];
			String btn_text[] = {"��ȸ","��ü����","����","����","�ݱ�"};
			
			add(label); 
			add(jtf);
			for(int i = 0 ; i < btn.length ; i++) {
				btn[i] = new JButton(btn_text[i]);
				add(btn[i]);
				btn[i].addActionListener(new ActionListener() {			//��ȸ, ��ü����, ����, ����, �ݱ� ��ư�� actionlistener�߰�
					@Override
					public void actionPerformed(ActionEvent e) {
						JButton b = (JButton)e.getSource();
						switch(b.getText()) {
							case "��ȸ" : Select(); break;				
							case "��ü����" : Select_all(); break;				
							case "����" : Update(); break;		 
							case "����" : Delete(); Select_all(); break;		//���� �� ������ ���� �ݿ��ϱ� ���� ��ü���⸦ �ٷ� ����
							case "�ݱ�" : dispose(); break;
						}
					}
				});
			}
		}
	}
	
	Vector<String> vc;
	class Center extends JPanel{
		public Center() {
			rowData = new Vector<Vector<String>>();
			colData = new Vector<String>();
			String title[] = {"code","name","birth","tel","address","company"};
			for(int i = 0 ; i < title.length ; i++) {
				colData.add(title[i]);
			}
			table = new JTable(rowData,colData);				//
			JScrollPane jsp = new JScrollPane(table);
			add(jsp);
			table.addMouseListener(new MouseAdapter() {			//table�� mouseListener�߰�
				@Override
				public void mouseClicked(MouseEvent e) {		
					int selection = table.getSelectedRow();		//����Ʈ����� Ŭ���� ��� 
					vc = rowData.get(selection);				//����Ʈ����� Ŭ���� ����� ������ vector vc�� �־��� 
				}
			});
		}
	}
	
	void Select() {
		String search = jtf.getText();
		String sql = "select * from customer where name like'"+ search + "%'";		// ����ȸ â���� �˻��� ������ �Է��� �� �˻� ��ư�� Ŭ���ϸ� ��ġ�ϴ� ���� select�ϴ� sql��
		try {
			rowData.clear();							
			Connection conn = db.connect.makeConnection("customer");
			Statement st = conn.createStatement();
			ResultSet re = st.executeQuery(sql);
			while(re.next()) {
				Vector<String> v = new Vector<String>();
				for(int i = 0 ; i < 6 ; i++) {
					v.add(re.getString(i+1));	
				}
				rowData.add(v);
			}
			table.updateUI();
		} catch (Exception e) {
			
		}
	}
	
	void Select_all() {
		
		String sql = "select * from customer";
		
		try {
			rowData.clear();									
			Connection conn = db.connect.makeConnection("customer");
			Statement st = conn.createStatement();
			ResultSet re = st.executeQuery(sql);
			while(re.next()) {
				Vector<String> v = new Vector<String>();
				for(int i = 0 ; i < 6 ; i++) {
					v.add(re.getString(i+1));	
				}
				rowData.add(v);
			}
			table.updateUI();
		} catch (Exception e) {
			
		}
	}
	void Update() {
		new update_customer(vc);		//�������͸� ������Ʈ�ϴ� Ŭ���� ����
		table.clearSelection();		
	}
	void Delete() {
		new delete_customer(vc);		//�������͸� �����ϴ� Ŭ���� ����
		table.clearSelection();
	}
}
