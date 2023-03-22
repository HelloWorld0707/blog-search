package blog.search.controller.search;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import blog.search.service.rank.RankResponse;
import blog.search.service.rank.RankService;
import blog.search.service.search.BlogSearchResponse;
import blog.search.service.search.BlogSearchRquest;
import blog.search.service.search.SearchService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@RestController
@Validated
@RequestMapping("/search")
public class SearchController {

	@Autowired
	private SearchService searchService;

	@Autowired
	private RankService rankService;

	@GetMapping(value = "/blog")
	public ResponseEntity<BlogSearchResponse> Blog(@Valid @ModelAttribute BlogSearchRquest req) {
		try {
			BlogSearchResponse res = searchService.SearchBlog(req);
			return ResponseEntity.status(HttpStatus.OK).body(res);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@GetMapping(value = "/rank")
	@Valid
	public ResponseEntity<List<RankResponse>> Rank(
			@RequestParam(defaultValue = "10", required = false) @Min(1) @Max(20) int size) {
		return ResponseEntity.status(HttpStatus.OK).body(rankService.GetTopXViewQuery(size));
	}
}
