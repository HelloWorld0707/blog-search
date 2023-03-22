package blog.search.service.rank;

import java.util.ArrayList;
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

@Service
public class RankService {

	@Autowired
	private RankRepository rankRepository;
	
	@Transactional
	public int UpdateQueryView(String query) throws Exception {
		Optional<Rank> rank = rankRepository.findByQuery(query);

		if (rank.isEmpty()) {
			Rank newRank = new Rank(query);
			rankRepository.save(newRank);
			return 1;
		}

		return rankRepository.updateView(rank.get().getRank_id());
	}

	public List<RankResponse> GetTopXViewQuery(int size) {
		List<RankResponse> list = new ArrayList<RankResponse>();
		PageRequest page = PageRequest.of(0, size, Sort.by("view").descending());
		Page<Rank> ranks = rankRepository.findAll(page);

		for (Rank rank : ranks) {
			list.add(new RankResponse(rank.getQuery(), rank.getView()));
		}

		return list;
	}

	// 
	@Transactional
	public Rank FindByQuery(String query) throws NoSuchElementException {
		return rankRepository.findByQuery(query).get();
	}
}
