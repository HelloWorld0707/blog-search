package blog.search.service.api.kakao;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import blog.search.service.search.BlogItem;
import blog.search.service.search.BlogSearchResponse;
import blog.search.service.search.BlogSearchResponse.SearchDomain;

@Component
public class KakaoConv {
	protected BlogSearchResponse ConvBlogSearchResponse(JSONObject resJson, int page, int size) {

		int pageSize = resJson.getJSONObject("meta").getInt("pageable_count");
		boolean isEnd = resJson.getJSONObject("meta").getBoolean("is_end");

		JSONArray docs = resJson.getJSONArray("documents");
		List<BlogItem> result = ParseKakaoItems(docs);

		return new BlogSearchResponse(page, size, pageSize, isEnd, result, SearchDomain.KAKAO);
	}

	// ParseKakaoItems - kakao result를 파싱한다.
	private List<BlogItem> ParseKakaoItems(JSONArray items) {
		List<BlogItem> result = new ArrayList<BlogItem>();

		for (int i = 0; i < items.length(); i++) {
			JSONObject doc = (JSONObject) items.get(i);

			String title = doc.getString("title");
			String contents = doc.getString("contents");
			String url = doc.getString("url");
			String blogname = doc.getString("blogname");
			String thumbnail = doc.getString("thumbnail");
			String datetime = doc.getString("datetime");

			result.add(new BlogItem(title, contents, url, blogname, thumbnail, datetime));
		}

		return result;
	}
}
