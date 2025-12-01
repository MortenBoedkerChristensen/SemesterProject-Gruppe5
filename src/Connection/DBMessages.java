package Connection;

/**
 * 
 * @author knol
 * @version 2018-08-30
 * @author gibe - feb 2023
 * 
 */
public class DBMessages {
	
	private DBMessages() {
	}
	
	public static final String GENERIC_MESSAGE = "Some kind of errors....";
	public static final String COULD_NOT_READ_RESULTSET = "Could not read resultset";
	public static final String COULD_NOT_PREPARE_STATEMENT = "Could not prepare statement";
	public static final String COULD_NOT_BIND_OR_EXECUTE_QUERY = "Could not bind or execute query";
	public static final String COULD_NOT_BIND_PS_VARS_INSERT = "Could not bind ps variables for insert";
	public static final String COULD_NOT_INSERT = "Could not insert new record";
	public static final String COULD_NOT_UPDATE = "Could not update record";
	public static final String COULD_NOT_GET_GENERATED_KEY = "Could not get generated key";
	public static final String COULD_NOT_INSERT_ORDERLINE = "Could not insert order line items";

}