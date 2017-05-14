package com.cherry.stunner.data.service.impl.type;

import com.cherry.stunner.data.enums.CategoryStatus;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Administrator on 2017/5/14 0014.
 */
public class CategoryStatusTypeHandler extends BaseTypeHandler<CategoryStatus> {

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, CategoryStatus categoryStatus, JdbcType jdbcType) throws SQLException {
        preparedStatement.setInt(i, categoryStatus.code);
    }

    @Override
    public CategoryStatus getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return CategoryStatus.valueOf(resultSet.getInt(s));
    }

    @Override
    public CategoryStatus getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return CategoryStatus.valueOf(resultSet.getInt(i));
    }

    @Override
    public CategoryStatus getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return CategoryStatus.valueOf(callableStatement.getInt(i));
    }
}
