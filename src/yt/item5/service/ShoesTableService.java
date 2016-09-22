package yt.item5.service;

import javax.inject.Inject;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.transaction.annotation.Transactional;

import yt.item5.bean.Brand;
import yt.item5.bean.Shoes;
import yt.item5.dao.GenericDao;

public class ShoesTableService extends GeneralService<Shoes, Integer> implements ApplicationContextAware {

//	@Inject // or @Autowired (can be constructor or setter)
	private ApplicationContext ctx;

	private boolean isShoesMapToBrand(Shoes shoes, Brand brand) {
		return shoes.getBrand().getBrandId() == brand.getBrandId();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Brand buildFkEntity(Integer fkId) {
		@SuppressWarnings("rawtypes")
		GenericDao brandDao = (GenericDao) ctx.getBean("brandDao");
		Brand brand = null;
		try {
			brand = (Brand) brandDao.getById(fkId);
			if (null == brand)
				return null;

		} catch (NumberFormatException e) {
			e.printStackTrace();
			return null;
		}
		return brand;
	}

	@Transactional
	@Override
	public Shoes associateFkEntity(Shoes shoes, Integer fkId) {
		Brand brand = buildFkEntity(fkId);

		if (brand == null)
			return null;

		if (shoes == null)
			return shoes = new Shoes().setBrand(brand);

		if (!isShoesMapToBrand(shoes, brand))
			return null;
		return shoes;
	}

	@Override
	public void setApplicationContext(ApplicationContext paramApplicationContext) throws BeansException {
		this.ctx = paramApplicationContext;

	}

}
