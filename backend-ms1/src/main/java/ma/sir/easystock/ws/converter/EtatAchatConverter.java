package  ma.sir.easystock.ws.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import ma.sir.easystock.zynerator.util.StringUtil;
import ma.sir.easystock.zynerator.converter.AbstractConverter;
import ma.sir.easystock.zynerator.util.DateUtil;
import ma.sir.easystock.bean.history.EtatAchatHistory;
import ma.sir.easystock.bean.core.EtatAchat;
import ma.sir.easystock.ws.dto.EtatAchatDto;

@Component
public class EtatAchatConverter extends AbstractConverter<EtatAchat, EtatAchatDto, EtatAchatHistory> {


    public  EtatAchatConverter(){
        super(EtatAchat.class, EtatAchatDto.class, EtatAchatHistory.class);
    }

    @Override
    public EtatAchat toItem(EtatAchatDto dto) {
        if (dto == null) {
            return null;
        } else {
        EtatAchat item = new EtatAchat();
            if(StringUtil.isNotEmpty(dto.getId()))
                item.setId(dto.getId());
            if(StringUtil.isNotEmpty(dto.getLibelle()))
                item.setLibelle(dto.getLibelle());
            if(StringUtil.isNotEmpty(dto.getCode()))
                item.setCode(dto.getCode());
            if(StringUtil.isNotEmpty(dto.getStyle()))
                item.setStyle(dto.getStyle());


        return item;
        }
    }

    @Override
    public EtatAchatDto toDto(EtatAchat item) {
        if (item == null) {
            return null;
        } else {
            EtatAchatDto dto = new EtatAchatDto();
            if(StringUtil.isNotEmpty(item.getId()))
                dto.setId(item.getId());
            if(StringUtil.isNotEmpty(item.getLibelle()))
                dto.setLibelle(item.getLibelle());
            if(StringUtil.isNotEmpty(item.getCode()))
                dto.setCode(item.getCode());
            if(StringUtil.isNotEmpty(item.getStyle()))
                dto.setStyle(item.getStyle());


        return dto;
        }
    }


    public void initObject(boolean value) {
    }


}
