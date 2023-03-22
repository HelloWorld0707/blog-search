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
	private boolean is_end;
	private List<BlogItem> result;
	private SearchDomain domain;
	
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
