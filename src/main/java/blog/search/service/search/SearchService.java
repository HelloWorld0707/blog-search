package blog.search.service.search;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.sql.exec.ExecutionException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import blog.search.service.api.kakao.KakaoApiService;
import blog.search.service.api.kakao.KakaoSearchBlogRequest;
import blog.search.service.api.kakao.KakaoSearchBlogRequest.KakaoBlogSortType;
import blog.search.service.api.naver.NaverApiService;
import blog.search.service.api.naver.NaverSearchBlogRequest;
import blog.search.service.api.naver.NaverSearchBlogRequest.NaverBlogSortType;
import blog.search.service.rank.RankService;
import blog.search.service.search.BlogSearchResponse.BlogItem;
import blog.search.service.search.BlogSearchResponse.SearchDomain;

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

	public BlogSearchResponse SearchBlog(String query, String sort, int page, int size) throws Exception {
		BlogSearchResponse res = null;
		try {
			res = CallSearchAPI(query, sort, page, size);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException(e);
		} catch (Exception e) {
			throw new Exception(e);
		}

		try {
			rankService.UpdateQueryView(query);
		} catch (Exception e) {
			logger.error("Failed to update rank view. ", e);
		}

		return res;
	}

	public BlogSearchResponse CallSearchAPI(String query, String sort, int page, int size)
			throws IllegalArgumentException, Exception {
		// parse search option.
		KakaoBlogSortType kakaoSortOption = null;
		NaverBlogSortType naverSortOption = null;
		if (sort.equals(KakaoBlogSortType.RECENCY.type())) {
			kakaoSortOption = KakaoBlogSortType.RECENCY;
			naverSortOption = naverSortOption.DATE;
		} else if (sort.equals(KakaoBlogSortType.ACCURACY.type())) {
			kakaoSortOption = KakaoBlogSortType.ACCURACY;
			naverSortOption = naverSortOption.SIM;
		} else {
			logger.error("invalid sort options. " + sort);
			throw new IllegalArgumentException("Unexpected");
		}

		// kakao API
		try {
			// call API
			KakaoSearchBlogRequest req = new KakaoSearchBlogRequest(query, kakaoSortOption, page, size);
			JSONObject resJson = kakaoApiService.SearchBlog(req);

			// parse result
			int pageSize = resJson.getJSONObject("meta").getInt("pageable_count");
			JSONArray docs = resJson.getJSONArray("documents");
			List<BlogItem> result = ParseKakaoItems(docs);

			// set response
			return new BlogSearchResponse(page, size, pageSize, result, SearchDomain.KAKAO);

		} catch (Exception e) {
			logger.error("Failed to execute Kakao blog search. ", e);
		}
		// ~kakao API

		// naver API
		try {
			// call API
			NaverSearchBlogRequest param = new NaverSearchBlogRequest(query, naverSortOption, page, size);
			JSONObject resJson = naverApiService.SearchBlog(param);

			// parse result
			int pageSize = resJson.getInt("total");

			JSONArray docs = resJson.getJSONArray("items");
			List<BlogItem> result = ParseNaverItems(docs);

			// set response
			return new BlogSearchResponse(page, size, pageSize, result, SearchDomain.NAVER);
		} catch (Exception e) {
			logger.error("Failed to execute naver blog search. ", e);
			throw new ExecutionException("All API call is failed", e);
		}
		// ~naver API
	}

	// ParseKakaoItems - kakao result를 파싱한다.
	protected List<BlogItem> ParseKakaoItems(JSONArray items) {
		List<BlogItem> result = new ArrayList<BlogItem>();
		for (int i = 0; i < items.length(); i++) {
			JSONObject doc = (JSONObject) items.get(i);

			String title = doc.getString("title");
			String contents = doc.getString("contents");
			String url = doc.getString("url");
			String blogname = doc.getString("blogname");
			String thumbnail = doc.getString("thumbnail");
			DateFormat sdFormat = new SimpleDateFormat("yyyyMMdd");
			String datetime = "";
			try {
				Date date = sdFormat.parse(doc.getString("datetime"));
				datetime = sdFormat.format(date);
			} catch (ParseException e) {
				// Date format 에러가 발생해도 진행한다.
				logger.error("invalid datetime. ", e);
			}

			result.add(new BlogItem(title, contents, url, blogname, thumbnail, datetime));
		}

		return result;
	}

	// ParseKakaoItems - naver검색 result를 파싱한다.
	protected List<BlogItem> ParseNaverItems(JSONArray items) {
		List<BlogItem> result = new ArrayList<BlogItem>();
		for (int i = 0; i < items.length(); i++) {
			JSONObject doc = (JSONObject) items.get(i);

			String title = doc.getString("title");
			String contents = doc.getString("description");
			String url = doc.getString("link");
			String blogname = doc.getString("bloggername");
			String thumbnail = ""; // naver는 thumbnail이 없음.
			DateFormat sdFormat = new SimpleDateFormat("yyyyMMdd");
			String datetime = doc.getString("postdate");

			result.add(new BlogItem(title, contents, url, blogname, thumbnail, datetime));
		}

		return result;
	}
}
