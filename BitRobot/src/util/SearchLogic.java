package util;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

import bean.SearchBean;
import dbtools.DBConnectionProperties;

public class SearchLogic{
	
	public ResultSet getResult(String sql){  
		
		if(null == DBConnectionProperties.getConnection()){
			return null;
		}

        return DBConnectionProperties.executeQuery(sql);
        
    }
	
	public void closeBD(){
		DBConnectionProperties.close();
	}
	
	public void createIndex(ResultSet rs, String indexPath){
		
		File indexDir = new File(indexPath);
		
		Directory dir = null;
		
		IndexWriter indexWriter = null;
		
//		Analyzer ikanlyzer = new IKAnalyzer(true);
//		Analyzer ikanlyzer = new StandardAnalyzer(Version.LUCENE_43);
		SmartChineseAnalyzer ikanlyzer = new SmartChineseAnalyzer(Version.LUCENE_43);

		
		IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_43,ikanlyzer); 

		iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);//有完整的索引直接读，无索引创建
//		iwc.setOpenMode(OpenMode.APPEND);
		
		try {
			
			dir = FSDirectory.open(indexDir);
			indexWriter = new IndexWriter(dir,iwc);
			
			while(rs.next()){
				Document doc = new Document();
//				doc.add(new StringField("title",rs.getString("title"), Field.Store.YES));
//				doc.add(new StringField("abs",rs.getString("abs"), Field.Store.YES));
//				doc.add(new StringField("url",rs.getString("abs"),Field.Store.YES));
				doc.add(new TextField("abs",rs.getString("abs"), Field.Store.YES));
				doc.add(new TextField("title",rs.getString("title"), Field.Store.YES));
				doc.add(new TextField("url",rs.getString("url"), Field.Store.YES));
//				doc.add(new Field("name",rs.getString("Au_name"), Field.Store.YES,Field.Index.ANALYZED));
				indexWriter.addDocument(doc);
			}
			
			indexWriter.close();
		} catch (IOException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public List<SearchBean> searcher(String [] queryStrings, String [] fields, String indexPath){
		ScoreDoc [] hits = null;
		Query query = null;
		
		Analyzer ikanlyzer = new IKAnalyzer(true);
//		Analyzer ikanlyzer = new StandardAnalyzer(Version.LUCENE_43);
//		SmartChineseAnalyzer ikanlyzer = new SmartChineseAnalyzer(Version.LUCENE_43);

		IndexReader reader = null;
		IndexSearcher searcher = null;
		
		List<SearchBean> searchList = new ArrayList<SearchBean>();
		
		SearchBean bean = null;
		
		try {
			reader = IndexReader.open(FSDirectory.open(new File(indexPath)));
			searcher = new IndexSearcher(reader);
			query = MultiFieldQueryParser.parse(Version.LUCENE_43, queryStrings, fields, ikanlyzer);
			
			System.out.println("Query: " + query);
			
			if(searcher != null){
				
				TopDocs results = searcher.search(query, 100);
				hits = results.scoreDocs;
				
				for(int i = hits.length - 1; i >= 0; i--){
					bean = new SearchBean();
					System.out.println(searcher.doc(hits[i].doc).get("abs") + "|||||" + searcher.doc(hits[i].doc).get("title")+ "|||||" + searcher.doc(hits[i].doc).get("url"));
					bean.setAbs(searcher.doc(hits[i].doc).get("abs"));
					bean.setTitle(searcher.doc(hits[i].doc).get("title"));
					bean.setUrl(searcher.doc(hits[i].doc).get("url"));
					searchList.add(bean);
				}
				
				System.out.println(hits.length);
			}
			
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return searchList;
	}
	
	public static void main(String[] args) {
		
		SearchLogic search = new SearchLogic();
		
		String [] queryStrings = {"数据库","数据库*"};
		String [] fields = {"title","abs"};
		String indexPath = "D:\\index1";
		
		search.createIndex(search.getResult("select expert_id,date,title,org,author,abs,url from interviews"), indexPath);
		search.searcher(queryStrings, fields, indexPath);
		search.closeBD();
	}
	
}