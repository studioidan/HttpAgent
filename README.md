# HttpAgent
A perfectically simple library to manage http requests.

Gradle
------
```groovy

repositories {
  maven {
        url 'https://dl.bintray.com/studioidan/maven'
    }
}

dependencies {
    compile 'com.studioidan.httpagent:httpagent:1.0.1@aar'
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


