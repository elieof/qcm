package com.clinkast.qcm.entities;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class AbstractEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Integer id;

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	// initialisation
	public AbstractEntity build(Integer id) {
		this.id = id;
		return this;
	}

	@Override
	public boolean equals(Object entity) {
		String class1 = this.getClass().getName();
		String class2 = entity.getClass().getName();
		if (!class2.equals(class1)) {
			return false;
		}
		AbstractEntity other = (AbstractEntity) entity;
		return this.id == other.id;
	}

	// getters et setters
	public Integer getId() {
		return id;
	}	

	public void setId(Integer id) {
		this.id = id;
	}
	
}
