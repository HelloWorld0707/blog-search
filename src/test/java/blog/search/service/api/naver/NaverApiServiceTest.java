package blog.search.service.api.naver;

import java.io.IOException;
import java.net.URISyntaxException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import blog.search.service.api.naver.NaverSearchBlogRequest.NaverBlogSortType;

@SpringBootTest
public class NaverApiServiceTest {

	@Autowired
	private NaverApiService naverApiService;

	@DisplayName("call naver blog search api 'v1/search/blog.json'")
	@Test
	void NaverSearchBlog() throws IOException, URISyntaxException {

		String query = "TEST WORLD!";
		NaverBlogSortType sort = NaverBlogSortType.DATE;
		int start = 1;
		int display = 20;

		NaverSearchBlogRequest param = new NaverSearchBlogRequest(query, sort, start, display);

		JSONObject resJson = naverApiService.SearchBlog(param);
		JSONArray resDoc = resJson.getJSONArray("items");

		// default size
		assert Integer.toString(resDoc.length()).equals(Integer.toString(display));
	}

}
