package blog.search.service.rank;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RankResponse {
	private String query;
	private Long view;
}
