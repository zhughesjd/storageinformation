package net.joshuahughes.storageinformation.field;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Field
{
    protected Class<?> type;
    protected String id;
    public Field( Class<?> type, String id )
    {
        this.type = type;
        this.id = id;
    }
    public Class<?> getType( )
    {
        return type;
    }
    public String getId( )
    {
        return id;
    }
    public String toString()
    {
        return id;
    }
    public static List<Class<?>> defaultTypes = Arrays.asList( Integer.class,Float.class,Long.class,Double.class,Boolean.class,String.class,Date.class);
    @SuppressWarnings( "unchecked" )
    public static <T> T fromString(Field field,String value)
    {
        if(defaultTypes.contains( field.getClass( ) ))
            try
            {
                return (T) field.getClass( ).getConstructor( String.class ).newInstance( value );
            }
            catch ( Exception e )
            {
                e.printStackTrace();
            }
        return null;
    }
}
