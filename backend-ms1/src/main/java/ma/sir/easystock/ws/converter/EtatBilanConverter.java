package  ma.sir.easystock.ws.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import ma.sir.easystock.zynerator.util.StringUtil;
import ma.sir.easystock.zynerator.converter.AbstractConverter;
import ma.sir.easystock.zynerator.util.DateUtil;
import ma.sir.easystock.bean.history.EtatBilanHistory;
import ma.sir.easystock.bean.core.EtatBilan;
import ma.sir.easystock.ws.dto.EtatBilanDto;

@Component
public class EtatBilanConverter extends AbstractConverter<EtatBilan, EtatBilanDto, EtatBilanHistory> {


    public  EtatBilanConverter(){
        super(EtatBilan.class, EtatBilanDto.class, EtatBilanHistory.class);
    }

    @Override
    public EtatBilan toItem(EtatBilanDto dto) {
        if (dto == null) {
            return null;
        } else {
        EtatBilan item = new EtatBilan();
            if(StringUtil.isNotEmpty(dto.getId()))
                item.setId(dto.getId());
            if(StringUtil.isNotEmpty(dto.getLibelle()))
                item.setLibelle(dto.getLibelle());
            if(StringUtil.isNotEmpty(dto.getCode()))
                item.setCode(dto.getCode());


        return item;
        }
    }

    @Override
    public EtatBilanDto toDto(EtatBilan item) {
        if (item == null) {
            return null;
        } else {
            EtatBilanDto dto = new EtatBilanDto();
            if(StringUtil.isNotEmpty(item.getId()))
                dto.setId(item.getId());
            if(StringUtil.isNotEmpty(item.getLibelle()))
                dto.setLibelle(item.getLibelle());
            if(StringUtil.isNotEmpty(item.getCode()))
                dto.setCode(item.getCode());


        return dto;
        }
    }


    public void initObject(boolean value) {
    }


}
