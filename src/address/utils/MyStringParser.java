package address.utils;

public class MyStringParser {
	public static int getId(String selectedList) { //id�� �Ľ�����
		// .�� �Ľ��� �ȵ�. || Ȥ�� [] ���
		return Integer.parseInt(selectedList.split("[.]")[0]); // .�� �������� �ڸ��� ù��°�׸��� ��������
	}
}

