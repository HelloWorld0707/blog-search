package blog.search.service.search;

import java.util.Objects;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class BlogSearchRquest {

	@NotBlank
	private String query;

	@Pattern(regexp = "recency|accuracy")
	private String sort;

	@Min(1)
	@Max(50)
	private Integer page = 1;

	@Min(1)
	@Max(50)
	private Integer size = 10;

	public BlogSearchRquest(String query, String sort, Integer page, Integer size) {
		this.query = query;
		this.sort = Objects.requireNonNullElse(sort, "accuracy");
		this.page = Objects.requireNonNullElse(page, 1);
		this.size = Objects.requireNonNullElse(size, 10);
	}
}
