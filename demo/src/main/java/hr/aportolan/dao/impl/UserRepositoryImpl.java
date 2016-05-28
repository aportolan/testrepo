package hr.aportolan.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import hr.aportolan.dao.CustomUserRepository;
import hr.aportolan.domain.User;

@Repository
public class UserRepositoryImpl implements CustomUserRepository {
	private static final int DEFAULT_LIMIT = 10;
	@Autowired
	private EntityManager entityManager;

	@Override
	public List<User> selectUsersByCriteria(User payload, int offset, int limit) {
		if (limit == 0)
			limit = DEFAULT_LIMIT;
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();

		CriteriaQuery<User> getUsersByCriteria = cb.createQuery(User.class);
		Root<User> e = getUsersByCriteria.from(User.class);
		List<Predicate> predicatesOr = new ArrayList<>();
		if (payload.getUid() != 0)
			predicatesOr.add(cb.equal(e.get("uid"), payload.getUid()));
		if (payload.getTag() != null)
			predicatesOr.add(cb.or(cb.equal(e.<String> get("tag"), payload.getTag())));
		if (payload.getName() != null)
			predicatesOr
					.add(cb.or(cb.like(cb.upper(e.<String> get("name")), "%" + payload.getName().toUpperCase() + "%")));

		getUsersByCriteria.where(predicatesOr.toArray(new Predicate[] {}));
		return entityManager.createQuery(getUsersByCriteria).setFirstResult(offset).setMaxResults(limit)
				.getResultList();
	}

}
