package yt.item5;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import yt.item5.bean.Brand;
import yt.item5.bean.Shoes;

/**
 * Servlet implementation class ShoesTableController
 */
@WebServlet("/ShoesTableController")
public class ShoesTableController extends AbstractTableController<Shoes, Integer> {

	private static final long serialVersionUID = 1L;

	public static final String LIST_SHOESS = "/listShoes.jsp";

	public static final String INSERT_OR_EDIT = "/modifyShoes.jsp";

	public ShoesTableController() {
		super(LIST_SHOESS, INSERT_OR_EDIT, Shoes.class);
	}

	@Override
	public Integer parsePkFromReq(HttpServletRequest request) {
		return checkString2Int(request.getParameter("shoesId"));
	}

	@Override
	public Shoes buildEntityByReq(HttpServletRequest request) {
		Shoes shoes = new Shoes(request.getParameter("shoesName"));
		shoes.setShoesId(parsePkFromReq(request)).setCategory(request.getParameter("category")).setPrice(Integer.valueOf(request.getParameter("price")))
				.setSeries(request.getParameter("series")).setBrand(generalService.buildFkEntity(Integer.valueOf(request.getParameter("brandId"))));
		return shoes;
	}

	@Override
	public String dispatchToUpdate(HttpServletRequest request, Shoes shoes) {
		shoes = generalService.processUpdate(shoes, Integer.valueOf(request.getParameter("brandId")));
		if (shoes == null)
			return dispatchToList(request);

		request.setAttribute("brand", shoes.getBrand());
		return super.dispatchToUpdate(request, shoes);
	}

	@Override
	public String dispatchToList(HttpServletRequest request) {
		Brand brand = generalService.buildFkEntity(Integer.valueOf(request.getParameter("brandId")));
		if (brand == null)
			return null;
		request.setAttribute("brand", brand);
		request.setAttribute("shoesList", generalService.findByCondition("brandId =" + String.valueOf(brand.getBrandId())));
		return LIST_SHOESS;
	}

	@Override
	public String buildListUrl(HttpServletRequest request) throws IOException {
		return super.buildListUrl(request) + "&brandId=" + request.getParameter("brandId");
	}

}