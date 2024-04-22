package com.example.controller;

import java.util.List;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.form.ItemForm;
import com.example.entity.Item;
import com.example.service.ItemService;
import com.example.entity.Category;
import com.example.service.CategoryService;

@Controller
@RequestMapping("/item")
public class ItemController {

	private final ItemService itemService;
	private final CategoryService categoryService;

	@Autowired
	public ItemController(ItemService itemService, CategoryService categoryService) {
		this.itemService = itemService;
		this.categoryService = categoryService;
	}

	//商品一覧の表示
	@GetMapping
	public String index(Model model) {
		// DELETED_ATがnullのデータのみを検索します
		List<Item> items = this.itemService.findByDeletedAtIsNull();
		//画面で利用する変数としてitemsをセットします
		model.addAttribute("items", items);
		//templates/item/index.htmlを表示
		return "item/index";
	}

	//商品登録ページ表示用
	@GetMapping("toroku")
	public String torokuPage(@ModelAttribute("itemForm") ItemForm itemForm, Model model) {
		List<Category> categories = this.categoryService.findAll();

		model.addAttribute("categories", categories);
		return "item/torokuPage";
	}

	//商品登録の実行
	@PostMapping("toroku")
	public String toroku(ItemForm itemForm) {
		this.itemService.save(itemForm);
		// 一覧ページへリダイレクトします
		return "redirect:/item";
	}

	//商品編集の実行
	@PostMapping("henshu/{id}")
	public String henshu(@PathVariable("id") Integer id, @ModelAttribute("itemForm") ItemForm itemForm) {
		this.itemService.update(id, itemForm);
		return "redirect:/item";
	}

	//商品削除の実行
	@PostMapping("sakujo/{id}")
	public String sakujo(@PathVariable("id") Integer id) {
		this.itemService.delete(id);
		return "redirect:/item";
	}

	@GetMapping("henshu/{id}")
	public String henshuPage(@PathVariable("id") Integer id, Model model,
			@ModelAttribute("itemForm") ItemForm itemForm) {
		// Entityクラスのインスタンスをidより検索し取得します
		Item item = this.itemService.findById(id);
		// フィールドのセットを行います
		itemForm.setName(item.getName());
		itemForm.setPrice(item.getPrice());
		itemForm.setCategoryId(item.getCategoryId());

		List<Category> categories = this.categoryService.findAll();

		// idをセットします
		model.addAttribute("id", id);

		model.addAttribute("categories", categories);
		// templates/item/henshuPageを表示します
		return "item/henshuPage";
	}

	// 送信ボタンのname属性が in の場合は入荷処理の実行
	@PostMapping(path = "stock/{id}", params = "in")
	public String nyuka(@PathVariable("id") Integer id, @RequestParam("stock") Integer inputValue) {
		// 入荷処理
		this.itemService.nyuka(id, inputValue);

		// 一覧ページへのリダイレクト処理
		return "redirect:/item";
	}

	// 送信ボタンのname属性が out の場合は出荷処理の実行
	@PostMapping(path = "stock/{id}", params = "out")
	public String shukka(@PathVariable("id") Integer id, @RequestParam("stock") Integer inputValue) {
		// 出荷処理
		this.itemService.shukka(id, inputValue);

		// 一覧ページへのリダイレクト処理
		return "redirect:/item";
	}

}
