package com.ipanel.web.kgbgsys.pojo;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.alibaba.fastjson.annotation.JSONField;

@Entity
@Table(name = "department_list")
public class DepartmentList implements java.io.Serializable {

	private Integer id;
	
	@JSONField(name = "Id")
	private Integer departId;
	
	@JSONField(name = "Name")
	private String departName;
	
	@JSONField(name = "Person")
	private String departPerson;
	
	@JSONField(name = "Beds")
	private String departBeds;
	
	@JSONField(name = "location")
	private String departLocation;
	
	@JSONField(name = "Tel")
	private String departTel;
	
	@JSONField(name = "Email")
	private String departEmail;
	
	@JSONField(name = "Duties")
	private String departDuties;
	
	@JSONField(name = "Status")
	private String departStatus;
	
	@JSONField(name = "Technology")
	private String departTechnology;
	
	@JSONField(name = "Research")
	private String departResearch;
	
	@JSONField(name = "Features")
	private String departFeatures;
	
	@JSONField(name = "Training")
	private String departTraining;
	
	@JSONField(name = "Sort")
	private String departSort;
	
	@JSONField(name = "Addtime")
	private String departAddtime;
	
	@JSONField(name = "OrderBy")
	private String departOrderBy;
	
	@JSONField(name = "EditTime")
	private String departEdittime;
	
	@JSONField(name = "Guid")
	private String departGuid;
	
	@JSONField(name = "Pic")
	private String departPic;
	
	@JSONField(name = "State")
	private String departState;
	
	@JSONField(name = "bqdz")
	private String departBqdz;
	
	private Integer hospitalId;
	
	public DepartmentList() {
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

	@Column(name = "depart_id", nullable = false)
	public Integer getDepartId() {
		return departId;
	}

	public void setDepartId(Integer departId) {
		this.departId = departId;
	}

	@Column(name = "depart_name")
	public String getDepartName() {
		return departName;
	}

	public void setDepartName(String departName) {
		this.departName = departName;
	}

	@Column(name = "depart_person")
	public String getDepartPerson() {
		return departPerson;
	}

	public void setDepartPerson(String departPerson) {
		this.departPerson = departPerson;
	}

	@Column(name = "depart_beds")
	public String getDepartBeds() {
		return departBeds;
	}

	public void setDepartBeds(String departBeds) {
		this.departBeds = departBeds;
	}

	@Column(name = "depart_location")
	public String getDepartLocation() {
		return departLocation;
	}

	public void setDepartLocation(String departLocation) {
		this.departLocation = departLocation;
	}

	@Column(name = "depart_tel")
	public String getDepartTel() {
		return departTel;
	}

	public void setDepartTel(String departTel) {
		this.departTel = departTel;
	}

	@Column(name = "depart_email")
	public String getDepartEmail() {
		return departEmail;
	}

	public void setDepartEmail(String departEmail) {
		this.departEmail = departEmail;
	}

	@Column(name = "depart_duties")
	public String getDepartDuties() {
		return departDuties;
	}

	public void setDepartDuties(String departDuties) {
		this.departDuties = departDuties;
	}

	@Column(name = "depart_status")
	public String getDepartStatus() {
		return departStatus;
	}

	public void setDepartStatus(String departStatus) {
		this.departStatus = departStatus;
	}

	@Column(name = "depart_technology")
	public String getDepartTechnology() {
		return departTechnology;
	}

	public void setDepartTechnology(String departTechnology) {
		this.departTechnology = departTechnology;
	}

	@Column(name = "depart_research")
	public String getDepartResearch() {
		return departResearch;
	}

	public void setDepartResearch(String departResearch) {
		this.departResearch = departResearch;
	}

	@Column(name = "depart_features")
	public String getDepartFeatures() {
		return departFeatures;
	}

	public void setDepartFeatures(String departFeatures) {
		this.departFeatures = departFeatures;
	}

	@Column(name = "depart_training")
	public String getDepartTraining() {
		return departTraining;
	}

	public void setDepartTraining(String departTraining) {
		this.departTraining = departTraining;
	}

	@Column(name = "depart_sort")
	public String getDepartSort() {
		return departSort;
	}

	public void setDepartSort(String departSort) {
		this.departSort = departSort;
	}

	@Column(name = "depart_addtime")
	public String getDepartAddtime() {
		return departAddtime;
	}

	public void setDepartAddtime(String departAddtime) {
		this.departAddtime = departAddtime;
	}

	@Column(name = "depart_order_by")
	public String getDepartOrderBy() {
		return departOrderBy;
	}

	public void setDepartOrderBy(String departOrderBy) {
		this.departOrderBy = departOrderBy;
	}

	@Column(name = "depart_edittime")
	public String getDepartEdittime() {
		return departEdittime;
	}

	public void setDepartEdittime(String departEdittime) {
		this.departEdittime = departEdittime;
	}

	@Column(name = "depart_guid")
	public String getDepartGuid() {
		return departGuid;
	}

	public void setDepartGuid(String departGuid) {
		this.departGuid = departGuid;
	}

	@Column(name = "depart_pic")
	public String getDepartPic() {
		return departPic;
	}

	public void setDepartPic(String departPic) {
		this.departPic = departPic;
	}

	@Column(name = "depart_state")
	public String getDepartState() {
		return departState;
	}

	public void setDepartState(String departState) {
		this.departState = departState;
	}

	@Column(name = "depart_bqdz")
	public String getDepartBqdz() {
		return departBqdz;
	}

	public void setDepartBqdz(String departBqdz) {
		this.departBqdz = departBqdz;
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
		return "DepartmentList [id=" + id + ", departId=" + departId
				+ ", departName=" + departName + ", departPerson="
				+ departPerson + ", departBeds=" + departBeds
				+ ", departLocation=" + departLocation + ", departTel="
				+ departTel + ", departEmail=" + departEmail
				+ ", departDuties=" + departDuties + ", departStatus="
				+ departStatus + ", departTechnology=" + departTechnology
				+ ", departResearch=" + departResearch + ", departFeatures="
				+ departFeatures + ", departTraining=" + departTraining
				+ ", departSort=" + departSort + ", departAddtime="
				+ departAddtime + ", departOrderBy=" + departOrderBy
				+ ", departEdittime=" + departEdittime + ", departGuid="
				+ departGuid + ", departPic=" + departPic + ", departState="
				+ departState + ", departBqdz=" + departBqdz + ", hospitalId="
				+ hospitalId + "]";
	}
	
	
	
}
