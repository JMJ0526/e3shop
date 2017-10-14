package org.jmj.front.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jmj.bean.TbContent;
import org.jmj.bean.TbContentExample;
import org.jmj.front.service.FrontContcentCategoryService;
import org.jmj.jedis.JedisClient;
import org.jmj.mappers.TbContentMapper;
import org.jmj.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class FrontContcentCategoryServiceImpl implements FrontContcentCategoryService {

	@Autowired
	private TbContentMapper tbContentMapper;

	@Autowired
	private JedisClient jedisClient;

	@Override
	public List<TbContent> getConcont(Long cid) {
		try {
			String result = jedisClient.hget(cid + "", "ContentList");
			if (!StringUtils.isEmpty(result)) {
				return JsonUtils.jsonToList(result, TbContent.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		TbContentExample example = new TbContentExample();
		example.createCriteria().andCategoryIdEqualTo(cid);
		List<TbContent> list = tbContentMapper.selectByExample(example);

		try {
			jedisClient.hset(cid + "", "ContentList", JsonUtils.objectToJson(list));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

}
