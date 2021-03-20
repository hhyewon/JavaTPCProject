import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;



public class Project01_D {
	public static void main(String[] args) {
		String apiURL = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?query="; //��������: JSON //?�� get ���
		String client_id = "ecp4j8j1gd";
		String client_secret="atpwsiYOcO1YRW07gCQj4eyrDsktXq88dxo47VCx";
		BufferedReader io = new BufferedReader(new InputStreamReader(System.in)); //System.in: ����Ʈ ��Ʈ��, BufferedReader: ���� ��Ʈ��, ����Ʈ ��Ʈ���� ���� ��Ʈ���� �ٷ� ����� �� ���� ������ InputStreamReader�� ���(�긴�� ��Ʈ��)
		try {
			System.out.println("�ּҸ� �Է��ϼ���: ");
			String address = io.readLine();
			String addr = URLEncoder.encode(address, "UTF-8"); //������ ���� �ȵǱ� ������
			String reqUrl = apiURL + addr;
			
			URL url = new URL(reqUrl); // URL ��ü�� �̿��� ��Ȯ�� URL���� �˾ƺ��� (��ȿ�� �ּ�����)
			HttpURLConnection con = (HttpURLConnection)url.openConnection(); // url��ü�� �̿��� �����ϱ� 
			
			con.setRequestMethod("GET");
			con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", client_id);
			con.setRequestProperty("X-NCP-APIGW-API-KEY", client_secret);   //�� ���� ������ �Է��ؼ� Ŀ�ؼ��� ���������� �Ǹ� ���� api�� ����
			
			BufferedReader br; //JSON�� �� �پ� �޾ƿ;��ϱ� ������ 
			int responseCode = con.getResponseCode(); //�����ڵ� 200 = OK(����), 400 = INVALID_REQUEST, 500 = SYSTEM_ERROR
			if (responseCode == 200) {
				br= new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8")); // byte ��Ʈ���� reader�� ������ �ٷ� �ȵǱ� ������ new InputStream�� ���ش�
			}else {
				br= new BufferedReader(new InputStreamReader(con.getErrorStream()));
			}
			
			String line;// �� ������ �о� ������ ���� ����
			StringBuffer response = new StringBuffer();//JSON, �� �پ� �����ϱ� ����
			
			while( (line = br.readLine()) != null) { //�� �پ� �б�
				response.append(line); //response�� JSON�� ����
			}
			br.close();
			
			// JSON���� ���ϴ� ���� �̾Ƴ��� ����
			JSONTokener tokener = new JSONTokener(response.toString());
			JSONObject object = new JSONObject(tokener);
			System.out.println(object.toString(2));
			
			JSONArray arr=object.getJSONArray("addresses"); //object���� �迭 ������
			for(int i=0; i<arr.length(); i++) {
				JSONObject temp = (JSONObject)arr.get(i);
				System.out.println("address: " + temp.get("roadAddress"));
				System.out.println("jibunAddress: " + temp.get("jibunAddress"));
				System.out.println("�浵: " + temp.get("x"));
				System.out.println("����: " + temp.get("y"));
				}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
}
