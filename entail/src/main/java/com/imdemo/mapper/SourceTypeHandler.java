package com.imdemo.mapper;

import com.imdemo.model.Source;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SourceTypeHandler extends BaseTypeHandler<Source> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Source parameter, JdbcType jdbcType) throws SQLException {
        ps.setByte(i, parameter.getValue());
    }

    @Override
    public Source getNullableResult(ResultSet rs, String columnName) throws SQLException {
        byte value = rs.getByte(columnName);
        return Source.fromValue(value);
    }

    @Override
    public Source getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        byte value = rs.getByte(columnIndex);
        return Source.fromValue(value);
    }

    @Override
    public Source getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return null;
    }
}
