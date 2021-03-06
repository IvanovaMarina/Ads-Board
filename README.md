# Ads-Board
Restful API. Adverts board.					

[Аутентификация пользователя](#Аутентификация-пользователя)<br>
[Регистрация пользователя](#Регистрация-пользователя)<br>
[Извлечение категорий объявлений](#Извлечение-категорий-объявлений)<br>
[Извлечение подкатегорий объявлений](#Извлечение-подкатегорий-объявлений)<br>
[Извлечение регионов](#Извлечение-регионов)<br>
[Извлечение возможных валют](#Извлечение-возможных-валют)<br>
[Извлечение возможных маркеров](#Извлечение-возможных-маркеров)<br>
[Извлечение объявления](#Извлечение-объявления)<br>
[Добавление объявления](#Добавление-объявления)<br>
[Извлечение страницы объявлений](#Извлечение-страницы-объявлений)<br>
[Увеличение просмотров объявления](#Увеличение-просмотров-объявления)<br>
[Изменение данных о пользователе](#Изменение-данных-о-пользователе)<br>
[Изменение данных о пользователе](#Изменение-данных-о-пользователе)<br>
[Изменение данных об объявлении](#Изменение-данных-об-объявлении)<br>
[Извлечение случайных тегов](#Извлечение-случайных-тегов)<br>
[Удаление объявления](#Удаление-объявления)<br>
[Удаление региона](#Удаление-региона)<br>
[Удаление маркера](#Удаление-маркера)<br>
[Добавление маркера](#Добавление-маркера)<br>
[Изменение маркера](#Изменение-маркера)<br>
[Извлечение тегов с наибольшим количеством объявлений](#Извлечение-тегов-с-наибольшим-количеством-объявлений)<br>
[Извлечение подкатегрий с наибольшим количеством объявлений](#Извлечение-подкатегрий-с-наибольшим-количеством-объявлений)<br>

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
  "phone": "752 25 51",
  "email": "haistler97@ukr.net",
  "registrationDate": "2016-11-23T12:42:25",
  "regionName": "Винницкая область",
  "countryName": "Украина",
  "admin": false,
  "_links": {
    "self": {
      "href": "http://localhost:8080/users/1"
    },
    "image": {
      "href": "http://localhost:8080/users/1/image"
    },
    "adverts": {
      "href": "http://localhost:8080/users/1/adverts"
    }
  },
  "id": 1
}
```
			
			
# Регистрация пользователя		
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
    },
    "adverts": {
      "href": "http://localhost:8080/users/1/adverts"
    }
  },
  "id": 1
}
```		
			
			
# Извлечение категорий объявлений		
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
          },
          "adverts": {
            "href": "http://localhost:8080/adverts?page=1&size=2&categoryId=1"
          }
        },
        "id": 1
      },
      {
        "name": "Бытовая техника",
        "_links": {
          "self": {
            "href": "http://localhost:8080/categories/2"
          },
          "adverts": {
            "href": "http://localhost:8080/adverts?page=1&size=2&categoryId=2"
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
			
			
# Извлечение подкатегорий объявлений		
url	/categories/{categoryId}/subcategories		
method	GET		
			
Request			
\-			
			
Response			
body	
```json
{
  "_embedded": {
    "subcategories": [
      {
        "name": "Легковые",
        "_links": {
          "self": {
            "href": "http://localhost:8080/categories/1/subcategories/1"
          },
          "adverts": {
            "href": "http://localhost:8080/adverts?page=1&size=2&subcategoryId=1"
          }
        },
        "id": 1
      },
      {
        "name": "Грузовики",
        "_links": {
          "self": {
            "href": "http://localhost:8080/categories/1/subcategories/2"
          },
          "adverts": {
            "href": "http://localhost:8080/adverts?page=1&size=2&subcategoryId=2"
          }
        },
        "id": 2
      },
      {
        "name": "Прицепы",
        "_links": {
          "self": {
            "href": "http://localhost:8080/categories/1/subcategories/3"
          },
          "adverts": {
            "href": "http://localhost:8080/adverts?page=1&size=2&subcategoryId=3"
          }
        },
        "id": 3
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

# Извлечение регионов		
url	/geolocation/countries/{countryId}/regions	
method	GET		
			
Request			
\-			
			
Response			
body	
```json
{
  "_embedded": {
    "regions": [
      {
        "name": "Одесская область",
        "_links": {
          "self": {
            "href": "http://localhost:8080/geolocation/regions/1"
          }
        },
        "id": 1
      },
      {
        "name": "Винницкая область",
        "_links": {
          "self": {
            "href": "http://localhost:8080/geolocation/regions/2"
          }
        },
        "id": 2
      }
    ]
  },
  "_links": {
    "linkToAdd": {
      "href": "http://localhost:8080/geolocation/regions"
    }
  }
}
```
			
# Извлечение возможных валют		
url	/currencies		
method	GET		
			
Request			
\-			
			
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
			
# Извлечение возможных маркеров		
url	/markers		
method	GET<br>
comment: markers are sorted
			
Request			
\-			
			
Response			
body	
```json
{
  "_embedded": {
    "markers": [
      {
        "name": "Срочно",
        "_links": {
          "self": {
            "href": "http://localhost:8080/markers/1"
          }
        },
        "id": 1
      },
      {
        "name": "Дешево",
        "_links": {
          "self": {
            "href": "http://localhost:8080/markers/2"
          }
        },
        "id": 2
      },
      {
        "name": "Новинка",
        "_links": {
          "self": {
            "href": "http://localhost:8080/markers/6"
          }
        },
        "id": 6
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
			
# Извлечение объявления		
url	/adverts/{advertId}		
method	GET		
			
Request			
\-			
			
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
    "image": {
      "href": "http://localhost:8080/adverts/25/image"
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
    "_links": {
      "image": {
        "href": "http://localhost:8080/users/1/image"
      }
    },
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
error
Status: 404 Not found
```json
{
  "timestamp": "2016-11-30T20:47:29.423+0000",
  "status": 404,
  "error": "Not Found",
  "exception": "main.java.service.AdvertNotFoundException",
  "message": "Advert not found.",
  "path": "/adverts/23"
}
```
			
# Добавление объявления		
url	/adverts		
method	POST		
			
Request			
header	Authorization	Basic login:password	
body	
```json
{
  "title": "New ad.",
  "description": "My description. Lorem ipsum.",
  "tags": [
    {
      "name": "б/у"
    },
    {
      "name": "random_tag"
    },
    {
      "name": "new_tag"
    }
  ],
  "price": 112,
  "owner": {
    "id": 1
  },
  "subcategory": {
    "id": 1
  },
  "marker": {
    "id": 1
  },
  "currency": {
    "id": 1
  },
   "image": "data:image/jpeg;base64,/9j/4AAQSkZJRgABAQEBLAEsAA"
}
```			
Response			
body	
```json
{
  "title": "New ad.",
  "description": "My description. Lorem ipsum.",
  "addTime": "2016-11-30T22:04:39",
  "views": 0,
  "tags": [
    {
      "name": "б/у",
      "id": 1
    },
    {
      "name": "random_tag",
      "id": 3
    },
    {
      "name": "new_tag",
      "id": 6
    }
  ],
  "price": 112,
  "_links": {
    "self": {
      "href": "http://localhost:8080/adverts/27"
    },
    "image": {
      "href": "http://localhost:8080/adverts/27/image"
    },
    "incrementViews": {
      "href": "http://localhost:8080/adverts/27/incrementViews"
    }
  },
  "id": 27,
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
    "abbreviation": "грн",
    "id": 1
  }
}
```
error	
Status: 401 Unauthorized
```json
{
  "timestamp": "2016-11-30T20:07:24.525+0000",
  "status": 401,
  "error": "Unauthorized",
  "exception": "main.java.controller.UnauthorizedUserException",
  "message": "Wrong authentication data.",
  "path": "/adverts/"
}
```

# Извлечение страницы объявлений		
url	/adverts?page={intValue}&size={intValue}<br>
&nbsp;&nbsp;&nbsp;[&tagName={textParam} | &categoryId={intValue} | &subcategoryId={intValue} &title={textParam}]<br>
method	GET		
			
Request			
\-			
			
Response			
body			
```json
{
  "_embedded": {
    "adverts": [
      {
        "title": "New ad.",
        "description": "My description. Lorem ipsum.",
        "addTime": "2016-11-30T22:04:39",
        "views": 0,
        "tags": [
          {
            "name": "б/у",
            "id": 1
          },
          {
            "name": "random_tag",
            "id": 3
          },
          {
            "name": "new_tag",
            "id": 6
          }
        ],
        "price": 112,
        "_links": {
          "self": {
            "href": "http://localhost:8080/adverts/27"
          },
          "image": {
            "href": "http://localhost:8080/adverts/27/image"
          },
          "incrementViews": {
            "href": "http://localhost:8080/adverts/27/incrementViews"
          }
        },
        "id": 27,
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
          "abbreviation": "грн",
          "id": 1
        }
      },
      {
        "title": "New ad2",
        "description": "My description. Lorem ipsum.",
        "addTime": "2016-11-30T19:41:32",
        "views": 0,
        "tags": [
          {
            "name": "б/у",
            "id": 1
          },
          {
            "name": "random_tag",
            "id": 3
          },
          {
            "name": "new_tag",
            "id": 6
          }
        ],
        "price": 112,
        "_links": {
          "self": {
            "href": "http://localhost:8080/adverts/26"
          },
          "image": {
            "href": "http://localhost:8080/adverts/26/image"
          },
          "incrementViews": {
            "href": "http://localhost:8080/adverts/26/incrementViews"
          }
        },
        "id": 26,
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
          "abbreviation": "грн",
          "id": 1
        }
      }
    ]
  },
  "_links": {
    "firstPage": {
      "href": "http://localhost:8080/adverts?page=1&size=2"
    },
    "currentPage": {
      "href": "http://localhost:8080/adverts?page=1&size=2"
    },
    "lastPage": {
      "href": "http://localhost:8080/adverts?page=2&size=2"
    }
  }
}
```
# Увеличение просмотров объявления	
url	adverts/{id}/incrementViews<br>
method	PUT

Request			
\-			
			
Response			
\-

# Изменение данных о пользователе
url	/users/{id}
method	PUT

Request			
header	Authorization	Basic login:password	
body
```json
{
  "name": "Bogdan",
  "surname": "Fedoronchuk",
  "phone": "752 25 51",
  "email": "haistler97@ukr.net",
  "region": 2,
  "id": 1
}
```
			
Response			
body
```json
{
  "name": "Bogdan",
  "surname": "Fedoronchuk",
  "phone": "752 25 51",
  "email": "haistler97@ukr.net",
  "registrationDate": "2016-11-23T12:42:25",
  "regionName": "Винницкая область",
  "countryName": "Украина",
  "admin": false,
  "id": 1
}
```
error	
Status: 401 Unauthorized
```json
{
  "timestamp": "2016-12-02T17:12:35.494+0000",
  "status": 401,
  "error": "Unauthorized",
  "exception": "main.java.controller.UnauthorizedUserException",
  "message": "Wrong authentication data.",
  "path": "/users/1"
}
```

# Изменение данных об объявлении
url	/adverts/{id}<br>
method	PUT

Request			
header	Authorization	Basic login:password	
body
```json
{
        "title": "DVD-плеер1",
        "description": "Lorem ipsum.Lorem ipsum.",
        "tags": [
          {
            "name": "random_tag"
          },
          {
            "name": "new_tag"
          },
          {
            "name": "hvgv"
          }
        ],
        "price": 12001,
        "owner": {
          "id": 1
        },
        "region": {
          "id": 1
        },
        "subcategory": {
          "id": 3
        },
        "marker": {
          "id": 1
        },
        "currency": {
          "id": 1
        }
      }
```
			
Response			
body
```json
{
  "title": "DVD-плеер1",
  "description": "Lorem ipsum.Lorem ipsum.",
  "addTime": "2016-12-01T00:29:49",
  "views": 17,
  "tags": [
    {
      "name": "random_tag",
      "id": 3
    },
    {
      "name": "new_tag",
      "id": 6
    },
    {
      "name": "hvgv",
      "id": 7
    }
  ],
  "price": 12001,
  "id": 28,
  "owner": {
    "name": "Богдан",
    "surname": "Федорончук",
    "phone": "0963458465",
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
    "name": "Прицепы",
    "id": 3
  },
  "marker": {
    "name": "Срочно",
    "id": 1
  },
  "currency": {
    "abbreviation": "грн",
    "id": 1
  }
}
```
error	
Status: 401 Unauthorized
```json
{
  "timestamp": "2016-12-03T21:31:41.862+0000",
  "status": 401,
  "error": "Unauthorized",
  "exception": "main.java.controller.UnauthorizedUserException",
  "message": "Wrong authentication data.",
  "path": "/adverts/28"
}
```

# Извлечение случайных тегов
url	/adverts/randomTags?amount={intValue}	
method	GET		
			
Request			
\-			
			
Response			
body
```json
[
  {
    "name": "б/у",
    "advertsAmount": 3,
    "links": [
      {
        "rel": "adverts",
        "href": "http://localhost:8080/adverts?page=1&size=2&tagName=new_tag"
      }
    ],
    "id": 1
  },
  {
    "name": "random_tag",
    "advertsAmount": 4,
    "links": [
      {
        "rel": "adverts",
        "href": "http://localhost:8080/adverts?page=1&size=2&tagName=random_tag"
      }
    ],
    "id": 3
  }
]
```
error	
Status: 400 Bad Request
```json
{
  "timestamp": "2016-12-03T22:42:03.807+0000",
  "status": 400,
  "error": "Bad Request",
  "exception": "main.java.service.TagException",
  "message": "Tag amount must be more then 0.",
  "path": "/adverts/randomTags"
}
```

# Удаление объявления
url	/adverts/{id}
method	DELETE		
comment: owner and admin have access

Request			
\-			
			
Response			
body
```JSON
{
  "title": "New ad2",
  "description": "My description. Lorem ipsum.",
  "addTime": "2016-11-30T19:41:32",
  "views": 6,
  "tags": [
    {
      "name": "б/у",
      "id": 1
    },
    {
      "name": "random_tag",
      "id": 3
    },
    {
      "name": "new_tag",
      "id": 6
    }
  ],
  "price": 112,
  "id": 26,
  "owner": {
    "name": "Богдан",
    "surname": "Федорончук",
    "phone": "0963458465",
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
    "abbreviation": "грн",
    "id": 1
  }
}
```
error	
Status: 401 Unauthorized
```json
{
  "timestamp": "2016-12-04T17:52:20.005+0000",
  "status": 401,
  "error": "Unauthorized",
  "exception": "main.java.controller.UnauthorizedUserException",
  "message": "Wrong authentication data.",
  "path": "/adverts/26"
}
```

# Удаление региона
url	/geolocation/regions/{id}<br>
method	DELETE		
comment: only admin have access

Request			
header	Authorization	Basic login:password		
			
Response			
body
```json
{
  "name": "Random region",
  "id": 5
}
```
error	
Status: 401 Unauthorized
```json
{
  "timestamp": "2016-12-04T20:10:35.644+0000",
  "status": 401,
  "error": "Unauthorized",
  "exception": "main.java.controller.UnauthorizedUserException",
  "message": "Only admin is allowed to perform this action.",
  "path": "/geolocation/regions/5"
}
```

# Удаление маркера		
url	/markers/{id}		
method	DELETE		
comment: only admin have access

Request			
header	Authorization	Basic login:password				
			
Response			
body	
```json
{
  "name": "New marker1",
  "id": 7
}
```
errors:
Status: 401 Unauthorized
```json
{
  "timestamp": "2016-12-10T14:27:39.908+0000",
  "status": 401,
  "error": "Unauthorized",
  "exception": "main.java.controller.UnauthorizedUserException",
  "message": "Wrong authentication data.",
  "path": "/markers/10"
}
```
Status: 404 Not found
```json
{
  "timestamp": "2016-12-10T21:15:53.193+0000",
  "status": 404,
  "error": "Not Found",
  "exception": "main.java.service.MarkerNotFoundException",
  "message": "Marker is not found",
  "path": "/markers/20"
}
```
Status: 403 Forbidden
```json
{
  "timestamp": "2016-12-10T21:16:18.232+0000",
  "status": 403,
  "error": "Forbidden",
  "exception": "main.java.service.DeleteMarkerException",
  "message": "Adverts with this marker exist.",
  "path": "/markers/1"
}
```
# Добавление маркера		
url	/markers	
method	POST	
comment: only admin have access

Request			
header	Authorization	Basic login:password				
body
```json
{
	"name":"New marker"
}
```

Response			
body	
```json
{
  "name": "New marker",
  "id": 7
}
```
errors:
Status: 401 Unauthorized
```json
{
  "timestamp": "2016-12-10T14:20:04.256+0000",
  "status": 401,
  "error": "Unauthorized",
  "exception": "main.java.controller.UnauthorizedUserException",
  "message": "Wrong authentication data.",
  "path": "/markers"
}
```
Status: 403 Forbidden
```json
{
  "timestamp": "2016-12-10T21:17:16.977+0000",
  "status": 403,
  "error": "Forbidden",
  "exception": "main.java.service.MarkerAlreadyExistingException",
  "message": "Marker already exists.",
  "path": "/markers"
}
```

# Изменение маркера		
url	/markers/{id}	
method	PUT		
comment: only admin have access

Request			
header	Authorization	Basic login:password				
body
```json
{
	"name":"New marker1"
}
```
Response			
body	
```json
{
  "name": "New marker1",
  "id": 7
}
```
errors:
Status: 401 Unauthorized
```json
{
  "timestamp": "2016-12-10T14:36:15.398+0000",
  "status": 401,
  "error": "Unauthorized",
  "exception": "main.java.controller.UnauthorizedUserException",
  "message": "Wrong authentication data.",
  "path": "/markers/7"
}
```
Status: 404 Not Found
```json
{
  "timestamp": "2016-12-10T21:19:12.176+0000",
  "status": 404,
  "error": "Not Found",
  "exception": "main.java.service.MarkerNotFoundException",
  "message": "Marker is not found",
  "path": "/markers/20"
}
```
# Извлечение тегов с наибольшим количеством объявлений
url	/adverts/mostAdvertsTags	
method	GET		
comment: only admin have access

Request			
header	Authorization	Basic login:password	

Response
body
```json
[
  {
    "name": "бу",
    "advertsAmount": 4,
    "links": [],
    "id": 12
  }
]
```
errors:
Status: 401 Unauthorized
```json
{
  "timestamp": "2016-12-10T15:38:22.948+0000",
  "status": 401,
  "error": "Unauthorized",
  "exception": "main.java.controller.UnauthorizedUserException",
  "message": "Wrong authentication data.",
  "path": "/adverts/mostAdvertsTags"
}
```
# Извлечение подкатегрий с наибольшим количеством объявлений
url	/categories/subcategories/mostAdverts	
method	GET		
comment: only admin have access

Request			
header	Authorization	Basic login:password	

Response
body
```json
{
  "_embedded": {
    "subcategories": [
      {
        "name": "Грузовики",
        "id": 2
      },
      {
        "name": "Легковые",
        "id": 1
      }
    ]
  }
}
```
errors:
Status: 401 Unauthorized
```json
{
  "timestamp": "2016-12-10T15:46:02.173+0000",
  "status": 401,
  "error": "Unauthorized",
  "exception": "main.java.controller.UnauthorizedUserException",
  "message": "Wrong authentication data.",
  "path": "/categories/subcategories/mostAdverts"
}
```
