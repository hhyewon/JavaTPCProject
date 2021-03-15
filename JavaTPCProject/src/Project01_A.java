import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import kr.inflearn.BookDTO;

public class Project01_A {
	public static void main(String[] args) {

		//Object(BookDTO) -> JSON(String)
		BookDTO dto = new BookDTO("�ڹ�", 21000, "������", 670);
		Gson g = new Gson(); //Gson API �ٿ�ޱ�(https://mvnrepository.com/artifact/com.google.code.gson/gson)
		String json= g.toJson(dto); //Json ���·� �ٲٱ�
		System.out.println(json); //{"title":"�ڹ�","price":21000,"company":"������","page":670}

		//�ݴ�� JSON -> Object
		BookDTO dto1 = g.fromJson(json, BookDTO.class);
		System.out.println(dto1); //BookDTO [title=�ڹ�, price=21000, company=������, page=670]
		System.out.println(dto1.getTitle()); //�ϳ��� ��������
		
		//Object(List<BookDTO>) -> JSON(String) : [{ }, { }...];
		List<BookDTO> lst = new ArrayList<BookDTO>();
		lst.add(new BookDTO("�ڹ�1", 2100, "������1", 570));
		lst.add(new BookDTO("�ڹ�2", 3100, "������2", 670));
		lst.add(new BookDTO("�ڹ�3", 1100, "������3", 310));
		
		String lstJson = g.toJson(lst); //�˾Ƽ� �迭 ������ �������
		System.out.println(lstJson); //[{"title":"�ڹ�1","price":2100,"company":"������1","page":570},{"title":"�ڹ�2","price":3100,"company":"������2","page":670},{"title":"�ڹ�3","price":1100,"company":"������3","page":310}]

		//JSON(String) -> Object(List<BookDTO>)
		//g.fromJson(lstJson, List<BookDTO>.class); //error: Ÿ�� ������ �� �� 
		List<BookDTO> lst1 = g.fromJson(lstJson, new TypeToken<List<BookDTO>>(){}.getType());
		for(BookDTO vo: lst1) {
			System.out.println(vo);
		} /*BookDTO [title=�ڹ�1, price=2100, company=������1, page=570]
		    BookDTO [title=�ڹ�2, price=3100, company=������2, page=670]
			BookDTO [title=�ڹ�3, price=1100, company=������3, page=310] */

	}
}
