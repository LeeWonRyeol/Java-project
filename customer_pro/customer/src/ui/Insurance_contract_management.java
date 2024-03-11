package ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

//보험계약관리 화면
public class Insurance_contract_management extends JFrame{
	public Insurance_contract_management() {
		setTitle("보험계약 관리화면");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
		add(new North(),BorderLayout.NORTH);
		add(new Center(), BorderLayout.CENTER);
		
		setVisible(true);
		setSize(600,500);
	}
	
	public class North extends JPanel{
		public North() {
			JButton btn[] = new JButton[4];
			String btn_text[] = {"고객 등록","고객 조회","계약 관리","종     료"};
			for(int i = 0 ; i < btn.length ; i++) {
				btn[i] = new JButton(btn_text[i]);
				add(btn[i]);
				btn[i].addActionListener(new MyActionListener()); //actionListener 추가 
			}
		}
	}
	
	public class MyActionListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton b = (JButton)e.getSource(); 		//누른 버튼의 소스를 가져온다
			
			if(b.getText().equals("고객 등록")) {			//누른 버튼이 고객 등록일 경우
				new Customer_registration(); 			//고객등록창 실행
			}else if(b.getText().equals("고객 조회")) {	//누른 버튼이 고객 조회일 경우
				new Customer_inquiry(); 				//고객조회창 실행
			}else if(b.getText().equals("계약 관리")) {	//누른 버튼이 계약관리일 경우
				new Contract_management();				//계약관리창 실행
			}else if(b.getText().equals("종     료")) {	//누른 버튼이 종료일 경우
				dispose();								//창닫기
			}
		}
	}
	
	public class Center extends JPanel{
		//메인 화면 이미지
		ImageIcon icon = new ImageIcon("C:\\Users\\sj_home\\Desktop\\customer_pro\\customer\\Datafiles\\img.jpg");
		JLabel img = new JLabel(icon);
		public Center() {
			add(img);
		}
	}
}
