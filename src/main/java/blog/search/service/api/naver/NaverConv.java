package blog.search.service.api.naver;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import blog.search.service.search.BlogItem;
import blog.search.service.search.BlogSearchResponse;
import blog.search.service.search.BlogSearchResponse.SearchDomain;

@Component
public class NaverConv {
	protected BlogSearchResponse ConvBlogSearchResponse(JSONObject resJson, int page, int size) {
		
		int pageSize = resJson.getInt("total");
		boolean isEnd = page * size > pageSize;

		JSONArray docs = resJson.getJSONArray("items");
		List<BlogItem> result = ParseNaverItems(docs);
		
		return new BlogSearchResponse(page, size, pageSize, isEnd, result, SearchDomain.NAVER);
	}
	
	// ParseKakaoItems - naver검색 result를 파싱한다.
	protected List<BlogItem> ParseNaverItems(JSONArray items) {
		List<BlogItem> result = new ArrayList<BlogItem>();
		for (int i = 0; i < items.length(); i++) {
			JSONObject doc = (JSONObject) items.get(i);

			String title = doc.getString("title");
			String contents = doc.getString("description");
			String url = doc.getString("link");
			String blogname = doc.getString("bloggername");
			String thumbnail = ""; // naver는 thumbnail이 없음.
			String datetime = doc.getString("postdate");

			result.add(new BlogItem(title, contents, url, blogname, thumbnail, datetime));
		}

		return result;
	}
}
