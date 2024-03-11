package ui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

//로그인
public class login extends JFrame{
	
	public login() {
		setTitle("로그인");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout(0,10));
		
		add(new North(), BorderLayout.NORTH);
		add(new Center(), BorderLayout.CENTER);
		add(new South(), BorderLayout.SOUTH);
		
		setVisible(true);
		setSize(300,200);
	}
	
	//north 패널
	public class North extends JPanel{
		public North() {
			JLabel title = new JLabel("관리자 로그인");
			add(title);
			title.setOpaque(true);
			title.setFont(new Font("맑은고딕",Font.PLAIN,20));
		}
		
	}
	
	//center 패널
	JTextField jtf; // 아이디 필드
	JPasswordField jpf; //패스워드 필드
	public class Center extends JPanel{
		public Center() {
			setLayout(new GridLayout(2,1));
			JPanel jp[] = new JPanel[2];
			
			String label_text[] = {"이름        ","비밀번호"};
			JLabel label[] = new JLabel[2];
			jtf = new JTextField(15);
			jpf = new JPasswordField(15);
			for(int i = 0 ; i < label.length ; i++) {
				label[i] = new JLabel(label_text[i]);
			}
			for(int i = 0 ; i < jp.length ; i++) {
				jp[i] = new JPanel();	
				jp[i].add(label[i]);
				if(i==0) {
					jp[i].add(jtf);				
				}else {
					jp[i].add(jpf);
				}
				add(jp[i]);
			}
			 
		}
	}
	
	//south 패널
	public class South extends JPanel{
		public South() {
			String btn_text[] = {"확인","종료"};
			JButton btn[] = new JButton[2];
			for(int i = 0 ; i < btn.length ; i++) {
				btn[i] = new JButton(btn_text[i]);
				add(btn[i]);
				//actionListener 추가
				btn[i].addActionListener(new MyActionListener());
			}
			
		}
	}
	
	//확인 or 종료버튼 눌렀을때 실행되는 actionListener
	public class MyActionListener implements ActionListener{	
			@Override
			public void actionPerformed(ActionEvent e) {
				JButton b = (JButton)e.getSource();
				String pass =new String( jpf.getPassword() ); //패스워드를 문자열화
				
				if(b.getText().equals("확인")) { //버튼이 확인일 경우
					Connection conn = db.connect.makeConnection("customer"); //customer db접속
					try {
						PreparedStatement psmt = conn.prepareStatement("select name from admin where name = ? and passwd = ?");
						
						psmt.setString(1, jtf.getText()); 
						psmt.setString(2, pass);
						ResultSet rs = psmt.executeQuery();
						if(rs.next()) { //결과값이 있을 경우
							dispose(); //창닫기
							new Insurance_contract_management();
						}else { 		//결과값이 없을 경우
							JOptionPane.showMessageDialog(null,"틀린정보입니다","메시지",JOptionPane.ERROR_MESSAGE);
							jtf.setText("");
							jpf.setText("");
						}
					} catch (Exception e2) {
						System.out.println("ERROR");
					}
				}else if(b.getText().equals("종료")) { //버튼이 종료일 경우
					dispose(); //창닫기
				}
			}
	}
	
	public static void main(String[] args) {
		new login();
	}

}
