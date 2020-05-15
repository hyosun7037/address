package address.utils;

public class MyStringParser {
	public static int getId(String selectedList) { //id를 파싱해줌
		// .은 파싱이 안됨. || 혹은 [] 사용
		return Integer.parseInt(selectedList.split("[.]")[0]); // .을 기준으로 자르고 첫번째항목을 리턴해줌
	}
}

