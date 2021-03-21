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
			//url에 어떻게 입력해야 하는지
			String pos=URLEncoder.encode(point_x+" "+point_y, "UTF-8"); //pos:127.15(공백)38.15,126.12(공백)37.523
			url += "center=" + point_x+ "," + point_y;
			url += "&level=16&w=700&h=500";
			url += "&markers=type:t|size:mid|pos:"+pos+"|label:"+URLEncoder.encode(address, "UTF-8"); //주소를 마커에 툴팁으로 표시
			URL u = new URL(url);
			HttpURLConnection con = (HttpURLConnection)u.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", "ecp4j8j1gd");
			con.setRequestProperty("X-NCP-APIGW-API-KEY", "atpwsiYOcO1YRW07gCQj4eyrDsktXq88dxo47VCx");
			int responseCode =con.getResponseCode();
			BufferedReader br;
			if(responseCode == 200) { //정상호출
				InputStream is = con.getInputStream();
				int read = 0;
				byte[] bytes = new byte[1024];
				//랜덤한 이름으로 파일 생성
				String tempname = Long.valueOf(new Date().getTime()).toString();
				File f = new File(tempname + ".jpg");
				f.createNewFile();
				OutputStream outputStream = new FileOutputStream(f);
				while((read = is.read(bytes)) != -1) { //-1: 끝이 아니면
					outputStream.write(bytes,0,read);
				}
				is.close();
			}
			
		}catch (Exception e){
			System.out.print(e);
		}
	}
	
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
			
			String x= ""; String y = ""; String z=""; //경도 위도 주소
			
			
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
