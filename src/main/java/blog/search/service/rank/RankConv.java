package blog.search.service.rank;

import java.util.List;
import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import blog.search.repository.Rank;

@Component
public class RankConv {
	protected List<RankResponse> ConvTopXViewResponse(Page<Rank> ranks) {
		return ranks.stream().map(convToRankResponse()).toList();
	}
	
	private Function<Rank, RankResponse> convToRankResponse() {
		return (rank) -> new RankResponse(rank.getQuery(), rank.getView());
	}
}
