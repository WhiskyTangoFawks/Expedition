package wtf.utilities.wrappers;

public class XZ{
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + z;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		XZ other = (XZ) obj;
		if (x != other.x)
			return false;
		if (z != other.z)
			return false;
		return true;
	}
	public final int x;
	public final int z;
	public XZ (int x, int z){
		this.x = x;
		this.z = z;
	}
	public XZ[] getAdj() {
		XZ[] set= {new XZ(x+1, z), new XZ(x-1, z), new XZ(x, z+1), new XZ(x, z-1)}; 
		return set;
	}
	
	
	
	
}