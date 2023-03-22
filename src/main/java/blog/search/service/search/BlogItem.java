package blog.search.service.search;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BlogItem {
	private String title;
	private String contents;
	private String url;
	private String blog_name;
	private String thumbnail;
	private String datetime;
}
