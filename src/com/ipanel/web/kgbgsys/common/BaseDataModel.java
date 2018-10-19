package com.ipanel.web.kgbgsys.common;


public class BaseDataModel {

	private Integer page;
	private Integer rows;

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public static class ResponseDataModel {

		public final static boolean SUCCESS = true;
		public final static boolean NOT_SUCCESS = false;
		public static ResponseDataModel RESPONSE_ERROR_DATA_MODEL;
		public static ResponseDataModel RESPONSE_SUCCESS_DATA_MODEL;
		public static ResponseDataModel RESPONSE_ADD_TASK_SUCCESS_DATA_MODEL;
		static {
			RESPONSE_ERROR_DATA_MODEL = new ResponseDataModel(NOT_SUCCESS,
					"操作异常！");
			RESPONSE_SUCCESS_DATA_MODEL = new ResponseDataModel(SUCCESS,
					"执行成功!");
			
			RESPONSE_ADD_TASK_SUCCESS_DATA_MODEL = new ResponseDataModel(SUCCESS,
			"成功添加任务!");
		}

		private boolean success;

		private String msg;

		private Object obj;

		public ResponseDataModel(boolean success, String msg) {
			this.success = success;
			this.msg = msg;
		}

		public ResponseDataModel(boolean success, Object obj) {
			this.success = success;
			this.obj = obj;
			if (success)
				msg = "执行成功!";
			else
				msg = "操作异常！";
		}

		public boolean isSuccess() {
			return success;
		}

		public void setSuccess(boolean success) {
			this.success = success;
		}

		public String getMsg() {
			return msg;
		}

		public void setMsg(String msg) {
			this.msg = msg;
		}

		public Object getObj() {
			return obj;
		}

		public void setObj(Object obj) {
			this.obj = obj;
		}
	}

}
