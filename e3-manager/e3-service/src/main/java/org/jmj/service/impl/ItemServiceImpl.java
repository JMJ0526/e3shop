package org.jmj.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.jmj.bean.TbItem;
import org.jmj.bean.TbItemCat;
import org.jmj.bean.TbItemCatExample;
import org.jmj.bean.TbItemCatExample.Criteria;
import org.jmj.bean.TbItemDesc;
import org.jmj.bean.TbItemDescExample;
import org.jmj.bean.TbItemExample;
import org.jmj.easy.EasyResultData;
import org.jmj.easy.ItemTreeNode;
import org.jmj.mappers.TbItemCatMapper;
import org.jmj.mappers.TbItemDescMapper;
import org.jmj.mappers.TbItemMapper;
import org.jmj.mappers.TbItemParmVoMapper;
import org.jmj.service.ItemSerivce;
import org.jmj.util.ManagerUtil;
import org.jmj.vo.TbItemParmVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

public class ItemServiceImpl implements ItemSerivce {
	
	@Autowired
	private TbItemMapper tbItemMapper;
	
	@Autowired
	private TbItemCatMapper tbItemCatMapper;
	
	@Autowired
	private TbItemParmVoMapper tbItemParmVoMapper;
	
	@Autowired
	private TbItemDescMapper tbItemDescMapper;
	
	@Autowired
	private JmsTemplate jmsTemplate;
	
	@Resource
	private Destination itemTopic;
	
	@Override
	public EasyResultData<TbItem> getResultData(Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<TbItem> list = tbItemMapper.selectByExample(null);
		PageInfo<TbItem> pages = new PageInfo<>(list);
		return new EasyResultData<TbItem>(pages.getTotal(), pages.getList());
	}

	@Override
	public void addNewItem(TbItem item, String desc) throws Exception {
		item.setId(ManagerUtil.newPrimaryKey());
		item.setStatus((byte)1);
		item.setCreated(new Date());
		item.setUpdated(new Date());
		tbItemMapper.insert(item);
		TbItemDesc tbItemDesc = new TbItemDesc();
		tbItemDesc.setItemId(ManagerUtil.newPrimaryKey());
		tbItemDesc.setCreated(new Date());
		tbItemDesc.setUpdated(new Date());
		tbItemDesc.setItemDesc(desc);
		tbItemDescMapper.insert(tbItemDesc);
		
		//发送信息,同步所索引到solr
		jmsTemplate.send(itemTopic, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				TextMessage message = session.createTextMessage(item.getId()+"");
				return message;
			}
		});
	}

	@Override
	public List<ItemTreeNode> getItemCatTree(Long id) {
		ArrayList<ItemTreeNode> result = new ArrayList<>();
		TbItemCatExample example = new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(id);
		List<TbItemCat> list = tbItemCatMapper.selectByExample(example);
		ItemTreeNode itnode = null;
		for (TbItemCat tbItemCat : list) {
			itnode = new ItemTreeNode(tbItemCat.getId(),
					tbItemCat.getName(),tbItemCat.getIsParent() ? "closed" :"open" );
			result.add(itnode);
		}
		return result;
	}

	@Override
	public EasyResultData<TbItemParmVo> getItemsParams(Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<TbItemParmVo> list = tbItemParmVoMapper.selectAll();
		PageInfo<TbItemParmVo> pages = new PageInfo<>(list);
		return new EasyResultData<TbItemParmVo>(pages.getTotal(), pages.getList());
	}

	@Override
	public TbItem getItemById(Long id) {
		TbItemExample example = new TbItemExample();
		org.jmj.bean.TbItemExample.Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(id);
		List<TbItem> list = tbItemMapper.selectByExample(example);
		return list.get(0);
	}

	@Override
	public TbItemDesc getItemDescById(Long itemId) {
		TbItemDescExample example = new TbItemDescExample();
		org.jmj.bean.TbItemDescExample.Criteria criteria = example.createCriteria();
		criteria.andItemIdEqualTo(itemId);
		List<TbItemDesc> list = tbItemDescMapper.selectByExampleWithBLOBs(example);
		return list.get(0);
	}

	@Override
	public void updateItemWithDesc(TbItem item, String desc) {
		TbItemExample itemExample = new TbItemExample();
		org.jmj.bean.TbItemExample.Criteria c = itemExample.createCriteria();
		c.andIdEqualTo(item.getId());
		List<TbItem> byExample = tbItemMapper.selectByExample(itemExample);
		TbItem tbItem = byExample.get(0);
		tbItem.setTitle(item.getTitle());
		tbItem.setBarcode(item.getBarcode());
		tbItem.setImage(item.getImage());
		tbItem.setNum(item.getNum());
		tbItem.setPrice(item.getPrice());
		tbItem.setSellPoint(item.getSellPoint());
		tbItem.setUpdated(new Date());
		tbItemMapper.updateByPrimaryKey(tbItem);
		
		TbItemDescExample example = new TbItemDescExample();
		org.jmj.bean.TbItemDescExample.Criteria criteria = example.createCriteria();
		criteria.andItemIdEqualTo(item.getId());
		List<TbItemDesc> list = tbItemDescMapper.selectByExample(example);
		list.get(0).setItemDesc(desc);
		tbItemDescMapper.updateByPrimaryKeyWithBLOBs(list.get(0));
	}

	@Override
	public void deleteItemsByIds(String ids) throws Exception {
		List<Long> itemIds = new ArrayList<>();
		String[] split = ids.split(",");
		for (String id : split) {
			try {
				itemIds.add(Long.parseLong(id));
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException("无法转换id");
			}
		}
		TbItemExample example = new TbItemExample();
		org.jmj.bean.TbItemExample.Criteria criteria = example.createCriteria();
		criteria.andIdIn(itemIds);
		tbItemMapper.deleteByExample(example);
		
		TbItemDescExample descExample = new TbItemDescExample();
		org.jmj.bean.TbItemDescExample.Criteria descCriteria = descExample.createCriteria();
		descCriteria.andItemIdIn(itemIds);
		tbItemDescMapper.deleteByExample(descExample);
	}
	
	/**
	 * category页面
	 * @return
	 */
	@RequestMapping("/content-category")
	public String contentCategoryPage() {
		return "content-category";
	}

	
	
	
}
