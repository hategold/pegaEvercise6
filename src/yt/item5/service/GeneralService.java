package yt.item5.service;

import java.io.Serializable;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

import yt.item5.bean.EntityInterface;
import yt.item5.dao.GenericDao;

public abstract class GeneralService<T extends EntityInterface, PK extends Serializable> implements CrudServiceInterface<T, PK> {

	protected GenericDao<T, PK> genericDao;

	@Override
	public GenericDao<T, PK> getGenericDao() {
		return genericDao;
	}

	@Override
	public void setGenericDao(GenericDao<T, PK> genericDao) {
		this.genericDao = genericDao;
	}

	@Override
	public List<T> findAll() {
		return genericDao.findAll();
	}

	@Override
	public void deleteById(PK Id) {
		genericDao.deleteById(Id);

	}

	@Override
	public T getById(PK Id) {
		return genericDao.getById(Id);
	}

	@Override
	public void insert(T entity) {
		genericDao.insert(entity);

	}

	@Transactional
	@Override
	public void update(T entity) {
		genericDao.update(entity);

	}

	@Override
	public List<T> findByCondition(String subHQL) {
		return genericDao.findByCondition(subHQL);
	}

	@Override
	public List<T> findByFk(String fkFieldName, PK fkId) {
		return genericDao.findByFk(fkFieldName, fkId);
	}

	@Override
	public abstract T associateFkEntity(T entity, PK fkId);

	@Override
	public abstract <FT extends EntityInterface> FT buildFkEntity(PK fkId);

}
