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
//고객조회 창
public class Customer_inquiry extends JFrame{
	
	Vector<Vector<String>> rowData ;
	Vector<String> colData ;
	JTable table;
	JTextField jtf;
	
	public Customer_inquiry() {
		setTitle("고객 조회");
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
			JLabel label  = new JLabel("성명");
			jtf = new JTextField(10);
			JButton btn[] = new JButton[5];
			String btn_text[] = {"조회","전체보기","수정","삭제","닫기"};
			
			add(label); 
			add(jtf);
			for(int i = 0 ; i < btn.length ; i++) {
				btn[i] = new JButton(btn_text[i]);
				add(btn[i]);
				btn[i].addActionListener(new ActionListener() {			//조회, 전체보기, 수정, 삭제, 닫기 버튼에 actionlistener추가
					@Override
					public void actionPerformed(ActionEvent e) {
						JButton b = (JButton)e.getSource();
						switch(b.getText()) {
							case "조회" : Select(); break;				
							case "전체보기" : Select_all(); break;				
							case "수정" : Update(); break;		 
							case "삭제" : Delete(); Select_all(); break;		//삭제 후 삭제된 것을 반영하기 위해 전체보기를 바로 실행
							case "닫기" : dispose(); break;
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
			table.addMouseListener(new MouseAdapter() {			//table에 mouseListener추가
				@Override
				public void mouseClicked(MouseEvent e) {		
					int selection = table.getSelectedRow();		//리스트목록중 클릭한 요소 
					vc = rowData.get(selection);				//리스트목록중 클릭한 요소의 정보를 vector vc에 넣어줌 
				}
			});
		}
	}
	
	void Select() {
		String search = jtf.getText();
		String sql = "select * from customer where name like'"+ search + "%'";		// 고객조회 창에서 검색할 성명을 입력한 후 검색 버튼을 클릭하면 일치하는 고객만 select하는 sql문
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
		new update_customer(vc);		//고객데이터를 업데이트하는 클래스 실행
		table.clearSelection();		
	}
	void Delete() {
		new delete_customer(vc);		//고객데이터를 제거하는 클래스 실행
		table.clearSelection();
	}
}
