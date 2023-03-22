package blog.search.service.api.naver;

import org.apache.http.client.methods.RequestBuilder;

import blog.search.service.api.kakao.KakaoSearchBlogRequest.KakaoBlogSortType;
import blog.search.service.search.BlogSearchRquest;
import lombok.Getter;

@Getter
public class NaverSearchBlogRequest {
	private String query;
	private NaverBlogSortType sort;
	private int start;
	private int display;

	public enum NaverBlogSortType {
		SIM("sim"), DATE("date");

		private final String type;

		NaverBlogSortType(String type) {
			this.type = type;
		}

		public String type() {
			return type;
		}
	}

	private NaverSearchBlogRequest(String query, NaverBlogSortType sort, int start, int display) {
		this.query = query;
		this.sort = sort;
		this.start = start;
		this.display = display;
	}

	public static NaverSearchBlogRequest init(BlogSearchRquest req) {
		NaverBlogSortType naverSortOption = null;
		String sort = req.getSort();
		if (sort.equals(KakaoBlogSortType.RECENCY.type())) {
			naverSortOption = NaverBlogSortType.DATE;
		} else if (sort.equals(KakaoBlogSortType.ACCURACY.type())) {
			naverSortOption = NaverBlogSortType.SIM;
		}

		return new NaverSearchBlogRequest(req.getQuery(), naverSortOption, req.getPage(), req.getSize());
	}

	protected void addParam(RequestBuilder request) {
		request.addParameter("query", query);
		request.addParameter("sort", sort.type);
		request.addParameter("start", Integer.toString(start));
		request.addParameter("display", Integer.toString(display));
	}

	protected void addHeader(RequestBuilder request) {
		request.addHeader("Content-Type", "application/json;charset=UTF-8");

		final String clientId = "vezR1xWlUUA6L6Uq35Y6";
		request.addHeader("X-Naver-Client-Id", clientId);

		final String clientSecret = "PDTj1rmmtR";
		request.addHeader("X-Naver-Client-Secret", clientSecret);
	}
}
