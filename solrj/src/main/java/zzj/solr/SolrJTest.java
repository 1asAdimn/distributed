package zzj.solr;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class SolrJTest {




    @Test
    public void  addSolr(){
        try {
            String url = "http://59.110.233.202:8983/solr/testcore";
            HttpSolrClient solrClient =  new HttpSolrClient.Builder(url).build();
            SolrInputDocument inputDocument = new SolrInputDocument();
            inputDocument.addField("id","4");
            inputDocument.addField("name","myfield3");
            solrClient.commit();
            solrClient.add(inputDocument);
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Test
    public void  deleteSolr(){
        try {
            String url = "http://59.110.233.202:8983/solr/testcore";
            HttpSolrClient solrClient =  new HttpSolrClient.Builder(url).build();
            solrClient.deleteById("4");
            solrClient.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testQuery(){
        try {
            String url = "http://59.110.233.202:8983/solr/testcore";
            HttpSolrClient solrClient = new HttpSolrClient.Builder(url).build();

            //封装了所有查询条件
            SolrQuery params = new SolrQuery();
            params.setQuery("name:苹果");
            //排序
            params.setSort("price", SolrQuery.ORDER.desc);
            //分页
            params.setStart(0);
            params.setRows(1);
            //高亮
            params.setHighlight(true);
            params.addHighlightField("name");
            params.setHighlightSimplePre("<span>");
            params.setHighlightSimplePost("</span>");

            QueryResponse response = solrClient.query(params);
            SolrDocumentList list = response.getResults();
            System.out.println("总条数："+list.getNumFound());

            //高亮数据
            Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();

            for(SolrDocument doc :list){
                System.out.println(doc.get("id"));
                Map<String, List<String>> map = highlighting.get(doc.get("id"));
                List<String> HLList = map.get("name");
                if(HLList!=null&&HLList.size()>0){//显示高亮数据
                    System.out.println(HLList.get(0));
                }else{
                    System.out.println(doc.get("name"));
                }
                System.out.println(doc.get("price"));
                System.out.println("===================");
            }
            solrClient.close();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

