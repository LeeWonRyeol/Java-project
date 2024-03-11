package ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

//��������� ȭ��
public class Insurance_contract_management extends JFrame{
	public Insurance_contract_management() {
		setTitle("������ ����ȭ��");
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
			String btn_text[] = {"�� ���","�� ��ȸ","��� ����","��     ��"};
			for(int i = 0 ; i < btn.length ; i++) {
				btn[i] = new JButton(btn_text[i]);
				add(btn[i]);
				btn[i].addActionListener(new MyActionListener()); //actionListener �߰� 
			}
		}
	}
	
	public class MyActionListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton b = (JButton)e.getSource(); 		//���� ��ư�� �ҽ��� �����´�
			
			if(b.getText().equals("�� ���")) {			//���� ��ư�� �� ����� ���
				new Customer_registration(); 			//�����â ����
			}else if(b.getText().equals("�� ��ȸ")) {	//���� ��ư�� �� ��ȸ�� ���
				new Customer_inquiry(); 				//����ȸâ ����
			}else if(b.getText().equals("��� ����")) {	//���� ��ư�� �������� ���
				new Contract_management();				//������â ����
			}else if(b.getText().equals("��     ��")) {	//���� ��ư�� ������ ���
				dispose();								//â�ݱ�
			}
		}
	}
	
	public class Center extends JPanel{
		//���� ȭ�� �̹���
		ImageIcon icon = new ImageIcon("C:\\Users\\sj_home\\Desktop\\customer_pro\\customer\\Datafiles\\img.jpg");
		JLabel img = new JLabel(icon);
		public Center() {
			add(img);
		}
	}
}
