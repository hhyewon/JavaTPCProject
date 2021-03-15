import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import kr.inflearn.BookDTO;

public class Project01_A {
	public static void main(String[] args) {

		//Object(BookDTO) -> JSON(String)
		BookDTO dto = new BookDTO("자바", 21000, "에이콘", 670);
		Gson g = new Gson(); //Gson API 다운받기(https://mvnrepository.com/artifact/com.google.code.gson/gson)
		String json= g.toJson(dto); //Json 형태로 바꾸기
		System.out.println(json); //{"title":"자바","price":21000,"company":"에이콘","page":670}

		//반대로 JSON -> Object
		BookDTO dto1 = g.fromJson(json, BookDTO.class);
		System.out.println(dto1); //BookDTO [title=자바, price=21000, company=에이콘, page=670]
		System.out.println(dto1.getTitle()); //하나씩 가져오기
		
		//Object(List<BookDTO>) -> JSON(String) : [{ }, { }...];
		List<BookDTO> lst = new ArrayList<BookDTO>();
		lst.add(new BookDTO("자바1", 2100, "에이콘1", 570));
		lst.add(new BookDTO("자바2", 3100, "에이콘2", 670));
		lst.add(new BookDTO("자바3", 1100, "에이콘3", 310));
		
		String lstJson = g.toJson(lst); //알아서 배열 구조로 만들어짐
		System.out.println(lstJson); //[{"title":"자바1","price":2100,"company":"에이콘1","page":570},{"title":"자바2","price":3100,"company":"에이콘2","page":670},{"title":"자바3","price":1100,"company":"에이콘3","page":310}]

		//JSON(String) -> Object(List<BookDTO>)
		//g.fromJson(lstJson, List<BookDTO>.class); //error: 타입 정보가 두 개 
		List<BookDTO> lst1 = g.fromJson(lstJson, new TypeToken<List<BookDTO>>(){}.getType());
		for(BookDTO vo: lst1) {
			System.out.println(vo);
		} /*BookDTO [title=자바1, price=2100, company=에이콘1, page=570]
		    BookDTO [title=자바2, price=3100, company=에이콘2, page=670]
			BookDTO [title=자바3, price=1100, company=에이콘3, page=310] */

	}
}
