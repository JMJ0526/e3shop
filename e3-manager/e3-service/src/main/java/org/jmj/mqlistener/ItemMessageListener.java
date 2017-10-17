package org.jmj.mqlistener;

import java.io.IOException;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.jmj.bean.TbItem;
import org.jmj.service.ItemSerivce;
import org.jmj.solr.SearhClientBuilder;

public class ItemMessageListener implements MessageListener{
	
	//集群
	/*@Autowired
	private SolrCloudClientBuilder builder;*/
	//单机
	private SearhClientBuilder builder;
	
	private ItemSerivce itemSerivce;
	
	@Override
	public void onMessage(Message message) {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e2) {
			e2.printStackTrace();
		}
		TextMessage itemMsg = (TextMessage) message;
		Long itemId = null;
		try {
			itemId = new Long(itemMsg.getText());
		} catch (NumberFormatException e1) {
			e1.printStackTrace();
		} catch (JMSException e1) {
			e1.printStackTrace();
		}
		TbItem item = itemSerivce.getItemById(itemId);
		//CloudSolrClient client = builder.build();
		HttpSolrClient client = builder.build();
		SolrInputDocument doc = new SolrInputDocument();
		doc.addField("id", item.getId());
		doc.addField("image", item.getImage());
		doc.addField("price", item.getPrice());
		doc.addField("title", item.getTitle());
		doc.addField("updated", item.getUpdated());
		doc.addField("sellPoint", item.getSellPoint());
		try {
			client.add(doc);
			client.commit();
			client.close();
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
