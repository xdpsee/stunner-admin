package com.cherry.stunner.data.service.impl.type;

import com.cherry.stunner.data.enums.TagType;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TagTypeHandler extends BaseTypeHandler<TagType> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, TagType parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.code);
    }

    @Override
    public TagType getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return TagType.valueOf(rs.getInt(columnName));
    }

    @Override
    public TagType getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return TagType.valueOf(rs.getInt(columnIndex));

    }

    @Override
    public TagType getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return TagType.valueOf(cs.getInt(columnIndex));

    }
}
