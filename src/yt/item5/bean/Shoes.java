package yt.item5.bean;

import java.io.Serializable;

public class Shoes implements Serializable, EntityInterface {

	private static final long serialVersionUID = 1L;

	private int shoesId;

	private String shoesName;

	private String series;

	private String category;

	private int price;

	private Brand brand;
	
	private int brandId;

	public Shoes() {

	}

	public Shoes(String shoesName) {
		setShoesName(shoesName);
	}

	public int getShoesId() {
		return shoesId;
	}

	public Shoes setShoesId(int shoesId) {
		this.shoesId = shoesId;
		return this;
	}

	public String getShoesName() {
		return shoesName;
	}

	public Shoes setShoesName(String shoesName) {
		this.shoesName = shoesName;
		return this;
	}

	public String getSeries() {
		return series;
	}

	public Shoes setSeries(String series) {
		this.series = series;
		return this;
	}

	public String getCategory() {
		return category;
	}

	public Shoes setCategory(String category) {
		this.category = category;
		return this;
	}

	public int getPrice() {
		return price;
	}

	public Shoes setPrice(int price) {
		this.price = price;
		return this;
	}

	public Brand getBrand() {
		return brand;
	}

	public Shoes setBrand(Brand brand) {
		this.brand = brand;
		return this;
	}

	public Shoes setBrandById(int id) {
		this.brand = new Brand(id);
		return this;
	}

	@Override
	public int getId() {
		return this.shoesId;
	}

	@Override
	public String toString() {
		return shoesId + " " + shoesName + " " + series + " " + price + " " + brand;

	}

	@Override
	public void setForeignClassNull() {
		setBrand(null);

	}

	public int getBrandId() {
		return brandId;
	}

	public Shoes setBrandId(int brandId) {
		this.brandId = brandId;
		return this;
	}
}
