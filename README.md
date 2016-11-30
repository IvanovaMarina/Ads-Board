# Ads-Board
Restful API. Adverts board.
Documentation: https://docs.google.com/spreadsheets/d/1r3DN5eK8weqfvPauLsXdgniRbzXsvn54JhfnOk80KPE/edit?usp=sharing
			
			
			
	# Аутентификация пользователя		
url	/users/login		
method	GET		
			
Request			
header	Authorization	Basic login:password	
			
Response			
body	
```json
{
  "name": "Bogdan",
  "surname": "Fedoronchuk",
  "phone": "752 25 50",
  "email": "haistler97@ukr.net",
  "registrationDate": "2016-11-19T12:31:22",
  "regionName": "Одесская область",
  "countryName": "Украина",
  "admin": false,
  "_links": {
    "self": {
      "href": "http://localhost:8080/users/1"
    },
    "image": {
      "href": "http://localhost:8080/users/1/image"
    }
  },
  "id": 1
}
```
			
			
	Регистрация пользователя		
url	/users		
method	POST		
			
Request			
body	
```json
{
    "name":"Bodya",
    "surname":"Fedoronchuk",
    "login":"haistler",
    "password":"1234",
    "phone":"752 65 70",
    "email":"haistler@ukr.net",
    "region": 1,
    "image": "data:image/jpeg;base64,[encodedInBaseImage]"
}
```
			
Response
body
```json
{
  "name": "Bogdan",
  "surname": "Fedoronchuk",
  "phone": "754 24 30",
  "email": "haistler21@ukr.net",
  "registrationDate": "2016-11-19T12:31:22",
  "regionName": "Одесская область",
  "countryName": "Украина",
  "admin": false,
  "_links": {
    "self": {
      "href": "http://localhost:8080/users/1"
    },
    "image": {
      "href": "http://localhost:8080/users/1/image"
    }
  },
  "id": 1
}
```		
			
			
	#Извлечение категорий объявлений		
url	/categories		
method	GET		
			
Request			
\-		
			
Response			
body	
```json
{
  "_embedded": {
    "categories": [
      {
        "name": "Автомобили",
        "_links": {
          "self": {
            "href": "http://localhost:8080/categories/1"
          }
        },
        "id": 1
      },
      {
        "name": "Промшленность",
        "_links": {
          "self": {
            "href": "http://localhost:8080/categories/2"
          }
        },
        "id": 2
      }
    ]
  },
  "_links": {
    "self": {
      "href": "http://localhost:8080/categories"
    }
  }
}
```			
			
			
	Извлечение подкатегорий объявлений		
url	/categories/{categoryId}/subcategories		
method	GET		
			
Request			
-			
			
Response			
body	
```json
{
  "_embedded": {
    "subcategories": [
      {
        "name": "Грузовики",
        "_links": {
          "self": {
            "href": "http://localhost:8080/categories/1/subcategories/1"
          }
        },
        "id": 1
      },
      {
        "name": "Легковые",
        "_links": {
          "self": {
            "href": "http://localhost:8080/categories/1/subcategories/2"
          }
        },
        "id": 2
      }
    ]
  },
  "_links": {
    "self": {
      "href": "http://localhost:8080/categories/1/subcategories"
    }
  }
}
```
			
			
	Извлечение возможных валют		
url	/currencies		
method	GET		
			
Request			
-			
			
Response			
body
```json
{
  "_embedded": {
    "currencies": [
      {
        "abbreviation": "грн",
        "_links": {
          "self": {
            "href": "http://localhost:8080/currencies/1"
          }
        },
        "id": 1
      },
      {
        "abbreviation": "долл",
        "_links": {
          "self": {
            "href": "http://localhost:8080/currencies/2"
          }
        },
        "id": 2
      }
    ]
  },
  "_links": {
    "self": {
      "href": "http://localhost:8080/currencies"
    }
  }
}
```
			
	Извлечение возможных маркеров(пометок)		
url	/markers		
method	GET		
			
Request			
-			
			
Response			
body	
```json
{
  "_embedded": {
    "markers": [
      {
        "name": "дешево",
        "_links": {
          "self": {
            "href": "http://localhost:8080/markers/2"
          }
        },
        "id": 2
      },
      {
        "name": "срочно",
        "_links": {
          "self": {
            "href": "http://localhost:8080/markers/1"
          }
        },
        "id": 1
      }
    ]
  },
  "_links": {
    "self": {
      "href": "http://localhost:8080/markers"
    }
  }
}
```			
			
	Извлечение объявления		
url	/adverts/{advertId}		
method	GET		
			
Request			
-			
			
Response			
body	
```json
{
  "title": "New ad",
  "description": "My description. Lorem ipsum.",
  "addTime": "2016-11-30T19:40:56",
  "views": 0,
  "tags": [
    {
      "name": "б/у",
      "id": 1
    },
    {
      "name": "random_tag",
      "id": 3
    }
  ],
  "price": 601,
  "_links": {
    "self": {
      "href": "http://localhost:8080/adverts/25"
    },
    "incrementViews": {
      "href": "http://localhost:8080/adverts/25/incrementViews"
    }
  },
  "id": 25,
  "owner": {
    "name": "Bodya",
    "surname": "Fedoronchuk",
    "phone": "752 25 50",
    "email": "haistler@ukr.net",
    "id": 1
  },
  "region": {
    "name": "Одесская область",
    "id": 1
  },
  "country": {
    "name": "Украина",
    "id": 1
  },
  "category": {
    "name": "Автомобили",
    "id": 1
  },
  "subcategory": {
    "name": "Легковые",
    "id": 1
  },
  "marker": {
    "name": "Срочно",
    "id": 1
  },
  "currency": {
    "abbreviation": "долл",
    "id": 3
  }
}
```

			
	Добавление объявления		
url	/adverts		
method	POST		
			
Request			
header	Authorization	Basic login:password	
body	

			
Response			
body	

error	

			
			
