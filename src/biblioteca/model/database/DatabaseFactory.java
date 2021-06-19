package biblioteca.model.database;

/**
 *
 * @author julia
 */
public class DatabaseFactory {
    public static Database getDatabase(String nome){
        if(nome.equals("postgresql")){
            return new DatabasePostgreSQL();
        }else if(nome.equals("mysql")){
            return new DatabaseMySQL();
        }
        return null;
    }
}