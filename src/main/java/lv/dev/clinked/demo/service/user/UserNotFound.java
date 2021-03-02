package lv.dev.clinked.demo.service.user;

import lv.dev.clinked.demo.infra.ResourceNotFound;

class UserNotFound extends ResourceNotFound {

    public UserNotFound(String fieldName, Object fieldValue) {
        super("User", fieldName, fieldValue);
    }

}
