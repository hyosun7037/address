package address.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import address.model.GroupType;
import address.model.Member;
import address.service.MemberService;

public class AddFrame extends JFrame {
	
	private final static String TAG = "AddFrame: "; // 로그 찍기

	private AddFrame addFrame; // 전역으로 빼놓으면 사용가능
	private MainFrame mainFrame;
	private Container backgoundPanel; // borderLayout으로 만들기
	private JPanel addPanel; // 추가 패널
	private JLabel laName, laPhone, laAddress, laGroup;
	private JTextField tfName, tfPhone, tfAddress;
	private JComboBox<GroupType> cbGroup;
	private JButton addButton;
	private MemberService memberService = MemberService.getInstance(); // 어디서든 쓸 수 있게 전역으로 넣어줌

	public AddFrame(MainFrame mainFrame) {
		addFrame = this;
		this.mainFrame = mainFrame;
		initObject();
		initDesign();
		initListener();
		setVisible(true);
	}

	private void initObject() {
		backgoundPanel = getContentPane();
		laName = new JLabel("이름");
		laPhone = new JLabel("전화번호");
		laAddress = new JLabel("주소");
		laGroup = new JLabel("그룹");
		tfName = new JTextField(20);
		tfPhone = new JTextField(20);
		tfAddress = new JTextField(20);

		// 넣을 수 있는 타입 GroupType (직접 지정)
		cbGroup = new JComboBox<>(GroupType.values()); // GroupType의 배열

		addButton = new JButton("추가하기");
	}

	private void initDesign() {
		setTitle("주소록 추가하기");
		setSize(300, 300);
		setLocationRelativeTo(null);

		// EXIT로 설정하면 메인까지 꺼짐, 해당프레임만 꺼지도록 DISPOSE 설정
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		backgoundPanel.setLayout(new BorderLayout());
		addPanel = new JPanel();
		addPanel.setLayout(new GridLayout(4, 2));

		addPanel.add(laName);
		addPanel.add(tfName);
		addPanel.add(laPhone);
		addPanel.add(tfPhone);
		addPanel.add(laAddress);
		addPanel.add(tfAddress);
		addPanel.add(laGroup);
		addPanel.add(cbGroup);
		addPanel.add(addButton);

		backgoundPanel.add(addPanel, BorderLayout.CENTER);
		backgoundPanel.add(addButton, BorderLayout.SOUTH);
	}

	private void initListener() {
		addButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				// 1. TextField에 있는 값을 가져옴 / 2. 값을 Member에 담음
				System.out.println(TAG + "addButton리스너 : " + tfName.getText()); // 로그 찍기
				Member member = Member.builder()
						.name(tfName.getText())
						.phone(tfPhone.getText())
						.address(tfAddress.getText())
						.groupType(GroupType.valueOf(cbGroup.getSelectedItem().toString()))
						.build();

				int result = memberService.주소록추가(member);
				// 4. return값을 보고 로직을 직접 짜야함 (성공, 실패)
				if (result == 1) {
					// 5. 성공 = mainFrame에 값을 변경(동기화)
					mainFrame.notifyUserList(); // 동기화 해주는 메서드
					addFrame.dispose();
					mainFrame.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(null, "주소록 추가에 실패하였습니다.");
				}

			}
		});

		// 추가 (창을 끄면 메인 프레임이 다시 켜짐)
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				mainFrame.setVisible(true);
			}
		});
	}
}
