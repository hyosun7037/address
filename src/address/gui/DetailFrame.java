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

	private DetailFrame detailFrame = this; // 전역으로 빼놓으면 사용가능
	private int memeberId; // mainFrame에서 넘어온 member의 id값
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
		laName = new JLabel("이름");
		laPhone = new JLabel("전화번호");
		laAddress = new JLabel("주소");
		laGroup = new JLabel("그룹");
		tfName = new JTextField(20);
		tfPhone = new JTextField(20);
		tfAddress = new JTextField(20);

		// 넣을 수 있는 타입 GroupType (직접 지정)
		cbGroup = new JComboBox<>(GroupType.values()); // GroupType의 배열

		updateButton = new JButton("수정하기");
		deleteButton = new JButton("삭제하기");
	}

	private void initData() {

	}

	private void initDesign() {
		setTitle("주소록 상세보기");
		setSize(300, 300);
		setLocationRelativeTo(null);

		// EXIT로 설정하면 메인까지 꺼짐, 해당프레임만 꺼지도록 DISPOSE 설정
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
