package test.java.yt.item5.service;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import yt.item5.bean.Brand;
import yt.item5.dao.GenericDao;
import yt.item5.service.BrandTableService;
import yt.item5.service.CrudServiceInterface;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BrandTableServiceTest {

	private CrudServiceInterface<Brand, Integer> testService;

	@Before
	public void setUp() throws Exception {
		testService = new BrandTableService();

	}

	@After
	public void tearDown() throws Exception {
		testService = null;
	}

	@Test
	public void testDao() {
		GenericDao<Brand, Integer> mockDao = new MockBrandTableDao();
		testService.setGenericDao(mockDao);
		assertEquals(mockDao, testService.getGenericDao());

	}

	@Test
	public void testInsert() {
		MockBrandTableDao mockDao = new MockBrandTableDao();
		testService.setGenericDao(mockDao);

		Brand brand = new Brand(5);
		testService.insert(brand);

		assertEquals(1, mockDao.findAll().size());

		Brand brand2 = new Brand(6);
		testService.insert(brand2);
		assertEquals(2, mockDao.findAll().size());

		testService.insert(brand2);
		assertEquals(2, mockDao.findAll().size());

		assertEquals(brand2.getId(), mockDao.getById(6).getId());
	}

	@Test
	public void testUpdate() {
		MockBrandTableDao mockDao = new MockBrandTableDao();
		testService.setGenericDao(mockDao);
		Brand brand = new Brand(5);
		testService.insert(brand);
		brand.setBrandName("5566").setCountry("TWN").setWebsite("www.5566.com");
		testService.update(brand);

		assertEquals(brand.toString(), mockDao.getById(5).toString());

		brand.setBrandId(33);
		testService.update(brand);
		assertEquals(null, mockDao.getById(33));
		assertNotEquals(brand, mockDao.getById(5));

	}

	@Test
	public void testDeleteById() {
		MockBrandTableDao mockDao = new MockBrandTableDao();
		testService.setGenericDao(mockDao);
		Brand brand = new Brand(5);
		testService.insert(brand);

		testService.deleteById(5);
		assertEquals(0, mockDao.findAll().size());
	}

}

//--------------------------------------------------------Mock class------------------------------------------------

class MockBrandTableDao implements GenericDao<Brand, Integer> {

	Map<Integer, Brand> brandMap = new HashMap<Integer, Brand>();

	public MockBrandTableDao() {
	}

	public MockBrandTableDao(Brand t) {
		Brand copyT = new Brand(t.getBrandId(), t.getBrandName(), t.getWebsite(), t.getCountry());
		try {
			brandMap.put(copyT.getId(), copyT);
		} catch (Exception e) {
			return;
		}
	}

	@Override
	public Brand getById(Integer Id) {
		Brand brand = brandMap.get(Id);
		return brand;
	}

	@Override
	public boolean deleteById(Integer Id) {
		try {
			brandMap.remove(Id);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean delete(Brand t) {
		return this.deleteById(t.getId());
	}

	@Override
	public boolean update(Brand t) {
		Brand copyT = new Brand(t.getBrandId(), t.getBrandName(), t.getWebsite(), t.getCountry());
		if (brandMap.get(t.getId()) != null) {

			brandMap.put(copyT.getId(), copyT);
			return true;
		}
		return false;
	}

	@Override
	public boolean insert(Brand t) {
		Brand copyT = new Brand(t.getBrandId(), t.getBrandName(), t.getWebsite(), t.getCountry());
		if (brandMap.get(copyT.getId()) == null) {
			brandMap.put(copyT.getId(), copyT);
			return true;
		}

		return false;
	}

	@Override
	public List<Brand> findAll() {
		List<Brand> brandList = new ArrayList<Brand>();
		brandList.addAll(brandMap.values());
		return brandList;
	}

	@Override
	public List<Brand> findByCondition(String s) {

		return this.findAll();
	}

	@Override
	public List<Brand> findByFk(String fkFieldName, Integer fkId) {
		return null;
	}
}
