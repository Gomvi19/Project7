package DBHelper;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class EngineeringStudents extends DBHelper {
	private final String TABLE_NAME = "EngineeringStudents";
	public static final String Student_ID = "Student_ID";
	public static final String Department = "Department";
	public static final String First_Name = "First_Name";
	public static final String Last_Name = "Last_Name";
	public static final String PassOutYear = "PassOutYear";
	public static final String UniversityRank = "UniversityRank";

    /*prepareSQL
    prepares the text of a SQL "select" command
    returns String
    arguments:
        fields: the fields to be displayed in the output
        whatField: field to search for
        whatValue: value to search for within whatField
        sort: use ASC or DESC to specify the sorting order
        softField: the field that the data will be sorted by
    */
	private String prepareSQL(String fields, String whatField, String whatValue, String sortField, String sort) {
		String query = "SELECT ";
		query += fields == null ? " * FROM " + TABLE_NAME : fields + " FROM " + TABLE_NAME;
		query += whatField != null && whatValue != null ? " WHERE " + whatField + " = \"" + whatValue + "\"" : "";
		query += sort != null && sortField != null ? " order by " + sortField + " " + sort : "";
		return query;
	}

    /*insert
     returns void
     arguments: each field listed in the table from the database in order
     notes: due to inheritance this executes the execute method found in the parent class
    */
	public void insert(Integer Student_ID, String Department, String First_Name, String Last_Name, Integer PassOutYear, Integer UniversityRank) {
		Department = Department != null ? "\"" + Department + "\"" : null;
		First_Name = First_Name != null ? "\"" + First_Name + "\"" : null;
		Last_Name = Last_Name != null ? "\"" + Last_Name + "\"" : null;
		
		Object[] values_ar = {Student_ID, Department, First_Name, Last_Name, PassOutYear, UniversityRank};
		String[] fields_ar = {EngineeringStudents.Student_ID, EngineeringStudents.Department, EngineeringStudents.First_Name, EngineeringStudents.Last_Name, EngineeringStudents.PassOutYear, EngineeringStudents.UniversityRank};
		String values = "", fields = "";
		for (int i = 0; i < values_ar.length; i++) {
			if (values_ar[i] != null) {
				values += values_ar[i] + ", ";
				fields += fields_ar[i] + ", ";
			}
		}
		if (!values.isEmpty()) {
			values = values.substring(0, values.length() - 2);
			fields = fields.substring(0, fields.length() - 2);
			super.execute("INSERT INTO " + TABLE_NAME + "(" + fields + ") values(" + values + ");");
		}
	}
    /*delete
    removes a record from the database
    * return type: void
    * arguments: the field (key) used to determine if a row should be deleted and the value to remove
    * notes: due to inheritance this executes the execute method found in the parent class
    * */
	public void delete(String whatField, String whatValue) {
		super.execute("DELETE from " + TABLE_NAME + " where " + whatField + " = " + whatValue + ";");
	}
    /*update
    updates a value in the database
    *return type void
    * arguments: the field (key) used to determine if a row should be updated and the value to update
    * notes: due to inheritance this executes the execute method found in the parent class
    * */
	public void update(String whatField, String whatValue, String whereField, String whereValue) {
		super.execute("UPDATE " + TABLE_NAME + " set " + whatField + " = \"" + whatValue + "\" where " + whereField + " = \"" + whereValue + "\";");
	}
    /*select
    * completes a SQL "select" command
    * return type: ArrayList<Objects> - this means it returns a 2D array of objects so it can be any type
    * arguments:
    *   fields: fields to be displayed
    *   whatField: field to be searched within
    *   whatValue: value to search for within whatField
    *   sort: use ASC or Desc to specify the sorting order
    *   softField: the field that the data wii be sorted by
    *   note: this method calls the private method "prepareSQL" within the class
    * */
	public ArrayList<ArrayList<Object>> select(String fields, String whatField, String whatValue, String sortField, String sort) {
		return super.executeQuery(prepareSQL(fields, whatField, whatValue, sortField, sort));
	}
    /*getExecuteResult
    performs a search of the database, where String "query" is the SQL command that would be in the command line
    *return type: ArrayList<ArrayList<Object>> this means it returns a 2D array of objects so the data can be of any type
    Arguments: query this is the SQL query command that would be entered at the command line  for a SQL query
    */
	public ArrayList<ArrayList<Object>> getExecuteResult(String query) {
		return super.executeQuery(query);
	}
    /*execute
    performs a SQL command where String query is the SQL command tha would be entered on the command line
    return type: void
    arguments: query this is the SQL command that would be entered at the command line for
    * */
	public void execute(String query) {
		super.execute(query);
	}
    /*DefaultTableModel
    performs a search of the database, where String query is the SQL command that
    return type: DefaultTableModel uses a vector of vectors(i.e a table) to store data
    arguments:
        fields: the fields to be displayed in the output
        whatField: fields to search within
        whatValue: value to search for within whatField
        sort: use ASC or Desc to specify the sorting order
    *   softField: the field that the data wii be sorted by
    * */
	public DefaultTableModel selectToTable(String fields, String whatField, String whatValue, String sortField, String sort) {
		return super.executeQueryToTable(prepareSQL(fields, whatField, whatValue, sortField, sort));
	}

}