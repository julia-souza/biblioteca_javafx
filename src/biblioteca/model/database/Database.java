package biblioteca.model.database;

import java.sql.Connection;

/**
 *
 * @author julia
 */


public interface Database {
    
    public Connection conectar();
    public void desconectar(Connection conn);
}