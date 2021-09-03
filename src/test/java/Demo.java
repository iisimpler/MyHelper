import lombok.Data;

/**
 * demo <== This will become the comment for table
 * @table [t_demo] <== @table tag is required, but value is not
 *                  if value is configured, it will become the final table name,
 *                  otherwise it will use the underline format of class name.
 */
@Data
public class Demo {
    /**
     * ID <== This will become the comment for id filed,
     *          the primary key index is created based on the 'id' by default.
     *
     * @primaryKey [true] <== Please configure @primaryKey tag
     *                when your primary key name is not called id , such as demo_id.
     */
    private Long id;

    /**
     * the name of sth <== This will become the comment for name filed
     *
     * @length 50 <== default 20, This will become the length for name filed, means varchar(50)
     *
     * @dbType varchar(20) <== Or you can specify data type directly
     *
     * @unique [true] <== Create a unique index based on the name field
     *
     * @index [true] <== Will create an index based on the name field
     *
     * @defaultVal 'tom' <== Indicates that the default value is Tom
     *
     * @nullAble [true] <== Indicates that the name field can be null
     *
     */
    private String name;
}
