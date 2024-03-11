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


//�������� ������ ���� Ŭ����
public class update_customer extends JFrame{
	Vector<String> vt;
	public update_customer(Vector<String> v) {		
		setTitle("�� ����");
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
			String label_text[] = {"���ڵ�:","�� �� ��:","�������:","����ó:","��    ��:","ȸ �� ��:"};
			
			
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
			String btn_text[] = {"����","���"};
			for(int i = 0 ; i < btn.length ; i++) {
				btn[i] = new JButton(btn_text[i]);
				add(btn[i]);
				btn[i].addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						JButton b = (JButton)e.getSource();
						if(b.getText().equals("����")) {		//������ư�� �������� sql()�޼ҵ� ����
							sql();
						}else if(b.getText().equals("���")) {	//��ҹ�ư�� �������� â�� ����
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
		String sql = "update customer set birth=? , tel=?, address=?, companey=? where code=?";		//������ ������ �����͸� ������Ʈ �����ִ� sql��
		try {
			conn = db.connect.makeConnection("customer");
			psmt = conn.prepareStatement(sql);
			for(int i = 1;i<5;i++) {
				psmt.setString(i, jtf[i+1].getText());
			}
			psmt.setString(5, vt.get(0));
			int re = psmt.executeUpdate();
			if(re > 0) {
				JOptionPane.showMessageDialog(null,"�� ������ �Ϸ�Ǿ����ϴ�.","�޽���",JOptionPane.PLAIN_MESSAGE); //�� ���� �Ϸ� �޽��� ���
				dispose();
			}else {
				JOptionPane.showMessageDialog(null,"�Է��� Ȯ�����ּ���","������ ����",JOptionPane.ERROR_MESSAGE); //�� ���� ���� �޽��� ���
			}
		} catch (Exception e2) {

		}
	}
}
 