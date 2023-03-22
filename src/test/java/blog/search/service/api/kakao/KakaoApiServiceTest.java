package blog.search.service.api.kakao;

import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import blog.search.service.api.kakao.KakaoSearchBlogRequest.KakaoBlogSortType;
import blog.search.service.search.BlogSearchResponse;
import blog.search.service.search.BlogSearchRquest;
import blog.search.service.search.BlogSearchResponse.SearchDomain;

@SpringBootTest
public class KakaoApiServiceTest {

	@Autowired
	private KakaoApiService kakaoApiService;

	@DisplayName("call kakao blog search api 'v2/search/blog'")
	@Test
	void TestKakaoSearchBlog() throws IOException, URISyntaxException {

		String query = "TEST WORLD!";
		KakaoBlogSortType sort = KakaoBlogSortType.ACCURACY;
		int page = 1;
		int size = 20;
		
		BlogSearchRquest req = new BlogSearchRquest(query, sort.type(), page, size);
		KakaoSearchBlogRequest param = KakaoSearchBlogRequest.init(req);

		BlogSearchResponse res = kakaoApiService.SearchBlog(param);
		
		assert Integer.toString(res.getResult().size()).equals(Integer.toString(size));
		assert Integer.toString(res.getPage_number()).equals(Integer.toString(page));
		assert Integer.toString(res.getPage_size()).equals(Integer.toString(size));
		assert res.getDomain().type().equals(SearchDomain.KAKAO.type());

	}
}
