package yt.item5.service;

import yt.item5.bean.Brand;
import yt.item5.bean.EntityInterface;

public class BrandTableService extends GeneralService<Brand, Integer> {

	@Override
	public Brand associateFkEntity(Brand entity, Integer fkId) {
		return entity;
	}

	@Override
	public <FT extends EntityInterface> FT buildFkEntity(Integer fkId) {
		return null;
	}

}
