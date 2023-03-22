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
	protected Rank() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "rank_id")
	private Long rank_id;

	@Column(name = "query", unique = true)
	private String query;

	@Column(name = "view")
	private Long view;

	public Rank(String query) {
		this.query = query;
		this.view = (long) 1;
	}
}
