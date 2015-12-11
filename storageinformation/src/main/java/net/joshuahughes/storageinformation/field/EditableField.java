package net.joshuahughes.storageinformation.field;

public class EditableField extends Field{
	 public EditableField( Class<?> type, String id )
     {
		 super(type,id);
     }
     public void setType( Class<?> type )
     {
         this.type = type;
     }
     public void setId( String id )
     {
         this.id = id;
     }
}
