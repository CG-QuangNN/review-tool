package com.review.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommentJavaService {
    private static final List<String> dataTypes =
            Arrays.asList("boolean ", "byte ", "short ", "int ", "long ", "char ", "float ", "double ",
                    "Boolean ", "Byte ", "Short ", "Integer ", "Long ", "Character ", "Float ", "Double ",
                    "BigDecimal ", "String ");

    private static final List<Character> operators =
            Arrays.asList('.', ',', '+', '-', '*', '/');

    public static void main(String[] args) throws IOException {
//        getDataHTML("https://github.com/CG-QuangNN/CaseStudyModule-3-JspServlet/blob/master/src/main/java/model/AttachService.java");
//        getDataHTML("https://github.com/Minchou99/C0622G1_PhamThiMinhChau_module3/tree/main/casestudy2");
//        List<String> errorMessage = commentJavaFile2("https://github.com/ngocquang95/learn/blob/master/tesst/AttcachService3.java");
        List<String> errorMessage = commentJavaFile2("https://github.com/trantri0801/Module2/blob/master/Array/Array2c.java");

        for (String item : errorMessage) {
            System.out.println(item);
        }
    }

    public static List<String> commentJavaFile2(String path) {
        List<String> errorMessage = new ArrayList<>();
        List<String> lines = null;
        try {
            lines = removeComment(getDataHTML(path));
        } catch (IOException e) {
            e.printStackTrace();
            errorMessage.add("Link 404");
            return errorMessage;
        }

        int size = lines.size();
        String line = null;
        int count = 0; //Đến dấu  {
        boolean isInterface = false;
        boolean isOperatorEndLine = false;
        for (int i = 0; i < size; i++) {
            line = lines.get(i);
            line = line.trim().replaceAll("[ ]+", " ");

            if (line.startsWith("public interface ")) {
                isInterface = true;
                continue;
            }

            if (isOperatorEndLine) {
                isOperatorEndLine = false;

                if (line.length() > 0) {
                    char endLineChar = line.charAt(line.length() - 1);
                    if (operators.contains(endLineChar)) { //Xét trường hợp là toán tử ở cuối dòng
                        isOperatorEndLine = true;
                    }
                    if (line.contains("{") || line.contains("}")) { // Tính toán để lấy thuộc tính và biến phương thức;
                        if (line.contains("{")) {
                            count += line.length() - line.replaceAll("[{]", "").length();
//                        count++;
                        }
                        if (line.contains("}")) {
                            count -= line.length() - line.replaceAll("[}]", "").length();
//                        count--;
                        }
                    }
                }
                continue;
            }

            if (isInterface) {
                if (line.contains("public abstract")) {
                    errorMessage.add(String.format("- Line %s: Phương thức của interface mặc định là public abstract rồi nên không cần viết rõ ra", i + 1));
                } else if (line.contains("public static final")) {
                    errorMessage.add(String.format("- Line %s: Thuộc tính của interface mặc định là public static final rồi nên không cần viết rõ ra", i + 1));
                } else if (line.contains("public ")) {
                    if (line.contains(";")) {
                        errorMessage.add(String.format("- Line %s: Phương thức của interface mặc định là public rồi nên không cần viết rõ ra", i + 1));
                    } else {
                        errorMessage.add(String.format("- Line %s: Thuộc tính của interface mặc định là public rồi nên không cần viết rõ ra", i + 1));
                    }
                }
            } else {
                if (line.startsWith("public class") || line.startsWith("public abstract class")) { //Kiểm tra tên class
                    String className;
                    if (line.startsWith("public class")) {
                        className = line.substring(13); // public class
                    } else {
                        className = line.substring(22); // public abstract class
                    }
                    int indexSpace = className.indexOf(" ");
                    if (indexSpace == -1) {
                        indexSpace = className.indexOf(" extends");
                        if (indexSpace == -1) {
                            indexSpace = className.indexOf(" implements");
                            if (indexSpace == -1) {
                                indexSpace = className.indexOf("{");
                            }
                            if (indexSpace == -1) {
                                indexSpace = className.length();
                            }
                        }
                    }
                    className = className.substring(0, indexSpace).trim();
                    if (!(className.charAt(0) >= 'A' && className.charAt(0) <= 'Z')) {
                        errorMessage.add(String.format("- Line %s: Tên class phải theo quy tắc PascalCase", i + 1));
                    } else if (!className.matches("[a-zA-Z]+")) {
                        errorMessage.add(String.format("- Line %s: Tên class phải theo quy tắc PascalCase và không được chứa ký tự đặc biệt", i + 1));
                    }
                }

                if (line.contains("{") || line.contains("}")) { // Tính toán để lấy thuộc tính và biến phương thức;
                    if (line.contains("{")) {
                        count += line.length() - line.replaceAll("[{]", "").length();
//                        count++;
                    }
                    if (line.contains("}")) {
                        count -= line.length() - line.replaceAll("[}]", "").length();
//                        count--;
                    }
                } else if (count == 1) { // thuộc tính

                    if (line.contains(";") && line.contains(" ")) {
                        if (line.contains("=")) {
                            line = line.substring(0, line.indexOf("="));
                        }

                        if (!line.contains("private") && !line.contains("static")) {
                            errorMessage.add(String.format("- Line %s: Access modifier của thuộc tính class phải là private", i + 1));
                        }


                        if (line.endsWith(";")) {
                            line = line.substring(0, line.length() - 1).trim();
                        } else {
                            line = line.trim();
                        }
                        String nameProperty = line.substring(line.lastIndexOf(" ") + 1);
                        if (line.contains(" final ") && !line.contains("serialVersionUID")) {
                            if (!nameProperty.matches("[A-Z_]+")) {
                                for (String type : dataTypes) {
                                    if (line.contains(type)) {
                                        errorMessage.add(String.format("- Line %s: Tên hằng phải theo quy tắc CONSTANT_VARIABLE_NAME", i + 1));
                                        break;
                                    }
                                }

                            }
                        } else {
                            if (!(nameProperty.charAt(0) >= 'a' && nameProperty.charAt(0) <= 'z')) {
                                errorMessage.add(String.format("- Line %s: Tên biến phải theo quy tắc camelCase", i + 1));
                            } else if (!nameProperty.matches("[a-zA-Z]+")) {
                                errorMessage.add(String.format("- Line %s: Tên biến phải theo quy tắc camelCase và không được chứa ký tự đặc biệt", i + 1));
                            }
                        }
                    }
//                System.out.println(line);
                } else { // Thuộc tính của phương thức
                    String nameProperty;
                    for (String dataType : dataTypes) {
                        if (line.contains(dataType)) {
                            int index = line.indexOf(dataType) + dataType.length();

                            line = line.replaceAll("[\\[\\]]", "");  //trường hợp là mảng float[][] arr;

                            nameProperty = line.substring(index).trim();
                            int indexLast = nameProperty.indexOf(" ");
                            if (indexLast < 0) {
                                indexLast = nameProperty.indexOf("=");
                            }
                            if (indexLast < 0) {
                                indexLast = nameProperty.indexOf(",");
                            }
                            if (indexLast < 0) {
                                indexLast = nameProperty.indexOf(";");
                            }

                            nameProperty = nameProperty.substring(0, indexLast).trim();
                            if (line.contains("final ")) {
                                if (!nameProperty.matches("[A-Z_]")) {
                                    errorMessage.add(String.format("- Line %s: Tên hằng phải theo quy tắc CONSTANT_VARIABLE_NAME", i + 1));
                                }
                            } else {
                                if (!(nameProperty.charAt(0) >= 'a' && nameProperty.charAt(0) <= 'z')) {
                                    errorMessage.add(String.format("- Line %s: Tên biến phải theo quy tắc camelCase", i + 1));
                                } else if (!nameProperty.matches("[a-zA-Z=,]+")) { //int column, row;
                                    //id= :https://github.com/ledat0711/C0622G1_LeAnhDat_Module_3/blob/main/case_study_furama/src/main/java/repository/impl/FacilityRepository.java
                                    errorMessage.add(String.format("- Line %s: Tên biến phải theo quy tắc camelCase và không được chứa ký tự đặc biệt", i + 1));
                                }
                            }
                        }
                    }
                }

            }


            if (line.length() > 0) {
                char endLineChar = line.charAt(line.length() - 1);
                if (operators.contains(endLineChar)) { //Xét trường hợp là toán tử ở cuối dòng
                    isOperatorEndLine = true;
                }
            }
        }

        return errorMessage;
    }

    private static List<String> removeComment(List<String> lines) {
        List<String> linesRemove = new ArrayList<>();
        String line;
        int count = 0;
        for (int i = 0; i < lines.size(); i++) {
            line = lines.get(i);
            if (line.contains("//")) {
                linesRemove.add(line.substring(0, line.indexOf("//")));
            } else {
                if (line.contains("/*")) {
                    count++;
                }
                if (line.contains("*/")) {
                    count--;
                    line = "";
                }

                if (count == 1) {
                    linesRemove.add("");
                } else {
                    linesRemove.add(line);
                }
            }
        }
        return linesRemove;
    }

    private static List<String> getDataHTML(String path) throws IOException {
        path = path.replace("https://github.com", "https://raw.githubusercontent.com");
        path = path.replace("/blob/", "/");
        URL url = null;
        List<String> strings = new ArrayList<>();

//        try {                 //"https://github.com               /TaiNhut231094/C0322G1_LeAnhTai/blob/main/module_2/src/ss03_array_to_java/excrecise/MergeArray.java"
        url = new URL(path);
        java.net.URLConnection uc;
        uc = url.openConnection();

        BufferedReader reader = new BufferedReader(new InputStreamReader(uc.getInputStream()));
        String line = null;
        while ((line = reader.readLine()) != null) {
            strings.add(line);
        }

        return strings;
    }
}
