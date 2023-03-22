package blog.search.service.api.naver;

import org.apache.http.client.methods.RequestBuilder;

import lombok.AllArgsConstructor;

@AllArgsConstructor
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

	public void addParam(RequestBuilder request) {
		request.addParameter("query", query);
		request.addParameter("sort", sort.type);
		request.addParameter("start", Integer.toString(start));
		request.addParameter("display", Integer.toString(display));
	}
}
