package ui;
import java.awt.BorderLayout;
import java.awt.FileDialog;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

	

//계약관리창
public class Contract_management extends JFrame{
	Vector<Vector<String>> rowData;
	JTable table;
	JTextField right_jtf[];
	JTextField left_jtf[];
	JComboBox<String> combo1; //고객명 콤보박스
	JComboBox<String> combo2; //보험상품 콤보박스
	JComboBox<String> combo; //담당자 콤보박스
	public Contract_management() {
		setTitle("보험 계약");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
		add(new North(), BorderLayout.NORTH);
		add(new Center(), BorderLayout.CENTER);
		
		setVisible(true);
		setSize(500,700);
	}
	
	class North extends JPanel{
		public North() {
			setLayout(new BorderLayout());
			add(new North_Center(),BorderLayout.CENTER);
			add(new North_South(), BorderLayout.SOUTH);
			
		}
		class North_Center extends JPanel{
			public North_Center() {
				setLayout(new GridLayout(1,2));
				JLabel label[] = new JLabel[7];		
				add(new North_Center_left());
				add(new North_Center_right());
			}
			class North_Center_left extends JPanel{
				public North_Center_left() {
					setLayout(new GridLayout(4,2));
					JLabel label[] = new JLabel[4];
					String label_text[] = {"고객코드:","고 객 명:","생년월일:","연 락 처:"};
					left_jtf = new JTextField[3];
					combo1 = new JComboBox<String>();
					Connection conn = null;
					PreparedStatement psmt = null;
					String customer_name = "Select name from customer";	//고객 명을 출력하는 sql문
					try {
						conn = db.connect.makeConnection("customer");
						psmt = conn.prepareStatement(customer_name);
						ResultSet re = psmt.executeQuery();
						while(re.next()) {
							String name = re.getString("name");
							combo1.addItem(name);						//고객명 콤보박스에 고객명을 추가
						}
					} catch (Exception e) {
						
					}
					//좌측 그리드에 콤보박스,텍스트필드 추가
					for(int i = 0 ; i < label.length ; i++) {
						label[i] = new JLabel(label_text[i]);
						add(label[i]);
						if(i==0) {
							left_jtf[i] = new JTextField(10);
							add(left_jtf[i]);
						
						}
						if(i==1) {
							add(combo1);
						}
						else if(i>1){
							i--;
							left_jtf[i] = new JTextField(10);
							add(left_jtf[i]);
							i++;
						}
					}
					//콤보박스에서 선택된 고객명에 따른 정보 변화를 위한 ActionListener
					combo1.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							
							PreparedStatement psmt = null;
							Connection conn = null;
							
							String name = (String) combo1.getSelectedItem();	//콤보박스에 선택된 고객명을 가져온다
							String customer_info = "Select code,birth,tel from customer where name = '"+ name +"'";
							String setText[] = {"code","birth","tel"};
							try {
								conn = db.connect.makeConnection("customer");
								psmt = conn.prepareStatement(customer_info);
								ResultSet re = psmt.executeQuery();
								while(re.next()) {
									for(int i = 0 ; i < 3 ; i++) {		//코드,생년월일,연락처 3칸의 정보를 바꿔주는 반복문
										String info = re.getString(setText[i]);
										left_jtf[i].setText(info);
										left_jtf[i].setEnabled(false);	//JTextFiled를 비활성화 시킴 *직접수정불가*										
									}
								}
							} catch (Exception e1) {
								
							}
							Select_all();
						}
					});
					
					
					
					//콤보박스에서 선택된 고객명에 따른 정보 출력을 위한 sql문
					String name = (String) combo1.getSelectedItem();	//콤보박스에 선택된 고객명을 가져온다
					String customer_info = "Select code,birth,tel from customer where name = '"+ name +"'";
					String setText[] = {"code","birth","tel"};
					try {
						conn = db.connect.makeConnection("customer");
						psmt = conn.prepareStatement(customer_info);
						ResultSet re = psmt.executeQuery();
						while(re.next()) {
							for(int i = 0 ; i < 3 ; i++) {		//코드,생년월일,연락처 3칸의 정보를 바꿔주는 반복문
								String info = re.getString(setText[i]);
								left_jtf[i].setText(info);
								left_jtf[i].setEnabled(false);	//JTextFiled를 비활성화 시킴 *직접수정불가*										
							}
						}
					} catch (Exception e) {
						
					}
					
				}
			}
			
			class North_Center_right extends JPanel{
				public North_Center_right() {
					setLayout(new GridLayout(3,2));
					JLabel label[] = new JLabel[3];
					String label_text[] = {"보험상품:","가입금액:","월보험료:"};
					right_jtf = new JTextField[2];
					
					combo2 = new JComboBox<String>();
					Connection conn = null;
					PreparedStatement psmt = null;
					String contract = "select distinct ContractName from contract ";	//중복없이 보험상품 종류 출력하는 sql
					try {
						conn = db.connect.makeConnection("customer");
						psmt = conn.prepareStatement(contract);
						ResultSet re = psmt.executeQuery();
						while(re.next()) {
							String contract_name = re.getString("contractname");
							combo2.addItem(contract_name);								//보험상품 콤보박스에 보험상품종류 추가
						}
					} catch (Exception e) {
						
					}
					//우측 그리드에 콤보박스,텍스트필드 추가
					for(int i = 0 ; i < label.length ; i++) {
						label[i] = new JLabel(label_text[i]);
						add(label[i]);
						if(i==0) {
							add(combo2);
						}else if(i>0) {
							i--;
							right_jtf[i] = new JTextField(10);
							add(right_jtf[i]);
							i++;
						}
						
					}
					
				}
			}
		}
		
		class North_South extends JPanel{
			public North_South() {
				JLabel label = new JLabel("담당자: ");
				combo = new JComboBox<String>();
				JButton btn[] = new JButton[4];
				String btn_text[] = {"가입","삭제","파일로저장","닫기"};
				add(label);
				Connection conn = null;
				PreparedStatement psmt = null;
				String admin = "select name from admin ";
				try {
					conn = db.connect.makeConnection("customer");
					psmt = conn.prepareStatement(admin);
					ResultSet re = psmt.executeQuery();
					while(re.next()) {
						String admin_name = re.getString("name");
						combo.addItem(admin_name);
					}
				} catch (Exception e) {
					
				}
				add(combo);
				for(int i = 0 ; i < btn.length ; i++) {
					btn[i] = new JButton(btn_text[i]);
					add(btn[i]);
					//가입,삭제,파일로저장,닫기 버튼에 따른 ActionListener추가
					btn[i].addActionListener(new MyActionListener());						
				}
			}
		}
	}
	
	class MyActionListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton btn = (JButton)e.getSource();
			String pattern = "yyyy-mm-dd";
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			Date now = new Date();
			String nowString = sdf.format(now);
			
			//가입버튼을 눌렀을때
			if(btn.getText().equals("가입")) {									
				if(right_jtf[0].getText().equals("") || right_jtf[1].getText().equals("")) {
					JOptionPane.showMessageDialog(null,"빈칸이 존재합니다!!","Error",JOptionPane.ERROR_MESSAGE);
				}else {
					Connection conn = null;
					PreparedStatement psmt = null;
					//현재 날짜 가져오기
			
					String product = (String) combo2.getSelectedItem(); 					//보험상품 가져오기
					String admin = (String) combo.getSelectedItem(); 					//담당자명 가져오기					
					Calendar cal = Calendar.getInstance();//날짜
					String date = Integer.toString(cal.get(cal.YEAR))+"-"+Integer.toString(cal.get(cal.MONTH)+1)+"-"+Integer.toString(cal.get(cal.DATE));
					String sql = "insert into contract values(?,?,?,?,?,?)";
					try {
						conn = db.connect.makeConnection("customer");
						psmt = conn.prepareStatement(sql);
						psmt.setString(1, left_jtf[0].getText());
						psmt.setString(2, product);
						psmt.setInt(3, Integer.valueOf(right_jtf[0].getText()));
						psmt.setString(4, date);
						psmt.setInt(5, Integer.valueOf(right_jtf[1].getText()));
						psmt.setString(6, admin);
						int re = psmt.executeUpdate(); 
					} catch (Exception e1) {
						System.out.println("error");
					}
					
					Select_all();
				}
			}
			
			//삭제버튼을 눌렀을때
			else if(btn.getText().equals("삭제")) {			
				int selectrow = table.getSelectedRow();		//선택한것 가져오기
				Vector<String> v = rowData.get(selectrow);	//선택한것을 벡터에 넣어줌
				String code = v.get(0);						//선택한것의 고객코드를 가져옴
				String product = v.get(1);					//선택한것의 보험상품명을 가져옴
				int var = JOptionPane.showConfirmDialog(null, code + "(" + product + ")을 삭제하시겠습니까?","계약정보 삭제",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);	//삭제할지 안할지물어보는 대화상자
				if(var == 0) {													//예 버튼을 눌렀을 때 삭제 실행 
					if(selectrow == -1) return;										//선택한것이 없을 경우 리턴
					DefaultTableModel m = (DefaultTableModel)table.getModel();	
					m.removeRow(selectrow);											//선택한 것을 삭제함
					
					Connection conn = null;
					PreparedStatement psmt = null;
					String sql = "delete from contract where customerCode=?";	//선택한 것을 삭제하는 sql문
					try {
						conn = db.connect.makeConnection("customer");
						psmt = conn.prepareStatement(sql);
						psmt.setString(1,code);
						int re = psmt.executeUpdate();
					} catch (Exception e2) {
						
					}
				}
			}
			
			//파일로저장 버튼을 눌렀을때
			else if(btn.getText().equals("파일로저장")) {
				JFrame j = new JFrame();
				FileDialog fdSave = new FileDialog(j, "파일저장", FileDialog.SAVE);	//파일을 저장하기위한 dialog객체 생성
				fdSave.setVisible(true);				//가시화		
				String path = fdSave.getDirectory();	//파일경로 얻기 *취소버튼을 누르면 null반환
				String name = fdSave.getFile();
				
				if(path == null) {	//취소버튼을 눌렀을때 
					return;
				}
				File file = new File(path);
				
				BufferedWriter br = null;
				String customer_name = (String) combo1.getSelectedItem();	//콤보박스에 선택된 고객명 가져오기
				String code = left_jtf[0].getText();
				try {
					/*------------------------저장할 파일에 입력*--------------------------*/
					br = new BufferedWriter(new FileWriter(file + "/" + name));
					br.write("고객명 : " + customer_name +"(" + code + ")" +  "\r\n\n");
					br.flush();
					br.write("보험상품\t\t가입금액\t\t가입일\t\t월보험료\t담당자명\r\n");
					br.flush();
					for(int i = 0 ; i < table.getRowCount() ; i++) {
						for(int k = 1 ; k < table.getColumnCount() ; k++) {
							br.write(table.getValueAt(i, k) + "\t");
							br.flush();
						}
						br.write("\r\n");
						br.flush();
					}
					/*--------------------------------------------------------------*/
					
					
				} catch (Exception e2) {
					
				}
			}
			
			//닫기버튼을 눌렀을때
			else if(btn.getText().equals("닫기")) {
				dispose();
			}
		}
	}
	
	class Center extends JPanel{
		public Center() {
			setLayout(new BorderLayout());
			add(new Center_North(), BorderLayout.NORTH);
			add(new Center_Center(), BorderLayout.CENTER);
		}
		class Center_North extends JPanel{
			public Center_North() {
				JLabel title = new JLabel("< 고객 보험 계약 현황 >");		//타이틀 라벨 생성
				add(title);						
			}
		}
		//고객 보험 계약 현황을 나타내는 JTABLE
		class Center_Center extends JPanel{
			public Center_Center() {
				rowData = new Vector<Vector<String>>();
				Vector<String> colData = new Vector<String>();
				String title[] = {"customerCode","contractName","regPrice","regDate","monthPrice","adminName"};
				for(int i = 0 ; i < title.length ; i++) {
					colData.add(title[i]);
				}
				table = new JTable(rowData,colData);				//고객보험 계약현황을 나타내줄 JTable 
				JScrollPane jsp = new JScrollPane(table);			
				add(jsp);
				Select_all();
			}
		}
		//고객 보험 계약 현황을 출력해주는 SQL문
		
	}
	
	//선택한 고객명에 따른 고객보험 계약현황을 나타내주는 메소드
	void Select_all() {
		String code = left_jtf[0].getText(); //고객 코드를 가져옴
		String sql = "select * from contract where customerCode = ?  order by regdate asc"; 	//고객 코드에 따른 계약현황을 출력해주는 sql문
		Connection conn = null;
		PreparedStatement psmt = null;
		try {
			rowData.clear();		//rowData 제거								
			conn = db.connect.makeConnection("customer");
			psmt = conn.prepareStatement(sql);
			psmt.setString(1,code);
			ResultSet re = psmt.executeQuery();
			while(re.next()) {
				Vector<String> v = new Vector<String>();
				for(int i = 0 ; i < 6 ; i++) {
					v.add(re.getString(i+1));	//출력된 내용을 벡터v에 추가	
				}
				rowData.add(v);		//rowData에 벡터v 추가
			}
			table.updateUI();		//테이블을 업데이트해줌		
		} catch (Exception e) {
			
		}
	}
}
