package com.ipanel.web.kgbgsys.pojo;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.alibaba.fastjson.annotation.JSONField;

@Entity
@Table(name = "department_type")
public class DepartmentType implements java.io.Serializable {

	private Integer id;
	
	@JSONField(name = "Id")
	private Integer typeId;
	
	@JSONField(name = "Sort")
	private String typeSort;
	
	@JSONField(name = "Keys")
	private String typeKeys;
	
	@JSONField(name = "Addtime")
	private String typeAddtime;
	
	@JSONField(name = "Orderby")
	private String typeOrderBy;
	
	@JSONField(name = "Class")
	private String typeClass;
	
	@JSONField(name = "Target")
	private String typeTarget;
	
	@JSONField(name = "ArticleSort")
	private String typeArticleSort;
	
	@JSONField(name = "Url")
	private String typeUrl;
	
	private Integer hospitalId;
	
	public DepartmentType() {
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "type_id", nullable = false)
	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	@Column(name = "type_sort")
	public String getTypeSort() {
		return typeSort;
	}

	public void setTypeSort(String typeSort) {
		this.typeSort = typeSort;
	}

	@Column(name = "type_keys")
	public String getTypeKeys() {
		return typeKeys;
	}

	public void setTypeKeys(String typeKeys) {
		this.typeKeys = typeKeys;
	}

	@Column(name = "type_addtime")
	public String getTypeAddtime() {
		return typeAddtime;
	}

	public void setTypeAddtime(String typeAddtime) {
		this.typeAddtime = typeAddtime;
	}

	@Column(name = "type_order_by")
	public String getTypeOrderBy() {
		return typeOrderBy;
	}

	public void setTypeOrderBy(String typeOrderBy) {
		this.typeOrderBy = typeOrderBy;
	}

	@Column(name = "type_class")
	public String getTypeClass() {
		return typeClass;
	}

	public void setTypeClass(String typeClass) {
		this.typeClass = typeClass;
	}

	@Column(name = "type_target")
	public String getTypeTarget() {
		return typeTarget;
	}

	public void setTypeTarget(String typeTarget) {
		this.typeTarget = typeTarget;
	}
	
	@Column(name = "type_article_sort")
	public String getTypeArticleSort() {
		return typeArticleSort;
	}

	public void setTypeArticleSort(String typeArticleSort) {
		this.typeArticleSort = typeArticleSort;
	}

	@Column(name = "type_url")
	public String getTypeUrl() {
		return typeUrl;
	}

	public void setTypeUrl(String typeUrl) {
		this.typeUrl = typeUrl;
	}

	@Column(name = "hospital_id")
	public Integer getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(Integer hospitalId) {
		this.hospitalId = hospitalId;
	}

	@Override
	public String toString() {
		return "DepartmentType [id=" + id + ", typeId=" + typeId
				+ ", typeSort=" + typeSort + ", typeKeys=" + typeKeys
				+ ", typeAddtime=" + typeAddtime + ", typeOrderBy="
				+ typeOrderBy + ", typeClass=" + typeClass + ", typeTarget="
				+ typeTarget + ", typeArticleSort=" + typeArticleSort
				+ ", typeUrl=" + typeUrl + ", hospitalId=" + hospitalId + "]";
	}
	
	
}
