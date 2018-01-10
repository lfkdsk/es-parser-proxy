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
## Test

包含基础语法结构（）的测试。