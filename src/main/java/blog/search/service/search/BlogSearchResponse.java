package blog.search.service.search;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BlogSearchResponse {
	private int page_number;
	private int page_size;
	private int total_size;
	private List<BlogItem> result;
	private SearchDomain domain;

	@Getter
	@AllArgsConstructor
	public static class BlogItem {
		private String title;
		private String contents;
		private String url;
		private String blog_name;
		private String thumbnail;
		private String datetime;
	}

	public enum SearchDomain {
		KAKAO("kakao"), NAVER("naver");

		private final String type;

		SearchDomain(String type) {
			this.type = type;
		}

		public String type() {
			return type;
		}
	}
}
