package util;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
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

import bean.InterviewsBean;
import bean.PaperBean;
import bean.PatentBean;
import bean.QuestionBean;
import bean.SearchBean;
import bean.UpdatesBean;
import dbtools.DBConnectionProperties;


/**
 * ik分词及lucence索引的类
 * */
public class SearchLogic{
	
	public ResultSet getResult(String sql){  
		if(null == DBConnectionProperties.getConnection()){
			return null;
		}

        return DBConnectionProperties.executeQuery(sql);
        
    }

	/**
	 * 执行数据库插入
	 * */
	public boolean execute(String sql){  
		
		if(null == DBConnectionProperties.getConnection()){
			return false;
		}

        return DBConnectionProperties.execute(sql);
        
    }
	
	public void closeBD(){
		DBConnectionProperties.close();
	}
	
	//创建目录索引
	public void createIndex(ResultSet rs, String table_name,String indexPath){
		
		File indexDir = new File(indexPath);
		//不存在目录就创建
		if(!indexDir.exists()) {
			indexDir.mkdir();
        }
		Directory dir = null;
		
		IndexWriter indexWriter = null;
		
		Analyzer ikanlyzer = new IKAnalyzer();
		
		//设置索引分词类型
		IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_43,ikanlyzer); 

		iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);//有完整的索引直接读，无索引创建
		
		try {
			
			dir = FSDirectory.open(indexDir);
			indexWriter = new IndexWriter(dir,iwc);
			
			//根据不同的表，读取不同格式的数据
			while(rs.next()){
				Document doc = new Document();
				if(table_name.equals("interviews") ){

					doc.add(new TextField("expert_id",rs.getString("expert_id"), Field.Store.YES));
					doc.add(new TextField("date",rs.getString("date"), Field.Store.YES));
					doc.add(new TextField("org",rs.getString("org"),Field.Store.YES));
					doc.add(new TextField("abs",rs.getString("abs"), Field.Store.YES));
					doc.add(new TextField("title",rs.getString("title"), Field.Store.YES));
					doc.add(new TextField("url",rs.getString("url"), Field.Store.YES));
				}else if(table_name.equals("question")){
					doc.add(new TextField("id",rs.getString("id"), Field.Store.YES));
					doc.add(new TextField("first_key",rs.getString("first_key"), Field.Store.YES));
					doc.add(new TextField("table_name",rs.getString("table_name"), Field.Store.YES));
					doc.add(new TextField("index_file",rs.getString("index_file"), Field.Store.YES));
					doc.add(new TextField("second_key",rs.getString("second_key"), Field.Store.YES));
					doc.add(new TextField("field",rs.getString("field"), Field.Store.YES));
					doc.add(new TextField("bean",rs.getString("bean"), Field.Store.YES));
				}else if(table_name.equals("paper")){
					doc.add(new TextField("paper_name",rs.getString("paper_name"), Field.Store.YES));
					doc.add(new TextField("expert_id",rs.getString("expert_id"), Field.Store.YES));
					doc.add(new TextField("abs",rs.getString("abs"), Field.Store.YES));
					doc.add(new TextField("guanjianci",rs.getString("guanjianci"), Field.Store.YES));
					doc.add(new TextField("author_cn",rs.getString("author_cn"), Field.Store.YES));
					doc.add(new TextField("url",rs.getString("url"), Field.Store.YES));
					doc.add(new TextField("expert_org",rs.getString("expert_org"), Field.Store.YES));
					doc.add(new TextField("fundation",rs.getString("fundation"), Field.Store.YES));
					doc.add(new TextField("parent_context",rs.getString("parent_context"), Field.Store.YES));
					doc.add(new TextField("meeting_name",rs.getString("meeting_name"), Field.Store.YES));
					doc.add(new TextField("meeting_date",rs.getString("meeting_date"), Field.Store.YES));
					doc.add(new TextField("meeting_address",rs.getString("meeting_address"), Field.Store.YES));
					doc.add(new TextField("meeting_org",rs.getString("meeting_org"), Field.Store.YES));
				}else if(table_name.equals("patent")){
					doc.add(new TextField("patent_name",rs.getString("patent_name"), Field.Store.YES));
					doc.add(new TextField("expert_id",rs.getString("expert_id"), Field.Store.YES));
					doc.add(new TextField("patent_date",rs.getString("patent_date"), Field.Store.YES));
					doc.add(new TextField("patent_owner",rs.getString("patent_owner"), Field.Store.YES));
					doc.add(new TextField("patent_id",rs.getString("patent_id"), Field.Store.YES));
				}else if(table_name.equals("updates")){
					doc.add(new TextField("date",rs.getString("date"), Field.Store.YES));
					doc.add(new TextField("expert_id",rs.getString("expert_id"), Field.Store.YES));
					doc.add(new TextField("abs",rs.getString("abs"), Field.Store.YES));
				}
				//将读取的数据加入索引
				indexWriter.addDocument(doc);

			}
			
			indexWriter.close();
		} catch (IOException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	 
	//在Search表中检索
	public List<SearchBean> searcherSearchBean(String [] queryStrings, String [] fields, String indexPath){
		ScoreDoc [] hits = null;
		Query query = null;
		
		Analyzer ikanlyzer = new IKAnalyzer(true);

		IndexReader reader = null;
		IndexSearcher searcher = null;
		
		//数据表
		List<SearchBean> searchList = new ArrayList<SearchBean>();
		
		SearchBean bean = null;
		
		try {
			//从目录读取索引
			reader = IndexReader.open(FSDirectory.open(new File(indexPath)));
			searcher = new IndexSearcher(reader);
			
			//对提问分词
			query = MultiFieldQueryParser.parse(Version.LUCENE_43, queryStrings, fields, ikanlyzer);
			
			//查看分词结果
			System.out.println("Query: " + query);
			
			if(searcher != null){
				
				//根据分词在数据库中搜索，返回前100条结果
				TopDocs results = searcher.search(query, 100);
				
				//测试代码
				System.out.println(results);
				hits = results.scoreDocs;
				
				//读取数据
				for(int i = hits.length - 1; i >= 0; i--){
					bean = new SearchBean();
					System.out.println(searcher.doc(hits[i].doc).get("abs") + "|||||" + searcher.doc(hits[i].doc).get("title")+ "|||||" + 
					searcher.doc(hits[i].doc).get("url"));
					bean.setAbs(searcher.doc(hits[i].doc).get("abs"));
					bean.setTitle(searcher.doc(hits[i].doc).get("title"));
					bean.setUrl(searcher.doc(hits[i].doc).get("url"));
					searchList.add(bean);
				}
				
				//输出结果长度
				System.out.println(hits.length);
			}
			
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return searchList;
	}
	

	//在question表中检索
	public List<QuestionBean> searcherQuestionBean(String [] queryStrings, String [] fields, String indexPath){
		ScoreDoc [] hits = null;
		Query query = null;
		
		Analyzer ikanlyzer = new IKAnalyzer(true);

		IndexReader reader = null;
		IndexSearcher searcher = null;
		//数据表
		List<QuestionBean> searchList = new ArrayList<QuestionBean>();
		
		QuestionBean bean = null;
		
		try {
			//从目录读取索引
			reader = IndexReader.open(FSDirectory.open(new File(indexPath)));
			searcher = new IndexSearcher(reader);
			
			//对提问分词
			query = MultiFieldQueryParser.parse(Version.LUCENE_43, queryStrings, fields, ikanlyzer);
			System.out.println("Query: " + query);
			
			if(searcher != null){
				//根据分词在数据库中搜索，返回第一条结果
				TopDocs results = searcher.search(query, 1);
								
				hits = results.scoreDocs;
				
				//测试代码
				System.out.println("结果长度："+hits.length);
				
				//读取数据
				for(int i = hits.length - 1; i >= 0; i--){
					bean = new QuestionBean();
					System.out.println(searcher.doc(hits[i].doc).get("first_key") + "关键字|||||"
										+ searcher.doc(hits[i].doc).get("second_key")+ "关键字细分|||||"
										+ searcher.doc(hits[i].doc).get("field")+ "|||||"
										+ searcher.doc(hits[i].doc).get("table_name")+ "|||||"
										+ searcher.doc(hits[i].doc).get("index_file")+ "|||||"
										+ searcher.doc(hits[i].doc).get("bean")+ "|||||");
					bean.setFirst_key(searcher.doc(hits[i].doc).get("first_key"));
					bean.setSecond_key(searcher.doc(hits[i].doc).get("second_key"));
					bean.setTable_name(searcher.doc(hits[i].doc).get("table_name"));
					bean.setIndex_file(searcher.doc(hits[i].doc).get("index_file"));
					bean.setField(searcher.doc(hits[i].doc).get("field"));
					bean.setBean(searcher.doc(hits[i].doc).get("bean"));
					searchList.add(bean);
				}
				
				//如果查询不到，存入一个空数据，防止系统报错
				if(hits.length == 0)
				{
					bean = new QuestionBean();
					bean.setFirst_key("null");
					bean.setSecond_key("null");
					bean.setTable_name("null");
					bean.setIndex_file("null");
					bean.setField("null");
					bean.setBean("null");
					searchList.add(bean);
				}
				//输出结果长度
				System.out.println(hits.length);
			}
			
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return searchList;
	}
	
	//在Paper表中检索
	public List<PaperBean> searcherPaperBean(String [] queryStrings, String [] fields, String indexPath){
		ScoreDoc [] hits = null;
		Query query = null;
		
		Analyzer ikanlyzer = new IKAnalyzer(true);
		
		IndexReader reader = null;
		IndexSearcher searcher = null;
		
		List<PaperBean> searchList = new ArrayList<PaperBean>();
		
		PaperBean bean = null;
		
		try {
			//从目录读取索引
			reader = IndexReader.open(FSDirectory.open(new File(indexPath)));
			searcher = new IndexSearcher(reader);
			
			//分词
			query = MultiFieldQueryParser.parse(Version.LUCENE_43, queryStrings, fields, ikanlyzer);
			
			//测试代码
			System.out.println("Query: " + query);
			
			if(searcher != null){
				//根据分词在数据库中搜索，返回前100条结果
				TopDocs results = searcher.search(query, 100);
				hits = results.scoreDocs;
				
				//读取数据
				for(int i = hits.length - 1; i >= 0; i--){
					bean = new PaperBean();
					System.out.println(searcher.doc(hits[i].doc).get("paper_name") + "|||||" + searcher.doc(hits[i].doc).get("expert_id")+ "|||||" 
					+ searcher.doc(hits[i].doc).get("guanjianci"));
					bean.setAbs(searcher.doc(hits[i].doc).get("abs"));
					bean.setExpert_id(searcher.doc(hits[i].doc).get("expert_id"));
					bean.setPaper_name(searcher.doc(hits[i].doc).get("paper_name"));
					bean.setExpert_org(searcher.doc(hits[i].doc).get("expert_org"));
					bean.setGuanjianci(searcher.doc(hits[i].doc).get("guanjianci"));
					bean.setFundation(searcher.doc(hits[i].doc).get("fundation"));
					bean.setParent_context(searcher.doc(hits[i].doc).get("parent_context"));
					bean.setMeeting_name(searcher.doc(hits[i].doc).get("meeting_name"));
					bean.setMeeting_date(searcher.doc(hits[i].doc).get("meeting_date"));
					bean.setMeeting_address(searcher.doc(hits[i].doc).get("meeting_address"));
					bean.setMeeting_org(searcher.doc(hits[i].doc).get("meeting_org"));
					bean.setAuthor_cn(searcher.doc(hits[i].doc).get("author_cn"));
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
	
	
	//在Patent表中检索
	public List<PatentBean> searcherPatentBean(String [] queryStrings, String [] fields, String indexPath){
		ScoreDoc [] hits = null;
		Query query = null;
		
		Analyzer ikanlyzer = new IKAnalyzer(true);
		
		IndexReader reader = null;
		IndexSearcher searcher = null;
		
		List<PatentBean> searchList = new ArrayList<PatentBean>();
		
		PatentBean bean = null;
		
		try {
			//从目录读取索引
			reader = IndexReader.open(FSDirectory.open(new File(indexPath)));
			searcher = new IndexSearcher(reader);
			//分词
			query = MultiFieldQueryParser.parse(Version.LUCENE_43, queryStrings, fields, ikanlyzer);
			
			//测试代码
			System.out.println("Query: " + query);
			
			if(searcher != null){
				//根据分词在数据库中搜索，返回前100条结果
				TopDocs results = searcher.search(query, 100);
				hits = results.scoreDocs;
				
				//读取数据
				for(int i = hits.length - 1; i >= 0; i--){
					bean = new PatentBean();
					System.out.println(searcher.doc(hits[i].doc).get("paper_name") + "|||||" + searcher.doc(hits[i].doc).get("expert_id")+ "|||||" 
					+ searcher.doc(hits[i].doc).get("guanjianci"));
					bean.setPatent_name(searcher.doc(hits[i].doc).get("patent_name"));
					bean.setExpert_id(searcher.doc(hits[i].doc).get("expert_id"));
					bean.setPatent_date(searcher.doc(hits[i].doc).get("patent_date"));
					bean.setPatent_owner(searcher.doc(hits[i].doc).get("patent_owner"));
					bean.setPatent_id(searcher.doc(hits[i].doc).get("patent_id"));
					searchList.add(bean);
				}
				//测试代码
				System.out.println(hits.length);
			}
			
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return searchList;
	}	

	
	//在Updates表中检索
	public List<UpdatesBean> searcherUpdatesBean(String [] queryStrings, String [] fields, String indexPath){
		ScoreDoc [] hits = null;
		Query query = null;
		
		Analyzer ikanlyzer = new IKAnalyzer(true);

		IndexReader reader = null;
		IndexSearcher searcher = null;
		
		List<UpdatesBean> searchList = new ArrayList<UpdatesBean>();
		
		UpdatesBean bean = null;
		
		try {
			
			//从目录读取索引
			reader = IndexReader.open(FSDirectory.open(new File(indexPath)));
			searcher = new IndexSearcher(reader);
			
			//分词
			query = MultiFieldQueryParser.parse(Version.LUCENE_43, queryStrings, fields, ikanlyzer);
			
			//测试代码
			System.out.println("Query: " + query);
			
			if(searcher != null){
				//根据分词在数据库中搜索，返回前100条结果
				TopDocs results = searcher.search(query, 100);
				hits = results.scoreDocs;
				//读取数据
				for(int i = hits.length - 1; i >= 0; i--){
					bean = new UpdatesBean();
					System.out.println(searcher.doc(hits[i].doc).get("paper_name") + "|||||" + searcher.doc(hits[i].doc).get("expert_id")+ "|||||" 
							+ searcher.doc(hits[i].doc).get("guanjianci"));
					bean.setDate(searcher.doc(hits[i].doc).get("date"));
					bean.setExpert_id(searcher.doc(hits[i].doc).get("expert_id"));
					bean.setAbs(searcher.doc(hits[i].doc).get("abs"));
					searchList.add(bean);
				}
				
				//测试代码
				System.out.println(hits.length);
			}
			
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return searchList;
	}	
	
	
	//在Interviews表中检索
	public List<InterviewsBean> searcherInterviewsBean(String [] queryStrings, String [] fields, String indexPath){
		ScoreDoc [] hits = null;
		Query query = null;
		
		Analyzer ikanlyzer = new IKAnalyzer(true);
		
		IndexReader reader = null;
		IndexSearcher searcher = null;
		//数据表
		List<InterviewsBean> searchList = new ArrayList<InterviewsBean>();
		
		InterviewsBean bean = null;
		
		try {
			//从目录读取索引
			reader = IndexReader.open(FSDirectory.open(new File(indexPath)));
			searcher = new IndexSearcher(reader);
			
			//分词
			query = MultiFieldQueryParser.parse(Version.LUCENE_43, queryStrings, fields, ikanlyzer);
			
			//测试代码
			System.out.println("Query: " + query);
			
			if(searcher != null){
				//根据分词在数据库中搜索，返回前100条结果
				TopDocs results = searcher.search(query, 100);
				hits = results.scoreDocs;
				
				//读取数据
				for(int i = hits.length - 1; i >= 0; i--){
					bean = new InterviewsBean();
					System.out.println(searcher.doc(hits[i].doc).get("paper_name") + "|||||" + searcher.doc(hits[i].doc).get("expert_id")+ "|||||" 
							+ searcher.doc(hits[i].doc).get("guanjianci"));
					bean.setDate(searcher.doc(hits[i].doc).get("date"));
					bean.setExpert_id(searcher.doc(hits[i].doc).get("expert_id"));
					bean.setAbs(searcher.doc(hits[i].doc).get("abs"));
					bean.setTitle(searcher.doc(hits[i].doc).get("title"));
					bean.setOrg(searcher.doc(hits[i].doc).get("org"));
					bean.setAuthor(searcher.doc(hits[i].doc).get("author"));
					bean.setUrl(searcher.doc(hits[i].doc).get("url"));
					searchList.add(bean);
				}
				
				//测试代码
				System.out.println(hits.length);
			}
			
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return searchList;
	}	
	
	
	//测试代码
	public static void main(String[] args) {
		
		SearchLogic search = new SearchLogic();
		
		String [] queryStrings = {"大规模数据库"};
		String [] fields = {"first_key"};
		String indexPath = "D:\\paper";
		
//		search.createIndex(search.getResult("select * from paper"),"paper", indexPath);
//		search.searcherPaperBean(queryStrings, fields, indexPath);
		search.createIndex(search.getResult("select * from question"),"question", indexPath);
		search.searcherQuestionBean(queryStrings, fields, indexPath);
//		search.createIndex(search.getResult("select expert_id,date,title,org,author,abs,url from interviews"),"interviews", indexPath);
//		search.searcherSearchBean(queryStrings, fields, indexPath);
		search.closeBD();
	}
	
}