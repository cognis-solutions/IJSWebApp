package com.rclgroup.dolphin.ijs.web.common;

import com.rclgroup.dolphin.ijs.web.exception.CustomDataAccessException;

import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;

public class IjsStandardSQLErrorCodesTranslator extends SQLErrorCodeSQLExceptionTranslator{
    protected DataAccessException customTranslate(String task, String sql, SQLException sqlex) {
        if (sqlex.getErrorCode() == 20001) {
            String errorMessage = sqlex.getMessage().toString();
            String customErrorString = errorMessage.substring(errorMessage.indexOf("ORA-")+11,errorMessage.indexOf("\n")).trim();

            return new CustomDataAccessException(customErrorString,sqlex); 
        }
        return null;
    }
}
