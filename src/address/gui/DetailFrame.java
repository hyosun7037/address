package address.gui;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import address.model.GroupType;

public class DetailFrame extends JFrame {

	private DetailFrame detailFrame = this; // �������� �������� ��밡��
	private int memeberId; // mainFrame���� �Ѿ�� member�� id��
	private MainFrame mainFrame;
	private Container backgounrPanel;
	private JLabel laName, laPhone, laAddress, laGroup;
	private JTextField tfName, tfPhone, tfAddress;
	private JComboBox<GroupType> cbGroup;
	private JButton updateButton, deleteButton;

	public DetailFrame(MainFrame mainFrame, int memberId) {
		this.mainFrame = mainFrame;
		this.memeberId = memberId;
		initObject();
		initData();
		initDesign();
		initListener();
		setVisible(true);
	}

	private void initObject() {
		backgounrPanel = getContentPane();
		laName = new JLabel("�̸�");
		laPhone = new JLabel("��ȭ��ȣ");
		laAddress = new JLabel("�ּ�");
		laGroup = new JLabel("�׷�");
		tfName = new JTextField(20);
		tfPhone = new JTextField(20);
		tfAddress = new JTextField(20);

		// ���� �� �ִ� Ÿ�� GroupType (���� ����)
		cbGroup = new JComboBox<>(GroupType.values()); // GroupType�� �迭

		updateButton = new JButton("�����ϱ�");
		deleteButton = new JButton("�����ϱ�");
	}

	private void initData() {

	}

	private void initDesign() {
		setTitle("�ּҷ� �󼼺���");
		setSize(300, 300);
		setLocationRelativeTo(null);

		// EXIT�� �����ϸ� ���α��� ����, �ش������Ӹ� �������� DISPOSE ����
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		backgounrPanel.setLayout(new GridLayout(5, 2));

		backgounrPanel.add(laName);
		backgounrPanel.add(tfName);
		backgounrPanel.add(laPhone);
		backgounrPanel.add(tfPhone);
		backgounrPanel.add(laAddress);
		backgounrPanel.add(tfAddress);
		backgounrPanel.add(laGroup);
		backgounrPanel.add(cbGroup);
		backgounrPanel.add(updateButton);
		backgounrPanel.add(deleteButton);
	}

	private void initListener() {
		updateButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				detailFrame.dispose();
			}
		});

		
		deleteButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				detailFrame.dispose();
			}
		});
	}
}
