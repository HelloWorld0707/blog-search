package blog.search.service.rank;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import blog.search.repository.Rank;
import blog.search.repository.RankRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RankService {

	private final RankConv rankConv;
	
	@Autowired
	private RankRepository rankRepository;

	@Transactional
	public Rank UpdateQueryView(String query) throws Exception {
		Optional<Rank> rank = rankRepository.findByQuery(query);

		if (rank.isEmpty()) {
			Rank newRank = Rank.withQuery(query);
			return rankRepository.saveAndFlush(newRank);
		}

		return rank.get().addView(1);
	}

	public List<RankResponse> GetTopXViewQuery(int size) {
		PageRequest page = PageRequest.of(0, size, Sort.by("view").descending());
		Page<Rank> ranks = rankRepository.findAll(page);
		
		return rankConv.ConvTopXViewResponse(ranks);
	}
	
	@Transactional
	protected Rank FindByQuery(String query) throws NoSuchElementException {
		return rankRepository.findByQuery(query).get();
	}

}
