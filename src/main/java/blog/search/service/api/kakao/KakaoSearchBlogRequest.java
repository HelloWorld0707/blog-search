package blog.search.service.api.kakao;

import org.apache.http.client.methods.RequestBuilder;

import blog.search.service.search.BlogSearchRquest;
import lombok.Getter;

@Getter
public class KakaoSearchBlogRequest {
	private String query;
	private KakaoBlogSortType sort;
	private int page;
	private int size;

	public enum KakaoBlogSortType {
		ACCURACY("accuracy"), RECENCY("recency");

		private final String type;

		KakaoBlogSortType(String type) {
			this.type = type;
		}

		public String type() {
			return type;
		}
	}

	private KakaoSearchBlogRequest(String query, KakaoBlogSortType sort, int page, int size) {
		this.query = query;
		this.sort = sort;
		this.page = page;
		this.size = size;
	}

	public static KakaoSearchBlogRequest init(BlogSearchRquest req) {
		KakaoBlogSortType kakaoSortOption = null;
		String sort = req.getSort();
		if (sort.equals(KakaoBlogSortType.RECENCY.type())) {
			kakaoSortOption = KakaoBlogSortType.RECENCY;
		} else if (sort.equals(KakaoBlogSortType.ACCURACY.type())) {
			kakaoSortOption = KakaoBlogSortType.ACCURACY;
		}

		return new KakaoSearchBlogRequest(req.getQuery(), kakaoSortOption, req.getPage(), req.getSize());
	}

	protected void addParam(RequestBuilder request) {
		request.addParameter("query", query);
		request.addParameter("sort", sort.type);
		request.addParameter("page", Integer.toString(page));
		request.addParameter("size", Integer.toString(size));
	}
	
	protected void addHeader(RequestBuilder request) {
		request.addHeader("Content-Type", "application/json;charset=UTF-8");

		final String token = "a6d3dac6a9281d2a3f652972f8771420";
		request.addHeader("Authorization", "KakaoAK " + token);
	}
}
