
/**
 * Represents a single attribute, in a real database would be more interesting, but
 * here it is just backed by a string and exposes some of the logical methods.
 *
 * An Attribute is immutable
 **/
public class Attribute {
	private String name;

	public Attribute(String name) {
		this.name = name;
	}

	public String toString() {
		return name;
	}

	public boolean equals(Object other) {
		if(other == null)
			return false;
		else if(!(other instanceof Attribute))
			return false;
		return name.equals(((Attribute)other).name);
	}

	public int hashCode() {
		return name.hashCode();
	}
}
