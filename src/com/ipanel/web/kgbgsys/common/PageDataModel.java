package com.ipanel.web.kgbgsys.common;

import java.util.List;

public class PageDataModel {
	
	private int total;
	
	private List<?> rows;
	
	public PageDataModel(int total,List<?> rows){
		this.total = total;
		this.rows = rows;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<?> getRows() {
		return rows;
	}

	public void setRows(List<?> rows) {
		this.rows = rows;
	}

	
}
