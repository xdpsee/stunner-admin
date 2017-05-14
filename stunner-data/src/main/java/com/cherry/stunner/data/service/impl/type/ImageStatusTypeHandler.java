package com.cherry.stunner.data.service.impl.type;

import com.cherry.stunner.data.enums.ImageStatus;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Administrator on 2017/5/14 0014.
 */
@SuppressWarnings("unused")
public class ImageStatusTypeHandler extends BaseTypeHandler<ImageStatus> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, ImageStatus imageStatus, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, imageStatus.code);
    }

    @Override
    public ImageStatus getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return ImageStatus.valueOf(resultSet.getInt(s));
    }

    @Override
    public ImageStatus getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return ImageStatus.valueOf(resultSet.getInt(i));
    }

    @Override
    public ImageStatus getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return ImageStatus.valueOf(callableStatement.getInt(i));
    }
}
