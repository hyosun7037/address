package address.gui;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.naming.spi.DirStateFactory.Result;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import address.dao.MemberDao;
import address.model.GroupType;
import address.model.Member;
import address.service.MemberService;

public class DetailFrame extends JFrame {

	private DetailFrame detailFrame = this; // �������� �������� ��밡��
	private int memberId; // mainFrame���� �Ѿ�� member�� id��
	private MainFrame mainFrame;
	private Container backgounrPanel;
	private JLabel laName, laPhone, laAddress, laGroup;
	private JTextField tfName, tfPhone, tfAddress;
	private JComboBox<GroupType> cbGroup;
	private JButton updateButton, deleteButton;
	private MemberService memberService = MemberService.getInstance();

	public DetailFrame(MainFrame mainFrame, int memberId) {
		this.mainFrame = mainFrame;
		this.memberId = memberId;
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
		// DetailFrame -> MemberService -> MemberDao�� �󼼺���() -> �����ͺ��̽��� ��û
		Member member = memberService.�󼼺���(memberId);
		tfName.setText(member.getName());
		tfPhone.setText(member.getPhone());
		tfAddress.setText(member.getAddress());
		cbGroup.setSelectedItem(member.getGroupType());
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
				// ���� ���� - ����
				Member member = Member.builder()
						.id(memberId)
						.name(tfName.getText())
						.phone(tfPhone.getText())
						.address(tfAddress.getText())
						.groupType(GroupType.valueOf(cbGroup.getSelectedItem().toString()))
						.build();
				
				int result = memberService.�ּҷϼ���(member);
				if (result == 1) {
					mainFrame.notifyUserList();
					detailFrame.dispose();
					mainFrame.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(null, "������ ��ҵǾ����ϴ�.");
				}
			}
		});

		deleteButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// ���� ���� - ����
				// result == 1�̸� �Ʒ� ���� ����, 1�� �ƴϸ� ���̾�α� �ڽ� (���� ����)
				int result = memberService.�ּҷϻ���(memberId);
				if (result == 1) {
					mainFrame.notifyUserList();
					detailFrame.dispose();
					mainFrame.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(null, "������ ��ҵǾ����ϴ�.");
				}

			}
		});
		
		
		// �߰� (â�� ���� ���� �������� �ٽ� ����)
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				mainFrame.setVisible(true);
			}
		});
		
	}
}
