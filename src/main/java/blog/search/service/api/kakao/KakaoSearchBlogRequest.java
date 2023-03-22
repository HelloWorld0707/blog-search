package blog.search.service.api.kakao;

import org.apache.http.client.methods.RequestBuilder;

import lombok.AllArgsConstructor;

@AllArgsConstructor
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

	public void addParam(RequestBuilder request) {
		request.addParameter("query", query);
		request.addParameter("sort", sort.type);
		request.addParameter("page", Integer.toString(page));
		request.addParameter("size", Integer.toString(size));
	}
}
