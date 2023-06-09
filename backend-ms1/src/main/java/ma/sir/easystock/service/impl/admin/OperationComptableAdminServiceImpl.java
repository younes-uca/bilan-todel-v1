package ma.sir.easystock.service.impl.admin;

import ma.sir.easystock.bean.core.*;
import ma.sir.easystock.bean.history.OperationComptableHistory;
import ma.sir.easystock.dao.criteria.core.OperationComptableCriteria;
import ma.sir.easystock.dao.criteria.history.OperationComptableHistoryCriteria;
import ma.sir.easystock.dao.facade.core.OperationComptableDao;
import ma.sir.easystock.dao.facade.history.OperationComptableHistoryDao;
import ma.sir.easystock.dao.specification.core.OperationComptableSpecification;
import ma.sir.easystock.service.facade.admin.OperationComptableAdminService;
import ma.sir.easystock.zynerator.service.AbstractServiceImpl;
import ma.sir.easystock.zynerator.service.Attribute;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ma.sir.easystock.zynerator.util.VelocityPdf;
import ma.sir.easystock.ws.dto.OperationComptableDto;
import org.springframework.http.HttpEntity;

import org.springframework.beans.factory.annotation.Autowired;


import ma.sir.easystock.service.facade.admin.SocieteAdminService ;
import ma.sir.easystock.service.facade.admin.EtatOperationComptableAdminService ;
import ma.sir.easystock.service.facade.admin.TypeOperationComptableAdminService ;
import ma.sir.easystock.service.facade.admin.CompteComptableAdminService ;
import org.springframework.web.multipart.MultipartFile;

@Service
public class OperationComptableAdminServiceImpl extends AbstractServiceImpl<OperationComptable,OperationComptableHistory, OperationComptableCriteria, OperationComptableHistoryCriteria, OperationComptableDao,
OperationComptableHistoryDao> implements OperationComptableAdminService {
    public static final String TEMPLATE = "template/operationComptable.vm";
    public static final String FILE_NAME = "operationComptable.pdf";
public static final List<Attribute> ATTRIBUTES = new ArrayList();
    static{
    ATTRIBUTES.add(new Attribute("id","Long"));
    ATTRIBUTES.add(new Attribute("libelle"));
    ATTRIBUTES.add(new Attribute("annee","Integer"));
    ATTRIBUTES.add(new Attribute("mois","Integer"));
    ATTRIBUTES.add(new Attribute("montant","BigDecimal"));
    }


    @Override
    public HttpEntity<byte[]> createPdf(OperationComptableDto dto) throws Exception{
        return velocityPdf.createPdf(FILE_NAME, TEMPLATE, dto);
    }
    public static List<OperationComptable> getCompteComptablesComptablesDataFromExcel(InputStream inputStream) {
        List<OperationComptable> operationComptables = new ArrayList<>();
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
                OperationComptable operationComptable = new OperationComptable();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();

                    switch (cellIndex) {
                        case 0 -> operationComptable.setLibelle(cell.getStringCellValue());
                        case 1 -> operationComptable.setAnnee((int) cell.getNumericCellValue());
                        case 2 -> operationComptable.setMois((int) cell.getNumericCellValue());
                        case 3 -> operationComptable.setDateOperationComptable( cell.getLocalDateTimeCellValue());
                        case 4 -> operationComptable.setMontant(BigDecimal.valueOf(cell.getNumericCellValue()));
                        case 5-> {
                            String iceSociete = null;
                            if (cell.getCellType() == CellType.NUMERIC) {
                                double numericValue =  cell.getNumericCellValue();
                                long longValue = (long) numericValue;
                                iceSociete = String.valueOf(longValue);
                            } else if(cell.getCellType() == CellType.STRING){
                                iceSociete=cell.getStringCellValue();
                            }
                            Societe societe= new Societe();
                            societe.setIce(iceSociete);
                            operationComptable.setSociete(societe);
                        }
                        case 6 -> {
                            String codeCompteComptable=null;
                            if (cell.getCellType() == CellType.NUMERIC) {
                                double numericValue =  cell.getNumericCellValue();
                                long longValue = (long) numericValue;
                                codeCompteComptable = String.valueOf(longValue);
                            } else if(cell.getCellType() == CellType.STRING){
                                codeCompteComptable=cell.getStringCellValue();
                            }

                            CompteComptable compteComptable= new CompteComptable();
                            compteComptable.setCode(codeCompteComptable);
                            operationComptable.setCompteComptable(compteComptable);
                        }


                        case 7-> {
                            String codeTypeOperationComptable=null;
                            if (cell.getCellType() == CellType.NUMERIC) {
                                double numericValue =  cell.getNumericCellValue();
                                long longValue = (long) numericValue;
                                codeTypeOperationComptable = String.valueOf(longValue);
                            } else if(cell.getCellType() == CellType.STRING){
                                codeTypeOperationComptable=cell.getStringCellValue();
                            }
                            TypeOperationComptable typeOperationComptable= new TypeOperationComptable();
                            typeOperationComptable.setCode(codeTypeOperationComptable);
                            operationComptable.setTypeOperationComptable(typeOperationComptable);
                        }case 8-> {
                            String codeEtatOperationComptable=null;
                            if (cell.getCellType() == CellType.NUMERIC) {
                                double numericValue =  cell.getNumericCellValue();
                                long longValue = (long) numericValue;
                                codeEtatOperationComptable = String.valueOf(longValue);
                            } else if(cell.getCellType() == CellType.STRING){
                                codeEtatOperationComptable=cell.getStringCellValue();
                            }

                            EtatOperationComptable etatOperationComptable= new EtatOperationComptable();
                            etatOperationComptable.setCode(codeEtatOperationComptable);
                            operationComptable.setEtatOperationComptable(etatOperationComptable);
                        }


                        default -> {
                        }
                    }
                    cellIndex++;
                }
                operationComptables.add(operationComptable);
            }
        } catch (IOException e) {
            e.getStackTrace();
        }

        return operationComptables;
    }
    @Override
    public void saveToDatabase(MultipartFile file) {


        if (isValidExcelFile(file)) {
            try {
                List<OperationComptable> operationComptables = getCompteComptablesComptablesDataFromExcel(file.getInputStream());
                for( OperationComptable operationComptable: operationComptables){
                    Societe societe= operationComptable.getSociete();
                    societe= societeService.findOrSave(societe);
                    CompteComptable compteComptable=operationComptable.getCompteComptable();
                    compteComptable=compteComptableService.findOrSave(compteComptable);
                     EtatOperationComptable etatOperationComptable=operationComptable.getEtatOperationComptable();
                    etatOperationComptable=etatOperationComptableService.findOrSave(etatOperationComptable);
                     TypeOperationComptable typeOperationComptable=operationComptable.getTypeOperationComptable();
                    typeOperationComptable=typeOperationComptableService.findOrSave(typeOperationComptable);


                    operationComptable.setSociete(societe);
                    operationComptable.setCompteComptable(compteComptable);
                    operationComptable.setEtatOperationComptable(etatOperationComptable);
                    operationComptable.setTypeOperationComptable(typeOperationComptable);

                    this.dao.save(operationComptable);

                }


            } catch (IOException e) {
                throw new IllegalArgumentException("The file is not a valid excel file");
            }
        }
    }



    @Override
    public Bilan createBilan(Bilan bilan){
        List<OperationComptable> list;
        String[] listCompte= {"immobilisationsIncorporelles", "immobilisationsCorporelles", "immobilisationsFinancieres", "stocks", "creancesClients", "depotEnBanque", "avoirEnCaisse", "capitalPersonnel", "emprunts", "resultat", "dettesFournisseurs", "autresDettesCirculantes"};
        BeanWrapper beanWrapper = new BeanWrapperImpl(bilan);
        for(String compte : listCompte){
          BigDecimal value=BigDecimal.ZERO;
          list=this.findBySocieteIdAndAnneeAndCompteComptableLibelleAndEtatOperationComptableLibelle(bilan.getSociete().getId(), bilan.getAnnee(),compte,"valide");
          if(list!=null){
              for(OperationComptable item : list ){
                  if(item.getTypeOperationComptable().getLibelle()=="credit"){
                      value=value.subtract(item.getMontant());
                  }else value=value.add(item.getMontant());
              }
              beanWrapper.setPropertyValue(compte, value);
          }
        }
        return bilan;
    }


    List<OperationComptable> findBySocieteIdAndAnneeAndCompteComptableLibelleAndEtatOperationComptableLibelle(Long id,int annee,String compteLibelle, String etatLibelle){
        return dao.findBySocieteIdAndAnneeAndCompteComptableLibelleAndEtatOperationComptableLibelle(id,annee,compteLibelle,etatLibelle);
    };

    public List<OperationComptable> findBySocieteId(Long id){
        return dao.findBySocieteId(id);
    }
    public int deleteBySocieteId(Long id){
        return dao.deleteBySocieteId(id);
    }
    public List<OperationComptable> findByCompteComptableId(Long id){
        return dao.findByCompteComptableId(id);
    }
    public int deleteByCompteComptableId(Long id){
        return dao.deleteByCompteComptableId(id);
    }
    public List<OperationComptable> findByTypeOperationComptableId(Long id){
        return dao.findByTypeOperationComptableId(id);
    }
    public int deleteByTypeOperationComptableId(Long id){
        return dao.deleteByTypeOperationComptableId(id);
    }
    public List<OperationComptable> findByEtatOperationComptableId(Long id){
        return dao.findByEtatOperationComptableId(id);
    }
    public int deleteByEtatOperationComptableId(Long id){
        return dao.deleteByEtatOperationComptableId(id);
    }


    @Override
    protected List<Attribute> getAttributes() {
        return ATTRIBUTES;
    }


    public void configure() {
        super.configure(OperationComptable.class,OperationComptableHistory.class, OperationComptableHistoryCriteria.class, OperationComptableSpecification.class);
    }

    @Autowired
    private SocieteAdminService societeService ;
    @Autowired
    private EtatOperationComptableAdminService etatOperationComptableService ;
    @Autowired
    private TypeOperationComptableAdminService typeOperationComptableService ;
    @Autowired
    private CompteComptableAdminService compteComptableService ;
    @Autowired
    private VelocityPdf velocityPdf;

    public OperationComptableAdminServiceImpl(OperationComptableDao dao, OperationComptableHistoryDao historyDao) {
        super(dao, historyDao);
    }

}