package basiclearning;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class CheckGit {
    public static void main(String[] args) {


//        https://github.com               /CG-QuangNN/CaseStudyModule-3-JspServlet/blob/master/src/main/java/controller/ContractDetailController.java
//        https://raw.githubusercontent.com/CG-QuangNN/CaseStudyModule-3-JspServlet     /master/src/main/java/controller/ContractDetailController.java


//        https://github.com               /pr-fullstack/01.KyThuatLapTrinhNenTangVoiJava/blob/main/05-quy-tac-dat-ten-bien-ghi-chu/src/basiclearning/DatTenBienVaGhiChu.java
//        https://raw.githubusercontent.com/pr-fullstack/01.KyThuatLapTrinhNenTangVoiJava     /main/05-quy-tac-dat-ten-bien-ghi-chu/src/basiclearning/DatTenBienVaGhiChu.java

//        String url = "";
//        url = url.replace("https://github.com", "https://raw.githubusercontent.com")
//                .replace("/blob/", "");


        getDataHTML("https://github.com/CG-QuangNN/CaseStudyModule-3-JspServlet/blob/master/src/main/java/repository/IContractDetailRepository.java");






        //https://raw.githubusercontent.com/pr-fullstack/01.KyThuatLapTrinhNenTangVoiJava/main/05-quy-tac-dat-ten-bien-ghi-chu/src/basiclearning/DatTenBienVaGhiChu.java


        //getDataHTML("https://github.com/pr-fullstack/01.KyThuatLapTrinhNenTangVoiJava/blob/main/05-quy-tac-dat-ten-bien-ghi-chu/src/basiclearning/DatTenBienVaGhiChu.java");


        //System.out.println(doesURLExist(new URL("https://github.com/TaiNhut231094/C0322G1_LeAnhTai/blob/main/module_2/src/ss03_array_to_java/excrecise/MergeArray.javas")));
    }



    private static void getDataHTML(String path) {
        path = path.replace("https://github.com", "https://raw.githubusercontent.com");
        path = path.replace("/blob/", "/");
        URL url = null;
        String file = "";

        try {                 //"https://github.com               /TaiNhut231094/C0322G1_LeAnhTai/blob/main/module_2/src/ss03_array_to_java/excrecise/MergeArray.java"
            url = new URL(path);
            java.net.URLConnection uc;
            uc = url.openConnection();

//            uc.setRequestProperty("X-Requested-With", "Curl");
            java.util.ArrayList<String> list = new java.util.ArrayList<>();

            BufferedReader reader = new BufferedReader(new InputStreamReader(uc.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null)
                file = file + line + "\n";
            System.out.println(file);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Wrong username and password");
        }
    }
}
