import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;



public class Project01_E {
	
	public static void map_services(String point_x,String point_y,String address) {
		String url = "https://naveropenapi.apigw.ntruss.com/map-static/v2/raster?";
		try {
			//url�� ��� �Է��ؾ� �ϴ���
			String pos=URLEncoder.encode(point_x+" "+point_y, "UTF-8"); //pos:127.15(����)38.15,126.12(����)37.523
			url += "center=" + point_x+ "," + point_y;
			url += "&level=16&w=700&h=500";
			url += "&markers=type:t|size:mid|pos:"+pos+"|label:"+URLEncoder.encode(address, "UTF-8"); //�ּҸ� ��Ŀ�� �������� ǥ��
			URL u = new URL(url);
			HttpURLConnection con = (HttpURLConnection)u.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", "ecp4j8j1gd");
			con.setRequestProperty("X-NCP-APIGW-API-KEY", "atpwsiYOcO1YRW07gCQj4eyrDsktXq88dxo47VCx");
			int responseCode =con.getResponseCode();
			BufferedReader br;
			if(responseCode == 200) { //����ȣ��
				InputStream is = con.getInputStream();
				int read = 0;
				byte[] bytes = new byte[1024];
				//������ �̸����� ���� ����
				String tempname = Long.valueOf(new Date().getTime()).toString();
				File f = new File(tempname + ".jpg");
				f.createNewFile();
				OutputStream outputStream = new FileOutputStream(f);
				while((read = is.read(bytes)) != -1) { //-1: ���� �ƴϸ�
					outputStream.write(bytes,0,read);
				}
				is.close();
			}
			
		}catch (Exception e){
			System.out.print(e);
		}
	}
	
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
			
			String x= ""; String y = ""; String z=""; //�浵 ���� �ּ�
			
			
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
				x=(String) temp.get("x");
				y=(String) temp.get("y");
				z=(String) temp.get("roadAddress");
				}
			map_services(x,y,z);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
}
