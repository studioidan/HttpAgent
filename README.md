# HttpAgent
A perfectically simple library to manage http requests.

Gradle
------
```groovy

dependencies {
    compile 'com.studioidan.httpagent:httpagent:1.0.7@aar'
}

```


Now see how easy it becomes with HttpAgent!
-------------------------------------------

Get Request
------
```groovy
HttpAgent.get("www.example.com/api/books")
                    .goJson(new JsonCallback() {
                        @Override
                        protected void onDone(boolean success, JSONObject jsonObject) {
                            
                        }
                    });
```

or you can go jsonArray like so
```groovy
HttpAgent.get("www.example.com/api/books")
                    .goJsonArray(new JsonArrayCallback() {
                        @Override
                        protected void onDone(boolean success, JSONArray jsonArray) {
                            
                        }
                    });
```
Need to add url parameters? no problem!
------
```groovy
HttpAgent.get("www.example.com/api/books")
                    .queryParams("key_1","value_1","key_2","value_2","key_N","value_N")
                    .goJsonArray(new JsonArrayCallback() {
                        @Override
                        protected void onDone(boolean success, JSONArray jsonArray) {
                            
                        }
                    });
```
Post? Put? Delete? of course...
------
```groovy
HttpAgent.post("www.example.com/api/books");
HttpAgent.put("www.example.com/api/books");
HttpAgent.delete("www.example.com/api/books")
```

Adding body is also simple...
------
```groovy
HttpAgent.post("www.example.com/api/books")
                    .queryParams("key_1","value_1","key_2","value_2","key_N","value_N")
                    .withBody("{name:popapp ,age:27}")
                    .goJsonArray(new JsonArrayCallback() {
                        @Override
                        protected void onDone(boolean success, JSONArray jsonArray) {

                        }
                    });
```

Don't forget the headers...
------
```groovy
HttpAgent.get("http://192.168.88.253/Video/inputs/channels/1")
                        .headers("Authorization", "Basic YWRtaW46P3V5YFZhNzAw", "Content-Type", "application/json")
                        .goString(new StringCallback() {
                            @Override
                            protected void onDone(boolean success, String stringResults) {
                                Log.d(TAG, stringResults);
                            }
                        });
```
Any request can be made with one of the following callbacks:
------

Get a string results
------
```groovy
goString(new StringCallback() {
                        @Override
                        protected void onDone(boolean success, String results) {
                            
                        }
                    })
```

Get Json results
------
```groovy
goJson(new JsonCallback() {
                        @Override
                        protected void onDone(boolean success, JSONObject jsonObject) {
                            
                        }
                    })
```

Get JsonArray results
------
```groovy
goJsonArray(new JsonArrayCallback() {
                        @Override
                        protected void onDone(boolean b, JSONArray jsonArray) {

                        }
                    });
```

Get no results, Just send the request
------
```groovy
go(new SuccessCallback() {
                        @Override
                        protected void onDone(boolean success) {
                            
                        }
                    })
```

You also have access to those on any callback
------
```groovy
HttpAgent.post("www.example.com/api/books")
                    .queryParams("key_1","value_1","key_2","value_2","key_N","value_N")
                    .withBody("{name:popapp ,age:27}")
                    .goJson(new JsonCallback() {
                        @Override
                        protected void onDone(boolean success, JSONObject jsonObject) {
                            getErrorMessage(); //returns error message if exists.
                            getResponseCode(); // well, it's obvious...
                            getStringResults(); // returns results as as string.
                        }
                    });
```

                    
  

