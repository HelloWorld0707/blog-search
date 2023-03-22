package blog.search.service.api.naver;

import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import blog.search.service.api.kakao.KakaoSearchBlogRequest.KakaoBlogSortType;
import blog.search.service.search.BlogSearchResponse;
import blog.search.service.search.BlogSearchResponse.SearchDomain;
import blog.search.service.search.BlogSearchRquest;

@SpringBootTest
public class NaverApiServiceTest {

	@Autowired
	private NaverApiService naverApiService;
	
	@Test
	void TestNaverSearchBlogWithparam() throws IOException, URISyntaxException {

		String query = "TEST WORLD!";
		KakaoBlogSortType sort = KakaoBlogSortType.ACCURACY;
		int start = 10;
		int display = 20;
		
		BlogSearchRquest req = new BlogSearchRquest(query, sort.type(), start, display);
		NaverSearchBlogRequest param = NaverSearchBlogRequest.init(req);

		BlogSearchResponse res = naverApiService.SearchBlog(param);
		
		// default size
		assert Integer.toString(res.getResult().size()).equals(Integer.toString(display));
		assert Integer.toString(res.getPage_number()).equals(Integer.toString(start));
		assert Integer.toString(res.getPage_size()).equals(Integer.toString(display));
		assert res.getDomain().type().equals(SearchDomain.NAVER.type());
	}

}
