package ma.sir.easystock.service.impl.admin;

import ma.sir.easystock.bean.core.CompteComptable;
import ma.sir.easystock.bean.core.SousClassComptable;
import ma.sir.easystock.bean.history.CompteComptableHistory;
import ma.sir.easystock.dao.criteria.core.CompteComptableCriteria;
import ma.sir.easystock.dao.criteria.history.CompteComptableHistoryCriteria;
import ma.sir.easystock.dao.facade.core.CompteComptableDao;
import ma.sir.easystock.dao.facade.history.CompteComptableHistoryDao;
import ma.sir.easystock.dao.specification.core.CompteComptableSpecification;
import ma.sir.easystock.service.facade.admin.CompteComptableAdminService;
import ma.sir.easystock.zynerator.service.AbstractServiceImpl;
import ma.sir.easystock.zynerator.service.Attribute;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ma.sir.easystock.zynerator.util.VelocityPdf;
import ma.sir.easystock.ws.dto.CompteComptableDto;
import org.springframework.http.HttpEntity;

import org.springframework.beans.factory.annotation.Autowired;


import ma.sir.easystock.service.facade.admin.SousClassComptableAdminService ;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CompteComptableAdminServiceImpl extends AbstractServiceImpl<CompteComptable,CompteComptableHistory, CompteComptableCriteria, CompteComptableHistoryCriteria, CompteComptableDao,
CompteComptableHistoryDao> implements CompteComptableAdminService {
    public static final String TEMPLATE = "template/compteComptable.vm";
    public static final String FILE_NAME = "compteComptable.pdf";



    public static List<CompteComptable> getCompteComptablesComptablesDataFromExcel(InputStream inputStream) {
        List<CompteComptable> compteComptables = new ArrayList<>();
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheetAt(0);
            int rowIndex = 0;
            for (Row row : sheet) {
                if (rowIndex == 0) {
                    rowIndex++;
                    continue;
                }
                Iterator<Cell> cellIterator = row.iterator();
                int cellIndex = 0;
                CompteComptable compteComptable = new CompteComptable();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    switch (cellIndex) {
                        case 0 -> compteComptable.setLibelle(cell.getStringCellValue());
                        case 1 -> compteComptable.setCode(cell.getStringCellValue());
                        case 2 -> {
                            String codeSousClassComptable= cell.getStringCellValue();
                            SousClassComptable sousClassComptable= new SousClassComptable();
                            sousClassComptable.setCode(codeSousClassComptable);
                            compteComptable.setSousClassComptable(sousClassComptable);
                        }

                        default -> {
                        }
                    }
                    cellIndex++;
                }
                compteComptables.add(compteComptable);
            }
        } catch (IOException e) {
            e.getStackTrace();
        }

        return compteComptables;
    }
    @Override
    public void saveToDatabase(MultipartFile file) {
        String codeClassComptable;

        if (isValidExcelFile(file)) {
            try {
                List<CompteComptable> compteComptables = getCompteComptablesComptablesDataFromExcel(file.getInputStream());
                for( CompteComptable compteComptable: compteComptables){
                    SousClassComptable sousClassComptable= compteComptable.getSousClassComptable();
                    sousClassComptable= sousClassComptableService.findOrSave(sousClassComptable);
                    compteComptable.setSousClassComptable(sousClassComptable);
                    this.dao.save(compteComptable);

                }


            } catch (IOException e) {
                throw new IllegalArgumentException("The file is not a valid excel file");
            }
        }
    }
    @Override
    protected List<Attribute> getAttributes() {
        List<Attribute> attributes = new ArrayList<>();
        attributes.add(new Attribute("libelle", "String"));
        attributes.add(new Attribute("code", "String"));
        attributes.add(new Attribute("sousClassComptable.code", "String", SousClassComptable.class));

        return attributes;

    }



    @Override
    public List<CompteComptable> getCompteComptables() {

        return dao.findAll();
    }


    @Override
    public HttpEntity<byte[]> createPdf(CompteComptableDto dto) throws Exception{
        return velocityPdf.createPdf(FILE_NAME, TEMPLATE, dto);
    }
    @Override
    public  CompteComptable findByCode(String code) {
        return dao.findByCode(code);
    }
    @Override
    public CompteComptable findByReferenceEntity(CompteComptable compteComptable) {
        return findByCode(compteComptable.getCode());
    }




    public List<CompteComptable> findBySousClassComptableId(Long id){
        return dao.findBySousClassComptableId(id);
    }
    public int deleteBySousClassComptableId(Long id){
        return dao.deleteBySousClassComptableId(id);
    }




    public void configure() {
        super.configure(CompteComptable.class,CompteComptableHistory.class, CompteComptableHistoryCriteria.class, CompteComptableSpecification.class);
    }

    @Autowired
    private SousClassComptableAdminService sousClassComptableService ;
    @Autowired
    private VelocityPdf velocityPdf;

    public CompteComptableAdminServiceImpl(CompteComptableDao dao, CompteComptableHistoryDao historyDao) {
        super(dao, historyDao);
    }

}