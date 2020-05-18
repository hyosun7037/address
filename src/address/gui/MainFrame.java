package address.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.w3c.dom.UserDataHandler;

import address.model.GroupType;
import address.model.Member;
import address.service.MemberService;
import address.utils.MyStringParser;

public class MainFrame extends JFrame {

	private MemberService memberService = MemberService.getInstance();

	private MainFrame mainFrame = this; // this가 mainFrame
	private Container backGroundPanel; // new 해줄 필요 X
	private JPanel topPanel, menuPanel, listPanel;
	private JButton homeButton, frButton, coButton, scButton, faButton, addButton;
	private JList<Member> userList;
	private DefaultListModel<Member> listModel;
	private JScrollPane jspane;

	public MainFrame() {

		// 최초에 띄울 것들 생성자로 불러오기
		initObject();
		initData();
		initDesign();
		initListener();
		setVisible(true);
	}

	// 객체생성
	private void initObject() {

		backGroundPanel = getContentPane();

		// new 패널
		topPanel = new JPanel();
		menuPanel = new JPanel();
		listPanel = new JPanel();

		// new 버튼
		homeButton = new JButton("주소록 전체");
		frButton = new JButton("친구");
		coButton = new JButton("회사");
		scButton = new JButton("학교");
		faButton = new JButton("가족");
		addButton = new JButton("추가");

		listModel = new DefaultListModel<>(); // 초기화 시켜서 DB에서 다시 값을 불러와서 넣어줘야한다.

		userList = new JList<>(listModel); // listModel에 데이터 집어넣어주면 된다.

		jspane = new JScrollPane(userList); // 뒤에 있는 패널의 색을 먹어버림
	}

	// 데이터 초기화
	private void initData() {
		List<Member> members = memberService.전체목록();
		for (Member member : members) {
			listModel.addElement(member);
		}
	}

	// 디자인
	private void initDesign() {

		// 1. 기본세팅
		setTitle("주소록 메인");
		setSize(400, 500);
		setLocationRelativeTo(null); // 가운데 정렬, 모니터 중앙에 배치
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 종료

		// 2. 패널세팅
		backGroundPanel.setLayout(new BorderLayout());
		topPanel.setLayout(new GridLayout(2, 1)); // 2행
		menuPanel.setLayout(new GridLayout(1, 4)); // 4열
		listPanel.setLayout(new BorderLayout());

		// 3. 디자인
		userList.setFixedCellHeight(50); // 리스트 각각의 높이
		topPanel.setPreferredSize(new Dimension(0, 100)); // 그리드레이아웃이기 때문에 높이만 적용됨

		// 4. 패널(컨테이너)에 컴포넌트 추가

		menuPanel.add(frButton);
		menuPanel.add(coButton);
		menuPanel.add(scButton);
		menuPanel.add(faButton);

		topPanel.add(homeButton);
		topPanel.add(menuPanel);
		listPanel.add(jspane);

		backGroundPanel.add(topPanel, BorderLayout.NORTH);
		backGroundPanel.add(listPanel, BorderLayout.CENTER);
		backGroundPanel.add(addButton, BorderLayout.SOUTH);
	}

	// 리스너 등록
	private void initListener() { // 리스트의 항목 클릭하면 창이 뜨도록 설계

		frButton.addActionListener(new ActionListener() { // 타겟

			@Override
			public void actionPerformed(ActionEvent e) { // 이 부분으로 콜백해줌
				notifyUserList(GroupType.친구);
			}

		});

		coButton.addActionListener(new ActionListener() { // 타겟

			@Override
			public void actionPerformed(ActionEvent e) { // 이 부분으로 콜백해줌
				notifyUserList(GroupType.회사);
			}

		});

		faButton.addActionListener(new ActionListener() { // 타겟

			@Override
			public void actionPerformed(ActionEvent e) { // 이 부분으로 콜백해줌
				notifyUserList(GroupType.가족);
			}

		});

		scButton.addActionListener(new ActionListener() { // 타겟

			@Override
			public void actionPerformed(ActionEvent e) { // 이 부분으로 콜백해줌
				notifyUserList(GroupType.학교);
			}

		});

		homeButton.addActionListener(new ActionListener() { // 타겟

			@Override
			public void actionPerformed(ActionEvent e) { // 이 부분으로 콜백해줌
				notifyUserList();
			}

		});

		userList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// System.out.println(userList.getSelectedValue()); // 현재 선택된 항목이 어떤것인지 알려줌
				// System.out.println(userList.getSelectedIndex()); // 현재 선택된 항목의 번호를 알려줌
				int memberId = MyStringParser.getId(userList.getSelectedValue().toString());
				new DetailFrame(mainFrame, memberId);
				mainFrame.setVisible(false); // 안보이게 설정
			}
		});

		addButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new AddFrame(mainFrame);
				mainFrame.setVisible(false);
			}
		});
	}

	// 전체 데이터 갱신
	public void notifyUserList() {
		// 1. listModel 비우기
		listModel.clear(); // repaint까지 다 내장되어있음

		// 2. select해서 전체목록 가져와서 List<Member>에 담기
		// 3. listModel 채워주기 (userList 자동갱신)
		initData();
	}

	// 그룹 데이터 갱신 (오버로딩)
	public void notifyUserList(GroupType groupType) {
		// 1. listModel 비우기
		listModel.clear(); // repaint까지 다 내장되어있음
		List<Member> members = memberService.그룹목록(groupType);
		for (Member member : members) {
			listModel.addElement(member);
		}
		// 2. select해서 전체목록 가져와서 List<Member>에 담기
		// 3. listModel 채워주기 (userList 자동갱신)
	}
}
