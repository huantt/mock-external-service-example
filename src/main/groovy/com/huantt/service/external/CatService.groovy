package com.huantt.service.external

import com.huantt.commons.httpclient.HttpClient
import groovy.transform.CompileStatic
import org.bson.Document

/**
 * @author huantt on 2019-04-19
 */
@CompileStatic
class CatService {

    public static final String CATS_ENDPOINT = "http://localhost:8080"

    public static Document getCatAction(String catName) {
        String stringResponse = HttpClient.get("$CATS_ENDPOINT/action?name=$catName", null).body().string()
        return Document.parse(stringResponse)
    }

}
