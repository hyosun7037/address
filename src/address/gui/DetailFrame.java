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

	private DetailFrame detailFrame = this; // 전역으로 빼놓으면 사용가능
	private int memberId; // mainFrame에서 넘어온 member의 id값
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
		// DetailFrame -> MemberService -> MemberDao의 상세보기() -> 데이터베이스에 요청
		Member member = memberService.상세보기(memberId);
		tfName.setText(member.getName());
		tfPhone.setText(member.getPhone());
		tfAddress.setText(member.getAddress());
		cbGroup.setSelectedItem(member.getGroupType());
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
				// 서비스 연결 - 수정
				Member member = Member.builder()
						.id(memberId)
						.name(tfName.getText())
						.phone(tfPhone.getText())
						.address(tfAddress.getText())
						.groupType(GroupType.valueOf(cbGroup.getSelectedItem().toString()))
						.build();
				
				int result = memberService.주소록수정(member);
				if (result == 1) {
					mainFrame.notifyUserList();
					detailFrame.dispose();
					mainFrame.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(null, "수정이 취소되었습니다.");
				}
			}
		});

		deleteButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// 서비스 연결 - 삭제
				// result == 1이면 아래 로직 실행, 1이 아니면 다이어로그 박스 (삭제 실패)
				int result = memberService.주소록삭제(memberId);
				if (result == 1) {
					mainFrame.notifyUserList();
					detailFrame.dispose();
					mainFrame.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(null, "삭제가 취소되었습니다.");
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
