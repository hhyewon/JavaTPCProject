import org.json.JSONArray;
import org.json.JSONObject;

public class Project01_B {

	public static void main(String[] args) {
		//JSON-Java(org.json)
		
		JSONArray students = new JSONArray();
		
		JSONObject student = new JSONObject();
		//한 사람의 JSON 객체
		student.put("name", "홍길동");
		student.put("phone", "010-1111-1111");
		student.put("address", "서울");
		
		System.out.println(student); //{"address":"서울","phone":"010-1111-1111","name":"홍길동"}
		
		students.put(student); //배열에 들어감
		
		
		student= new JSONObject();
		student.put("name", "나길동");
		student.put("phone", "010-2222-2222");
		student.put("address", "광주");
		
		students.put(student); //배열이 만들어지고 객체 두 개가 들어간다.
		
		JSONObject object = new JSONObject();
		object.put("students", students);
		
		System.out.println(object.toString(1)); //들여쓰기해서 출력
		/* 
		 * {"address":"서울","phone":"010-1111-1111","name":"홍길동"}
			{"students": [
			 {
			  "address": "서울",
			  "phone": "010-1111-1111",
			  "name": "홍길동"
			 },
			 {
			  "address": "광주",
			  "phone": "010-2222-2222",
			  "name": "나길동"
			 }
			]} */
		 
}

}
