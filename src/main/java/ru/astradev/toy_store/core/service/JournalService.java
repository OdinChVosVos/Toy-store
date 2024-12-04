package ru.astradev.toy_store.core.service;


import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.astradev.toy_store.core.mapper.JournalMapper;
import ru.astradev.toy_store.core.model.JournalDto;
import ru.astradev.toy_store.db.repository.JournalRepository;
import ru.astradev.toy_store.db.repository.UsersRepository;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class JournalService {

    @Autowired
    private JournalRepository journalRepository;

    @Autowired
    private JournalMapper journalMapper;

    @Autowired
    private UsersRepository usersRepository;

    public void add(String name, String description){
        if(usersRepository.getByName(name) == null) return;

        Long userId = usersRepository.getByName(name).getId();
        journalRepository.add(userId, new Date(), description);
//        return journalMapper.map(journalRepository.getLast(userId), JournalDto.class);
    }

    public void delete(Long id){
        journalRepository.delete(id);
    }

    public List<JournalDto> getAll(){
        return journalMapper.mapAsList(journalRepository.getAll(), JournalDto.class);
    }

    private void setResponseHeader(HttpServletResponse response, String contentType, String extension, String prefix){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String timestamp = dateFormat.format(new Date());
        String filename = prefix + timestamp + extension;

        response.setContentType(contentType);

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=" + filename;
        response.setHeader(headerKey, headerValue);
    }

    public void load(HttpServletResponse response) throws IOException {
        setResponseHeader(response, "application/octet-stream", ".xlsx", "Journal_");

        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet("Журнал");
        XSSFCellStyle cellStyle = wb.createCellStyle();
        XSSFFont font = wb.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        cellStyle.setFont(font);

        List<JournalDto> journal = getAll();

        for(int i = 0; i < journal.size(); i++){
            XSSFRow row = sheet.createRow(i);

            if (i == 0){
                XSSFCell head0 = row.createCell(0);
                head0.setCellValue("Номер записи");
                head0.setCellStyle(cellStyle);

                XSSFCell head1 = row.createCell(1);
                head1.setCellValue("Пользователь");
                head1.setCellStyle(cellStyle);

                XSSFCell head2 = row.createCell(2);
                head2.setCellValue("Дата/Время");
                head2.setCellStyle(cellStyle);

                XSSFCell head3 = row.createCell(3);
                head3.setCellValue("Описание");
                head3.setCellStyle(cellStyle);

            }
            else {
                for (int col = 0; col < journal.get(i-1).getAll().size(); col++){
                    XSSFCell cell = row.createCell(col);
                    cell.setCellValue(journal.get(i-1).getAll().get(col));
                }
            }
        }
        ServletOutputStream fos = response.getOutputStream();
        wb.write(fos);
        fos.close();
        wb.close();
    }
}