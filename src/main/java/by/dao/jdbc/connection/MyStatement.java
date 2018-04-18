package by.dao.jdbc.connection;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.Calendar;

class MyStatement implements PreparedStatement {

    private PreparedStatement statement;

    MyStatement(PreparedStatement statement) {
        this.statement = statement;
    }

    @Override
    public void close() {
        return;
    }

    @Override
    public ResultSet executeQuery() throws SQLException {
        return statement.executeQuery();
    }

    @Override
    public int executeUpdate() throws SQLException {
        return statement.executeUpdate();
    }

    @Override
    public ResultSetMetaData getMetaData() throws SQLException {
        return statement.getMetaData();
    }

    @Override
    public ParameterMetaData getParameterMetaData()
            throws SQLException {
        return statement.getParameterMetaData();
    }

    @Override
    public void setArray(int parameterIndex, Array x)
            throws SQLException {
        statement.setArray(parameterIndex, x);
    }

    @Override
    public void setAsciiStream(int parameterIndex, InputStream x)
            throws SQLException {
        statement.setAsciiStream(parameterIndex, x);
    }

    @Override
    public void setAsciiStream(int parameterIndex, InputStream x, int length)
            throws SQLException {
        statement.setAsciiStream(parameterIndex, x, length);
    }

    @Override
    public void setAsciiStream(int parameterIndex, InputStream x, long length)
            throws SQLException {
        statement.setAsciiStream(parameterIndex, x, length);
    }

    @Override
    public void setBigDecimal(int parameterIndex, BigDecimal x)
            throws SQLException {
        statement.setBigDecimal(parameterIndex, x);
    }

    @Override
    public void setBinaryStream(int parameterIndex, InputStream x)
            throws SQLException {
        statement.setBinaryStream(parameterIndex, x);
    }

    @Override
    public void setBinaryStream(int parameterIndex, InputStream x, int length)
            throws SQLException {
        statement.setBinaryStream(parameterIndex, x, length);
    }

    @Override
    public void clearParameters() throws SQLException {
        statement.clearParameters();
    }

    @Override
    public void setBinaryStream(int parameterIndex, InputStream x, long length)
            throws SQLException {
        statement.setBinaryStream(parameterIndex, x, length);
    }

    @Override
    public void setBlob(int parameterIndex, Blob x)
            throws SQLException {
        statement.setBlob(parameterIndex, x);
    }

    @Override
    public void setBlob(int parameterIndex, InputStream inputStream)
            throws SQLException {
        statement.setBlob(parameterIndex, inputStream);
    }

    @Override
    public void setBlob(int parameterIndex, InputStream inputStream, long length)
            throws SQLException {
        statement.setBlob(parameterIndex, inputStream, length);
    }

    @Override
    public void setBoolean(int parameterIndex, boolean x)
            throws SQLException {
        statement.setBoolean(parameterIndex, x);
    }

    @Override
    public void setByte(int parameterIndex, byte x)
            throws SQLException {
        statement.setByte(parameterIndex, x);
    }

    @Override
    public void setBytes(int parameterIndex, byte[] x)
            throws SQLException {
        statement.setBytes(parameterIndex, x);
    }

    @Override
    public void setCharacterStream(int parameterIndex, Reader reader)
            throws SQLException {
        statement.setCharacterStream(parameterIndex, reader);
    }

    @Override
    public void setCharacterStream(int parameterIndex, Reader reader, int length)
            throws SQLException {
        statement.setCharacterStream(parameterIndex, reader, length);
    }

    @Override
    public void setCharacterStream(int parameterIndex, Reader reader, long length)
            throws SQLException {
        statement.setCharacterStream(parameterIndex, reader, length);
    }

    @Override
    public void setClob(int parameterIndex, Clob x)
            throws SQLException {
        statement.setClob(parameterIndex, x);
    }

    @Override
    public void setClob(int parameterIndex, Reader reader)
            throws SQLException {
        statement.setClob(parameterIndex, reader);
    }

    @Override
    public void setClob(int parameterIndex, Reader reader, long length)
            throws SQLException {
        statement.setClob(parameterIndex, reader, length);
    }

    @Override
    public void setDate(int parameterIndex, Date x)
            throws SQLException {
        statement.setDate(parameterIndex, x);
    }

    @Override
    public void setDate(int parameterIndex, Date x, Calendar cal)
            throws SQLException {
        statement.setDate(parameterIndex, x, cal);
    }

    @Override
    public void setDouble(int parameterIndex, double x)
            throws SQLException {
        statement.setDouble(parameterIndex, x);
    }

    @Override
    public void setFloat(int parameterIndex, float x)
            throws SQLException {
        statement.setFloat(parameterIndex, x);
    }

    @Override
    public void setInt(int parameterIndex, int x)
            throws SQLException {
        statement.setInt(parameterIndex, x);
    }

    @Override
    public void setLong(int parameterIndex, long x)
            throws SQLException {
        statement.setLong(parameterIndex, x);
    }

    @Override
    public void setNCharacterStream(int parameterIndex, Reader value)
            throws SQLException {
        statement.setNCharacterStream(parameterIndex, value);
    }

    @Override
    public void setNCharacterStream(int parameterIndex, Reader value, long length)
            throws SQLException {
        statement.setNCharacterStream(parameterIndex, value, length);
    }

    @Override
    public void setNClob(int parameterIndex, NClob value)
            throws SQLException {
        statement.setNClob(parameterIndex, value);
    }

    @Override
    public void setNClob(int parameterIndex, Reader reader)
            throws SQLException {
        statement.setNClob(parameterIndex, reader);
    }

    @Override
    public void setNClob(int parameterIndex, Reader reader, long length)
            throws SQLException {
        statement.setNClob(parameterIndex, reader, length);
    }

    @Override
    public void setNString(int parameterIndex, String value)
            throws SQLException {
        statement.setNString(parameterIndex, value);
    }

    @Override
    public void setNull(int parameterIndex, int sqlType)
            throws SQLException {
        statement.setNull(parameterIndex, sqlType);
    }

    @Override
    public void setNull(int parameterIndex, int sqlType, String typeName)
            throws SQLException {
        statement.setNull(parameterIndex, sqlType, typeName);
    }

    @Override
    public void setObject(int parameterIndex, Object x)
            throws SQLException {
        statement.setObject(parameterIndex, x);
    }

    @Override
    public boolean execute() throws SQLException {
        return statement.execute();
    }

    @Override
    public void addBatch() throws SQLException {
        statement.addBatch();
    }

    @Override
    public void setObject(int parameterIndex, Object x, int targetSqlType)
            throws SQLException {
        statement.setObject(parameterIndex, x, targetSqlType);
    }

    @Override
    public void setObject(int parameterIndex, Object x, int targetSqlType, int scaleOrLength)
            throws SQLException {
        statement.setObject(parameterIndex, x, targetSqlType, scaleOrLength);
    }

    @Override
    public void setRef(int parameterIndex, Ref x)
            throws SQLException {
        statement.setRef(parameterIndex, x);
    }

    @Override
    public void setRowId(int parameterIndex, RowId x)
            throws SQLException {
        statement.setRowId(parameterIndex, x);
    }

    @Override
    public void setShort(int parameterIndex, short x)
            throws SQLException {
        statement.setShort(parameterIndex, x);
    }

    @Override
    public void setSQLXML(int parameterIndex, SQLXML xmlObject)
            throws SQLException {
        statement.setSQLXML(parameterIndex, xmlObject);
    }

    @Override
    public void setString(int parameterIndex, String x)
            throws SQLException {
        statement.setString(parameterIndex, x);
    }

    @Override
    public void setTime(int parameterIndex, Time x)
            throws SQLException {
        statement.setTime(parameterIndex, x);
    }

    @Override
    public void setTime(int parameterIndex, Time x, Calendar cal)
            throws SQLException {
        statement.setTime(parameterIndex, x, cal);
    }

    @Override
    public void setTimestamp(int parameterIndex, Timestamp x)
            throws SQLException {
        statement.setTimestamp(parameterIndex, x);
    }

    @Override
    public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal)
            throws SQLException {
        statement.setTimestamp(parameterIndex, x, cal);
    }

    @Override
    public void setUnicodeStream(int parameterIndex, InputStream x, int length)
            throws SQLException {
        statement.setUnicodeStream(parameterIndex, x, length);
    }

    @Override
    public void setURL(int parameterIndex, URL x)
            throws SQLException {
        statement.setURL(parameterIndex, x);
    }

    @Override
    public void addBatch(String sql)
            throws SQLException {
        statement.addBatch(sql);
    }

    @Override
    public void cancel()
            throws SQLException {
        statement.cancel();
    }

    @Override
    public void clearBatch()
            throws SQLException {
        statement.clearBatch();
    }

    @Override
    public void clearWarnings()
            throws SQLException {
        statement.clearWarnings();
    }

    @Override
    public boolean execute(String sql)
            throws SQLException {
        return statement.execute(sql);
    }

    @Override
    public boolean execute(String sql, int autoGeneratedKeys)
            throws SQLException {
        return statement.execute(sql, autoGeneratedKeys);
    }

    @Override
    public boolean execute(String sql, int[] columnIndexes)
            throws SQLException {
        return statement.execute(sql, columnIndexes);
    }

    @Override
    public boolean execute(String sql, String[] columnNames)
            throws SQLException {
        return statement.execute(sql, columnNames);
    }

    @Override
    public int[] executeBatch()
            throws SQLException {
        return statement.executeBatch();
    }

    @Override
    public ResultSet executeQuery(String sql)
            throws SQLException {
        return statement.executeQuery(sql);
    }

    @Override
    public int executeUpdate(String sql)
            throws SQLException {
        return statement.executeUpdate(sql);
    }

    @Override
    public int executeUpdate(String sql, int autoGeneratedKeys)
            throws SQLException {
        return statement.executeUpdate(sql, autoGeneratedKeys);
    }

    @Override
    public int executeUpdate(String sql, int[] columnIndexes)
            throws SQLException {
        return statement.executeUpdate(sql, columnIndexes);
    }

    @Override
    public int executeUpdate(String sql, String[] columnNames)
            throws SQLException {
        return statement.executeUpdate(sql, columnNames);
    }

    @Override
    public Connection getConnection()
            throws SQLException {
        return statement.getConnection();
    }

    @Override
    public int getFetchDirection() throws SQLException {
        return statement.getFetchDirection();
    }

    @Override
    public void setFetchDirection(int direction)
            throws SQLException {
        statement.setFetchDirection(direction);
    }

    @Override
    public int getFetchSize()
            throws SQLException {
        return statement.getFetchSize();
    }

    @Override
    public void setFetchSize(int rows)
            throws SQLException {
        statement.setFetchSize(rows);
    }

    @Override
    public ResultSet getGeneratedKeys()
            throws SQLException {
        return statement.getGeneratedKeys();
    }

    @Override
    public int getMaxFieldSize()
            throws SQLException {
        return statement.getMaxFieldSize();
    }

    @Override
    public void setMaxFieldSize(int max)
            throws SQLException {
        statement.setMaxFieldSize(max);
    }

    @Override
    public int getMaxRows()
            throws SQLException {
        return statement.getMaxRows();
    }

    @Override
    public void setMaxRows(int max)
            throws SQLException {
        statement.setMaxRows(max);
    }

    @Override
    public boolean getMoreResults()
            throws SQLException {
        return statement.getMoreResults();
    }

    @Override
    public boolean getMoreResults(int current)
            throws SQLException {
        return statement.getMoreResults(current);
    }

    @Override
    public int getQueryTimeout()
            throws SQLException {
        return statement.getQueryTimeout();
    }

    @Override
    public void setQueryTimeout(int seconds)
            throws SQLException {
        statement.setQueryTimeout(seconds);
    }

    @Override
    public ResultSet getResultSet()
            throws SQLException {
        return statement.getResultSet();
    }

    @Override
    public int getResultSetConcurrency()
            throws SQLException {
        return statement.getResultSetConcurrency();
    }

    @Override
    public int getResultSetHoldability()
            throws SQLException {
        return statement.getResultSetHoldability();
    }

    @Override
    public int getResultSetType()
            throws SQLException {
        return statement.getResultSetType();
    }

    @Override
    public int getUpdateCount()
            throws SQLException {
        return statement.getUpdateCount();
    }

    @Override
    public SQLWarning getWarnings()
            throws SQLException {
        return statement.getWarnings();
    }

    @Override
    public boolean isClosed()
            throws SQLException {
        return statement.isClosed();
    }

    @Override
    public boolean isPoolable()
            throws SQLException {
        return statement.isPoolable();
    }

    @Override
    public void setPoolable(boolean poolable)
            throws SQLException {
        statement.setPoolable(poolable);
    }

    @Override
    public void closeOnCompletion() throws SQLException {
        statement.closeOnCompletion();
    }

    @Override
    public boolean isCloseOnCompletion() throws SQLException {
        return statement.isCloseOnCompletion();
    }

    @Override
    public void setCursorName(String name)
            throws SQLException {
        statement.setCursorName(name);
    }

    @Override
    public void setEscapeProcessing(boolean enable)
            throws SQLException {
        statement.setEscapeProcessing(enable);
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) {
        return iface.isInstance(statement);
    }

    @Override
    public <T> T unwrap(Class<T> iface) {
        return iface.cast(statement);
    }
}