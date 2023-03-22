package blog.search.service.api.kakao;

import java.io.IOException;
import java.net.URISyntaxException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import blog.search.service.api.kakao.KakaoSearchBlogRequest.KakaoBlogSortType;

@SpringBootTest
public class KakaoApiServiceTest {

	@Autowired
	private KakaoApiService kakaoApiService;

	@DisplayName("call kakao blog search api 'v2/search/blog'")
	@Test
	void KakaoSearchBlog() throws IOException, URISyntaxException {

		String query = "TEST WORLD!";
		KakaoBlogSortType sort = KakaoBlogSortType.RECENCY;
		int page = 1;
		int size = 20;

		KakaoSearchBlogRequest param = new KakaoSearchBlogRequest(query, sort, page, size);

		JSONObject resJson = kakaoApiService.SearchBlog(param);
		JSONArray resDoc = resJson.getJSONArray("documents");

		// default size
		assert Integer.toString(resDoc.length()).equals(Integer.toString(size));
	}
}
