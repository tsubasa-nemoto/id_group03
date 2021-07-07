package com.example.demo;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class TopController {

	@Autowired
	HttpSession session;

//	@Autowired
//	ItemRepository itemRepository;
//
//	@Autowired
//	CategoryRepository categoryRepository;

	/**
	 * 全商品を表示
	 */
	@RequestMapping(value = "/top")
	public ModelAndView items(ModelAndView mv) {
//		List<Item> itemList = itemRepository.findAllByOrderByCodeAsc();
	//	mv.addObject("items", itemList);

		mv.setViewName("top");
		return mv;
	}
//
//	/**
//	 * 指定したカテゴリコードの商品を表示
//	 */
//	@RequestMapping(value = "/items/{code}")
//	public ModelAndView itemsByCode(
//			@PathVariable(name="code") int categoryCode,
//			ModelAndView mv) {
////		List<Item> itemList = itemRepository.findByCategoryCode(categoryCode);
////		mv.addObject("items", itemList);
//		//session.setAttribute("Category", categoryCode);
//		mv.setViewName("list");
//		return mv;
//	}
//
//	@RequestMapping(value = "/items/search") //Step1（あいまい検索）
//	public ModelAndView itemsearch(ModelAndView mv,
//			@RequestParam("search") String name, //検索バーからのデータ取得
//			@RequestParam(name = "minMoney", defaultValue = "0") int minMoney,
//			@RequestParam(name = "maxMoney", defaultValue = "0") int maxMoney) {
//		List<Item> itemList = null;
//		if (name == null) {//テキスト空欄の場合
//			if (minMoney == 0 && maxMoney == 0) {//すべて空欄なら全件検索
//				itemList = itemRepository.findAllByOrderByCodeAsc();
//			} else if (minMoney != 0 && maxMoney == 0) {//最小値だけ入力
//				itemList = itemRepository.findByPriceGreaterThanEqual(minMoney);//以上の検索
//			} else if (minMoney == 0 && maxMoney != 0) {//最大値だけ入力
//				itemList = itemRepository.findByPriceGreaterThanEqual(maxMoney);//以下の検索
//			} else {
//				itemList = itemRepository.findByPriceBetween(minMoney, maxMoney);
//			}
//
//		} else {//テキスト入力有の場合
//			if (minMoney == 0 && maxMoney == 0) {//名前だけ入力
//				itemList = itemRepository.findByNameLike("%" + name + "%");//あいまい検索
//			} else if (minMoney == 0) {//最小値が空欄
//				itemList = itemRepository.findByNameLikeAndPriceLessThanEqual("%" + name + "%", maxMoney);
//			} else if (maxMoney == 0) {
//				itemList = itemRepository.findByNameLikeAndPriceGreaterThanEqual("%" + name + "%", minMoney);
//			} else {
//				itemList = itemRepository.findByNameLikeAndPriceBetween("%" + name + "%", minMoney, maxMoney);
//			}
//		}
//		//itemRepositoryであいまい検索用のメソッド設定
//		mv.addObject("items", itemList);//検索されたListをitemsで保存
//		session.setAttribute("key", name);
//		session.setAttribute("max",maxMoney);
//		session.setAttribute("min",minMoney);
//		mv.setViewName("list");
//		return mv;
//	}
//
//	@RequestMapping(value = "/item/{itemCode}")
//	public ModelAndView View(ModelAndView mv,
//			@PathVariable(name = "itemCode") int Code) {
//		Item item = itemRepository.findById(Code).get();
//		mv.addObject("item", item);
//		mv.setViewName("view_items");
//		return mv;
//	}
//
//}

}
