package blog.search.service.search;

import org.hibernate.sql.exec.ExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import blog.search.service.api.kakao.KakaoApiService;
import blog.search.service.api.kakao.KakaoSearchBlogRequest;
import blog.search.service.api.naver.NaverApiService;
import blog.search.service.api.naver.NaverSearchBlogRequest;
import blog.search.service.rank.RankService;

@Service
public class SearchService {

	@Autowired
	private KakaoApiService kakaoApiService;

	@Autowired
	private NaverApiService naverApiService;

	@Autowired
	private RankService rankService;

	// log
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	public BlogSearchResponse SearchBlog(BlogSearchRquest req) throws Exception {
		BlogSearchResponse res = callSearchApi(req);
		try {
			rankService.UpdateQueryView(req.getQuery());
		} catch (Exception e) {
			logger.error("Failed to update rank view. ", e);
		}
		
		return res;
	}
	
	private BlogSearchResponse callSearchApi(BlogSearchRquest req) throws Exception {
		// kakao API
		try {
			return kakaoApiService.SearchBlog(KakaoSearchBlogRequest.init(req));
		} catch (Exception e) {
			logger.error("Failed to execute Kakao blog search. ", e);
		}

		// naver API
		try {
			return naverApiService.SearchBlog(NaverSearchBlogRequest.init(req));
		} catch (Exception e) {
			logger.error("Failed to execute naver blog search. ", e);
			throw new ExecutionException("All API call is failed", e);
		}
	}
}
