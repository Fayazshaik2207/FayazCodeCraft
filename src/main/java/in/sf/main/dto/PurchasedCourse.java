package in.sf.main.dto;

public class PurchasedCourse {
	private String purchasedOn;
	private String name;
	private String imageUrl;
	private String description;
	private String updatedOn;
	public String getPurchasedOn() {
		return purchasedOn;
	}
	public void setPurchasedOn(String purchasedOn) {
		this.purchasedOn = purchasedOn;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUpdatedOn() {
		return updatedOn;
	}
	public void setUpdatedOn(String updatedOn) {
		this.updatedOn = updatedOn;
	}
}
