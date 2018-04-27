package dtoIfc;

import java.io.Serializable;

public interface PropertyDTOIfc extends Serializable {
	
	public String getProp_key();
	public void setProp_key(String prop_key);
	
	public String getProp_value();
	public void setProp_value(String prop_value);
	
}
