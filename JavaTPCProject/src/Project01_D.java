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
		String apiURL = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?query="; //응답형식: JSON //?는 get 방식
		String client_id = "ecp4j8j1gd";
		String client_secret="atpwsiYOcO1YRW07gCQj4eyrDsktXq88dxo47VCx";
		BufferedReader io = new BufferedReader(new InputStreamReader(System.in)); //System.in: 바이트 스트림, BufferedReader: 문자 스트림, 바이트 스트림과 문자 스트림은 바로 연결될 수 없기 때문에 InputStreamReader를 사용(브릿지 스트림)
		try {
			System.out.println("주소를 입력하세요: ");
			String address = io.readLine();
			String addr = URLEncoder.encode(address, "UTF-8"); //공백이 들어가면 안되기 때문에
			String reqUrl = apiURL + addr;
			
			URL url = new URL(reqUrl); // URL 객체를 이용해 정확한 URL인지 알아보기 (유효한 주소인지)
			HttpURLConnection con = (HttpURLConnection)url.openConnection(); // url객체를 이용해 연결하기 
			
			con.setRequestMethod("GET");
			con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", client_id);
			con.setRequestProperty("X-NCP-APIGW-API-KEY", client_secret);   //세 가지 정보를 입력해서 커넥션이 정상적으로 되면 지도 api와 연결
			
			BufferedReader br; //JSON을 한 줄씩 받아와야하기 때문에 
			int responseCode = con.getResponseCode(); //오류코드 200 = OK(정상), 400 = INVALID_REQUEST, 500 = SYSTEM_ERROR
			if (responseCode == 200) {
				br= new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8")); // byte 스트림과 reader는 연결이 바로 안되기 때문에 new InputStream을 해준다
			}else {
				br= new BufferedReader(new InputStreamReader(con.getErrorStream()));
			}
			
			String line;// 한 라인을 읽어 저장해 놓을 변수
			StringBuffer response = new StringBuffer();//JSON, 한 줄씩 저장하기 위해
			
			while( (line = br.readLine()) != null) { //한 줄씩 읽기
				response.append(line); //response에 JSON이 저장
			}
			br.close();
			
			// JSON에서 원하는 것을 뽑아내기 위해
			JSONTokener tokener = new JSONTokener(response.toString());
			JSONObject object = new JSONObject(tokener);
			System.out.println(object.toString(2));
			
			JSONArray arr=object.getJSONArray("addresses"); //object에서 배열 빼내기
			for(int i=0; i<arr.length(); i++) {
				JSONObject temp = (JSONObject)arr.get(i);
				System.out.println("address: " + temp.get("roadAddress"));
				System.out.println("jibunAddress: " + temp.get("jibunAddress"));
				System.out.println("경도: " + temp.get("x"));
				System.out.println("위도: " + temp.get("y"));
				}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
}
