package lv.dev.clinked.demo.model.user;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class RoleNameConverter implements AttributeConverter<RoleName, String> {

    @Override
    public String convertToDatabaseColumn(RoleName status) {
        return status.toString();
    }

    @Override
    public RoleName convertToEntityAttribute(String name) {
        return RoleName.valueOf(name);
    }

}