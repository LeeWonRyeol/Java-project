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

//����� â
public class Customer_registration extends JFrame{
 
	public Customer_registration() {
		setTitle("�� ���");
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
			String label_text[] = {"�� �ڵ�:","*�� �� ��:","�������(YYYY-MM-DD):","*�� �� ó","��   ��:","ȸ   ��:"};
			jtf = new JTextField[6];
			for(int i = 0 ; i < label.length ; i++) {
				label[i] = new JLabel(label_text[i]);
				add(label[i]);
				jtf[i] = new JTextField(15);
				add(jtf[i]);
			}
			jtf[0].setEnabled(false); //�Է�â ��Ȱ��ȭ
			jtf[2].addActionListener(new ActionListener() { //������� �Է¶��� actionListener �߰�
				@Override
				public void actionPerformed(ActionEvent e) {
					Calendar cal = Calendar.getInstance();
					//���ڵ� ����
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
			String btn_text[] = {"�߰�","�ݱ�"};
			for(int i = 0 ; i < btn.length ; i++) {
				btn[i] = new JButton(btn_text[i]);
				add(btn[i]);
				btn[i].addActionListener(new MyActionListener()); // actionListener �߰�
			}
		}
	}
	
	//�߰�or�ݱ� ��ư actionListener
	public class MyActionListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton b = (JButton)e.getSource();	//���� ��ư�� �ҽ��� �޾ƿ´�
			if(b.getText().equals("�߰�")) { //�߰� ��ư�� ������ �� 
				if(jtf[1].getText() == "" || jtf[2].getText() == "" || jtf[3].getText() =="") {
					JOptionPane.showMessageDialog(null,"�ʼ��׸�(*)�� ��� �Է��ϼ���","����� ����",JOptionPane.ERROR_MESSAGE); //���� , �������, ����ó�� ��ĭ�� ��� �����޼��� ���
				}else {
					Connection conn = db.connect.makeConnection("customer");
					String sql = "insert into customer values(?,?,?,?,?,?)";
					try {	
						PreparedStatement psmt = conn.prepareStatement(sql);
						
						for(int i = 0 ; i < jtf.length ; i++) {
							psmt.setString(i+1, jtf[i].getText()); //�ؽ�Ʈ �ʵ忡 �ִ� ������ db�� ����
						}
						int re = psmt.executeUpdate(); //sql�� ����
						JOptionPane.showMessageDialog(null,"�� �߰��� �Ϸ�Ǿ����ϴ�.","�޽���",JOptionPane.PLAIN_MESSAGE); //�� �߰� �Ϸ� �޽��� ��� 
					} catch (Exception e2) {

					}
				}
			}else if(b.getText().equals("�ݱ�")) { //�ݱ��ư�� ��������
				dispose();
			}
		}
	}
}
