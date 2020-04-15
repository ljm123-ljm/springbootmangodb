package com.ljm;/**
 * @Author LJM
 * @Date 2020/4/15 16:01
 * @Version 1.0
 */

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName TestMango
 * @Description 测试manggo
 * @Author ljm
 * @Date 16:01 2020/4/15
 * @Version 2.1
 **/
public class TestMango {
    MongoClient mongoClient;
    MongoCollection<Document> documentMongoCollection;
    @Before
    public void init(){
        //创建连接
         mongoClient = new MongoClient("localhost:27017");
        //打开数据库
        MongoDatabase commentdb = mongoClient.getDatabase("commentdb");
        //获取集合
        documentMongoCollection= commentdb.getCollection("comment");
    }
    @Test
    public void test1(){

        //查询
        FindIterable<Document> documents = documentMongoCollection.find();
        //获取查询记录文档集合
        for (Document document : documents) {
            System.out.println("_id"+document.get("_id"));
            System.out.println("内容"+document.get("content"));
        }
    }
    @Test
    public void test2(){
        //根据id查询一个文档
        FindIterable<Document> documents = documentMongoCollection.find(new BasicDBObject("_id", "3"));
        for (Document document : documents) {
            System.out.println("_id" + document.get("_id"));
            System.out.println("内容" + document.get("content"));
            System.out.println("用户id" + document.get("userid"));
        }
    }
    @Test
    public void test(){
        Map<String,Object> hashMap = new HashMap();
        hashMap.put("_id", "7");
        hashMap.put("content", "很棒！");
        hashMap.put("userid", "9999");
        hashMap.put("thumbup", 123);
        Document document = new Document(hashMap);
        documentMongoCollection.insertOne(document);
    }
    @Test
    public  void test4(){
        //修改条件
        BasicDBObject basicObject = new BasicDBObject("_id","6");
        //修改的数据
        Bson update = new BasicDBObject("$set", new Document("userid", "8888"));
        documentMongoCollection.updateOne(basicObject, update);

    }
    @Test
    public  void test5(){
        //删除条件
        Bson filter = new BasicDBObject("_id","6");
        documentMongoCollection.deleteOne(filter);
    }
    @After
    public  void After(){
        //关闭连接
        mongoClient.close();
    }
}
