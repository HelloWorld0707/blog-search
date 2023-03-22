package blog.search.service.search;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import blog.search.service.search.BlogSearchResponse.BlogItem;

@SpringBootTest
public class SearchServiceTest {

	@Autowired
	private SearchService searchService;

	@Test
	void TestParseKakaoItems() {
		String blogName = "test_blogName";
		String content = "test_content";
		String datetime = "2023-03-22T00:13:28.000+09:00";
		String thumbnail = "test_thumbnail";
		String title = "test_title";
		String url = "test_url";

		JSONObject obj = new JSONObject("{" + "\"blogname\":\"" + blogName + "\"," + "\"contents\":\"" + content + "\","
				+ "\"datetime\":\"" + datetime + "\"," + "\"thumbnail\":\"" + thumbnail + "\"," + "\"title\":\"" + title
				+ "\"," + "\"url\":\"" + url + "\"" + "}");
		JSONArray arr = new JSONArray();
		arr.put(obj);

		List<BlogItem> blogItems = searchService.ParseKakaoItems(arr);
		Assertions.assertEquals(1, blogItems.size());
		
		BlogItem blogItem = blogItems.get(0);
		Assertions.assertEquals(blogName, blogItem.getBlog_name());
		Assertions.assertEquals(content, blogItem.getContents());
		Assertions.assertEquals("20230322", blogItem.getDatetime());
		Assertions.assertEquals(thumbnail, blogItem.getThumbnail());
		Assertions.assertEquals(title, blogItem.getTitle());
		Assertions.assertEquals(url, blogItem.getUrl());
	}
}
