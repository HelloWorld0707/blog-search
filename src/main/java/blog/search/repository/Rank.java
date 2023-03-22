package blog.search.repository;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Getter
@Table(name = "RANK")
public class Rank {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "rank_id")
	private Long rank_id;

	@Column(name = "query", unique = true)
	private String query;

	@Column(name = "view")
	private Long view;

	public Rank addView(int view) {
		this.view += view;
		return this;
	}

	public static Rank withQuery(String query) {
		return new Rank(query);
	}

	private Rank(String query) {
		this.query = query;
		this.view = (long) 1;
	}

	protected Rank() {
	}
}
