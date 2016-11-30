# Ads-Board
Restful API. Adverts board.
Documentation: https://docs.google.com/spreadsheets/d/1r3DN5eK8weqfvPauLsXdgniRbzXsvn54JhfnOk80KPE/edit?usp=sharing
			
			
			
	Аутентификация пользователя		
url	/users/login		
method	GET		
			
Request			
header	Authorization	Basic login:password	
			
Response			
body	"{
  ""name"": ""Bogdan"",
  ""surname"": ""Fedoronchuk"",
  ""phone"": ""752 25 50"",
  ""email"": ""haistler97@ukr.net"",
  ""registrationDate"": ""2016-11-19T12:31:22"",
  ""regionName"": ""Одесская область"",
  ""countryName"": ""Украина"",
  ""admin"": false,
  ""_links"": {
    ""self"": {
      ""href"": ""http://localhost:8080/users/1""
    },
    ""image"": {
      ""href"": ""http://localhost:8080/users/1/image""
    }
  },
  ""id"": 1
}"		
			
			
	Регистрация пользователя		
url	/users		
method	POST		
			
Request			
body	"{
    ""name"":""Bodya"",
    ""surname"":""Fedoronchuk"",
    ""login"":""haistler"",
    ""password"":""1234"",
    ""phone"":""752 65 70"",
    ""email"":""haistler@ukr.net"",
    ""region"": 1,
    ""image"": ""data:image/jpeg;base64,[encodedInBaseImage]""
}"		
			
Response			
body	"{
  ""name"": ""Bogdan"",
  ""surname"": ""Fedoronchuk"",
  ""phone"": ""754 24 30"",
  ""email"": ""haistler21@ukr.net"",
  ""registrationDate"": ""2016-11-19T12:31:22"",
  ""regionName"": ""Одесская область"",
  ""countryName"": ""Украина"",
  ""admin"": false,
  ""_links"": {
    ""self"": {
      ""href"": ""http://localhost:8080/users/1""
    },
    ""image"": {
      ""href"": ""http://localhost:8080/users/1/image""
    }
  },
  ""id"": 1
}"		
			
			
	Извлечение категорий объявлений		
url	/categories		
method	GET		
			
Request			
-			
			
Response			
body	"{
  ""_embedded"": {
    ""categories"": [
      {
        ""name"": ""Автомобили"",
        ""_links"": {
          ""self"": {
            ""href"": ""http://localhost:8080/categories/1""
          }
        },
        ""id"": 1
      },
      {
        ""name"": ""Промшленность"",
        ""_links"": {
          ""self"": {
            ""href"": ""http://localhost:8080/categories/2""
          }
        },
        ""id"": 2
      }
    ]
  },
  ""_links"": {
    ""self"": {
      ""href"": ""http://localhost:8080/categories""
    }
  }
}"		
			
			
			
	Извлечение подкатегорий объявлений		
url	/categories/{categoryId}/subcategories		
method	GET		
			
Request			
-			
			
Response			
body	"{
  ""_embedded"": {
    ""subcategories"": [
      {
        ""name"": ""Грузовики"",
        ""_links"": {
          ""self"": {
            ""href"": ""http://localhost:8080/categories/1/subcategories/1""
          }
        },
        ""id"": 1
      },
      {
        ""name"": ""Легковые"",
        ""_links"": {
          ""self"": {
            ""href"": ""http://localhost:8080/categories/1/subcategories/2""
          }
        },
        ""id"": 2
      }
    ]
  },
  ""_links"": {
    ""self"": {
      ""href"": ""http://localhost:8080/categories/1/subcategories""
    }
  }
}"		
			
			
	Извлечение возможных валют		
url	/currencies		
method	GET		
			
Request			
-			
			
Response			
body	"{
  ""_embedded"": {
    ""currencies"": [
      {
        ""abbreviation"": ""грн"",
        ""_links"": {
          ""self"": {
            ""href"": ""http://localhost:8080/currencies/1""
          }
        },
        ""id"": 1
      },
      {
        ""abbreviation"": ""долл"",
        ""_links"": {
          ""self"": {
            ""href"": ""http://localhost:8080/currencies/2""
          }
        },
        ""id"": 2
      }
    ]
  },
  ""_links"": {
    ""self"": {
      ""href"": ""http://localhost:8080/currencies""
    }
  }
}"		
			
	Извлечение возможных маркеров(пометок)		
url	/markers		
method	GET		
			
Request			
-			
			
Response			
body	"{
  ""_embedded"": {
    ""markers"": [
      {
        ""name"": ""дешево"",
        ""_links"": {
          ""self"": {
            ""href"": ""http://localhost:8080/markers/2""
          }
        },
        ""id"": 2
      },
      {
        ""name"": ""срочно"",
        ""_links"": {
          ""self"": {
            ""href"": ""http://localhost:8080/markers/1""
          }
        },
        ""id"": 1
      }
    ]
  },
  ""_links"": {
    ""self"": {
      ""href"": ""http://localhost:8080/markers""
    }
  }
}"		
			
			
	Извлечение объявления		
url	/adverts/{advertId}		
method	GET		
			
Request			
-			
			
Response			
body	"{
  ""advertId"": 4,
  ""title"": ""Second ad"",
  ""description"": ""My description"",
  ""addTime"": ""2016-11-23T13:56:27"",
  ""views"": 0,
  ""tags"": [
    {
      ""name"": ""б/у"",
      ""id"": 1
    },
    {
      ""name"": ""зарубежный"",
      ""id"": 2
    }
  ],
  ""price"": 25.56,
  ""owner"": {
    ""name"": ""Bodya"",
    ""surname"": ""Fedoronchuk"",
    ""phone"": ""752 25 50"",
    ""email"": ""haistler@ukr.net"",
    ""id"": 1
  },
  ""region"": {
    ""name"": ""Одесская область"",
    ""id"": 1
  },
  ""country"": {
    ""name"": ""Украина"",
    ""id"": 1
  },
  ""category"": {
    ""name"": ""Автомобили"",
    ""id"": 1
  },
  ""subcategory"": {
    ""name"": ""Легковые"",
    ""id"": 1
  },
  ""marker"": {
    ""name"": ""Срочно"",
    ""id"": 1
  },
  ""currency"": {
    ""abbreviation"": ""грн"",
    ""id"": 1
  }
}"		
			
	Добавление объявления		
url	/adverts		
method	POST		
			
Request			
header	Authorization	Basic login:password	
body	"{
  ""title"": ""Second ad"",
  ""description"": ""My description"",
  ""tags"": [
    {
      ""name"": ""б/у""
    },
    {
      ""name"": ""random_tag""
    }
  ],
  ""price"": 25.56,
  ""owner"": {
    ""id"": 1
  },
  ""subcategory"": {
    ""id"": 1
  },
  ""marker"": {
    ""id"": 1
  },
  ""currency"": {
    ""id"": 1
  },
   ""image"": ""data:image/jpeg;base64,/9j/4AAQSkZJRgABAQEBLAEsAA""}"		
			
Response			
body	"{
  ""advertId"": 13,
  ""title"": ""Second ad"",
  ""description"": ""My description"",
  ""addTime"": ""2016-11-28T21:33:22"",
  ""views"": 0,
  ""tags"": [
    {
      ""name"": ""б/у"",
      ""id"": 1
    },
    {
      ""name"": ""зарубежный"",
      ""id"": 2
    }
  ],
  ""price"": 25.56,
  ""_links"": {
    ""self"": {
      ""href"": ""http://localhost:8080/adverts/13""
    },
    ""image"": {
      ""href"": ""http://localhost:8080/adverts/13/image""
    }
  },
  ""owner"": {
    ""name"": ""Bodya"",
    ""surname"": ""Fedoronchuk"",
    ""phone"": ""752 25 50"",
    ""email"": ""haistler@ukr.net"",
    ""id"": 1
  },
  ""region"": {
    ""name"": ""Одесская область"",
    ""id"": 1
  },
  ""country"": {
    ""name"": ""Украина"",
    ""id"": 1
  },
  ""category"": {
    ""name"": ""Автомобили"",
    ""id"": 1
  },
  ""subcategory"": {
    ""name"": ""Легковые"",
    ""id"": 1
  },
  ""marker"": {
    ""name"": ""Срочно"",
    ""id"": 1
  },
  ""currency"": {
    ""abbreviation"": ""грн"",
    ""id"": 1
  }
}"		
error	"{
  ""timestamp"": ""2016-11-28T19:32:49.903+0000"",
  ""status"": 401,
  ""error"": ""Unauthorized"",
  ""exception"": ""main.java.controller.UnauthorizedUserException"",
  ""message"": ""Wrong authentication data."",
  ""path"": ""/adverts/""
}"		
			
			
