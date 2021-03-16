import org.json.JSONArray;
import org.json.JSONObject;

public class Project01_B {

	public static void main(String[] args) {
		//JSON-Java(org.json)
		
		JSONArray students = new JSONArray();
		
		JSONObject student = new JSONObject();
		//�� ����� JSON ��ü
		student.put("name", "ȫ�浿");
		student.put("phone", "010-1111-1111");
		student.put("address", "����");
		
		System.out.println(student); //{"address":"����","phone":"010-1111-1111","name":"ȫ�浿"}
		
		students.put(student); //�迭�� ��
		
		
		student= new JSONObject();
		student.put("name", "���浿");
		student.put("phone", "010-2222-2222");
		student.put("address", "����");
		
		students.put(student); //�迭�� ��������� ��ü �� ���� ����.
		
		JSONObject object = new JSONObject();
		object.put("students", students);
		
		System.out.println(object.toString(1)); //�鿩�����ؼ� ���
		/* 
		 * {"address":"����","phone":"010-1111-1111","name":"ȫ�浿"}
			{"students": [
			 {
			  "address": "����",
			  "phone": "010-1111-1111",
			  "name": "ȫ�浿"
			 },
			 {
			  "address": "����",
			  "phone": "010-2222-2222",
			  "name": "���浿"
			 }
			]} */
		 
}

}
