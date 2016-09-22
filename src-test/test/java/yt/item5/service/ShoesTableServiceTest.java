package test.java.yt.item5.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import yt.item5.bean.Brand;
import yt.item5.bean.Shoes;
import yt.item5.dao.GenericDao;
import yt.item5.service.CrudServiceInterface;
import yt.item5.service.ShoesTableService;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class ShoesTableServiceTest {

	private CrudServiceInterface<Shoes, Integer> testService;

	private GenericDao mockDao;

	@Before
	public void setUp() throws Exception {
		testService = new ShoesTableService();
		mockDao = mock(GenericDao.class);
		testService.setGenericDao(mockDao);
	}

	@After
	public void tearDown() throws Exception {
		testService = null;
	}

	@Test
	public void testDao() {
		testService.setGenericDao(mockDao);
		assertEquals(mockDao, testService.getGenericDao());
	}

	@Test
	public void testInsert() {
		Shoes shoes = new Shoes();
		shoes.setShoesId(5);

		insertWithTrue(shoes); //insertCase1

		reset(mockDao);

		insertWithFalse(shoes); //insertCase2

	}

	private void insertWithTrue(Shoes shoes) { //insertCase1 : insert mockDao return true
		when(mockDao.insert(shoes)).thenReturn(true);
		testService.insert(shoes);
		verify(mockDao).insert(shoes);
	};

	private void insertWithFalse(Shoes shoes) { //insertCase2 : insert mockDao return false
		when(mockDao.insert(shoes)).thenReturn(false);
		testService.insert(shoes);
		verify(mockDao).insert(shoes);
	};

	@Test
	public void testUpdate() {

		Shoes shoes = new Shoes();
		shoes.setShoesId(5);

		when(mockDao.insert(shoes)).thenReturn(true);
		testService.insert(shoes);
		
		shoes.setShoesName("5566").setCategory("KKK").setPrice(5566).setBrandById(2);
		when(mockDao.update(shoes)).thenReturn(true);
		testService.update(shoes);
		verify(mockDao).update(shoes);

		when(mockDao.getById(5)).thenReturn(shoes);
		assertEquals(shoes.toString(), testService.getById(5).toString());

		reset(mockDao);
		shoes.setShoesId(33);
		when(mockDao.update(shoes)).thenReturn(false);
		testService.update(shoes);
		verify(mockDao).update(shoes);

	}

	@Test
	public void testDeleteById() {

		Shoes shoes = new Shoes();
		shoes.setShoesId(5);
		when(mockDao.insert(shoes)).thenReturn(true);
		testService.insert(shoes);

		when(mockDao.deleteById(5)).thenReturn(true);
		testService.deleteById(5);
		verify(mockDao).deleteById(5);
	}

	@Test
	public void testBuildFkEntity() {
		ShoesTableService shoesService = (ShoesTableService)testService;// for set context

		ApplicationContext mockCtx = mock(ApplicationContext.class);
		GenericDao mockBrandDao = mock(GenericDao.class);
		shoesService.setApplicationContext(mockCtx);
		when(mockCtx.getBean("brandDao")).thenReturn(mockBrandDao);

		Brand brand = new Brand(15);
		when(mockBrandDao.getById(15)).thenReturn(brand);
		when(mockBrandDao.getById(3)).thenReturn(null);
		assertEquals(brand, shoesService.buildFkEntity(15));
		assertEquals(null, shoesService.buildFkEntity(3));
	}

	@Test
	public void testAssociateFkEntity() {
		ShoesTableService spyShoesService = spy(new ShoesTableService());
		doReturn(new Brand(15)).when(spyShoesService).buildFkEntity(15);
		doReturn(null).when(spyShoesService).buildFkEntity(3);

		Shoes shoes = new Shoes();
		shoes.setShoesId(20).setBrandById(15);
		Shoes normalResult = spyShoesService.associateFkEntity(shoes, 15);
		assertEquals(shoes.getBrand().getBrandId(), normalResult.getBrand().getBrandId());

		Shoes nullResult = spyShoesService.associateFkEntity(null, 3);
		assertEquals(null, nullResult);

		Shoes shoes2 = new Shoes();
		shoes2.setShoesId(20).setBrandById(10);
		Shoes notMapResult = spyShoesService.associateFkEntity(shoes2, 15);
		assertEquals(null, notMapResult);

		Shoes nullBrandResult = spyShoesService.associateFkEntity(shoes2, 3);
		assertEquals(null, nullBrandResult);

		Shoes nullShoesResult = spyShoesService.associateFkEntity(null, 15);
		assertEquals(nullShoesResult.getBrand().getBrandId(), 15);
	}

}
