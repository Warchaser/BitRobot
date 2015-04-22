package util;

import java.io.File;
import java.io.IOException;
import java.sql.*;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.apache.lucene.queryparser.classic.QueryParser;

import dbtools.DBConnectionProperties;

public class DBLucene {

	public ResultSet getResult(String sql){
		Connection conn = DBConnectionProperties.getConnection();
		try{
			Statement stmt = conn.createStatement();
			ResultSet rs=stmt.executeQuery(sql);
			return rs;
		}catch(SQLException e){
			e.printStackTrace();
			
		}
		return null;
	}
	
	public void Index(ResultSet rs){
		try{
			File indexDir = new File("D:\\index1");  
			Directory dir = FSDirectory.open(indexDir);
			Analyzer luceneAnalyzer = new StandardAnalyzer(Version.LUCENE_40);   
			IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_40,luceneAnalyzer); //第一个参数是Lucene的版本，第二个参数是分析器
		    iwc.setOpenMode(OpenMode.CREATE);
		    IndexWriter indexWriter = new IndexWriter(dir,iwc);  
		    while(rs.next()){
		    	Document doc=new Document();
		    	doc.add(new Field("title",rs.getString("title"), Field.Store.YES,Field.Index.ANALYZED));
		    	doc.add(new Field("abs",rs.getString("abs"), Field.Store.YES,Field.Index.ANALYZED));
//		    	doc.add(new Field("name",rs.getString("Au_name"), Field.Store.YES,Field.Index.ANALYZED));
		    	indexWriter.addDocument(doc);
		    	
		    }
		    
		    indexWriter.close();
		}catch(IOException e){
			e.printStackTrace();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	public void seacher(String[] queryString){
		String index = "D:\\index1"; 
		ScoreDoc[] hits = null;
		String[] fields={"abs","title"};
		  Query query = null; //Query是一个抽象类，这个类的目的是把用户输入的查询字符串封装成Lucene能够识别的Query。
		    Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_40);     
		try{
			IndexReader reader = IndexReader.open(FSDirectory.open(new File(index)));   
	 	    IndexSearcher searcher = new IndexSearcher(reader);  
	 	   // QueryParser qp = new QueryParser(Version.LUCENE_40,"id", analyzer);  
	 	   query=MultiFieldQueryParser.parse(Version.LUCENE_40,queryString, fields, analyzer);
	 	   // query = qp.parse(queryString); 
	 	  if (searcher != null) {      
	 		    
		        TopDocs results = searcher.search(query,10);    //返回最多为10条记录
		        hits = results.scoreDocs; 
//		            System.out.println("ID      "+"Au_id    "+"Au_name");
		        for(int i=hits.length-1;i>=0;i--){
		        	
		        System.out.println(searcher.doc(hits[i].doc).get("abs")+"   "+"    "+ searcher.doc(hits[i].doc).get("title"));
		        	
		        	
		        }
		       
		        if (hits.length > 0) {      
		            
		            System.out.println("找到:" + hits.length +"个结果!");     
		        }     
	 	  }
		}catch (org.apache.lucene.queryparser.classic.ParseException e) {     
	        e.printStackTrace();
	    } catch(IOException e1){
	    	e1.printStackTrace();
	    }
		
	}
	public static void main(String[] args) throws Exception{
       
		String[] queryString={"数据库","数据库*"};
		DBLucene lunce=new DBLucene();
		lunce.Index(lunce.getResult("select expert_id,date,title,org,author,abs,url from interviews"));
		lunce.seacher(queryString);
		
	}

}
