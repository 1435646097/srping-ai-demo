package com.siteweb.springaidemo;

import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@SpringBootTest
public class VectorTests {
    @Autowired
    VectorStore vectorStore;

    @Test
    public void testVectorStore() {
        List<Document> documents = List.of(
                new Document("1 如何开启s6的防重放攻击\n" +
                        "1.登陆上s6部署的服务器\n" +
                        "2.找到/siteweb/backend/config/application.yml文件,搜索\"antiReplayAttack\"关键字，将其后面的false修改成true,如果已经是true，则代表已开启\n" +
                        "3.找到/siteweb/nginx/assets/json/app.json文件，搜索\"web.antiReplayAttack.enable\"关键字，将其后面的false修改成true,如果已经是true，则代表已开启\n" +
                        "4.调整完毕后记得重启前后台，使用docker restart siteweb-server && docker restart siteweb-frontend命令即可",
                        Map.of("title","如何开启s6的防重放攻击")),
                new Document("2 配电 400 问题/告警灯没有颜色【s2 升级 s6 必备】\n" +
                        "发生原因:s2升级到s6时,升级脚本中没有更新告警灯的颜色\n" +
                        "使用如下脚本即可\n" +
                        "-- 更新扩展字段的颜色\n" +
                        "update tbl_dataitem set ExtendField3 = '#77BDF8' ,ExtendField4 = 4 where entryid = 23  and ItemId= 0;\n" +
                        "update tbl_dataitem set ExtendField3 = '#EDD951' ,ExtendField4 = 3 where entryid = 23  and ItemId= 1;\n" +
                        "update tbl_dataitem set ExtendField3 = '#F39924' ,ExtendField4 = 2 where entryid = 23  and ItemId= 2;\n" +
                        "update tbl_dataitem set ExtendField3 = '#ff2626' ,ExtendField4 = 1 where entryid = 23  and ItemId= 3;\n" +
                        "-- 处理现有告警的告警等级\n" +
                        "update tbl_dataitem a,tbl_activeevent b set b.EventLevel = a.ExtendField4 where a.ItemId = b.EventSeverityId and a.EntryId = 23;",Map.of("title","配电 400 问题/告警灯没有颜色【s2 升级 s6 必备】")),
                new Document("使用MySQL Workbench提示You are using safe update model\n" +
                        "MySQL 中的“safe update”模式是一种保护机制，旨在防止意外更改或删除数据。当启用该模式时，MySQL 会阻止执行没有 WHERE 子句的 UPDATE 和 DELETE 语句，以及那些 WHERE 子句不包含主键或唯一索引列的语句。\n" +
                        "要禁用“安全更新”模式，您可以在脚本前使用以下命令：\n" +
                        "SET SQL_SAFE_UPDATES = 0;",Map.of("title","使用MySQL Workbench提示You are using safe update model")),
                new Document("new Document(\"使用MySQL Workbench提示You are using safe update model\\n\" +\n" +
                        "                        \"MySQL 中的“safe update”模式是一种保护机制，旨在防止意外更改或删除数据。当启用该模式时，MySQL 会阻止执行没有 WHERE 子句的 UPDATE 和 DELETE 语句，以及那些 WHERE 子句不包含主键或唯一索引列的语句。\\n\" +\n" +
                        "                        \"要禁用“安全更新”模式，您可以在脚本前使用以下命令：\\n\" +\n" +
                        "                        \"SET SQL_SAFE_UPDATES = 0;\")",Map.of("title","使用MySQL Workbench提示You are using safe update model")));

        vectorStore.add(documents);
    }

    @Test
    public void queryVectorStore() {
        List<Document> results = this.vectorStore.similaritySearch(
                SearchRequest.builder().query("Spring").topK(5).build());
        System.out.println(results);
    }
}
