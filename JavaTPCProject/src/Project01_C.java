import java.io.InputStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Project01_C {
	public static void main(String[] args) {
		//JSON 파일 처리하기
		
		String src = "info.json"; 
		// IO -> Stream (데이터를 읽고 쓰기위한 스트림이 필요하다.)
		InputStream is = Project01_C.class.getResourceAsStream(src); //현재 Project01_C가 만들어진 곳에서 리소스와 연결해서 src과 연결해서 얻어와라 (같은 경로안에 있다면 언제든지 불러올 수 있도록)
		
		if(is == null) {
			throw new NullPointerException("Cannot find resource file");
		}
		//문자열을 JSON 형태로 받아오기 위해
		JSONTokener tokener = new JSONTokener(is); //is라는 스트림을 이용해서 문자열 데이터를 메모리로 올려놓는 작업을 하는 함수(JSON 구조로 토큰화) //문자열을 JSON객체로
		JSONObject object = new JSONObject(tokener); //문자열 전체가 JSON object가됨
		JSONArray students = object.getJSONArray("students");
		for(int i= 0; i<students.length(); i++) {
			JSONObject student = (JSONObject)students.get(i); //JSONObject로 받아야함 //다운캐스팅
			System.out.print(student.get("name")+"\t");
			System.out.print(student.get("address")+"\t");
			System.out.println(student.get("phone"));
			//json 안에 있는 내가 원하는 두사람의 회원정보를 빼올수있다.
			/* 홍길동	
			서울	
			010-1111-1111	
			나길동	
			광주	
			010-2222-2222	
			*/
		}
	}

}
