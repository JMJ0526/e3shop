package org.jmj.service;

import java.util.List;

import org.jmj.bean.TbItem;
import org.jmj.bean.TbItemDesc;
import org.jmj.easy.EasyResultData;
import org.jmj.easy.ItemTreeNode;
import org.jmj.vo.TbItemParmVo;

public interface ItemSerivce {
	
	/**
	 * 查询商品
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public EasyResultData<TbItem> getResultData(Integer pageNum,Integer pageSize);
	
	
	/**
	 * 添加Item时，获取商品父类目
	 * @return
	 */
	public List<ItemTreeNode> getItemCatTree(Long id);
	
	/**
	 * 新增Item
	 * @param item
	 * @throws Exception
	 */
	public void addNewItem(TbItem item,String desc) throws Exception;
	
	
	/**
	 * 获取Item规格参数
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public EasyResultData<TbItemParmVo> getItemsParams(Integer pageNum,Integer pageSize);
	
	
	/**
	 * 通过id获取item
	 * @param id
	 * @return
	 */
	public TbItem getItemById(Long id);


	/**
	 * 通过id 获取商品描述
	 * @param itemId
	 * @return
	 */
	public TbItemDesc getItemDescById(Long itemId);


	/**
	 * 更新item及desc
	 * @param item
	 * @param desc
	 */
	public void updateItemWithDesc(TbItem item, String desc);


	/**
	 * 删除Item
	 * @param ids
	 */
	public void deleteItemsByIds(String ids) throws Exception;
	
	
}
