import java.io.Serializable;

public class CartItem implements Serializable {
	String imgAddress;
	String name;
	int price;
	private static final long serialVersionUID = 1L; 
	public CartItem(String imgAddress, String name, int price) {
		
		this.imgAddress = imgAddress;
		this.name = name;
		this.price = price;
	
	}
	public String getImgAddress() {
		return imgAddress;
	}
	public void setImgAddress(String imgAddress) {
		this.imgAddress = imgAddress;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	@Override
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		}
		if(obj == null || this.getClass() != obj.getClass()) {
			return false;
		}
		CartItem cartItemToCompare = (CartItem) obj;
		return this.getName().equals(cartItemToCompare.getName());
		
	}
	public int hashCode() {
		return this.name.hashCode();
	}
	public String toString() {
		
		return this.name;
	}

}
