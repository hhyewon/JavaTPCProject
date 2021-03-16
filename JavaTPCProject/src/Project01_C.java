import java.io.InputStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Project01_C {
	public static void main(String[] args) {
		//JSON ���� ó���ϱ�
		
		String src = "info.json"; 
		// IO -> Stream (�����͸� �а� �������� ��Ʈ���� �ʿ��ϴ�.)
		InputStream is = Project01_C.class.getResourceAsStream(src); //���� Project01_C�� ������� ������ ���ҽ��� �����ؼ� src�� �����ؼ� ���Ͷ� (���� ��ξȿ� �ִٸ� �������� �ҷ��� �� �ֵ���)
		
		if(is == null) {
			throw new NullPointerException("Cannot find resource file");
		}
		//���ڿ��� JSON ���·� �޾ƿ��� ����
		JSONTokener tokener = new JSONTokener(is); //is��� ��Ʈ���� �̿��ؼ� ���ڿ� �����͸� �޸𸮷� �÷����� �۾��� �ϴ� �Լ�(JSON ������ ��ūȭ) //���ڿ��� JSON��ü��
		JSONObject object = new JSONObject(tokener); //���ڿ� ��ü�� JSON object����
		JSONArray students = object.getJSONArray("students");
		for(int i= 0; i<students.length(); i++) {
			JSONObject student = (JSONObject)students.get(i); //JSONObject�� �޾ƾ��� //�ٿ�ĳ����
			System.out.print(student.get("name")+"\t");
			System.out.print(student.get("address")+"\t");
			System.out.println(student.get("phone"));
			//json �ȿ� �ִ� ���� ���ϴ� �λ���� ȸ�������� ���ü��ִ�.
			/* ȫ�浿	
			����	
			010-1111-1111	
			���浿	
			����	
			010-2222-2222	
			*/
		}
	}

}
