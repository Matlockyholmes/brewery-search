# Brewery search

## General info

### Endpoint

We are going to use the following endpoint:

https://opendata.visitflanders.org/tourist/activities/breweries.json

We are interested in the following properties:
* name
* street
* houseNumber
* boxNumber
* postalCode
* city
* province
* description
* website

### Maven Pom

#### Properties
```xml
<properties>
  <java.version>11</java.version>
  <elasticsearch.version>7.2.0</elasticsearch.version>
</properties>
```

#### Dependencies
```xml
<!-- Spring boot web-->
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<!-- Elastic search-->
<dependency>
  <groupId>org.elasticsearch.client</groupId>
  <artifactId>elasticsearch-rest-high-level-client</artifactId>
</dependency>
<dependency>
  <groupId>org.elasticsearch</groupId>
  <artifactId>elasticsearch</artifactId>
</dependency>
<!-- Freemarker-->
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-freemarker</artifactId>
</dependency>
```

## Spring boot

### Application properties

```properties
spring.freemarker.template-loader-path: classpath:/templates
spring.freemarker.suffix: .ftl
```

## Elastic search configuration

### Settings

```json
 {  "number_of_shards" : "1",
    "number_of_replicas" : "1",
	"analysis": {
		"filter": {
			"dutch_stop": {
				"type": "stop",
				"stopwords": "_dutch_"
			},
			"dutch_keywords": {
				"type": "keyword_marker",
				"keywords": []
			},
			"dutch_stemmer": {
				"type": "stemmer",
				"language": "dutch_kp"
			},
			"dutch_override": {
				"type": "stemmer_override",
				"rules": ["hoegaarden=>hoegaarden","bruine=>bruin","brusselse=>brussel"]
			}
		},
		"analyzer": {
			"formica_dutch": {
				"tokenizer": "standard",
				"filter": [
					"lowercase",
					"dutch_stop",
					"dutch_keywords",
					"dutch_override",
					"dutch_stemmer"
				]
			}
		}
	}
}
```

### Mapping

```json
{
	"dynamic": false,
	"properties": {
		"city": {
			"type": "keyword"
		},
		"description": {
			"type": "text",
			"analyzer": "formica_dutch"
		},
		"name": {
			"type": "text",
			"analyzer": "formica_dutch"
		},
		"province": {
			"type": "keyword"
		},
		"street": {
			"type": "text"
		}
	}
}
```

## Elastic search snippets

### Initialize Client

```java
HttpHost host = new HttpHost("hostname", port, "protocol");
RestClientBuilder restClientBuilder = RestClient.builder(host);
RestHighLevelClient client = new RestHighLevelClient(restClientBuilder);
```

### Get the indices client

```java
IndicesClient indicesClient = client.indices();
```

### Create an index

```java
CreateIndexRequest createIndexRequest = new CreateIndexRequest(INDEX_NAME);
createIndexRequest.mapping(getConfigurationFromFile(MAPPING), XContentType.JSON);
createIndexRequest.settings(getConfigurationFromFile(SETTINGS), XContentType.JSON);
indicesClient.create(createIndexRequest, RequestOptions.DEFAULT);
```


### Delete an index

```java
DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest("INDEX_NAME");
indicesClient.delete(deleteIndexRequest, RequestOptions.DEFAULT);
```

### Check if an index exists

```java
GetIndexRequest getIndexRequest = new GetIndexRequest(INDEX_NAME);
boolean exists = indicesClient.exists(getIndexRequest, RequestOptions.DEFAULT);
```

### Perform a bulk update

```java
BulkRequest bulkRequest = new BulkRequest();
for(LOOP_OVER_YOUR_REQUESTS) {
  IndexRequest indexRequest = new IndexRequest(INDEX_NAME);
  indexRequest.source(JSON_DOCUMENT, XContentType.JSON);
  bulkRequest.add(indexRequest);
}
client.bulk(bulkRequest, RequestOptions.DEFAULT);

```

### Perform a search

```java
SearchRequest searchRequest = new SearchRequest(INDEX_NAME);
SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
// ...
// Add stuff to your searchSourceBuilder
// ...
searchRequest.source(searchSourceBuilder);
SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
```

### Create a query

In this example we create a simple **match all** query but there are a lot more query builders you can add!

```java
searchSourceBuilder.query(QueryBuilders.matchAllQuery());
```

https://www.elastic.co/guide/en/elasticsearch/client/java-rest/current/java-rest-high-query-builders.html

### Highlighting

```java
HighlightBuilder highlightBuilder = new HighlightBuilder();
HighlightBuilder.Field field = new HighlightBuilder.Field(SOURCE_PROPERTY);
highlightBuilder.field(field);
highlightBuilder.preTags(PRETAG);
highlightBuilder.postTags(POSTTAG);
searchSourceBuilder.highlighter(highlightBuilder);
```

### Aggregations

```java
TermsAggregationBuilder aggregationBuilder = AggregationBuilders.terms(NAME);
aggregationBuilder.field(KEYWORD);
searchSourceBuilder.aggregation(aggregationBuilder);
```

### Pagination

Simple paginagion can be achieved by starting your query from the hit you want:

```java
searchSourceBuilder.from(STARTING_POINT);
```

There are other pagination mechanics in *Elasticsearch* but that is beyond the scope of this excercise.

---

## Other usefull java snippets

### RestTemplate request

```java
RestTemplate restTemplate = new RestTemplate();
ResponseEntity<List<Object>> response = restTemplate.exchange(DATA_URL, HttpMethod.GET, null,
  new ParameterizedTypeReference<List<Object>>() {
  });
String json = response.getBody();
```

### Read a file

Read a file from the classpath and transform it into a string

```java
File file = new ClassPathResource(fileName).getFile();
String text = new String(Files.readAllBytes(file.toPath()));
```

### Marshalling

Use **ObjectMapper** to transform a POJO into a JSON String

```java
ObjectMapper objectMapper = new ObjectMapper();
objectMapper.writeValueAsString(OBJECT);
```

### Logging

Never use `System.out.println("Some message");` in your professional applications. Always use a logger library like **Log4j**

```java
private static final Logger LOGGER = LoggerFactory.getLogger(CLASS_NAME);

public void someMethod() {
  LOGGER.info("Some message");
}
```

## Html Snippets

### Success message

<div class="container">
  <i class="far fa-check-circle text-success"></i>
  <strong class="mr-auto text-success">Succes</strong>
  <div class="font-weight-lighter font-italic">83 breweries were imported</div>
</div>

### Search result

```html
<ul class="list-unstyled p-2">
  <li class="mb-4">
    <p class="lead mb-0">
      <a href="https://thewebsite">the name</a>
    </p>
    <p class="text-success mb-0">https://thewebsite</p>
    <p>Description</p>
    <p>
      <small class="text-muted">street number postcode <strong>city</strong><small>
    </p>
  </li>
</ul>
```

### Facet

```html
<h5 class="mt-3">My Facest</h5>
<div class="form-check">
  <input class="form-check-input" type="checkbox" />
  <label class="form-check-label" for="input">Checkbox 1
  </label>
</div>
```


### Pagination

```html
<nav>
  <ul class="pagination pagination-lg">
    <li class="page-item active" aria-current="page">
      <span class="page-link">ACTIVE_PAGE</span>
    </li>
	<li class="page-item">
      <button class="page-link" href="#">OTHER_PAGE</button>
    </li>
  </ul>
</nav>
```

## Freemarker documentation

* **Assign**: https://freemarker.apache.org/docs/ref_directive_assign.html
* **If**: https://freemarker.apache.org/docs/ref_directive_if.html
* **List**: https://freemarker.apache.org/docs/ref_directive_list.html
* **Builtins**: https://freemarker.apache.org/docs/ref_builtins_alphaidx.html