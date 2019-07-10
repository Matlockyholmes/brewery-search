package digital.formica.training.elastic.brewery.controller;

import digital.formica.training.elastic.brewery.domain.Brewery;
import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.client.*;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {

	private String DATA_URL = "https://opendata.visitflanders.org/tourist/activities/breweries.json";
	private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);
	private static final String INDEX_NAME = "INDEX";

	private List<Brewery> getData(){
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<List<Brewery>> response = restTemplate.exchange(DATA_URL, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Brewery>>() {
				});
		return response.getBody();
	}

	private IndicesClient initializeClient(){
		HttpHost host = new HttpHost("localhost:8080", 9200, "protocol");
		RestClientBuilder restClientBuilder = RestClient.builder(host);
		RestHighLevelClient client = new RestHighLevelClient(restClientBuilder);
		IndicesClient indicesClient = client.indices();
		return indicesClient;
	}

	private IndicesClient createIndex(){
		regulateIndex();
		IndicesClient indicesClient = initializeClient();
		try{
			CreateIndexRequest createIndexRequest = new CreateIndexRequest(INDEX_NAME);
			indicesClient.create(createIndexRequest, RequestOptions.DEFAULT);
		} catch (IOException ex){
			LOGGER.error("", ex);
		}
		return indicesClient;
	}

	public void regulateIndex(){
		IndicesClient indicesClient = initializeClient();
		try{
			GetIndexRequest getIndexRequest = new GetIndexRequest(INDEX_NAME);
			boolean exists = indicesClient.exists(getIndexRequest, RequestOptions.DEFAULT);
			if(exists){
				DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(INDEX_NAME);
				indicesClient.delete(deleteIndexRequest, RequestOptions.DEFAULT);
			}
		} catch (IOException ex){
			LOGGER.error("", ex);
		}
	}
	@GetMapping
	public String getDashboard() {
		return "dashboard";
	}

	@PostMapping("import")
	public String importCode(Model model){
		try {
			List<Brewery> breweries = getData();
			if (!(breweries.isEmpty())){
				model.addAttribute("status", "success");
				LOGGER.info("Breweries:{}", breweries.toString());
			} else {
				model.addAttribute("status", "leeg");
			}
		} catch (Exception ex){
			LOGGER.error("",ex);
		}
		try{
			IndicesClient indicesClient = createIndex();
			GetIndexRequest getIndexRequest = new GetIndexRequest(INDEX_NAME);
			boolean exists = indicesClient.exists(getIndexRequest, RequestOptions.DEFAULT);
			if(exists){
				model.addAttribute("indexcheck", "succesvol");
			}
		} catch (IOException ex){
			LOGGER.error("",ex);
		}
		return "dashboard";
	}
}
