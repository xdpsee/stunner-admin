package com.cherry.stunner.data.service.impl.type;

import com.cherry.stunner.data.enums.AlbumStatus;
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
public class AlbumStatusTypeHandler extends BaseTypeHandler<AlbumStatus> {

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, AlbumStatus albumStatus, JdbcType jdbcType) throws SQLException {
        preparedStatement.setInt(i, albumStatus.code);
    }

    @Override
    public AlbumStatus getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return AlbumStatus.valueOf(resultSet.getInt(s));
    }

    @Override
    public AlbumStatus getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return AlbumStatus.valueOf(resultSet.getInt(i));
    }

    @Override
    public AlbumStatus getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return AlbumStatus.valueOf(callableStatement.getInt(i));

    }
}
