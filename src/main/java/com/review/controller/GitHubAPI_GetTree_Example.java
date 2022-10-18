package com.review.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.review.service.CommentJavaService;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GitHubAPI_GetTree_Example {

    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void main(String[] args) throws IOException {
//        String url = "https://api.github.com/repos/CG-QuangNN/CaseStudyModule-3-JspServlet/branches/master";
//        String url = "https://github.com/codegym-vn/jwbd-simple-dictionary-jsp/tree/develop/src/main/webapp/dictionary.jsp";
//        List<String> urlFileList = getFileAllFromRepoAndBranches(getInfoFromUrl(url));
//
//        System.out.println("\n\n\n================================================================");
//        for (String item : urlFileList) {
//            System.out.println(item);
//        }
//        commentFileJava("https://github.com/nguyenvanhung112/C0622G1-NguyenVanHung/tree/main/module3/Module3/case_study");
        commentAllFile("https://github.com/nguyenvanhung112/C0622G1-NguyenVanHung/tree/main/module3/Module3/case_study/src/main/java/controlleráđá");
    }

    public static String commentAllFile(String url) throws IOException {
//        String url = "https://github.com/Minchou99/C0622G1_PhamThiMinhChau_module3/tree/main/ss9_introduction_web/exercise/src/main";
//        String url = "https://github.com/tranviethuy2199/module3/tree/main/module_3/mysql/furamaManager";

        Map<String, String> infoUrl = getInfoFromUrl(url);
        List<String> urlFileList = getFileAllFromRepoAndBranches(getInfoFromUrl(url));

        StringBuilder messageError = new StringBuilder();
        System.out.println("\n\n\n================================================================");
        for (String item : urlFileList) {
            String link = "https://github.com/" + infoUrl.get("repoName") + "/blob/main/" + item;
            commentFileType(link, messageError);
        }
        return messageError.toString();
    }

    public static void commentFileType(String path, StringBuilder commentOneLink) {
        if (path.contains(".java")) {
            List<String> errorMessage = CommentJavaService.commentJavaFile2(path);
            if (errorMessage.size() != 0) {
                commentOneLink.append("\nTại: " + path + "\n");
                for (String item : errorMessage) {
                    commentOneLink.append(item + "\n");
                }
            }
        } else if (path.contains(".jsp")) {
            // TODO
        } else if (path.contains(".html")) {
            // TODO
        } else if (path.contains(".sql")) {
            // TODO
        }
    }

    /**
     * Lấy tất cả file từ Repo và nhánh
     *
     * @return
     * @throws IOException
     */
    private static List<String> getFileAllFromRepoAndBranches(Map<String, String> infoUrl) throws IOException {
//        https://api.github.com/repos/codegym-vn/jwbd-simple-dictionary-jsp/branches/develop
        String branchesName = infoUrl.get("branchesName");
        String url = String.format("https://api.github.com/repos/%s/branches/%s", infoUrl.get("repoName"), branchesName);

        List<String> urlFileList = new ArrayList<>();

        Map jsonMap = makeRESTCall(url);

        String treeApiUrl = gson.toJsonTree(jsonMap).getAsJsonObject().get("commit").getAsJsonObject().get("commit")
                .getAsJsonObject().get("tree").getAsJsonObject().get("url").getAsString();

        Map jsonTreeMap = makeRESTCall(treeApiUrl + "?recursive=1");

        //Lọc theo url
        String uri = infoUrl.get("uri");
        String path;
        for (Object obj : ((List) jsonTreeMap.get("tree"))) {
            Map fileMetadata = (Map) obj;
            if (fileMetadata.get("type").equals("blob")) { // file
                path = (String) fileMetadata.get("path");
                if (path.indexOf(uri) == 0) {
                    urlFileList.add(path);
                }
            }
        }
        return urlFileList;
    }

    /**
     * Đảm bảo ở đây là tree (Thư mục)
     * Lấy được: Tên repo, tên nhánh
     *
     * @param url
     * @return
     */
    private static Map<String, String> getInfoFromUrl(String url) {
        Map<String, String> infoURL = new HashMap<>();

        //https://github.com/ => 19
        int indexTree = url.indexOf("/tree/");
        String repoName = url.substring(19, indexTree);

        // https://github.com/ => 19 + /tree/ = 25
        String temp = url.substring(25 + repoName.length());
        String branchesName = temp.substring(0, temp.indexOf("/"));

        // 25 + repoName.length() + branchesName.length() + /
        String uri = url.substring(26 + repoName.length() + branchesName.length());

        infoURL.put("repoName", repoName);
        infoURL.put("branchesName", branchesName);
        infoURL.put("uri", uri);
        return infoURL;
    }

    /**
     * This method will make a REST GET call for this URL using Apache http client &
     * fluent library.
     * <p>
     * Then parse response using GSON & return parsed Map.
     */
    private static Map makeRESTCall(String restUrl) throws IOException {
        Content content = Request.Get(restUrl).execute().returnContent();
        return gson.fromJson(content.asString(), Map.class);
    }
}
