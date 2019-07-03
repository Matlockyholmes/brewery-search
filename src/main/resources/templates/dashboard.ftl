<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Brewery search</title>
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
  <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
  <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
  <script src="https://kit.fontawesome.com/731e4f2d3a.js"></script>
</head>
<body>
  <div class="jumbotron text-center">
    <i class="fas fa-beer fa-3x"></i>
    <h2 class="card-title h2"><a href="/">Brewery search</a></h2>
    <p class="blue-text my-4">A powerfull brewery search engine powered by Elasticsearch</p>
    <hr class="my-4" />
    <div class="pt-2">
      <form>
        <button class="btn btn-outline-primary waves-effect">Import breweries <i class="fas fa-download ml-1"></i></button>
      </form>
    </div>
  </div>
  <div class="container">
    <form>
      <div class="form-row">
        <div class="form-group col-md-9">
          <input name="query" type="text" class="form-control" id="query" placeholder="Find a brewery" />
          <p class="ml-2"><small><em>83 results (20ms)</em></small></p>
          <div class="alert alert-secondary" role="alert">
  			Search result placeholder
          </div>
        </div>
        <div class="form-group col-md-3">
          <button class="btn btn-success btn-block mb-4">Search</button>
          <div class="alert alert-secondary" role="alert">
  			Facet placeholder
          </div>
        </div>
      </div>
      <div class="alert alert-secondary" role="alert">
  			Pagination placeholder
      </div>
    </form>
  </div>
</body>
</html>