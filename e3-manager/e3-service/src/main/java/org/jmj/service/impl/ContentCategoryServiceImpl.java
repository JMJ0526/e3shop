package org.jmj.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jmj.bean.TbContent;
import org.jmj.bean.TbContentCategory;
import org.jmj.bean.TbContentCategoryExample;
import org.jmj.bean.TbContentCategoryExample.Criteria;
import org.jmj.bean.TbContentExample;
import org.jmj.easy.EasyResultData;
import org.jmj.easy.ItemTreeNode;
import org.jmj.jedis.JedisClient;
import org.jmj.mappers.TbContentCategoryMapper;
import org.jmj.mappers.TbContentMapper;
import org.jmj.service.ContentCategoryService;
import org.jmj.util.ManagerUtil;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

public class ContentCategoryServiceImpl implements ContentCategoryService {

	
	@Autowired
	private TbContentCategoryMapper tbContentCategoryMapper;

	@Autowired
	private TbContentMapper tbContentMapper;
	
	@Autowired
	private JedisClient jedisClient;
	
	@Override
	public List<ItemTreeNode> getContentCateGoryTree(Long parentId) {
		List<ItemTreeNode> result = new ArrayList<>();
		ItemTreeNode node = null;
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbContentCategory> list = tbContentCategoryMapper.selectByExample(example);
		for (TbContentCategory tbContentCategory : list) {
			node = new ItemTreeNode(tbContentCategory.getId(),tbContentCategory.getName(),
					tbContentCategory.getIsParent() ? "closed" : "open");
			result.add(node);
		}
		
		return result;
	}

	@Override
	public TbContentCategory saveContentCategory(TbContentCategory cc) {
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(cc.getParentId());
		List<TbContentCategory> list = tbContentCategoryMapper.selectByExample(example);
		TbContentCategory contentCategory  = list.get(0);
	    if(!contentCategory.getIsParent()) {
	    	contentCategory.setIsParent(true);
	    	tbContentCategoryMapper.updateByPrimaryKey(contentCategory);
	    }
		
		cc.setId(ManagerUtil.newPrimaryKey());
		cc.setCreated(new Date());
		cc.setSortOrder(1);
		cc.setStatus(1);
		cc.setIsParent(false);
		cc.setUpdated(new Date());
		tbContentCategoryMapper.insert(cc);
		return cc;
	}

	@Override
	public void updateContentCategoryById(String id, String name) throws Exception {
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(Long.parseLong(id));
		List<TbContentCategory> list = tbContentCategoryMapper.selectByExample(example);
		list.get(0).setName(name);
		tbContentCategoryMapper.updateByPrimaryKey(list.get(0));
	}

	@Override
	public void deleteContentCategoryById(String id) throws Exception {
		long ccid = Long.parseLong(id);
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(ccid);
		tbContentCategoryMapper.deleteByPrimaryKey(ccid);
		tbContentCategoryMapper.deleteByExample(example);
	}

	@Override
	public EasyResultData<TbContent> getContensByCid(long cid, int page, int rows) {
		PageHelper.startPage(page, rows);
		TbContentExample example = new TbContentExample();
		org.jmj.bean.TbContentExample.Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(cid);
		List<TbContent> list = tbContentMapper.selectByExampleWithBLOBs(example);
		PageInfo<TbContent> info = new PageInfo<>(list);
		return new EasyResultData<TbContent>(info.getTotal(),info.getList());
	}

	@Override
	public void deleteContents(String ids) {
		List<Long> cids = new ArrayList<>();
		String[] ss = ids.split(",");
		for (String string : ss) {
			cids.add(Long.parseLong(string));
			jedisClient.del(string);
		}
		TbContentExample example = new TbContentExample();
		org.jmj.bean.TbContentExample.Criteria criteria = example.createCriteria();
		criteria.andIdIn(cids);
		tbContentMapper.deleteByExample(example);
	}

	@Override
	public void updateContent(TbContent content) {
		TbContentExample example = new TbContentExample();
	    org.jmj.bean.TbContentExample.Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(content.getId());
		List<TbContent> list = tbContentMapper.selectByExample(example);
		TbContent cont = list.get(0);
		cont.setContent(content.getContent());
		cont.setPic(content.getPic());
		cont.setPic2(content.getPic2());
		cont.setSubTitle(content.getSubTitle());
		cont.setTitle(content.getTitle());
		cont.setTitleDesc(content.getTitleDesc());
		cont.setUrl(content.getUrl());
		cont.setUpdated(new Date());
		tbContentMapper.updateByPrimaryKeyWithBLOBs(cont);
		jedisClient.del(content.getId()+"");
	}

}
