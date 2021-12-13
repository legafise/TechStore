package by.lashkevich.logic.dao.pool;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

class ConnectionPoolTest {

    @AfterEach
    void tearDown() {
        ConnectionPool.getInstance().closeConnections();
    }

    @Test
    void initializeConnectionPoolTest() {
        ConnectionPool.getInstance().initializeConnectionPool(2);
        Assert.assertEquals(2, ConnectionPool.getInstance().getFreeConnectionsSize());
    }

    @Test
    void checkFreeConnectionSizeAfterConnectionAcquiringTest() {
        ConnectionPool.getInstance().initializeConnectionPool(2);
        ConnectionPool.getInstance().acquireConnection();
        Assert.assertEquals(1, ConnectionPool.getInstance().getFreeConnectionsSize());
    }

    @Test
    void checkBusyConnectionSizeAfterConnectionAcquiringTest() {
        ConnectionPool.getInstance().initializeConnectionPool(2);
        ConnectionPool.getInstance().acquireConnection();
        Assert.assertEquals(1, ConnectionPool.getInstance().getBusyConnectionsSize());
    }

    @Test
    void acquireConnectionFromNonInitializePoolTest() {
        Assert.assertThrows(ConnectionPoolException.class, () -> ConnectionPool.getInstance().acquireConnection());
    }

    @Test
    void putBackConnectionTest() {
        ConnectionPool.getInstance().initializeConnectionPool(2);
        Connection connection = ConnectionPool.getInstance().acquireConnection();
        ConnectionPool.getInstance().putBackConnection(connection);
        Assert.assertEquals(2, ConnectionPool.getInstance().getFreeConnectionsSize());
    }

    @Test
    void putBackConnectionWithNullTest() {
        Assert.assertThrows(ConnectionPoolException.class, () -> ConnectionPool.getInstance().putBackConnection(null));
    }

    @Test
    void checkFreeConnectionsAfterConnectionClosingTest() {
        ConnectionPool.getInstance().closeConnections();
        Assert.assertEquals(0, ConnectionPool.getInstance().getFreeConnectionsSize());
    }

    @Test
    void checkBusyConnectionsAfterConnectionClosingTest() {
        ConnectionPool.getInstance().closeConnections();
        Assert.assertEquals(0, ConnectionPool.getInstance().getBusyConnectionsSize());
    }
}