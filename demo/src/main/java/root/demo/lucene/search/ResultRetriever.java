package root.demo.lucene.search;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import root.demo.lucene.indexing.handlers.DocumentHandler;
import root.demo.lucene.indexing.handlers.PDFHandler;
import root.demo.lucene.indexing.handlers.TextDocHandler;
import root.demo.lucene.indexing.handlers.Word2007Handler;
import root.demo.lucene.indexing.handlers.WordHandler;
import root.demo.lucene.model.IndexUnit;
import root.demo.lucene.model.RequiredHighlight;
import root.demo.lucene.model.ResultData;
import root.demo.repository.BookRepository;

@Service
public class ResultRetriever {
	
	@Autowired
	private BookRepository repository;
	
	public ResultRetriever(){
	}

	public List<ResultData> getResults(org.elasticsearch.index.query.QueryBuilder query,
			List<RequiredHighlight> requiredHighlights) {
		if (query == null) {
			return null;
		}
			
		List<ResultData> results = new ArrayList<ResultData>();
       
        for (IndexUnit indexUnit : repository.search(query)) {
        	results.add(new ResultData(indexUnit.getTitle(), indexUnit.getKeywords(), indexUnit.getFilename(), ""));
		}
        
		
		return results;
	}
	
	protected DocumentHandler getHandler(String fileName){
		if(fileName.endsWith(".txt")){
			return new TextDocHandler();
		}else if(fileName.endsWith(".pdf")){
			return new PDFHandler();
		}else if(fileName.endsWith(".doc")){
			return new WordHandler();
		}else if(fileName.endsWith(".docx")){
			return new Word2007Handler();
		}else{
			return null;
		}
	}
}
