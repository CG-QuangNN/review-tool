package basiclearning.dto;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;

public class GoCellDto {

    private static final DataFormatter DATA_FORMATTER = new DataFormatter();

    private CellAddress cellAddress;

    private XSSFCell xssfCell;

    public GoCellDto(XSSFSheet xssfSheet, String cellAddressStr) {
        this.cellAddress = new CellAddress(cellAddressStr);
        this.xssfCell = xssfSheet
                .getRow(cellAddress.getRow())
                .getCell(cellAddress.getColumn());
    }

    public String getCellValue() {
        return DATA_FORMATTER.formatCellValue(xssfCell).trim();
    }

    public void setCellValue(String value) {
        xssfCell.setCellValue(value);
    }
}
