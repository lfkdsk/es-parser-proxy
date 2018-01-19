# EtD Proxy Demo

![es-db](art/es-ast-db.png)

* Input：

  ``` json
  {
         "query": {
             "match" : {
                 "kind" : "SERVER"
             }
         }
  }
  ```

* Output:

  ``` json
  {
    "numResults": 10,
    "tableNames": [],
    "query": {
      "queryStr": "kind:SERVER",
      "queryType": "string"
    },
    "fields": [
      "*"
    ],
    "useApproximation": false,
    "ctx": "",
    "timeoutMillis": 0,
    "disableHighlight": false
  }
  ```
## AST Structure 

``` javascript
// AstObject -> Property List
{
  // => Property List
  // => Primary Property => [ StringLiterial, Literial]
  "bool": true,
  // => Array Property => [ Literial List ] | [ AstObject List ] | [ AstValueList List ]
  // => Array Property is List
  "array": [
      
   ],
  // => Object Property => AstObject 
  "object": {
  	    
  }
}
```

