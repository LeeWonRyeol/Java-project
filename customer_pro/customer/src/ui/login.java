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

//�α���
public class login extends JFrame{
	
	public login() {
		setTitle("�α���");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout(0,10));
		
		add(new North(), BorderLayout.NORTH);
		add(new Center(), BorderLayout.CENTER);
		add(new South(), BorderLayout.SOUTH);
		
		setVisible(true);
		setSize(300,200);
	}
	
	//north �г�
	public class North extends JPanel{
		public North() {
			JLabel title = new JLabel("������ �α���");
			add(title);
			title.setOpaque(true);
			title.setFont(new Font("�������",Font.PLAIN,20));
		}
		
	}
	
	//center �г�
	JTextField jtf; // ���̵� �ʵ�
	JPasswordField jpf; //�н����� �ʵ�
	public class Center extends JPanel{
		public Center() {
			setLayout(new GridLayout(2,1));
			JPanel jp[] = new JPanel[2];
			
			String label_text[] = {"�̸�        ","��й�ȣ"};
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
	
	//south �г�
	public class South extends JPanel{
		public South() {
			String btn_text[] = {"Ȯ��","����"};
			JButton btn[] = new JButton[2];
			for(int i = 0 ; i < btn.length ; i++) {
				btn[i] = new JButton(btn_text[i]);
				add(btn[i]);
				//actionListener �߰�
				btn[i].addActionListener(new MyActionListener());
			}
			
		}
	}
	
	//Ȯ�� or �����ư �������� ����Ǵ� actionListener
	public class MyActionListener implements ActionListener{	
			@Override
			public void actionPerformed(ActionEvent e) {
				JButton b = (JButton)e.getSource();
				String pass =new String( jpf.getPassword() ); //�н����带 ���ڿ�ȭ
				
				if(b.getText().equals("Ȯ��")) { //��ư�� Ȯ���� ���
					Connection conn = db.connect.makeConnection("customer"); //customer db����
					try {
						PreparedStatement psmt = conn.prepareStatement("select name from admin where name = ? and passwd = ?");
						
						psmt.setString(1, jtf.getText()); 
						psmt.setString(2, pass);
						ResultSet rs = psmt.executeQuery();
						if(rs.next()) { //������� ���� ���
							dispose(); //â�ݱ�
							new Insurance_contract_management();
						}else { 		//������� ���� ���
							JOptionPane.showMessageDialog(null,"Ʋ�������Դϴ�","�޽���",JOptionPane.ERROR_MESSAGE);
							jtf.setText("");
							jpf.setText("");
						}
					} catch (Exception e2) {
						System.out.println("ERROR");
					}
				}else if(b.getText().equals("����")) { //��ư�� ������ ���
					dispose(); //â�ݱ�
				}
			}
	}
	
	public static void main(String[] args) {
		new login();
	}

}
