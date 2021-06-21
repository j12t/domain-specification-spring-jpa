package com.j12t.domain.specification.jpa.converter.impl;

import java.lang.reflect.Field;

import javax.persistence.Column;

import org.springframework.data.jpa.domain.Specification;

import com.j12t.domain.specification.jpa.converter.SpringJpaMappingSpecification;
import com.j12t.domain.specification.jpa.converter.SpringJpaSpecification;
import com.jt93.domain.specification.impl.ContainsSpecification;

public class SpringJpaContainsSpecification<E> extends SpringJpaSpecification<E, ContainsSpecification<?>> {

	private final String wildcard = "%";

	public SpringJpaContainsSpecification(ContainsSpecification<?> domainSpecification, E entity) {
		super(domainSpecification, entity);
	}

	@Override
	public Specification<E> filter() {

		// find which field of Entity contains the specification name in its name, or on its annotation
		Field[] fields = this.getEntity().getClass().getDeclaredFields();

		for (Field field : fields) {

			// the name of the field is the same as the name of specification
			if (field.getName().equalsIgnoreCase(getDomainSpecification().getName())) {
				return attributeContains(getColumnName(field), getDomainSpecification().getValue());
			}

			// the annotation name of the field is the same as the name of specification
			SpringJpaMappingSpecification annotation = field.getAnnotation(SpringJpaMappingSpecification.class);
			if (annotation != null && annotation.name().equalsIgnoreCase(getDomainSpecification().getName())) {
				return attributeContains(getColumnName(field), getDomainSpecification().getValue());
			}
		}
		// TODO J12T : bizarre de renvoyer null... meme si ca doit passer avant dans un return
		return null;
	}

	private String containsLowerCase(String searchField) {
		return wildcard + searchField.toLowerCase() + wildcard;
	}

	private Specification<E> attributeContains(String attribute, String value) {
		return (root, query, cb) -> {
			if (value == null) {
				return null;
			}

			return cb.like(cb.lower(root.get(attribute)), containsLowerCase(value));
		};
	}

	/**
	 * Find the column name for a field of an Entity
	 * 
	 * @param aField
	 * @return
	 */
	// TODO J12T verifier si JPA ne fait pas le taf avec juste le nom de l'ettribut meme s'il est surchargé par une annotation @column
	private String getColumnName(Field aField) {

		Column column = aField.getAnnotation(Column.class);
		if (column != null) {
			return column.name();
		}
		return aField.getName();
	}

}
