package org.jmj.service;

import java.util.List;

import org.jmj.bean.TbContent;
import org.jmj.bean.TbContentCategory;
import org.jmj.easy.EasyResultData;
import org.jmj.easy.ItemTreeNode;

/**
 * 描述：后台内容管理服务接口
 * @author 简铭杰
 * @date   2017年9月28日
 */
public interface ContentCategoryService {
	
	
	/**
	 * 获取内容分类列表
	 * @param parentId
	 * @return
	 */
	List<ItemTreeNode> getContentCateGoryTree(Long parentId);

	
	/**
	 * 新增内容分类节点
	 * @param cc
	 * @return 
	 */
	TbContentCategory saveContentCategory(TbContentCategory cc);


	/**
	 * 修改内容分类名称
	 * @param id
	 * @param name
	 * @throws Exception
	 */
	void updateContentCategoryById(String id, String name) throws Exception;


	/**
	 * 删除内容分类节点
	 * @param id
	 * @throws Exception
	 */
	void deleteContentCategoryById(String id) throws Exception;
	
	
	/**
	 * 获取content
	 * @param cid
	 * @param page
	 * @param rows
	 * @return
	 */
	EasyResultData<TbContent> getContensByCid(long cid,int page,int rows);


	void deleteContents(String ids);


	void updateContent(TbContent content);
	
}
