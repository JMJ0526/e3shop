package org.jmj.controller;

import java.io.IOException;
import java.util.List;

import org.jmj.bean.TbContent;
import org.jmj.bean.TbContentCategory;
import org.jmj.bean.TbItem;
import org.jmj.bean.TbItemDesc;
import org.jmj.easy.EasyResultData;
import org.jmj.easy.ItemTreeNode;
import org.jmj.easy.PictureResult;
import org.jmj.easy.ResponseResult;
import org.jmj.service.ContentCategoryService;
import org.jmj.service.ItemSerivce;
import org.jmj.service.fastdfs.UploadFileService;
import org.jmj.vo.TbItemParmVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * 描述:
 * @author jmj
 * @date   2017年9月22日
 */
@Controller
public class DispatcherController {
	
	@Autowired
	private ItemSerivce itemService;
	
	@Autowired
	private UploadFileService uploadFileService;
	
	@Autowired
	private ContentCategoryService contentCategoryService;
	
	/**
	 * 到后台主页
	 * @return
	 */
	@RequestMapping("/")
	public String index() {
		return "index";
	}
	
	/**
	 * 跳转到商品列表
	 * @return
	 */
	@RequestMapping("/item-list")
	public String itemList() {
		return "item-list";
	}
	
	/**
	 * 跳转到添加商品页面
	 * @return
	 */
	@RequestMapping("/item-add")
	public String itemAdd(){
		return "item-add";
	}
	
	/**
	 * 跳转到规格参数列表页面
	 * @return
	 */
	@RequestMapping("/item-param-list")
	public String itemParam() {
		return "item-param-list";
	}
	
	
	/**
	 * 跳转到添加规格参数页面
	 * @return
	 */
	@RequestMapping("/item-param-add")
	public String itemParamAdd() {
		return "item-param-add";
	}
	
	
	/**
	 * 获取类目,获取取第一级类目时，id=null;
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/item/cat/list")
	public List<ItemTreeNode> itemCatList(
			@RequestParam(name="id",required=false)String id) {
		Long cid = (long)0;
		id = id == null ? "0" : id;
		if(!id.equals("0")) {
			try {
				cid = Long.parseLong(id);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		return itemService.getItemCatTree(cid);
	}
	
	/**
	 * 查询商品
	 * @param page
	 * @param rows
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/item/list")
	public EasyResultData<TbItem> itemList(@RequestParam("page")int page,@RequestParam("rows")int rows) {
		return itemService.getResultData(page, rows);
	}
	
	/**
	 * 上传图片
	 * @param dir
	 * @param uploadFile
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/pic/upload")
	public PictureResult uploadItemImage(String dir,MultipartFile uploadFile) {
		String url = "";
		String extName = uploadFile.getOriginalFilename().split("\\.")[1];
	    try {
			url = uploadFileService.uploadFile(uploadFile.getBytes(), extName,uploadFile.getOriginalFilename());
		} catch (IOException e) {
			return new PictureResult(1, "/","网络异常，请检查网络");
		}
		return new PictureResult(0, url);
	}
	
	
	/**
	 * 新增Item
	 * @param item
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/item/save")
	public ResponseResult addNewItem(TbItem item,String desc) {
		System.out.println("desc:"+desc);
		try {
			itemService.addNewItem(item,desc);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseResult.build(500, "保存失败");
		}
		return ResponseResult.ok();
	}
	
	/**
	 * 获取item规格参数
	 * @param page
	 * @param rows
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/item/param/list")
	public EasyResultData<TbItemParmVo> itemParamsList(@RequestParam("page")int page
			,@RequestParam("rows")int rows) {
		return itemService.getItemsParams(page, rows);
	}
	
	
	@ResponseBody
	@RequestMapping("/item/param/query/itemcatid/{cid}")
	public ResponseResult checkItemCat(@PathVariable("cid")long cid ) {
		return null;
	}
	
	
	@RequestMapping("/rest/page/item-edit")
	public String editPage() {
		return "item-edit";
	}
	
	
	/**
	 * 通过id获取item
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/rest/item/param/item/query/{id}")
	public ResponseResult getItemById(@PathVariable(value="id",required=true)String id) {
		Long itemId = null;
		TbItem item = null;
		try {
			 itemId = Long.parseLong(id);
			 item = itemService.getItemById(itemId);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			new ResponseResult(500,"服务器出错了",null);
		}
		return item == null ? new ResponseResult(200,"没有查询到相关结果",null) : new ResponseResult(item);
	}
	
	
	/**
	 * 通过id获取商品描述
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/rest/item/query/item/desc/{id}")
	public ResponseResult getItemDescById(@PathVariable(value="id",required=true)String id) {
		Long itemId = null;
		TbItemDesc desc = null;
		try {
			 itemId = Long.parseLong(id);
			 desc = itemService.getItemDescById(itemId);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			new ResponseResult(500,"服务器出错了",null);
		}
		return desc == null ? new ResponseResult(200,"没有查询到相关结果",null) : new ResponseResult(desc);
	}
	
	
	/**
	 * 更新
	 * @param item
	 * @param desc
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/rest/item/update")
	public  ResponseResult updateItem(TbItem item,String desc) {
		try {
			itemService.updateItemWithDesc(item,desc);
		} catch (Exception e) {
			e.printStackTrace();
			return  new ResponseResult(500,"服务器出错",null);
		}
		return  new ResponseResult(200,"更新item成功",null);
	}
	
	/**
	 * 删除items
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/rest/item/delete")
	public ResponseResult deleteItems(String ids) {
		try {
			itemService.deleteItemsByIds(ids);
		} catch (Exception e) {
			e.printStackTrace();
			return  new ResponseResult(500,"服务器出错",null);
		}
		return new ResponseResult(200,"删除成功",null);
	}
	
	
	/**
	 * contentCategory页面
	 * @return
	 */
	@RequestMapping("/content-category")
	public String contentCategoryPage() {
		return "content-category";
	}
	
	/**
	 * 获取contentCategories
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/content/category/list")
	public List<ItemTreeNode> getContentCateGories(@RequestParam(name="id",required=false)String id) {
		Long cid = (long)0;
		id = id == null ? "0" : id;
		if(!id.equals("0")) {
			try {
				cid = Long.parseLong(id);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		return contentCategoryService.getContentCateGoryTree(cid);
	}
	
	
	/**
	 * content主页面
	 * @return
	 */
	@RequestMapping("/content")
	public String contentPage() {
		return "content";
	}
	
	/**
	 * content-add页面
	 * @return
	 */
	@RequestMapping("/content-add")
	public String addContentCategoryPage() {
		return "content-add";
	}
	
	/**
	 * 后台内容分类修改页面
	 * @return
	 */
	@RequestMapping("/content-edit")
	public String contentEditPage() {
		return "content-edit";
	}
	
	/**
	 * 新增后台内容分类子节点
	 * @param cc
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/content/category/create")
	public ResponseResult addContentCategory(TbContentCategory cc) {
		System.out.println(cc);
		TbContentCategory result = null;
		try {
			result = contentCategoryService.saveContentCategory(cc);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseResult(500, "新增失败", null);
		}
		return new ResponseResult(200, "新增成功", result);
	}
	
	/**
	 * 修改后台内容分类子节点
	 * @param id
	 * @param name
	 */
	@RequestMapping("/content/category/update")
	public ResponseResult updateContentCategoryById(String id,String name) {
		try {
			contentCategoryService.updateContentCategoryById(id,name);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseResult(500, "更新失败", null);
		}
		return new ResponseResult(200, "更新成功", null);
	}

	/**
	 * 通过id删除内容分类节点
	 * @param id
	 */
	@ResponseBody
	@RequestMapping("/content/category/delete")
	public ResponseResult deleteContentCategoryById(String id){
		try {
			contentCategoryService.deleteContentCategoryById(id);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseResult(500, "删除失败", null);
		}
		return new ResponseResult(200, "删除成功", null);
	}
	
	
	/**
	 * 内容管理
	 * @param categoryId
	 * @param page
	 * @param rows
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/content/query/list")
	public EasyResultData<TbContent> getContentsByCid(Long categoryId,int page,int rows) {
		return contentCategoryService.getContensByCid(categoryId, page, rows);
	}
	
	/**
	 * 更新内容
	 * @param content
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/rest/content/edit")
	public ResponseResult updateContent(TbContent content) {
		try {
			contentCategoryService.updateContent(content);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseResult(500, "更新失败", null);
		}
		return new ResponseResult(200, "修改成功", null);
	}
	
	/**
	 * 删除内容
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/content/delete")
	public ResponseResult deleteContents(String ids) {
		try {
			contentCategoryService.deleteContents(ids);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseResult(500, "删除失败", null);
		}
		return new ResponseResult(200, "删除成功", null); 
	}
}
