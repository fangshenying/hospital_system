package com.ipanel.web.kgbgsys.pojo;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.alibaba.fastjson.annotation.JSONField;

@Entity
@Table(name = "department_doctor")
public class DepartmentDoctor implements java.io.Serializable {

	private Integer id;
	
	@JSONField(name = "Id")
	private Integer doctorId;
	
	@JSONField(name = "Name")
	private String doctorName;
	
	@JSONField(name = "Sex")
	private String doctorSex;
	
	@JSONField(name = "Tel")
	private String doctorTel;
	
	@JSONField(name = "Education")
	private String doctorEducation;
	
	@JSONField(name = "Title")
	private String doctorTitle;
	
	@JSONField(name = "Sort")
	private String doctorSort;
	
	@JSONField(name = "Subjects")
	private String doctorSubjects;
	
	@JSONField(name = "Specializes")
	private String doctorSpecializes;
	
	@JSONField(name = "PatientTime")
	private String doctorPatientTime;
	
	@JSONField(name = "ParientAddress")
	private String doctorParientAddress;
	
	@JSONField(name = "Max")
	private String doctorMax;
	
	@JSONField(name = "Money")
	private String doctorMoney;
	
	@JSONField(name = "CV")
	private String doctorCV;
	
	@JSONField(name = "Positions")
	private String doctorPositions;
	
	@JSONField(name = "Research")
	private String doctorResearch;
	
	@JSONField(name = "Results")
	private String doctorResults;
	
	@JSONField(name = "Addtime")
	private String doctorAddtime;
	
	@JSONField(name = "Pic")
	private String doctorPic;
	
	@JSONField(name = "State")
	private String doctorState;
	
	@JSONField(name = "OrderBy")
	private String doctorOrderBy;
	
	@JSONField(name = "EditTime")
	private String doctorEditTime;
	
	@JSONField(name = "Guid")
	private String doctorGuid;
	
	private Integer hospitalId;
	
	public DepartmentDoctor() {
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

	@Column(name = "doctor_id", nullable = false)
	public Integer getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(Integer doctorId) {
		this.doctorId = doctorId;
	}

	@Column(name = "doctor_name")
	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	@Column(name = "doctor_sex", length = 2)
	public String getDoctorSex() {
		return doctorSex;
	}

	public void setDoctorSex(String doctorSex) {
		this.doctorSex = doctorSex;
	}

	@Column(name = "doctor_tel")
	public String getDoctorTel() {
		return doctorTel;
	}

	public void setDoctorTel(String doctorTel) {
		this.doctorTel = doctorTel;
	}

	@Column(name = "doctor_education")
	public String getDoctorEducation() {
		return doctorEducation;
	}

	public void setDoctorEducation(String doctorEducation) {
		this.doctorEducation = doctorEducation;
	}

	@Column(name = "doctor_title")
	public String getDoctorTitle() {
		return doctorTitle;
	}

	public void setDoctorTitle(String doctorTitle) {
		this.doctorTitle = doctorTitle;
	}

	@Column(name = "doctor_sort")
	public String getDoctorSort() {
		return doctorSort;
	}

	public void setDoctorSort(String doctorSort) {
		this.doctorSort = doctorSort;
	}

	@Column(name = "doctor_subjects")
	public String getDoctorSubjects() {
		return doctorSubjects;
	}

	public void setDoctorSubjects(String doctorSubjects) {
		this.doctorSubjects = doctorSubjects;
	}

	@Column(name = "doctor_specializes")
	public String getDoctorSpecializes() {
		return doctorSpecializes;
	}

	public void setDoctorSpecializes(String doctorSpecializes) {
		this.doctorSpecializes = doctorSpecializes;
	}

	@Column(name = "doctor_patient_time")
	public String getDoctorPatientTime() {
		return doctorPatientTime;
	}

	public void setDoctorPatientTime(String doctorPatientTime) {
		this.doctorPatientTime = doctorPatientTime;
	}

	@Column(name = "doctor_parient_address")
	public String getDoctorParientAddress() {
		return doctorParientAddress;
	}

	public void setDoctorParientAddress(String doctorParientAddress) {
		this.doctorParientAddress = doctorParientAddress;
	}

	@Column(name = "doctor_max")
	public String getDoctorMax() {
		return doctorMax;
	}

	public void setDoctorMax(String doctorMax) {
		this.doctorMax = doctorMax;
	}

	@Column(name = "doctor_money")
	public String getDoctorMoney() {
		return doctorMoney;
	}

	public void setDoctorMoney(String doctorMoney) {
		this.doctorMoney = doctorMoney;
	}

	@Column(name = "doctor_cv")
	public String getDoctorCV() {
		return doctorCV;
	}

	public void setDoctorCV(String doctorCV) {
		this.doctorCV = doctorCV;
	}

	@Column(name = "doctor_positions")
	public String getDoctorPositions() {
		return doctorPositions;
	}

	public void setDoctorPositions(String doctorPositions) {
		this.doctorPositions = doctorPositions;
	}

	@Column(name = "doctor_research")
	public String getDoctorResearch() {
		return doctorResearch;
	}

	public void setDoctorResearch(String doctorResearch) {
		this.doctorResearch = doctorResearch;
	}

	@Column(name = "doctor_results")
	public String getDoctorResults() {
		return doctorResults;
	}

	public void setDoctorResults(String doctorResults) {
		this.doctorResults = doctorResults;
	}

	@Column(name = "doctor_addtime")
	public String getDoctorAddtime() {
		return doctorAddtime;
	}

	public void setDoctorAddtime(String doctorAddtime) {
		this.doctorAddtime = doctorAddtime;
	}

	@Column(name = "doctor_pic")
	public String getDoctorPic() {
		return doctorPic;
	}

	public void setDoctorPic(String doctorPic) {
		this.doctorPic = doctorPic;
	}

	@Column(name = "doctor_state")
	public String getDoctorState() {
		return doctorState;
	}

	public void setDoctorState(String doctorState) {
		this.doctorState = doctorState;
	}

	@Column(name = "doctor_order_by")
	public String getDoctorOrderBy() {
		return doctorOrderBy;
	}

	public void setDoctorOrderBy(String doctorOrderBy) {
		this.doctorOrderBy = doctorOrderBy;
	}

	@Column(name = "doctor_edittime")
	public String getDoctorEditTime() {
		return doctorEditTime;
	}

	public void setDoctorEditTime(String doctorEditTime) {
		this.doctorEditTime = doctorEditTime;
	}

	@Column(name = "doctor_guid")
	public String getDoctorGuid() {
		return doctorGuid;
	}

	public void setDoctorGuid(String doctorGuid) {
		this.doctorGuid = doctorGuid;
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
		return "DepartmentDoctor [id=" + id + ", doctorId=" + doctorId
				+ ", doctorName=" + doctorName + ", doctorSex=" + doctorSex
				+ ", doctorTel=" + doctorTel + ", doctorEducation="
				+ doctorEducation + ", doctorTitle=" + doctorTitle
				+ ", doctorSort=" + doctorSort + ", doctorSubjects="
				+ doctorSubjects + ", doctorSpecializes=" + doctorSpecializes
				+ ", doctorPatientTime=" + doctorPatientTime
				+ ", doctorParientAddress=" + doctorParientAddress
				+ ", doctorMax=" + doctorMax + ", doctorMoney=" + doctorMoney
				+ ", doctorCV=" + doctorCV + ", doctorPositions="
				+ doctorPositions + ", doctorResearch=" + doctorResearch
				+ ", doctorResults=" + doctorResults + ", doctorAddtime="
				+ doctorAddtime + ", doctorPic=" + doctorPic + ", doctorState="
				+ doctorState + ", doctorOrderBy=" + doctorOrderBy
				+ ", doctorEditTime=" + doctorEditTime + ", doctorGuid="
				+ doctorGuid + ", hospitalId=" + hospitalId + "]";
	}

	
}
