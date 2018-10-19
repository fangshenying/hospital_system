package com.ipanel.web.kgbgsys.utils;

import org.hibernate.dialect.MySQLDialect;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.type.StringType;

public class MySQLLocalDialect extends MySQLDialect {

	public MySQLLocalDialect() {
		super();
		registerFunction("convert", new SQLFunctionTemplate(StringType.INSTANCE, "convert(?1 using ?2)"));
	}

}