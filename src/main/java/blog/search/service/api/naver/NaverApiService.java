package blog.search.service.api.naver;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class NaverApiService {

	// log
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	// base URI
	private String schema = "https";
	private String host = "openapi.naver.com";

	public JSONObject SearchBlog(NaverSearchBlogRequest param) throws IOException {
		JSONObject resJson = null;
		final String uriPath = "v1/search/blog.json";

		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			RequestBuilder getBuilder = RequestBuilder.get();

			// build URI
			URI uri = new URIBuilder().setScheme(schema).setHost(host).setPath(uriPath).build();

			// set URI
			getBuilder.setUri(uri);

			// set header
			addHeader(getBuilder);

			// set param
			param.addParam(getBuilder);

			// build
			HttpUriRequest req = getBuilder.build();
			CloseableHttpResponse response = httpclient.execute(req);

			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				throw new RuntimeException("An error occurred when naver blog search.");
			}

			try {
				String json = EntityUtils.toString(response.getEntity(), "UTF-8");
				resJson = new JSONObject(json);
			} finally {
				response.close();
			}
		} catch (URISyntaxException e) {
			logger.error("Invalid URI. " + schema + host + uriPath, e);
		} finally {
			httpclient.close();
		}

		return resJson;
	}

	private void addHeader(RequestBuilder request) {
		request.addHeader("Content-Type", "application/json;charset=UTF-8");

		final String clientId = "vezR1xWlUUA6L6Uq35Y6";
		request.addHeader("X-Naver-Client-Id", clientId);

		final String clientSecret = "PDTj1rmmtR";
		request.addHeader("X-Naver-Client-Secret", clientSecret);
	}
}
