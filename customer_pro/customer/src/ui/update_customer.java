package ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


//고객데이터 수정을 위한 클래스
public class update_customer extends JFrame{
	Vector<String> vt;
	public update_customer(Vector<String> v) {		
		setTitle("고객 수정");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		this.vt = v;
		add(new Center(), BorderLayout.CENTER);
		add(new South(), BorderLayout.SOUTH);
		setVisible(true);
		setSize(600,400);
	}
	
	public update_customer() {
	
	}
	JTextField jtf[]= new JTextField[6];
	class Center extends JPanel{
		public Center() {
			JLabel la[] = new JLabel[6];
			String label_text[] = {"고객코드:","고 객 명:","생년월일:","연락처:","주    소:","회 사 명:"};
			
			
			setLayout(new GridLayout(6,2));
			
			for(int i = 0; i < la.length ; i++) {
				la[i] = new JLabel(label_text[i]);
				jtf[i] = new JTextField();
				jtf[i].setText(vt.get(i));
				add(la[i]);
				add(jtf[i]);
			}
			jtf[0].setEnabled(false);
			jtf[1].setEnabled(false);
			
		}
	}
	
	class South extends JPanel{
		public South() {
			JButton btn[] = new JButton[2];
			String btn_text[] = {"수정","취소"};
			for(int i = 0 ; i < btn.length ; i++) {
				btn[i] = new JButton(btn_text[i]);
				add(btn[i]);
				btn[i].addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						JButton b = (JButton)e.getSource();
						if(b.getText().equals("수정")) {		//수정버튼을 눌렀을때 sql()메소드 실행
							sql();
						}else if(b.getText().equals("취소")) {	//취소버튼을 눌렀을때 창을 닫음
							dispose();
						}
						
					}
				});
			}
		}
	}
	
	void sql() {
		String code = vt.get(0);
		Connection conn = null;
		PreparedStatement psmt = null;
		String sql = "update customer set birth=? , tel=?, address=?, companey=? where code=?";		//수정한 내용들로 데이터를 업데이트 시켜주는 sql문
		try {
			conn = db.connect.makeConnection("customer");
			psmt = conn.prepareStatement(sql);
			for(int i = 1;i<5;i++) {
				psmt.setString(i, jtf[i+1].getText());
			}
			psmt.setString(5, vt.get(0));
			int re = psmt.executeUpdate();
			if(re > 0) {
				JOptionPane.showMessageDialog(null,"고객 수정이 완료되었습니다.","메시지",JOptionPane.PLAIN_MESSAGE); //고객 수정 완료 메시지 출력
				dispose();
			}else {
				JOptionPane.showMessageDialog(null,"입력을 확인해주세요","고객수정 에러",JOptionPane.ERROR_MESSAGE); //고객 수정 에러 메시지 출력
			}
		} catch (Exception e2) {

		}
	}
}
 