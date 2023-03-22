package blog.search.service.rank;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import blog.search.repository.Rank;
import jakarta.transaction.Transactional;

@SpringBootTest
public class RankServiceTest {

	@Autowired
	RankService rankService;

	@DisplayName("rank update")
	@Test
	@Transactional
	void TestUpdateAndFind() throws Exception {
		final String testQuery = "DB_TEST";

		rankService.UpdateQueryView(testQuery);
		Rank rank = rankService.FindByQuery(testQuery);

		Assertions.assertEquals(testQuery, rank.getQuery());
		Assertions.assertEquals(1, rank.getView());
	}

	@DisplayName("get rank top X view")
	@Test
	@Transactional
	void TestGetTopXViewQuery() throws Exception {
		final String testQuery1 = "DB_TEST1";
		final String testQuery2 = "DB_TEST2";
		final String testQuery3 = "DB_TEST3";

		for (int i = 0; i < 10; i++) {
			rankService.UpdateQueryView(testQuery1);
		}
		for (int i = 0; i < 9; i++) {
			rankService.UpdateQueryView(testQuery2);
		}
		for (int i = 0; i < 8; i++) {
			rankService.UpdateQueryView(testQuery3);
		}

		final int rankSize = 2;
		List<RankResponse> ranks = rankService.GetTopXViewQuery(rankSize);
		Assertions.assertEquals(rankSize, ranks.size());

		RankResponse rank = ranks.get(0);
		Assertions.assertEquals(10, rank.getView());
		Assertions.assertEquals(testQuery1, rank.getQuery());

		rank = ranks.get(1);
		Assertions.assertEquals(9, rank.getView());
		Assertions.assertEquals(testQuery2, rank.getQuery());
	}
}
