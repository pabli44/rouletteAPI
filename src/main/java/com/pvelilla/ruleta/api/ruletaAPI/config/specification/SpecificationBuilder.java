package com.pvelilla.ruleta.api.ruletaAPI.config.specification;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

public class SpecificationBuilder<T> {
	private Map<String, Object> paramSpec;
	private List<Filter> filters;
	private List<OrderSb> orders;

	public SpecificationBuilder(Map<String, Object> paramSpec) {
		this.paramSpec = paramSpec;
		this.filters = new ArrayList<>();
		this.orders = new ArrayList<>();
	}

	public SpecificationBuilder<T> conjunctionLike(String prop, String param) {
		filters.add(new Filter(prop, param, TypeFilter.LIKE, true));
		return this;
	}

	public SpecificationBuilder<T> conjunctionEquals(String prop, String param) {
		filters.add(new Filter(prop, param, TypeFilter.EQUALS, true));
		return this;
	}

	public SpecificationBuilder<T> conjunctionIn(String prop, String param) {
		filters.add(new Filter(prop, param, TypeFilter.IN, true));
		return this;
	}

	public SpecificationBuilder<T> disjunctionLike(String prop, String param) {
		filters.add(new Filter(prop, param, TypeFilter.LIKE, false));
		return this;
	}

	public SpecificationBuilder<T> disjunctionEquals(String prop, String param) {
		filters.add(new Filter(prop, param, TypeFilter.EQUALS, false));
		return this;
	}

	public SpecificationBuilder<T> orderBy(String prop, boolean asc) {
		orders.add(new OrderSb(prop, asc));
		return this;
	}

	public Specification<T> build() {

		return new Specification<T>() {

			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicatesAnd = new ArrayList<>();
				List<Predicate> predicatesOr = new ArrayList<>();
				filters.forEach(action -> {
					if (action.typeFilter == TypeFilter.LIKE && action.conjunction
							&& paramSpec.get(action.param) != null) {
						predicatesAnd.add(criteriaBuilder.like(criteriaBuilder.lower(getPath(root, action.prop)),
								"%" + paramSpec.get(action.param).toString().toLowerCase() + "%"));
					} else if (action.typeFilter == TypeFilter.LIKE && !action.conjunction
							&& paramSpec.get(action.param) != null) {
						predicatesOr.add(criteriaBuilder.like(criteriaBuilder.lower(getPath(root, action.prop)),
								"%" + paramSpec.get(action.param).toString().toLowerCase() + "%"));
					} else if (action.typeFilter == TypeFilter.EQUALS && action.conjunction
							&& paramSpec.get(action.param) != null) {
						predicatesAnd
								.add(criteriaBuilder.equal(getPath(root, action.prop), paramSpec.get(action.param)));
					} else if (action.typeFilter == TypeFilter.EQUALS && !action.conjunction
							&& paramSpec.get(action.param) != null) {
						predicatesOr
								.add(criteriaBuilder.equal(getPath(root, action.prop), paramSpec.get(action.param)));
					} else if (action.typeFilter == TypeFilter.IN && paramSpec.get(action.param) != null) {
						predicatesAnd.add(getPath(root, action.prop).in((Collection<?>) paramSpec.get(action.param)));
					}
				});
				query.orderBy(orders.stream().map(mapper -> getOrder(mapper, criteriaBuilder, root))
						.collect(Collectors.toList()));
				if (!predicatesAnd.isEmpty() && !predicatesOr.isEmpty()) {
					predicatesAnd.add(criteriaBuilder.or(predicatesOr.toArray(new Predicate[0])));
					return criteriaBuilder.and(predicatesAnd.toArray(new Predicate[0]));
				} else if (!predicatesOr.isEmpty()) {
					return criteriaBuilder.or(predicatesOr.toArray(new Predicate[0]));
				} else {
					return criteriaBuilder.and(predicatesAnd.toArray(new Predicate[0]));
				}

			}
		};
	}

	private javax.persistence.criteria.Order getOrder(OrderSb order, CriteriaBuilder criteriaBuilder, Root<T> root) {
		return order.asc ? criteriaBuilder.asc(getPath(root, order.prop))
				: criteriaBuilder.desc(getPath(root, order.prop));
	}

	private <X> Path<X> getPath(Root<T> root, String prop) {
		StringTokenizer st = new StringTokenizer(prop, "[]");
		Path<X> result = null;
		while (st.hasMoreTokens()) {
			if (result != null) {
				result = result.get(st.nextToken());
			} else {
				result = root.get(st.nextToken());
			}
		}
		return result;
	}

	public enum TypeFilter {
		LIKE, EQUALS, IN
	}

	private class Filter {
		private String prop;
		private String param;
		private TypeFilter typeFilter;
		private boolean conjunction;

		public Filter(String prop, String param, TypeFilter typeFilter, boolean conjunction) {
			this.prop = prop;
			this.param = param;
			this.typeFilter = typeFilter;
			this.conjunction = conjunction;
		}

	}

	private class OrderSb {
		private String prop;
		private boolean asc;

		public OrderSb(String prop, boolean asc) {
			this.prop = prop;
			this.asc = asc;
		}
	}
}
