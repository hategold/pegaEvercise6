package yt.item5.service;

import java.io.Serializable;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

import yt.item5.bean.EntityInterface;
import yt.item5.dao.GenericDao;

public interface CrudServiceInterface<T extends EntityInterface, PK extends Serializable> {

	public GenericDao<T, PK> getGenericDao();

	public void setGenericDao(GenericDao<T, PK> genericDao);

	public List<T> findAll();

	@Transactional
	public void deleteById(PK Id);

	public T getById(PK Id);

	@Transactional
	public void insert(T entity);

	@Transactional
	public void update(T entity);

	public List<T> findByCondition(String subHQL);

	public T associateFkEntity(T entity, PK fkId);

	public <FT extends EntityInterface> FT buildFkEntity(PK fkId);

	List<T> findByFk(String fkFieldName, PK fkId);

}