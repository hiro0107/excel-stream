package com.github.hiro0107.excelstream

import java.io.File
import org.apache.poi.ss.usermodel.WorkbookFactory
import java.io.InputStream
import org.apache.poi.ss.usermodel.Workbook

/** Read excel file as stream
 * 
 * 
 */
object ExcelStream {
  def from(inputFile: File): Stream[Map[String, String]] =
    from(WorkbookFactory.create(inputFile))
    
  def from(inputStream: InputStream): Stream[Map[String, String]] =
    from(WorkbookFactory.create(inputStream)) 
 
  def from(workbook: Workbook): Stream[Map[String, String]] = {
    import org.apache.poi.ss.usermodel._
 
    implicit def cellValue(cell: Cell) = new {
      def toValue(): String = {
        cell.getCellType match {
          case Cell.CELL_TYPE_BLANK   => cell.getStringCellValue
          //        case Cell.CELL_TYPE_BOOLEAN => cell.getBooleanCellValue
          //        case Cell.CELL_TYPE_ERROR => cell.getErrorCellValue
          case Cell.CELL_TYPE_FORMULA => cell.getCellFormula
          case Cell.CELL_TYPE_NUMERIC => cell.getNumericCellValue.toString()
          case Cell.CELL_TYPE_STRING  => cell.getStringCellValue
        }
      }
    }
  
    val sheet = workbook.getSheetAt(0)
    val header = sheet.getRow(0)
    val headerNameIndex =
      (for (i <- Stream.from(0)) yield {
        (i, header.getCell(i))
      }).takeWhile(_._2 != null).map { case (i, cell) => (i, cell.toValue) }
    println(headerNameIndex)
    val rows =
      (for (i <- Stream.from(1)) yield {
        sheet.getRow(i)
      }).takeWhile(row => row != null)
    println(rows)
  
    val mapList =
      (for (row <- rows) yield {
        (for ((i, name) <- headerNameIndex) yield {
          (name, row.getCell(i) match {
            case cell if cell == null => ""
            case cell                 => cell.toValue()
          })
        }).toMap
      })
    mapList
  }
  
}