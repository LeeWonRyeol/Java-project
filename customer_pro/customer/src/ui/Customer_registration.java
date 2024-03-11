package ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

//고객등록 창
public class Customer_registration extends JFrame{
 
	public Customer_registration() {
		setTitle("고객 등록");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		add(new Center() , BorderLayout.CENTER);
		add(new South(), BorderLayout.SOUTH);
		
		setVisible(true);
		setSize(500,300);
	}
	JTextField jtf[];
	public class Center extends JPanel{
		public Center() {
			setLayout(new GridLayout(6,2)); 
			JLabel label[] = new JLabel[6];
			String label_text[] = {"고객 코드:","*고 객 명:","생년월일(YYYY-MM-DD):","*연 락 처","주   소:","회   사:"};
			jtf = new JTextField[6];
			for(int i = 0 ; i < label.length ; i++) {
				label[i] = new JLabel(label_text[i]);
				add(label[i]);
				jtf[i] = new JTextField(15);
				add(jtf[i]);
			}
			jtf[0].setEnabled(false); //입력창 비활성화
			jtf[2].addActionListener(new ActionListener() { //생년월일 입력란에 actionListener 추가
				@Override
				public void actionPerformed(ActionEvent e) {
					Calendar cal = Calendar.getInstance();
					//고객코드 생성
					int year = cal.get(Calendar.YEAR) - 2000;
					String str[] = jtf[2].getText().split("-");
					int hap = Integer.valueOf(str[0]) + Integer.valueOf(str[1]) +  Integer.valueOf(str[2]);   
					jtf[0].setText("S" + year + hap);
					
				}
			}); 
		}
	}
	
	
	
	public class South extends JPanel{
		public South() {
			JButton btn[] = new JButton[2];
			String btn_text[] = {"추가","닫기"};
			for(int i = 0 ; i < btn.length ; i++) {
				btn[i] = new JButton(btn_text[i]);
				add(btn[i]);
				btn[i].addActionListener(new MyActionListener()); // actionListener 추가
			}
		}
	}
	
	//추가or닫기 버튼 actionListener
	public class MyActionListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton b = (JButton)e.getSource();	//누른 버튼의 소스를 받아온다
			if(b.getText().equals("추가")) { //추가 버튼을 눌렀을 때 
				if(jtf[1].getText() == "" || jtf[2].getText() == "" || jtf[3].getText() =="") {
					JOptionPane.showMessageDialog(null,"필수항목(*)을 모두 입력하세요","고객등록 에러",JOptionPane.ERROR_MESSAGE); //고객명 , 생년월일, 연락처가 빈칸일 경우 에러메세지 출력
				}else {
					Connection conn = db.connect.makeConnection("customer");
					String sql = "insert into customer values(?,?,?,?,?,?)";
					try {	
						PreparedStatement psmt = conn.prepareStatement(sql);
						
						for(int i = 0 ; i < jtf.length ; i++) {
							psmt.setString(i+1, jtf[i].getText()); //텍스트 필드에 있는 내용을 db에 삽입
						}
						int re = psmt.executeUpdate(); //sql문 실행
						JOptionPane.showMessageDialog(null,"고객 추가가 완료되었습니다.","메시지",JOptionPane.PLAIN_MESSAGE); //고객 추가 완료 메시지 출력 
					} catch (Exception e2) {

					}
				}
			}else if(b.getText().equals("닫기")) { //닫기버튼을 눌렀을때
				dispose();
			}
		}
	}
}
