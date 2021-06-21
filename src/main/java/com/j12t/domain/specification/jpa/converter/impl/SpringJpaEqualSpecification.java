package com.j12t.domain.specification.jpa.converter.impl;

import java.lang.reflect.Field;

import javax.persistence.Column;

import org.springframework.data.jpa.domain.Specification;

import com.j12t.domain.specification.jpa.converter.SpringJpaMappingSpecification;
import com.j12t.domain.specification.jpa.converter.SpringJpaSpecification;
import com.jt93.domain.specification.impl.EqualSpecification;

public class SpringJpaEqualSpecification<E> extends SpringJpaSpecification<E, EqualSpecification<?, ?>> {

	public SpringJpaEqualSpecification(EqualSpecification<?, ?> domainSpecification, E entity) {
		super(domainSpecification, entity);
	}

	@Override
	public Specification<E> filter() {

		// find which field of Entity contains the specification name in its name, or on its annotation
		Field[] fields = this.getEntity().getClass().getDeclaredFields();

		for (Field field : fields) {

			// the name of the field is the same as the name of specification
			if (field.getName().equalsIgnoreCase(getDomainSpecification().getName())) {
				return attributeEquals(getColumnName(field), getDomainSpecification().getValue());
			}

			// the annotation name of the field is the same as the name of specification
			SpringJpaMappingSpecification annotation = field.getAnnotation(SpringJpaMappingSpecification.class);
			if (annotation != null && annotation.name().equalsIgnoreCase(getDomainSpecification().getName())) {
				return attributeEquals(getColumnName(field), getDomainSpecification().getValue());
			}
		}
		// TODO J12T : bizarre de renvoyer null... meme si ca doit passer avant dans un return
		return null;
	}

	private Specification<E> attributeEquals(String attribute, Object value) {
		return (root, query, cb) -> {
			if (value == null) {
				return null;
			}

			return cb.equal(cb.lower(root.get(attribute)), value);
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
