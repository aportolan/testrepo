package hr.aportolan.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import hr.aportolan.dao.CustomMessageRepository;
import hr.aportolan.domain.Message;
import hr.aportolan.domain.User;
import hr.aportolan.enums.CustomQueries;

@Repository
public class MessageRepositoryImpl implements CustomMessageRepository {
	@Autowired
	private EntityManager entityManager;

	@Transactional
	@Override
	public void deleteExpired() {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();

		CriteriaDelete<Message> delete = cb.createCriteriaDelete(Message.class);
		Root<Message> e = delete.from(Message.class);
		delete.where(cb.lessThan(e.<Date> get("validity"), new Date()));
		entityManager.createQuery(delete).executeUpdate();

	}

	@Transactional
	@Override
	public void saveByUserData(Message message) {

		Query query;

		if (message.getUser() != null && message.getUser().getUid() != 0) {
			query = entityManager.createNamedQuery(CustomQueries.UPDATE_ALL_USER_MESSAGES_BY_UID_NAME)
					.setParameter("user.uid", message.getUser().getTag());
		} else if (message.getUser() != null && message.getUser().getTag() != null) {
			query = entityManager.createNamedQuery(CustomQueries.UPDATE_ALL_USER_MESSAGES_BY_TAG_NAME)
					.setParameter("user.tag", message.getUser().getTag());
		} else if (message.getUser() != null && message.getUser().getName() != null) {
			query = entityManager.createNamedQuery(CustomQueries.UPDATE_ALL_USER_MESSAGES_BY_NAME_NAME)
					.setParameter("user.name", message.getUser().getTag());
		} else {
			query = entityManager.createNamedQuery(CustomQueries.UPDATE_ALL_USER_MESSAGES_NAME);
		}
		query.setParameter("title", message.getTitle()).setParameter("body", message.getBody())
				.setParameter("validity", message.getValidity()).executeUpdate();

	}

	@Transactional
	@Override
	public void deleteByUser(Message message) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();

		CriteriaDelete<Message> deleteByCriteria = cb.createCriteriaDelete(Message.class);
		Root<Message> messageRoot = deleteByCriteria.from(Message.class);

		Subquery<Long> subquery = deleteByCriteria.subquery(Long.class);
		Root<User> userRoot = subquery.from(User.class);
		subquery.select(userRoot.<Long> get("uid"));

		List<Predicate> predicatesOr = new ArrayList<>();
		List<Predicate> predicatesAnd = new ArrayList<>();

		if (message.getMid() != 0)
			predicatesAnd.add(cb.equal(messageRoot.<Long> get("mid"), message.getMid()));

		if (message.getUser().getUid() != 0)
			predicatesOr.add(cb.equal(userRoot.<Long> get("uid"), message.getUser().getUid()));
		if (message.getUser().getTag() != null)
			predicatesOr.add(cb.or(cb.equal(userRoot.<String> get("tag"), message.getUser().getTag())));
		subquery.where(predicatesOr.toArray(new Predicate[] {}));
		predicatesAnd.add(cb.and(cb.in(messageRoot.<User> get("user").<Long> get("uid")).value(subquery)));

		deleteByCriteria.where(predicatesAnd.toArray(new Predicate[] {}));
		entityManager.createQuery(deleteByCriteria).executeUpdate();

	}

	@Override
	public List<Message> getByUser(Message payload, int offset, int limit) {

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();

		CriteriaQuery<Message> getByUser = cb.createQuery(Message.class);
		Root<Message> e = getByUser.from(Message.class);
		Join<Message, User> userJoin = e.join("user", JoinType.INNER);
		List<Predicate> predicates = new ArrayList<>();
		if (payload.getUser().getUid() != 0)
			predicates.add(cb.equal(userJoin.<Long> get("uid"), payload.getUser().getUid()));
		if (payload.getUser().getTag() != null && !payload.getUser().getTag().equals(""))
			predicates.add(cb.equal(cb.upper(userJoin.<String> get("tag")), payload.getUser().getTag().toUpperCase()));
		if (payload.getMid() != 0)
			predicates.add(cb.equal(e.<Long> get("mid"), payload.getMid()));
		if (payload.getTitle() != null && !payload.getTitle().equals(""))
			predicates.add(cb.like(cb.upper(e.<String> get("title")), payload.getTitle().toUpperCase() + "%"));
		if (payload.getBody() != null && !payload.getBody().equals(""))
			predicates.add(cb.like(cb.upper(e.<String> get("body")), payload.getBody().toUpperCase() + "%"));
		if (payload.getValidity() != null)
			predicates.add(cb.greaterThanOrEqualTo(e.<Date> get("validity"), payload.getValidity()));
		getByUser.where(cb.and(predicates.toArray(new Predicate[] {})));
		return entityManager.createQuery(getByUser).setFirstResult(offset).setMaxResults(limit).getResultList();

	}

}
